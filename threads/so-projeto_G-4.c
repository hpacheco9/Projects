#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

#define MAX_MESAS 3
#define MAX_GRUPOS 18
#define TEMPO_OCUPACAO 5
#define TEMPO_SIMULACAO 100
const char* NOME_FICHEIRO = "resultado_simulacao.txt";

/**
 * @brief Estrutura que representa uma mesa no restaurante.
 *
 * A estrutura Mesa possui os seguintes membros:
 * - estado_ocupado: Indica se a mesa está ocupada (1) ou desocupada (0).
 * - tamanho_mesa: Representa o número total de lugares na mesa.
 * - lugares_disp: Indica o número de lugares disponíveis na mesa.
 */
typedef struct 
{
    int estado_ocupado;
    int tamanho_mesa;
    int lugares_disp;
} Mesa;

/**
 * @brief Estrutura que representa um grupo de clientes.
 *
 * A estrutura GrupoCliente possui os seguintes membros:
 * - tempo_espera: Indica o tempo de espera estimado para o grupo.
 * - tamanho_grupo: Representa o número de clientes no grupo.
 */
typedef struct 
{
    int tempo_espera;
    int tamanho_grupo;
} GrupoCliente;

/**
 * @brief Estrutura que representa o estado geral do restaurante.
 *
 * A estrutura Restaurante possui os seguintes membros:
 * - listaMesas: Um array de Mesas representando as mesas disponíveis no restaurante.
 * - fila_espera: Um array de GrupoCliente representando a fila de espera de grupos.
 * - capacidade: Indica a capacidade total do restaurante.
 */
typedef struct 
{
   Mesa listaMesas[MAX_MESAS];
   GrupoCliente fila_espera[MAX_GRUPOS];
   int capacidade;
} Restaurante;

// Criação do restaurante, semaforo e mutex das mesas
Restaurante restaurante;
sem_t semaforo;
pthread_mutex_t mutex_mesas;

/**
 * @brief Função para inicializar a lista de mesas do restaurante.
 *
 * Esta função preenche a lista de mesas do restaurante com mesas criadas aleatoriamente.
 * Cada mesa terá um tamanho definido com base em um índice aleatório, resultando em um
 * tamanho de mesa entre 2 e 6. A capacidade total do restaurante é calculada somando os
 * tamanhos de todas as mesas.
 */
void inicializarMesas() 
{
    int capacidade_res = 0; // Inicia a capacidade total do restaurante
    for (int i = 0; i < MAX_MESAS; ++i) 
    {
        int indice = (rand() % 3); // Cria um índice aleatório
        int tamanho = (indice + 1) * 2; // Determina o tamanho da mesa entre 2 e 6
        Mesa nova_mesa = 
        {
            .estado_ocupado = 0,
            .tamanho_mesa = tamanho,
            .lugares_disp = tamanho
        };
        capacidade_res += nova_mesa.tamanho_mesa; // Atualiza a capacidade total
        restaurante.listaMesas[i] = nova_mesa; // Adiciona a nova mesa à lista
    }
    restaurante.capacidade = capacidade_res; // Atribui a capacidade total ao restaurante
}


/**
 * @brief Função para criar um novo grupo de clientes.
 *
 * Esta função ciar um índice aleatório e utiliza-o para determinar o tamanho do grupo,
 * resultando em um tamanho de grupo entre 2 e 6. O tempo de espera do grupo é definido
 * como um valor aleatório entre 0 e 14.
 *
 * @return GrupoCliente Um novo grupo de clientes com tamanho e tempo de espera definidos.
 */
GrupoCliente criarGrupo() 
{
    int indice = (rand() % 3); // Ciar um índice aleatório
    GrupoCliente novo_grupo = 
    {
        .tamanho_grupo = (indice + 1) * 2, // Determina o tamanho do grupo entre 2 e 6
        .tempo_espera = (rand() % 15) // Define um tempo de espera aleatório entre 0 e 14
    };
    return novo_grupo; // Retorna o novo grupo de clientes
}



/**
 * @brief Função para inicializar a fila de espera com grupos de clientes.
 *
 * Esta função preenche a fila de espera do restaurante com grupos de clientes
 * criados pela função criarGrupo. A quantidade de grupos é determinada pela
 * constante MAX_GRUPOS.
 */
void inicializarClientes() 
{
    for (int i = 0; i < MAX_GRUPOS; i++) 
    {
        GrupoCliente novo_grupo = criarGrupo(); // Cria um novo grupo de clientes
        restaurante.fila_espera[i] = novo_grupo; // Adiciona o grupo à fila de espera
    }
}


/**
 * @brief Função para inicializar as mesas e clientes do restaurante.
 *
 * Esta função chama outras funções de inicialização para configurar
 * as mesas e clientes do restaurante antes de começar a operação.
 */
void inicializarRestaurante() 
{
    inicializarMesas();    // Chamado a função de inicialização das mesas
    inicializarClientes(); // Chamado a função de inicialização dos clientes
}


/**
 * @brief Função para escrever conteúdo em um ficheiro.
 *
 * Esta função abre o ficheiro especificado em modo de anexação ("a"),
 * escreve o conteúdo fornecido no final do ficheiro e fecha o ficheiro.
 *
 * @param conteudo A string contém o conteúdo a ser escrito no ficheiro.
 *                O conteúdo é anexado ao final do ficheiro existente.
 */
