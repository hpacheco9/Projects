; (Objetivos)
; Programa que cria digitos, baseando-se no input do utilizador
; Inputs: tipo de caracter, numero de caracters, cores 
; Output: O algarismo desenhado com estes inputs de base

; Grupo 6
; Miguel Rego: 2022108903
; João Linhares: 2022110926
; Manuel Estrela: 2022113477
; Henrique Pacheco: 2022113604


global _start                       ; apontador (etiqueta) p/a 1ª instrução

section .data                       ; variáveis e constantes
   EXIT_SUCCESS:  equ 0             ; código de saída sem erros
   SYS_exit:      equ 60            ; código da operação de saída (fim) do programa
   SYS_write:     equ 0x01          ; função escrever (print) 
   SYS_read:      equ 0x00          ; função ler (input)
   StdIn:         equ 0x00          ; input standard (teclado) 
   StdOut:        equ 0x01          ; output standard (ecrã)
   LF:            equ 0x0A          ; caracter (ASCII) de controlo Line Feed
   CR:            equ 0x0D          ; caracter (ASCII) de controlo Carriage Return
   EoS:           equ 0x00          ; caracter (ASCII) de controlo NULL (fim de string)
   newLine:       db CR,LF,EoS      ; string de controlo: mover o cursor para o início da linha abaixo
   compNL:        equ $-newLine     ; comprimento da string newLine

; Definir as variáveis numéricas e strings necessárias
   
   ESC:           equ 0x1B

   cor:           db ESC,"[39;49m","#",EoS
   corLen:        equ $-cor

   def:           db ESC,"[39;49m"," ",EoS
   defLen:        equ $-def 

   ; Lado
   lado:          db 5,   EoS
   compL:         equ 3

   ; Cor do Caracter
   corCaracter:   db 0,   EoS
   compCC:        equ 3  

   ; Cor do Fundo
   corFundo:      db 0,   EoS
   compCF:        equ 3  

   ; Caracter
   caracter:      db "#", EoS 
   compC:         equ 3

   ; Algarismo
   algarismo:     db 1,   EoS
   compAlg:       equ 3

   opcao:         db "?", EoS

   ; lerLado
   lerLado:         db "Lado <3-9> > ", EoS
   compLado:        equ $-lerLado

   ; lerCaracter
   lerCaracter:     db "Caracter de contorno  > ", EoS
   compCaracter:    equ $-lerCaracter

   ; lerCorCaracter
   lerCorCaracter:  db "Cor do caracter <0, 9> > ", EoS
   compCorCaracter: equ $-lerCorCaracter

   ; lerAlgarismo    
   lerAlgarismo:    db "Algarismo <0, 9> > ", EoS
   compAlgarismo:   equ $-lerAlgarismo

   ; lerCorFundo
   lerCorFundo:     db "Cor do fundo <0, 9> > ", EoS
   compCorFundo:    equ $-lerCorFundo

   ; menu
   lerMenu:          db "**************************", CR, LF
                     db "*                        *", CR, LF
                     db "* 1. Configurar          *", CR, LF
                     db "* 2. Desenhar Algarismo  *", CR, LF
                     db "* 3. Desenhar Algarismos *", CR, LF
                     db "* 0. Terminar            *", CR, LF
                     db "*                        *", CR, LF
                     db "**************************", CR, LF, EoS
   compMenu:         equ $-lerMenu

   ; lerOpcao
   lerOpcao:         db "Opção > ", EoS
   compOpcao:     equ $-lerOpcao

   ; opcaoErrada
   opcaoErrada:      db "Opção Errada", EoS, LF
   compErrada:       equ $-opcaoErrada

   ; lerTerminar
   lerTerminar:      db "Adeus!", EoS , LF
   compTerminar:     equ $-lerTerminar

