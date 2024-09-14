package Main;
import Industria.Industria;
import InteracaoAlimentar.InteracaoAlimentar;
import Sistema.Sistema;
import SubstanciaAtiva.SubstanciaAtiva;
import Utilizador.Utilizador;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe Main onde é executado o programa, implementando as funcionalidades do sistema.
 */
public class Main {

    /**
     * Método main onde é executado o programa.
     * @param args argumentos
     */
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        sistema.startup();
        menuPrincipal(sistema);
    }

    /**
     * Método que permite fazer login no sistema
     * @param sistema sistema farmacêutico
     * @return true se o login for efetuado com sucesso, false caso contrário
     */
    public static boolean login(Sistema sistema){
        Scanner sc = new Scanner(System.in);
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();
            if (sistema.login(email, pass) != null){
                sistema.setUtilizadorAtual(sistema.login(email,pass));
                return true;
            }else {
                return false;
            }
    }

    /**
     * Método que implementa o menu principal do sistema
     * @param sistema sistema farmacêutico
     */
    public static void menuPrincipal(Sistema sistema) {
        Scanner inputString = new Scanner(System.in);
        boolean cont = true;
        do {
            System.out.println("\n✦————— Menu Principal —————✦");
            System.out.println("1: Login");
            System.out.println("2: Entrar como convidado");
            System.out.println("0: Sair");
            System.out.println("✦——————————————————————————✦");
            System.out.print(">> ");
            String op = inputString.nextLine();
            System.out.println();
            switch (op) {
                case "1":
                    if (login(sistema)){
                        if (sistema.getutilizadorAtual().getPapel().equals("Administrador")){
                            System.out.println();
                            menuAdmin(sistema);
                        } else if (sistema.getutilizadorAtual().getPapel().equals("Farmaceutico")) {
                            System.out.println();
                            menuFarmaceutico(sistema);
                        } else if (sistema.getutilizadorAtual().getPapel().equals("Industria")) {
                            System.out.println();
                            menuIndustria(sistema);
                        }
                    }else{
                        System.out.println("Utilizador não existe");
                        System.out.println();
                    }
                    break;
                case "2":
                    menuConvidado(sistema);
                    break;
                case "0":
                    System.out.println("Adios!");
                    cont = false;
                    break;
                default:
                    System.out.println("Essa opção não existe!");
                    break;
            }
        } while (cont);
    }

    /**
     * Método que implementa o menu convidado, utilizador não logado no sistema
     * @param sistema sistema farmacêutico
     */
    public static void menuConvidado(Sistema sistema) {
        Scanner inputString = new Scanner(System.in);
        boolean cont = true;
        do {
            System.out.println("✦————— Menu Convidado —————✦");
            System.out.println("1: Pesquisar Interações");
            System.out.println("2: Pesquisar Contacto");
            System.out.println("0: Sair");
            System.out.println("✦——————————————————————————✦");
            System.out.print(">> ");
            String op = inputString.nextLine();
            System.out.println();
            switch (op){
                case "1":
                    pesquisaInteracoes(sistema);
                    break;
                case "2":
                    System.out.print("Introduza o nome do medicamento [0 - Cancelar] >> ");
                    String nomeMedicamentoContacto = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
                    if (nomeMedicamentoContacto.equals("0")) {
                        System.out.println();
                        break;
                    }
                    pesquisaContacto(sistema, nomeMedicamentoContacto);
                    break;
                case "0":
                    cont = false;
                    break;
                default:
                    System.out.println("Essa opção não existe!");
                    break;
            }

        } while (cont);
    }

    /**
     * Método que implementa o menu do administrador
     * @param sistema sistema farmacêutico
     */
    public static void menuAdmin(Sistema sistema) {
        Scanner inputString = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);
        boolean cont = true;
        do {
            System.out.println("✦————— Menu Admin —————✦");
            System.out.println("1: Pesquisar Interações");
            System.out.println("2: Pesquisar Contacto");
            System.out.println("3: Listar substancias ativas");
            System.out.println("4: Registar Utilizador");
            System.out.println("5: Adicionar substância ativa");
            System.out.println("0: Sair");
            System.out.println("✦——————————————————————✦");
            System.out.print(">> ");
            String op = inputString.nextLine();
            System.out.println();
            switch (op){
                case "1":
                    pesquisaInteracoes(sistema);
                    break;
                case "2":
                    System.out.print("Introduza o nome do medicamento [0 - Cancelar] >> ");
                    String nomeMedicamentoContacto = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
                    if (nomeMedicamentoContacto.equals("0")) {
                        System.out.println();
                        break;
                    }
                    pesquisaContacto(sistema, nomeMedicamentoContacto);
                    break;
                case "3":
                    System.out.println();
                    int counter = 10;
                    sistema.listarSubstanciasAtivas(counter);
                    while (true) {
                        System.out.print("\nDeseja listar mais substâncias ativas? (S/N) >> ");
                        String opcao = inputString.nextLine().toLowerCase();
                        System.out.println();
                        if (opcao.equals("s")) {
                            counter += 10;
                            sistema.listarSubstanciasAtivas(counter);
                        } else if (opcao.equals("n")) {
                            System.out.println();
                            break;
                        } else {
                            System.out.println("Opção inválida!");
                        }
                    }
                    break;
                case "4":
                    int contato = 0;
                    System.out.print("Primeiro Nome > ");
                    String pnome = inputString.nextLine();
                    System.out.print("Ultimo Nome > ");
                    String unome = inputString.nextLine();
                    System.out.print("Email > ");
                    String email = inputString.nextLine();
                    System.out.print("Password > ");
                    String pass = inputString.nextLine();
                    sistema.mostraPapel();
                    String papelInput;
                    do {
                        System.out.print("Papel > ");
                        papelInput = inputString.nextLine();
                    } while (!sistema.getPapelUtilizador().contains(papelInput));
                    if (papelInput.equals("Industria")){
                        System.out.print("Contacto > ");
                         contato = inputInt.nextInt();
                    }
                    if (sistema.registar(pnome,unome,email,pass,papelInput, contato)){
                        System.out.println("Utilizador criado com sucesso");
                    }else {
                        System.out.println("Utilizador já existe");
                    }
                    break;
                case "5":
                    String substancia;
                    String confirma;
                    do {
                        System.out.print("Substancia Ativa > ");
                        substancia = inputString.nextLine();
                    }while (sistema.verificaSubstancia(substancia));
                    System.out.print("Confirma criação S/N > ");
                    confirma = inputString.nextLine().toLowerCase();
                    if (confirma.equals("s")){
                        sistema.adicionarSubstanciaAtiva(substancia);
                        System.out.println("Substância adicionada com sucesso!");
                    }
                    break;
                case "0":
                    sistema.setUtilizadorAtual(null);
                    sistema.shutdown();
                    cont = false;
                    break;
                default:
                    System.out.println("Essa opção não existe!");
                    break;
            }
        } while (cont);
    }

    /**
     * Método que implementa o menu da indústria
     * @param sistema sistema farmacêutico
     */
    public static void menuIndustria(Sistema sistema) {
        Scanner inputString = new Scanner(System.in);
        boolean cont = true;
        do {
            System.out.println("✦————— Menu Indústria —————✦");
            System.out.println("1: Pesquisar Interações");
            System.out.println("2: Pesquisar Contacto");
            System.out.println("3: Listar substancias ativas");
            System.out.println("4: Listar medicamentos");
            System.out.println("5: Adicionar medicamento");
            System.out.println("0: Sair");
            System.out.println("✦——————————————————————————✦");
            System.out.print(">> ");
            String op = inputString.nextLine();
            System.out.println();
            switch (op) {
                case "1":
                    pesquisaInteracoes(sistema);
                    break;
                case "2":
                    System.out.print("Introduza o nome do medicamento [0 - Cancelar] >> ");
                    String nomeMedicamentoContacto = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
                    if (nomeMedicamentoContacto.equals("0")) {
                        System.out.println();
                        break;
                    }
                    pesquisaContacto(sistema, nomeMedicamentoContacto);
                    break;
                case "3":
                    System.out.println();
                    int counterSubstancias = 10;
                    sistema.listarSubstanciasAtivas(counterSubstancias);
                    while (true) {
                        System.out.print("\nDeseja listar mais substâncias ativas? (S/N) >> ");
                        String opcao = inputString.nextLine().toLowerCase();
                        System.out.println();
                        if (opcao.equals("s")) {
                            counterSubstancias += 10;
                            sistema.listarSubstanciasAtivas(counterSubstancias);
                        } else if (opcao.equals("n")) {
                            System.out.println();
                            break;
                        } else {
                            System.out.println("Opção inválida!");
                        }
                    }
                    break;
                case "4":
                    System.out.println();
                    int counterMedicamentos = 10;
                    sistema.listarMedicamentos(counterMedicamentos);
                    while (true) {
                        System.out.print("\nDeseja listar mais medicamentos? (S/N) >> ");
                        String opcao = inputString.nextLine().toLowerCase();
                        System.out.println();
                        if (opcao.equals("s")) {
                            counterMedicamentos += 10;
                            sistema.listarMedicamentos(counterMedicamentos);
                        } else if (opcao.equals("n")) {
                            System.out.println();
                            break;
                        } else {
                            System.out.println("Opção inválida!");
                        }
                    }
                    break;
                case "5":
                    String nomeMedicamento;
                    String formaMedicamento;
                    String dosagemMedicamento;
                    boolean contMedicamento = true;
                    do {
                        System.out.print("Nome do medicamento > ");
                        nomeMedicamento = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
                        System.out.print("Forma do medicamento > ");
                        formaMedicamento = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
                        System.out.print("Dosagem do medicamento > ");
                        dosagemMedicamento = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
                        if (sistema.verificaMedicamento(nomeMedicamento+dosagemMedicamento+formaMedicamento)) {
                            System.out.println("Medicamento já existe!");
                        } else {
                            contMedicamento = false;
                        }
                    } while (contMedicamento);
                    ArrayList<SubstanciaAtiva> substanciasAtivas = new ArrayList<>();
                    boolean contSubstancias = true;
                    do {
                        System.out.print("Substância ativa [0 - Terminar] > ");
                        String nomeSubstanciaAtiva = inputString.nextLine();
                        SubstanciaAtiva subAtiva = new SubstanciaAtiva();
                        subAtiva.setNome(nomeSubstanciaAtiva);
                        if (nomeSubstanciaAtiva.equals("0")) {
                            contSubstancias = false;
                        } else {
                            if (!sistema.verificaSubstancia(nomeSubstanciaAtiva)) {
                                System.out.println("Substância ativa não existe!");
                            } else if (substanciasAtivas.contains(subAtiva)) {
                                System.out.println("Substância ativa já existe no medicamento!");
                            } else {
                                SubstanciaAtiva s = sistema.getSubstanciaAtiva(nomeSubstanciaAtiva);
                                substanciasAtivas.add(s);
                            }
                        }
                    } while (contSubstancias);
                    System.out.print("Confirma criação? S/N >> ");
                    String confirma = inputString.nextLine().toLowerCase();
                    if (confirma.equals("s")) {
                        Utilizador industria = sistema.getutilizadorAtual();
                        String laboratorio = industria.getPrimeiroNome();
                        boolean resultado = sistema.adicionarMedicamento(nomeMedicamento, formaMedicamento, dosagemMedicamento, substanciasAtivas, laboratorio);
                        if (resultado) {
                            System.out.println("Medicamento adicionado com sucesso!");
                        } else {
                            System.out.println("Medicamento já existe!");
                        }
                    }
                    break;
                case "0":
                    sistema.setUtilizadorAtual(null);
                    sistema.shutdown();
                    cont = false;
                    break;
                default:
                    System.out.println("Essa opção não existe!");
            }
        } while (cont);
    }

    /**
     * Método que implementa o menu do farmacêutico
     * @param sistema sistema farmacêutico
     */
    public static void menuFarmaceutico(Sistema sistema) {
        Scanner inputString = new Scanner(System.in);
        boolean cont = true;
        do {
            System.out.println("✦————— Menu Farmacêutico —————✦");
            System.out.println("1: Pesquisar Interações");
            System.out.println("2: Pesquisar Contacto");
            System.out.println("3: Listar substancias ativas");
            System.out.println("4: Listar Interações Alimentares");
            System.out.println("5: Adicionar Interação Alimentar");
            System.out.println("0: Sair");
            System.out.println("✦—————————————————————————————✦");
            System.out.print(">> ");
            String op = inputString.nextLine();
            switch (op){
                case "1":
                    pesquisaInteracoes(sistema);
                    break;
                case "2":
                    System.out.print("Introduza o nome do medicamento [0 - Cancelar] >> ");
                    String nomeMedicamentoContacto = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
                    if (nomeMedicamentoContacto.equals("0")) {
                        System.out.println();
                        break;
                    }
                    pesquisaContacto(sistema, nomeMedicamentoContacto);
                    break;
                case "3":
                    System.out.println();
                    int counterSubstancias = 10;
                    sistema.listarSubstanciasAtivas(counterSubstancias);
                    while (true) {
                        System.out.print("\nDeseja listar mais substâncias ativas? (S/N) >> ");
                        String opcao = inputString.nextLine().toLowerCase();
                        System.out.println();
                        if (opcao.equals("s")) {
                            counterSubstancias += 10;
                            sistema.listarSubstanciasAtivas(counterSubstancias);
                        } else if (opcao.equals("n")) {
                            System.out.println();
                            break;
                        } else {
                            System.out.println("Opção inválida!");
                        }
                    }
                    break;
                case "4":
                    System.out.println();
                    int counterInteracoes = 10;
                    sistema.listarInteracoesAlimentares(counterInteracoes);
                    while (true) {
                        System.out.print("\nDeseja listar mais Interações Alimentares? (S/N) >> ");
                        String opcao = inputString.nextLine().toLowerCase();
                        System.out.println();
                        if (opcao.equals("s")) {
                            counterInteracoes += 10;
                            sistema.listarInteracoesAlimentares(counterInteracoes);
                        } else if (opcao.equals("n")) {
                            System.out.println();
                            break;
                        } else {
                            System.out.println("Opção inválida!");
                        }
                    }
                    break;
                case "5":
                    String nomeSubstanciaAtiva;
                    String explicacao;
                    String alimento;
                    String efeito;
                    int nivelEfeito = 0;
                    SubstanciaAtiva s;
                    String referencia;
                    do {
                        do{
                            System.out.print("Substância ativa > ");
                            nomeSubstanciaAtiva = inputString.nextLine();
                            if (!sistema.getSubstanciasAtivas().containsKey(nomeSubstanciaAtiva)){
                                System.out.println("A substancia não existe!");
                            }
                        }while (!sistema.getSubstanciasAtivas().containsKey(nomeSubstanciaAtiva));

                        s = sistema.getSubstanciaAtiva(nomeSubstanciaAtiva);
                        System.out.print("Explicação > ");
                        explicacao = inputString.nextLine();
                        sistema.mostraAlimentos();
                        System.out.println();
                        do {
                            System.out.print("Alimento > ");
                            alimento = inputString.nextLine();
                            if (!sistema.getAlimentos().contains(alimento)){
                                System.out.println("Introduza um alimento válido");
                            }
                        }while(!sistema.getAlimentos().contains(alimento));
                        System.out.print("Efeito > ");
                        efeito = inputString.nextLine();
                        do {
                            try {
                                System.out.print("Nivel do Efeito [1-3] > ");
                                nivelEfeito = Integer.parseInt(inputString.nextLine());
                                if (nivelEfeito < 1 || nivelEfeito > 3) {
                                    System.out.println("\nIntroduza um valor entre 1 e 3.\n");
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("\nIntroduza uma avaliação válida.\n");
                            }
                        } while (nivelEfeito < 1 || nivelEfeito > 3);
                        System.out.print("Referência > ");
                        referencia = inputString.nextLine();
                        System.out.println();
                    }while (!sistema.verificaSubstancia(nomeSubstanciaAtiva) && explicacao.isEmpty() && alimento.isEmpty() && efeito.isEmpty() && referencia.isEmpty());
                    System.out.print("Confirma criação? S/N >> ");
                    String confirma = inputString.nextLine().toLowerCase();
                    System.out.println();
                    if (confirma.equals("s")){
                        boolean resultado = sistema.adicionarInteracaoAlimentar(s, explicacao, alimento, efeito, nivelEfeito, referencia);
                        if (resultado) {
                            System.out.println("Interação alimentar adicionada com sucesso!");
                        } else {
                            System.out.println("Interação alimentar já existe!");
                        }
                    }
                    break;
                case "0":
                    sistema.setUtilizadorAtual(null);
                    sistema.shutdown();
                    cont = false;
                    break;
                default:
                    System.out.println("Essa opção não existe!");
                    break;
            }
        } while (cont);
    }

    /**
     * Método que implementa a pesquisa de contacto
     * @param sistema sistema farmacêutico
     * @param nomeMedicamentoContacto nome do medicamento
     */
    public static void pesquisaContacto(Sistema sistema, String nomeMedicamentoContacto) {
        Scanner inputString = new Scanner(System.in);
        System.out.print("Introduza a dosagem do medicamento >> ");
        String dosagemMedicamentoContacto = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
        System.out.print("Introduza a forma do medicamento >> ");
        String formaMedicamentoContacto = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
        while (!sistema.verificaMedicamento(nomeMedicamentoContacto+dosagemMedicamentoContacto+formaMedicamentoContacto) || nomeMedicamentoContacto.isEmpty()) {
            System.out.println("\nO medicamento não existe!");
            System.out.println();
            System.out.print("Introduza o nome do medicamento [0 - Cancelar] >> ");
            nomeMedicamentoContacto = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
            if (nomeMedicamentoContacto.equals("0")) {
                break;
            }
            System.out.print("Introduza a dosagem do medicamento >> ");
            dosagemMedicamentoContacto = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
            System.out.print("Introduza a forma do medicamento >> ");
            formaMedicamentoContacto = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
        }
        if (!nomeMedicamentoContacto.equals("0")) {
            Industria i = sistema.pesquisarContacto(nomeMedicamentoContacto);
            if (i != null) {
                System.out.println("\n"+i);
            } else {
                System.out.println("Não existe nenhuma Indústria com esse medicamento!\n");
            }
        }
        System.out.println();
    }

    /**
     * Método que implementa a pesquisa de interações
     * @param sistema sistema farmacêutico
     */
    public static void pesquisaInteracoes (Sistema sistema){
        Scanner inputString = new Scanner(System.in);
        System.out.print("Pretende Cancelar a pesquisa? (S/N) >> ");
        String cancela = inputString.nextLine().toLowerCase();
        if (cancela.equals("n")) {
            System.out.print("Introduza o nome do medicamento >> ");
            String nomeMedicamentoInteracao = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
            System.out.print("Introduza a dosagem do medicamento >> ");
            String dosagemMedicamentoInteracao = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
            System.out.print("Introduza a forma do medicamento >> ");
            String formaMedicamentoInteracao = inputString.nextLine().replaceAll("\\s", "").toLowerCase();
            while (!sistema.verificaMedicamento(nomeMedicamentoInteracao+dosagemMedicamentoInteracao+formaMedicamentoInteracao)) {
                System.out.println("O medicamento não existe!");
                System.out.println();
                System.out.print("Introduza o nome do medicamento >> ");
                nomeMedicamentoInteracao = inputString.nextLine();
                System.out.print("Introduza a dosagem do medicamento >> ");
                dosagemMedicamentoInteracao = inputString.nextLine();
                System.out.print("Introduza a forma do medicamento >> ");
                formaMedicamentoInteracao = inputString.nextLine();
            }
            ArrayList<InteracaoAlimentar> ias = sistema.pesquisarInteracoes(nomeMedicamentoInteracao+dosagemMedicamentoInteracao+formaMedicamentoInteracao);
            if (!ias.contains(null)) {
                System.out.println("Interações Alimentares:");
                for (InteracaoAlimentar ia : ias) {
                    System.out.println(ia);
                    System.out.print("Ver mais detalhes S/N > ");
                    String detalhes = inputString.nextLine().toLowerCase();
                    if (detalhes.equals("s")){
                        System.out.println("Descrição: "+ia.getExplicacao()+"\n"+"Referencia: "+ia.getReferencia());
                    }
                }
            } else {
                System.out.println("Não existem interações alimentares para esse medicamento!");
            }
        }
    }
}