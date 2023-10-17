#!/bin/bash

func_listar() {

    if [ "$1" == "listar" ]; then                       # Verifica se o primeiro argumento da função é igual a "listar"                        # Caso se verifique então dá echo na mesma linha a "Ficheiros"
        ls                                              # Mostra o dirétorio atual
    else
        if [ -d ~/"$1" ]; then                          # Verifica se existe o diretório introduzido pelo utilizador             
           ls ~/"$1"                                   # Caso se verifique que exista o diretorio, então mostra o conteudo nesse diretório
        else
            echo "Diretório não encontrado: $1"         # Caso não encontre, então mostra ao utilizador 'Diretório nao encontrado'
        fi
    fi

}

func_ler() {

    if [ -f "$1" ]; then                        # Verifica se existe o ficheiro insirido pelo utilizador
        cat "$1"                                # Caso exista, mostra o conteúdo do ficheiro
    else
        echo "Ficheiro não encontrado!"         # Caso não exista, mostra ao utlizador 'ficheiro não encontrado'
    fi
}

func_data() {
    echo -n "Data: "
    date                                        # Mostra a data do dia 
}

func_quem() {
    echo -n "Utilizador: "
    whoami                                      # Mostra o utilizador logado
}

func_processos() {
    ps                                          # Mostra os processos em execução
}

func_limpar() {
    clear                                       # Limpa o terminal
}

func_tempo() {
    echo -n "Tempo de atividade: "              
    uptime                                      # Mostra o uptime 
}


func_redirecionar() {
    if [ -d ~/"$1" ]; then                      # verifica se o diretorio inserido pelo utilizador existe
        func_listar "$1" >> "$2"                # caso exista então chama a função listar como o diretorio escolhido pelo utilizador e passa para um ficheiro escolhido pelo utilizador tambem
        echo "Redirecionamento concluido!"      # Mostra ao utilizador que o redirecionamento foi concluido
    else
        echo "Não existe esse diretorio"        # Caso nao exista o diretorio introduzido pelo utilizador é mostrado uma mensagem de que o diretorio nao existe
    fi
    
}

func_encontrar() {

    if [ "$(find ~/ -name "$1")" ]; then        # Verifica se existe caminho
        find ~/ -name "$1"                      # Mostra o caminho caso encontre o caminho
    else   
        echo "Ficheiro inexistente!"            # Caso contrário mostra 'Ficheiro inexistente' caso não o encontre
    fi
}

comandos=("listar" "ler" "data" "quem" "processos" "limpar" "tempo" "encontrar" "redirecionar" "pipe" "exit") # Array com todos os comandos existentes do programa

func_help () {

    case "$1" in
        "help")
            echo "** Todos os comandos abaixo devem ser inseridos em lowercase **"
            echo 
            for str in ${comandos[@]}; do
                func_help $str                    # Caso o utilizador insira 'help' corre pelos comandos existentes e dá sugestões dos comandos
                echo                              # Muda de linha
            done
            ;;
        "listar")
            echo "Listar         - Lista os Ficheiros presentes no Diretório corrente"
            echo
            echo "Listar 'path'  - Lista os Ficheiros'presentes num certo Diretório que nao seja o corrente"
            ;;
        "ler")
            echo "Ler  'ficheiro' - Permite visulizar o conteudo presente num determinado ficheiro"
            ;;
        "data")
            echo "Data            - Permite a visualização data do dia e as horas"
            ;;
        "quem")
            echo "Quem            - Permite visualizar qual o Utilizador se ecnontra logado na corrente sessão"
            ;;
        "processos")
            echo "Processos       - Permite visualizar os processos decorrentes no sistema"
            ;;
        "limpar")
            echo "Limpar          - Permite ao Utilizador limpar o terminal"
            ;;
        "tempo")
            echo "Tempo           - Permite ao Utilizador visualizar o tempo atual de sessão no sistema"
            ;;
        "encontrar")
            echo "Encontrar 'ficheiro' - Permite ao Utilizador localizar o caminho onde se ecnontra o ficheiro pretendido"
            ;;
        "redirecionar")
            echo "Redirecionar 'diretorio' 'ficheiro' - Permite ao Utilizador passar um output para dentro de um ficheiro"
            ;;
        "pipe")
            echo "Pipe         ' comando ' - Permite ao uitlizador consultar as especificações do computador"
            ;;
        "exit")
            echo "Exit             - Permite ao Utilizador sair do terminal"
            ;;
        *)
            echo "Não existe o comando em questão!"
    esac
}