section .text          ; código (instruções)
_start:
   cmp byte [opcao], 0
   je saida
   
   call novaLinha

   ; escrever a string menu
   mov rsi, lerMenu                 ; rsi <- endereço da string a escrever
   mov rdx, compMenu                ; rdx <- nº de caracteres a escrever (até EoS)
   call escrever                    ; chamada da função escrever

   call novaLinha

   ; escrever a string opcEsc
   mov rsi, lerOpcao                ; rsi <- endereço da string a escrever
   mov rdx, compOpcao               ; rdx <- nº de caracteres a escrever (até EoS)
   call escrever                    ; chamada da função escrever

   ; ler caracteres para a string escolha (<enter> incluído)
   mov rsi, opcao                   ; rsi <- endereço da string a ler
   mov rdx, 2                       ; rdx <- nº máximo de caracteres para ler
   call ler                         ; chamada da função ler

   cmp byte [opcao], "1"            ; Compara a opção introduzida com o valor 1
   je opcao1                        ; Caso a opção introduzida seja igual a 1 faz jump para a função opcao1

   cmp byte [opcao], "2"            ; Compara a opção introduzida com o valor 2
   je opcao2                        ; Caso a opção introduzida seja igual a 1 faz jump para a função opcao2

   cmp byte [opcao], "3"            ; Compara a opção introduzida com o valor 3
   je opcao3                        ; Caso a opção introduzida seja igual a 1 faz jump para a função opcao3

   cmp byte [opcao], "0"            ; Compara a opção introduzida com o valor 0
   je opcao0                        ; Caso a opção introduzida seja igual a 1 faz jump para a função opcao0
   jmp opcaoE                       ; Caso a opção introduzida não seja igual a 1,2,3 faz jump para a função opcaoE

   ; Configuração do caracter, cor do caracter, cor de fundo e comprimento
   opcao1:
      call funcEscolha1             ; Chamada da função funcEscolha1
      jmp _start                    ; Jump para o menu inicial

   opcao2:
      call funcEscolha2             ; Chamada da função funcEscolha2
      jmp _start                    ; Jump para o menu inicial

   opcao3:
      call desenharAlgarismos       ; Chamada da função desenharAlgarismos
      jmp _start                    ; Jump para o menu inicial

   opcaoE:
      call funcEscolhaE             ; Chamada da função funcEscolhaE
      jmp _start                    ; Jump para o menu inicial

   opcao0:
      call funcEscolha0             ; Chamada da função funcEscolha0
      
   saida:
      ; terminar o programa 
      mov rax, SYS_exit             ; operação sair - devolver o controlo ao SO
      mov rdi, EXIT_SUCCESS         ; sair com sucesso (sem erros)
      syscall                       ; chamar (executar) a função do SO

escrever: 
   ; parametros passados nos registos rsi (variavel) e rdx (comprimento)
   push rax                         ; conteudo do registo rax guardado na stack 
   push rdi                         ; conteudo do registo rdi guardado na stack 
   mov rax, SYS_write               ; função escrever 
   mov rdi, StdOut                  ; output standard (ecrã)
   syscall                          ; chamada função do SO (system call)
   pop rdi                          ; Conteudo de registo rdi retirado da stack
   pop rax                          ; Conteudo de registo rax retirado da stack
   ret                              ; Retoma a execução na instrução seguinte ao call

ler:
  ; parametros passados nos registos rsi (variavel) e rdx (comprimento)
   push rax                         ; conteudo do registo rax guardado na stack 
   push rdi                         ; conteudo do registo rdi guardado na stack 
   mov rax, SYS_read                ; função ler
   mov rdi, StdIn                   ; input standard (ecrã)
   syscall                          ; chamada função do SO (system call)
   pop rdi                          ; Conteudo de registo rdi retirado da stack
   pop rax                          ; Conteudo de registo rax retirado da stack
   ret                              ; Retoma a execução na instrução seguinte ao call

