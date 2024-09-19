// Grupo: 4
// Nome: Henrique Pacheco, João Linhares e Miguel Rego
// Número: 2022113664, 2022110926 e 2022108903
// Descrição: Código do processo 1.

#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <stdlib.h>


int shmid; 									// Variável para o identificador da memória compartilhada.
key_t key; 									// Variável para a chave da memória compartilhada.
const int sigid_1 = 30; 					// Variável para o identificador do sinal.
const int sigid_2 = 9; 						// Variável para o identificador do sinal.

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
void read_from_memory(int shmid){
	char *str = (char*) shmat(shmid,(void*)0,0);
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
	}
}

/*
 * A função main é a função principal do programa.
 * A função main cria a memória compartilhada, o identificador da memória compartilhada e o ponteiro para a memória compartilhada.
 * A função main chama a função notifica para notificar o processo que está esperando o resultado.
 * A função main aguarda um sinal 'sigid_1' para iniciar a leitura.
 * A função main registra o manipulador de sinais novamente para 'sigid_1'.
*/
int main()
{
	// Criação da memória compartilhada.
	key = ftok("shmfile",65);

	// Criação do identificador da memória compartilhada.
	shmid = shmget(key,1024,0666|IPC_CREAT);

	// Criação do ponteiro para a memória compartilhada.
	char *str = (char*) shmat(shmid,(void*)0,0);

	// Chama a função notifica para notificar o processo que está esperando o resultado.
	pid_t pid = atoi(str);
	printf("PID: %d\n", pid);
    printf("Write Data: ");
    scanf("%s", str);
	printf("A espera de mensagem ... \n");

	// Envia um sinal 'sigid_1' para notificar o outro processo.
	kill(pid, sigid_1);
	sleep(1);

	// Notificação do processo.
	notifica(shmid);

	// Aguarda um sinal 'sigid_1' para iniciar a leitura.
    while (!(signal(sigid_1,sig_handler)))
	{
		pause();
	}

	// Registra o manipulador de sinais novamente para 'sigid_1'
	signal(sigid_1 , sig_handler);
	sleep(1);

	// Aguarda um sinal 'sigid_2' para finalizar o processo.
	kill(pid, sigid_2);

	// Remove a memória compartilhada.
	shmctl(shmid,IPC_RMID,NULL);	
	return 0;
}