void escreverFicheiro(char* conteudo) 
{
    FILE *ficheiro; // Ponteiro para o ficheiro
    ficheiro = fopen(NOME_FICHEIRO, "a"); // Abre o ficheiro em modo de anexo
    fprintf(ficheiro, "%s", conteudo); // Escreve o conteúdo no ficheiro
    fclose(ficheiro); // Feicha o ficheiro após a escrita
}

/**
 * @brief Função thread para gerir grupos de clientes no restaurante.
 *
 * Esta função lida com a colocação e saída de grupos de clientes nas mesas do restaurante.
 * Procura por uma mesa disponível que atenda aos critérios de tamanho do grupo e atribui
 * o grupo à mesa. Após uma duração aleatória de ocupação, o grupo sai da mesa.
 *
 * @param arg Um ponteiro para a estrutura GrupoCliente representando o grupo de clientes.
 * @return void* Esta função retorna um ponteiro para void, e o valor de retorno não é utilizado.
 */
void* thread_clientes(void* arg) 
{
    GrupoCliente novo_grupo = *(GrupoCliente*) arg; // Extrai GrupoCliente do argumento
    sem_wait(&semaforo); // Espera o semáforo
    pthread_mutex_lock(&mutex_mesas); // Bloqueia um mutex para acessar dados partilhados (mesas do restaurante)
    
    // Iteração pelas mesas disponíveis para encontrar a adequada para o grupo
    int indice_mesa = 0;
    for (int j = 0; j < MAX_MESAS; j++) 
    {
        if (restaurante.listaMesas[j].estado_ocupado == 0 &&
            restaurante.listaMesas[j].tamanho_mesa >= novo_grupo.tamanho_grupo) {
            
            // Atribuir o grupo à mesa
            restaurante.listaMesas[j].estado_ocupado = 1;
            restaurante.listaMesas[j].lugares_disp -= novo_grupo.tamanho_grupo;
            char mensagem_entrada[100];
            sprintf(mensagem_entrada, "O grupo de %d clientes foi colocado na mesa %d \n", novo_grupo.tamanho_grupo, j);
            printf("%s", mensagem_entrada);
            escreverFicheiro(mensagem_entrada); 
            
            indice_mesa = j; // Guardar o índice da mesa
            pthread_mutex_unlock(&mutex_mesas); // Desbloqueando o mutex
            
            // Simular a ocupação do grupo por um tempo aleatório
            sleep(1 + rand() % TEMPO_OCUPACAO);
            
            pthread_mutex_lock(&mutex_mesas); // Bloquear o mutex novamente para atualizar informações da mesa
            
            // Libertar a mesa após a saída do grupo
            restaurante.listaMesas[indice_mesa].estado_ocupado = 0; 
            int grupoclientes = restaurante.listaMesas[indice_mesa].tamanho_mesa - restaurante.listaMesas[indice_mesa].lugares_disp; 
            char mensagem_saida[100];
            sprintf(mensagem_saida, "O grupo de %d saiu da mesa %d e do restaurante\n", grupoclientes, indice_mesa);
            printf("%s", mensagem_saida);
            escreverFicheiro(mensagem_saida);
            
            sem_post(&semaforo); // Sinalizar que uma mesa está disponível
            restaurante.listaMesas[indice_mesa].lugares_disp = restaurante.listaMesas[indice_mesa].tamanho_mesa;
            indice_mesa = 0; // Atualizar o índice da mesa
            pthread_mutex_unlock(&mutex_mesas); // Desbloquear o mutex após atualizar informações da mesa
        }
    }
    pthread_mutex_unlock(&mutex_mesas); // Desbloqueio final do mutex
    return NULL; // O valor de retorno não é utilizado neste caso
}

int main(int argc, char const *argv[])
{
    srand(time(NULL)); // Inicializa a semente para geração de números aleatórios
    inicializarRestaurante(); // Inicializa mesas e clientes do restaurante

    // Escreve informações iniciais no arquivo
    char informacao[100];
    sprintf(informacao, "\nNúmero de Grupos: %d | Número de Mesas: %d\n", MAX_GRUPOS, MAX_MESAS);
    escreverFicheiro(informacao);

    int capacidade = MAX_MESAS;
    sem_init(&semaforo, 0, capacidade); // Inicializa o semáforo com capacidade máxima
    pthread_mutex_init(&mutex_mesas, NULL); // Inicializa o mutex para exclusão mútua nas mesas

    pthread_t clientes_threads[MAX_GRUPOS]; // Array de threads para grupos de clientes
    for (int i = 0; i < MAX_GRUPOS; i++) 
    {
        GrupoCliente novo_grupo = restaurante.fila_espera[i];
        pthread_create(&clientes_threads[i], NULL, thread_clientes, &novo_grupo);
    }

    for (int i = 0; i < MAX_GRUPOS; i++) 
    {
        pthread_join(clientes_threads[i], NULL); // Espera a conclusão das threads de clientes
    }

    sem_destroy(&semaforo); // Destroi o semáforo após a conclusão das threads
    pthread_mutex_destroy(&mutex_mesas); // Destroi o mutex após a conclusão das threads

    return 0; // Retorna o código de saída do programa
}