funcEscolha1:
   ; Função para a configuração
   ; New Line
   call novaLinha                      ; Chamada da função de escrita de uma nova linha
   mov cl, 0                           ; Registo que dá inicio ao ciclo
   cicloProtecaoCar:                   
      cmp cl, 1                        ; Caso cl for igual a 1
      je terminarcicloCar              ; Termina o ciclo

      ; Caracter
      mov rsi, lerCaracter             ; rsi <- endereço da string a ler 
      mov rdx, compCaracter            ; rdx <- nº maximo de caracteres para ler
      call escrever                    ; chamada da função escrever

      mov rsi, caracter                ; rsi <- endereço da string a escrever
      mov rdx, compC                   ; rdx <- nº máximo de caracteres para escrever
      call ler                         ; chamada da função ler

      cmp byte[caracter], LF           ; Compara o caracter introduzido com o ENTER
      je cicloProtecaoCar              ; Caso seja igual volta a pedir um novo caracter
      jmp terminarcicloCar             ; Caso contrário termina o ciclo
      terminarcicloCar:

      ; Alteração da variável cor na sua posição 8 pelo caracter introduzido
      mov bh,  byte [caracter]
      mov byte [cor+8], bh
      
   mov ah, 0
   cicloProtecaoCorCar:
      cmp ah, 1
      jae terminacicloCorCar
      ; Cor do Caracter
      mov rsi, lerCorCaracter          ; rsi <- endereço da string a ler 
      mov rdx, compCorCaracter         ; rdx <- nº maximo de caracteres para ler
      call escrever                    ; chamada da função escrever

      mov rsi, corCaracter             ; rsi <- endereço da string a escrever
      mov rdx, compCC                  ; rdx <- nº máximo de caracteres para escrever
      call ler                         ; chamada da função ler
      
      mov al, byte [corCaracter]       ; Atribuição do valor da cor do Caracter ao registo al de modo a não ser perdido o valor
      sub al, 48                       ; Obtenção do valor decimal da cor do Caracter para ser alvo de comparação

      cmp al, 9                        ; Caso seja superior a 9
      ja cicloProtecaoCorCar           ; Volta a pedir para introduzir um novo valor
      cmp al, 0                        ; Caso seja menor que 0
      jb cicloProtecaoCorCar           ; Volta a pedir para introduzir um novo valor
      jmp terminacicloCorCar           ; Caso pertenca ao intervalo 0-9 termina o ciclo

   terminacicloCorCar:

   ; Alteração da variável cor na sua posição 3 pelo valor da cor do caracter introduzido
   mov bh,  byte [corCaracter]   
   mov byte [cor+3], bh

   mov ah, 0                           ; Registo que dá inicio ao ciclo
   cicloProtecaoCorFundo:  
      cmp ah, 1                        ; Caso seja igual a 1
      je terminacicloCorFundo          ; Termina o ciclo
      ; Cor do fundo
      mov rsi, lerCorFundo             ; rsi <- endereço da string a ler 
      mov rdx, compCorFundo            ; rdx <- nº maximo de caracteres para ler
      call escrever                    ; chamada da função escrever

      mov rsi, corFundo                ; rsi <- endereço da string a escrever
      mov rdx, compCF                  ; rdx <- nº máximo de caracteres para escrever
      call ler                         ; chamada da função ler

      mov al, byte [corFundo]          ; Atribuição do valor da cor do Fundo ao registo al de modo a não ser perdido o valor
      sub al, 48                       ; Obtenção do valor decimal da cor do Fundo para ser alvo de comparação
      cmp al, 9                        ; Caso seja superior a 9
      ja cicloProtecaoCorFundo         ; Volta a pedir para introduzir um novo valor
      cmp al, 0                        ; Caso seja inferior a 9
      jb cicloProtecaoCorFundo         ; Volta a pedir para introduzir um novo valor
      jmp terminacicloCorFundo         ; Caso pertenca ao intervalo 0-9 termina o ciclo
      terminacicloCorFundo:
 
   ; Alteração da variável cor na sua posição 6 pelo valor cor do fundo do caracter introduzido
   mov bh,  byte [corFundo]
   mov byte [cor+6], bh

   mov ah, 0                           ; Registo que dá inicio ao ciclo
   cicloProtecaoLado:   
      cmp ah, 1                        ; Caso seja igual a 1
      je terminacicloLado              ; Termina o ciclo
      ; Comprimento
      mov rsi, lerLado                 ; rsi <- endereço da string a ler 
      mov rdx, compLado                ; rdx <- nº maximo de caracteres para ler
      call escrever                    ; chamada da função escrever

      mov rsi, lado                    ; rsi <- endereço da string a escrever
      mov rdx, compL                   ; rdx <- nº máximo de caracteres para escrever
      call ler                         ; chamada da função ler

      ; Obtenção do valor decimal do comprimento.
      sub byte[lado], 48

      cmp byte [lado], 9               ; Caso seja superior a 9
      ja cicloProtecaoLado             ; Volta a pedir para introduzir um novo valor
      cmp byte [lado], 3               ; Caso seja inferior a 3
      jb cicloProtecaoLado             ; Volta a pedir para introduzir um novo valor
      jmp terminacicloLado             ; Caso pertenca ao intervalo 3-9 termina o ciclo
   terminacicloLado:
   ; New Line                          ; Chamada da função de escrita de uma nova linha
   call novaLinha                      ; Chamada da função 
   ret                                 ; Retoma a execução na instrução seguinte ao call