func_pipe() {

    case "$1" in 
        "arquitetura")
            lscpu | grep Architecture                     # Mostra ao utilizador a arquitetura do computador
            ;;
        "modelo")
            lscpu | grep name                             # Mostra o modelo do computador      
            ;;
        "cores")
            lscpu | grep Core                             # Mostra a quantidade de cores do cpu 
            ;;
        "threads")
            lscpu | grep Thread                           # Mostra a quantidade de Threads do cpu
            ;;
        "cache")
            lscpu | grep L1i                              # Mostra cache L1i, L2 e L3
            lscpu | grep L2
            lscpu | grep L3
            ;;
        *)
            echo "Comando inválido!"                      # Caso de introdução de comando que nao existe mostra ao utilizador "Comando Inválido"
    esac
}

func_main() {
    clear                                                # Ao inicializar o programa limpa o terminal do Linux 
    flag=1
    while [ $flag -eq 1 ]; do                            # Inicializa o clico 

        echo ""
        echo -n $(whoami)"@"$(hostname) ">> "                 
        read COMANDO                                     # Lê o input inserido pelo utilizador
        echo "$COMANDO" >> historico.txt                 # Guarda comandos no histórico
        echo ""

        case "$COMANDO" in
            "listar"*)
                ARGUMENTOS="${COMANDO#listar }"          # Remove argumento Listar do input  
                func_listar "$ARGUMENTOS"               # Executa a função: func_listar com "argumentos" como argumento da função
                ;;
            "ler"*)
                NOME_FICHEIRO="${COMANDO#ler }"          # Remove a palavra 'ler' do argumento 
                func_ler $NOME_FICHEIRO                  # Execcuta a função: func_ler com 'NOME_FICHEIRO' como argumento da função
                ;;
            "data")
                func_data                                # Executa a função: func_data
                ;;
            "quem")
                func_quem                                # Executa a função: func_quem
                ;;
            "processos")
                func_processos                           # Execcuta a função: func_processos
                ;; 
            "limpar")
                func_limpar                              # Execcuta a função: func_limpar
                ;;
            "tempo")
                func_tempo                               # Execccuta a função: func_tempo
                ;;
            "encontrar"*)
                NOME_FICHEIRO="${COMANDO#encontrar }"    # Retira a palavra 'encontrar' do argumento
                func_encontrar "$NOME_FICHEIRO"          # Executa a função: func_encontrar com 'Nome_Ficheiro' como argumento da função
                ;;
            "redirecionar "*)
                ARGUMENTOS="${COMANDO#redirecionar }"    # Retira a 1ª palavra
                CONTEUDO="${ARGUMENTOS%% *}"             # Retira do Inicio
                ARGUMENTOS="${ARGUMENTOS#* }"            # Retira do Fim
                NOME_DO_FICHEIRO="$ARGUMENTOS" 
                func_redirecionar $CONTEUDO "$NOME_DO_FICHEIRO"  # Executa a func_redirecionar como "conteudo" de argumento
                ;;
            "pipe "*)  
                ARGUMENTOS="${COMANDO#pipe }"          # Remove a palavra 'pipe ' do argumento
                func_pipe "$ARGUMENTOS"
                ;;
            "help"*)
                ARGUMENTOS="${COMANDO#help }"            # Retira o argumento 'help' do input  
                func_help "$ARGUMENTOS"                  # Execcuta a função_help com o argumento lido pelo utilizador
                ;; 
            "exit")
                echo "Adeus!"
                flag=0                                   # Caso Utilizador insira 'exit' flag passa para 0 e sai do 'While' 
                ;;
            *)
                echo "Comando Inválido!"                 # Caso Utilizador insira um comando que não exista a cima, então dá output de 'comando Inválido'
        esac
    done
}


func_main