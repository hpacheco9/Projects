// Grupo: 4
// Nome: Henrique Pacheco, João Linhares e Miguel Rego
// Número: 2022113664, 2022110926 e 2022108903
// Descrição: Código do processo 2.

#include <signal.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>

int shmid; // Variável para o identificador da memória compartilhada.
pid_t piid; // Variável para o identificador do processo.
key_t key; // Variável para a chave da memória compartilhada.
const int sigid_1 = 30; // Variável para o identificador do sinal.
const int sigid_2 = 9; // Variável para o identificador do sinal.

/*
 * A função notifica recebe como parâmetro o identificador da memória compartilhada.
 * A função notifica o processo que está esperando o resultado.
*/
void notifica(int shmid){
	char* str = (char*)shmat(shmid, (void*)0, 0);
    pid_t pid = getpid();
    sprintf(str, "%d", pid);
}


/*
 * A função read_from_memory recebe como parâmetro o identificador da memória compartilhada.
 * A função lê o resultado da memória compartilhada.
*/
void read_from_memory(int shmid) {
    char* str = (char*)shmat(shmid, (void*)0, 0);
    printf("Data read from memory: %s\n",str);
}

/*
 * A função sig_handler recebe como parâmetro o identificador do sinal.
 * A função verifica se o sinal recebido é o sinal 'sigid_1' ou 'sigid_2'.
 * Caso o sinal recebido seja o 'sigid_1', a função chama a função read_from_memory.
 * Caso o sinal recebido seja o 'sigid_2', a função imprime uma mensagem de despedida.
*/
void sig_handler(int sig) {
    if (sig == sigid_1) {
		read_from_memory(shmid);
	}else if (sig == sigid_2)
	{
		printf("Adeus");
	}
}

/*
 * A função main é a função principal do programa.
 * A função main cria a memória compartilhada, o identificador da memória compartilhada e o ponteiro para a memória compartilhada.
 * A função main chama a função notifica para notificar o processo que está esperando o resultado.
 * A função main aguarda um sinal 'sigid_1' para iniciar a leitura.
 * A função main registra o manipulador de sinais novamente para 'sigid_1'.
 * A função main lê o PID a ser notificado da memória compartilhada.
 * A função main lê o resultado da memória compartilhada.
 * A função main envia um sinal 'sigid_1' para notificar o outro processo.
 * A função main aguarda um sinal 'sigid_2' para finalizar o processo.
 * A função main remove a memória compartilhada.
*/
int main() {
	// Criação da memória compartilhada.
    key = ftok("shmfile", 65);

	// Criação do identificador da memória compartilhada.
    shmid = shmget(key, 1024, 0666 | IPC_CREAT);

	// Criação do ponteiro para a memória compartilhada.
	char* str = (char*)shmat(shmid, (void*)0, 0);
	printf("A espera de resultados ... \n");

	// Notificação do processo.
	notifica(shmid);

	// Aguarda um sinal 'sigid_1' para iniciar a leitura.
	while (!(signal(sigid_1, sig_handler)))
	{
		pause();
	}

	// Registra o manipulador de sinais novamente para 'sigid_1'
	signal(sigid_1, sig_handler);
	sleep(2);

	// Lê o PID a ser notificado da memória compartilhada
	piid = atoi(str);
	printf("PID: %d\n", piid);
    printf("Write Data: ");
    scanf("%s", str);

	// Envia um sinal 'sigid_1' para notificar o outro processo
	kill(piid, sigid_1);
	sleep(1);

	// Loop para esperar o sinal.
	while(!(signal(sigid_2, sig_handler))){
		sleep(1000);
	}

	// Remove a memória compartilhada
    shmctl(shmid, IPC_RMID, NULL);
    return 0;
}