funcEscolha2:
   ; Função para a escrita do Algarismo
   mov ah, 0                           ; Registo que dá inicio ao ciclo
   cicloProtecaoAlg:    
   cmp ah, 1                           ; Caso seja igual a 1
   je terminacicloalg                  ; Termina o ciclo
      ; New Line                       
      call novaLinha                   ; Chamada da função de escrita de uma nova linha

      ; Algarismo
      mov rsi, lerAlgarismo            ; rsi <- endereço da string a ler 
      mov rdx, compAlgarismo           ; rdx <- nº maximo de caracteres para ler
      call escrever                    ; chamada da função escrever
      
      mov rsi, algarismo               ; rsi <- endereço da string a escrever
      mov rdx, compAlg                 ; rdx <- nº máximo de caracteres para escrever
      call ler                         ; chamada da função ler

      ; Obtenção do valor decimal do comprimento
      sub byte[algarismo], 48

      cmp byte [algarismo], 9          ; Caso seja superior a 9 
      ja cicloProtecaoAlg              ; Volta a pedir para introduzir um novo valor
      cmp byte [algarismo], 0          ; Caso seja inferior a 0 
      jb cicloProtecaoAlg              ; Volta a pedir para introduzir um novo valor
      jmp terminacicloalg              ; Caso pertenca ao intervalo 0-9 termina o ciclo
   terminacicloalg:
   ; New Line
   call novaLinha                      ; Chamada da função de escrita de uma nova linha

   ; Se o algarismo introduzido for 0
   cmp byte[algarismo], 0           
   je escrever0                     ; Jump para a função escrever0 se o algarismo introduzido for 0

   ; Se o algarismo introduzido for 1
   cmp byte[algarismo], 1
   je escrever1                     ; Jump para a função escrever1 se o algarismo introduzido for 1

   ; Se o algarismo introduzido for 2
   cmp byte[algarismo], 2
   je escrever2                     ; Jump para a função escrever2 se o algarismo introduzido for 2

   ; Se o algarismo introduzido for 3
   cmp byte[algarismo], 3          
   je escrever3                     ; Jump para a função escrever3 se o algarismo introduzido for 3

   ; Se o algarismo introduzido for 4
   cmp byte[algarismo], 4
   je escrever4                     ; Jump para a função escrever4 se o algarismo introduzido for 4
   
   ; Se o algarismo introduzido for 5
   cmp byte[algarismo], 5
   je escrever5                     ; Jump para a função escrever5 se o algarismo introduzido for 5
   
   ; Se o algarismo introduzido for 6
   cmp byte[algarismo], 6
   je escrever6                     ; Jump para a função escrever6 se o algarismo introduzido for 6
   
   ; Se o algarismo introduzido for 7
   cmp byte[algarismo], 7
   je escrever7                     ; Jump para a função escrever7 se o algarismo introduzido for 7
   
   ; Se o algarismo introduzido for 8
   cmp byte[algarismo], 8
   je escrever8                     ; Jump para a função escrever8 se o algarismo introduzido for 8
   
   ; Se o algarismo introduzido for 9
   cmp byte[algarismo], 9
   je escrever9                     ; Jump para a função escrever9 se o algarismo introduzido for 9
   ret                              ; Retoma a execução na instrução seguinte ao call
   
funcEscolhaE:
   ; Função escolha errada
   ; New Line
   call novaLinha                   ; Chamada da função de escrita de uma nova linha

   ; Opção Errada
   mov rsi, opcaoErrada             ; rsi <- endereço da string a ler 
   mov rdx, compErrada              ; rdx <- nº maximo de caracteres para ler
   call escrever                    ; chamada da função escrever
   ret                              ; Retoma a execução na instrução seguinte ao call

funcEscolha0:
   ; Função saida do programa
   ; New Line
   call novaLinha                   ; Chamada da função de escrita de uma nova linha

   ; lerTerminar
   mov rsi, lerTerminar             ; rsi <- endereço da string a escrever
   mov rdx, compTerminar            ; rdx <- nº de caracteres a escrever (até EoS)
   call escrever                    ; chamada da função escrever

   ; New Line
   call novaLinha                   ; Chamada da função de escrita de uma nova linha
   ret                              ; Retoma a execução na instrução seguinte ao call

horizontal:
   ; Função que irá ser utilizada para a escrita do caracter na horizontal
   ; Variáveis utilizadas: cor alterada, cor default, lado

   mov ah, 0                        ; registo que dá inicio ao ciclo
   repeteHorizontal:                ; Ciclo inicio
      cmp ah, byte[lado]            ; compara o valor do lado com o registo ah
      jae fimcicloHorizontal        ; Caso seja maior ou igual termina o ciclo
         ; Cor alterada
         call corAlterada           ; Chamada da função que escreve a cor alterada
         inc   ah                   ; incrementa registro ah
         jmp repeteHorizontal       ; Jump para o inicio do equanto o registo ah é inferior ao lado

      fimcicloHorizontal:           ; Fim do ciclo
          ; Cor Default
         call corDefault            ; Chamada da função que escreve a cor default
         call novaLinha             ; Chamada da função de escrita de uma nova linha
   ret                              ; Retoma a execução na instrução seguinte ao call

verticalDireita:
   ; Função que irá ser utilizada para a escrita do caracter na vertical, lado direito
   ; Variáveis utilizadas: cor alterada, cor default, lado

   mov cl, byte [lado]              ; Altera o valor do registo cl pelo valor do lado
   sub cl, 1                        ; Subtrai 1 ao registo
   mov bl, 0                        ; registo que dá inicio ao ciclo

   repeteDireita:                   ; ciclo inicial que repete as linhas
   cmp bl, cl                       ; compara o valor do lado - 1 com o registo bl
   jae terminarcicloDireita         ; Caso seja maior ou igual termina o ciclo da repetição de linhas

      mov al, 0                     ; registo que dá inicio ao 2º ciclo
      repeteDireitaLinha:           ; ciclo secundário que escreve as linhas
         cmp al, cl                 ; compara o valor do lado - 1 com o registo al
         jae fimcicloDireitaLinha   ; Caso seja maior ou igual termina o ciclo da escrita das linhas

            ; Cor default
            call corDefault         ; Chamada da função que escreve a cor default
            inc   al                ; incrementação do o registo al
            jmp repeteDireitaLinha  ; jump para o ciclo da escrita das linhas enquanto o lado for menor que o registo
            
         fimcicloDireitaLinha:      ; fim do ciclo da escrita das linhas
            ; Cor alterada
            call corAlterada        ; Chamada da função que escreve a cor alterada
            ; Cor default
            call corDefault         ; Chamada da função que escreve a cor default
            ; New Line  
            call novaLinha          ; Chamada da função que escreve uma nova linha

   inc bl                           ; incrementação do registo bl
   jmp repeteDireita                ; jump para o ciclo novamente
   terminarcicloDireita:            ; fim do ciclo que repete as linhas
   ret                              ; Retoma a execução na instrução seguinte ao call

verticalEsquerda:
   ; Função que irá ser utilizada para a escrita do caracter na vertical, lado esquerdo.
   ; Variáveis utilizadas: cor alterada, cor default, lado

   mov cl, byte [lado]              ; Altera o valor do registo cl pelo valor do lado
   sub cl, 1                        ; Subtrai 1 ao registo
   mov bl, 0                        ; registo que dá inicio ao ciclo

   repeteEsquerda:                  ; Ciclo inicial
   cmp bl, cl                       ; compara o valor do lado - 1 com o registo bl
   jae fimcicloVerticalAmbosEsquerda             ; Caso seja maior ou igual termina o ciclo

      ; Cor alterada
      call corAlterada              ; Chamada da função que escreve a cor alterada
      ; Cor default
      call corDefault               ; Chamada da função que escreve a cor default
      ; New Line                    
      call novaLinha                ; Chamda da função que escreve a nova linha
      inc bl                        ; incrementação do registo bl
   jmp repeteEsquerda               ; Jump para o ciclo inicial enquanto o valor do lado - 1 for menor que o registo bl
   fimcicloVerticalAmbosEsquerda:                ; fim do ciclo inicial
   ret                              ; Retoma a execução na instrução seguinte ao call

verticalAmbos:
   ; Função que irá ser utilizada para a escrita do caracter na vertical, lado direito e no lado esquerdo.
   ; Variáveis utilizadas: cor alterada, cor default, lado

   mov cl, byte [lado]              ; Altera o valor do registo cl pelo valor do lado
   sub cl, 2                        ; Subtrai 2 ao registo
   mov bl, 0                        ; registo que dá inicio ao ciclo inicial

   repeteVerticalAmbos:             ; Ciclo inicial
   cmp bl, cl                       ; compara o valor do lado - 2 com o valor do registo bl
   jae fimcicloVerticalAmbos        ; Caso seja maior ou igual termina o ciclo

      ; Cor alterada  ex: [#      ]          
      mov rsi, cor                  ; rsi <- endereço da string a escrever
      mov rdx, corLen               ; rdx <- nº de caracteres a escrever (até EoS)
      push rcx                      ; guarda rcx na stack
      call escrever                 ; chamada da função escrever
      pop rcx                       ; remove rcx da stack

      mov al, 0                     ; Registo que dá inicio ao 2º ciclo
      vaEspacos:                    ; ciclo onde serão escritos os espaços
         cmp al, cl                 ; compara o valor do lado - 2 com o valor do registo al
         jae fimcicloEspacos        ; Caso seja maior ou igual que o registo al termina o 2º ciclo
            ; Cor Default
            call corDefault         ; Chamada da função que escreve a cor default
            inc   al                ; incrementação do registo al
            jmp   vaEspacos         ; Jump para o 2º ciclo enquanto o valor do lado - 2 for menor que o registo al 
         fimcicloEspacos:           ; fim do ciclo onde são escritos os espaços
            ; Cor alterada
            call corAlterada        ; Chamada da função que escreve a cor alterada
            ; Cor Default
            call corDefault         ; Chamada da função que escreve a cor default
            ; New Line
            call novaLinha          ; Chamada da função que escreve uma nova linha

   inc bl                           ; incrementação do registo bl
   jmp repeteVerticalAmbos          ; Jump para o ciclo inicial onde é escrito o primeiro caractere equando o valor do lado - 2 é inferior ao registo bl
   fimcicloVerticalAmbos:           ; fim do ciclo de escrita
   ret                              ; Retoma a execução na instrução seguinte ao call

escrever0:
   ; Função que contém as funções necessárias para a escrita do algarismo 0
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalAmbos               ; chamada da função que escreve o segmento vertical dos dois lados
   ; New Line
   call novaLinha                   ; Chamada da função que escreve uma nova linha     
   call verticalAmbos               ; chamada da função que escreve o segmento vertical dos dois lados
   call horizontal                  ; Chamada da função que escreve o segmento horizontal
   ret                              ; Retoma a execução na instrução seguinte ao call

escrever1:
   ; Função que contém as funções necessárias para a escrita do algarismo 1
   call verticalDireita             ; Chamada da função que escreve o segmento vertical direito
   ; New Line
   call novaLinha                   ; Chamada da função que escreve uma nova linha    
   call verticalDireita             ; Chamada da função que escreve o segmento vertical direito
   ret                              ; Retoma a execução na instrução seguinte ao call

escrever2:
   ; Função que contém as funções necessárias para a escrita do algarismo 2
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalDireita             ; Chamada da função que escreve o segmento vertical direito
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalEsquerda            ; Chamada da função que escreve o segmento vertical esquerdo
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   ret                              ; Retoma a execução na instrução seguinte ao call

escrever3:
   ; Função que contém as funções necessárias para a escrita do algarismo 3
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalDireita             ; Chamada da função que escreve o segmento vertical direito
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalDireita             ; Chamada da função que escreve o segmento vertical direito
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   ret                              ; Retoma a execução na instrução seguinte ao call

escrever4:
   ; Função que contém as funções necessárias para a escrita do algarismo 4
   call verticalAmbos               ; chamada da função que escreve o segmento vertical dos dois lados
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalDireita             ; Chamada da função que escreve o segmento vertical direito
   ret                              ; Retoma a execução na instrução seguinte ao call

escrever5:
   ; Função que contém as funções necessárias para a escrita do algarismo 5
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalEsquerda            ; Chamada da função que escreve o segmento vertical esquerdo
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalDireita             ; Chamada da função que escreve o segmento vertical direito
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   ret                              ; Retoma a execução na instrução seguinte ao call

escrever6:
   ; Função que contém as funções necessárias para a escrita do algarismo 6
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalEsquerda            ; Chamada da função que escreve o segmento vertical esquerdo
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalAmbos               ; chamada da função que escreve o segmento vertical dos dois lados
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   ret                              ; Retoma a execução na instrução seguinte ao call

escrever7:
   ; Função que contém as funções necessárias para a escrita do algarismo 7
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalDireita             ; Chamada da função que escreve o segmento vertical direito
   ; New Line
   call novaLinha                   ; Chamada da função que escreve uma nova linha
   call verticalDireita             ; Chamada da função que escreve o segmento vertical direito
   ret                              ; Retoma a execução na instrução seguinte ao call

escrever8:
   ; Função que contém as funções necessárias para a escrita do algarismo 8
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalAmbos               ; chamada da função que escreve o segmento vertical dos dois lados
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalAmbos               ; chamada da função que escreve o segmento vertical dos dois lados
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   ret                              ; Retoma a execução na instrução seguinte ao call

escrever9:
   ; Função que contém as funções necessárias para a escrita do algarismo 9
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalAmbos               ; chamada da função que escreve o segmento vertical dos dois lados
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   call verticalDireita             ; Chamada da função que escreve o segmento vertical direito
   call horizontal                  ; chamada da função que escreve o segmento horizontal
   ret                              ; Retoma a execução na instrução seguinte ao call

novaLinha:
   ; newLine
   mov rsi, newLine                 ; rsi <- endereço da string a escrever
   mov rdx, compNL                  ; rdx <- nº de caracteres a escrever (até EoS)
   push rcx                         ; guarda rcx na stack
   call escrever                    ; chamada da função escrever
   pop rcx                          ; Remove rcx da stack
   ret                              ; Retoma a execução na instrução seguinte ao call

corDefault:
   ; Cor default
   mov   rsi, def                   ; rsi <- endereço da string a escrever
   mov   rdx, defLen                ; rdx <- nº de caracteres a escrever (até EoS)
   push  rcx                        ; guarda rcx na stack
   call  escrever                   ; Chamada da função escrever
   pop   rcx                        ; Remove rcx da stack
   ret                              ; Retoma a execução na instrução seguinte ao call

corAlterada:
   ; Cor alterada
   mov rsi, cor                     ; rsi <- endereço da string a escrever   
   mov rdx, corLen                  ; rdx <- nº de caracteres a escrever (até EoS)
   push rcx                         ; guarda rcx na stack
   call escrever                    ; Chamada da função escrever
   pop rcx                          ; remove rcx da stack
   ret                              ; Retoma a execução na instrução seguinte ao call

desenharAlgarismos:
   ; Função que desenha todos os algarismos mediante de um ciclo
   mov cl, 0                        ; Registo que dá inicio ao ciclo
   repete:        
      cmp cl, 10                    ; Caso seja igual a 10
      je terminarciclo              ; Termina o ciclo
         cmp cl, 0                  ; Caso seja 0
         je alg0                    ; Jump para a função que chama a função que contém as funções necessárias para a escrita do algarismo
         cmp cl, 1                  ; Caso seja 1
         je alg1                    ; Jump para a função que chama a função que contém as funções necessárias para a escrita do algarismo
         cmp cl, 2                  ; Caso seja 2
         je alg2                    ; Jump para a função que chama a função que contém as funções necessárias para a escrita do algarismo
         cmp cl, 3                  ; Caso seja 3
         je alg3                    ; Jump para a função que chama a função que contém as funções necessárias para a escrita do algarismo
         cmp cl, 4                  ; Caso seja 4
         je alg4                    ; Jump para a função que chama a função que contém as funções necessárias para a escrita do algarismo
         cmp cl, 5                  ; Caso seja 5
         je alg5                    ; Jump para a função que chama a função que contém as funções necessárias para a escrita do algarismo
         cmp cl, 6                  ; Caso seja 6
         je alg6                    ; Jump para a função que chama a função que contém as funções necessárias para a escrita do algarismo
         cmp cl, 7                  ; Caso seja 7
         je alg7                    ; Jump para a função que chama a função que contém as funções necessárias para a escrita do algarismo
         cmp cl, 8                  ; Caso seja 8
         je alg8                    ; Jump para a função que chama a função que contém as funções necessárias para a escrita do algarismo
         cmp cl, 9                  ; Caso seja 9
         je alg9                    ; Jump para a função que chama a função que contém as funções necessárias para a escrita do algarismo
         inc cl                     ; Incrementação do registo cl por 1
         jmp repete                 ; Jump para o ciclo
      alg0:
      ; Função que contém a função para a escrita do algarismo 0
         call novaLinha             ; chamada da função que escreve uma nova linha
         call escrever0             ; chamada da função que contém as funções necessárias para a escrita do algarismo
         call novaLinha             ; chamada da função que escreve uma nova linha
      alg1:
      ; Função que contém a função para a escrita do algarismo 1
         call escrever1             ; chamada da função que contém as funções necessárias para a escrita do algarismo
         call novaLinha             ; chamada da função que escreve uma nova linha
      alg2:
      ; Função que contém a função para a escrita do algarismo 2
         call escrever2             ; chamada da função que contém as funções necessárias para a escrita do algarismo
         call novaLinha             ; chamada da função que escreve uma nova linha
      alg3:
      ; Função que contém a função para a escrita do algarismo 3
         call escrever3             ; chamada da função que contém as funções necessárias para a escrita do algarismo
         call novaLinha             ; chamada da função que escreve uma nova linha
      alg4:
      ; Função que contém a função para a escrita do algarismo 4
         call escrever4             ; chamada da função que contém as funções necessárias para a escrita do algarismo
         call novaLinha             ; chamada da função que escreve uma nova linha
      alg5:
      ; Função que contém a função para a escrita do algarismo 5
         call escrever5             ; chamada da função que contém as funções necessárias para a escrita do algarismo
         call novaLinha             ; chamada da função que escreve uma nova linha
      alg6:    
      ; Função que contém a função para a escrita do algarismo 6
         call escrever6             ; chamada da função que contém as funções necessárias para a escrita do algarismo
         call novaLinha             ; chamada da função que escreve uma nova linha
      alg7:
      ; Função que contém a função para a escrita do algarismo 7
         call escrever7             ; chamada da função que contém as funções necessárias para a escrita do algarismo
         call novaLinha             ; chamada da função que escreve uma nova linha
      alg8:
      ; Função que contém a função para a escrita do algarismo 8
         call escrever8             ; chamada da função que contém as funções necessárias para a escrita do algarismo
         call novaLinha             ; chamada da função que escreve uma nova linha
      alg9:
      ; Função que contém a função para a escrita do algarismo 9
         call escrever9             ; chamada da função que contém as funções necessárias para a escrita do algarismo
         call novaLinha             ; chamada da função que escreve uma nova linha
      terminarciclo:
   ret                              ; Retoma a execução na instrução seguinte ao call

; assemble: yasm -f elf64 -g dwarf2 -l projeto.lst projeto.asm
;     link: ld -g projeto -o projeto
;    debug: gdb ./projeto
; execute: ./projeto
