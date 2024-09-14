package Sistema;
import Utilizador.RegistHandler;
import Industria.Industria;
import InteracaoAlimentar.InteracaoAlimentar;
import InteracaoAlimentar.InteracaoHandler;
import Medicamento.Medicamento;
import Medicamento.MedicamentoHandler;
import SubstanciaAtiva.SubstanciaAtiva;
import SubstanciaAtiva.SubstanciaHandler;
import Utilizador.Utilizador;
import com.google.gson.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Classe que representa o sistema farmaceutico.
 */
public class Sistema {
    private  TreeMap<String, SubstanciaAtiva> substanciasAtivas;
    private  TreeMap<String, Medicamento> medicamentos;
    private  ArrayList<InteracaoAlimentar> interacoesAlimentares;
    private HashMap<String, Industria> industrias;
    private HashMap<String, Utilizador> utilizadores;
    private ArrayList<String> papelUtilizador = new ArrayList<>();
    private Utilizador utilizadorAtual;
    private ArrayList<String> alimentos;
    private MedicamentoHandler medicamentoHandler;
    private SubstanciaHandler substanciaAtivaHandler;
    private InteracaoHandler interacaoAlimentarHandler;
    private RegistHandler registHandler;

    /**
     * Construtor default da classe sistema
     */
    public Sistema() {
        this.substanciasAtivas = new TreeMap<>();
        this.medicamentos = new TreeMap<>();
        this.interacoesAlimentares = new ArrayList<>();
        this.industrias = new HashMap<>();
        this.utilizadores = new HashMap<>();
        this.alimentos = new ArrayList<>();
        this.medicamentoHandler = new MedicamentoHandler();
        this.substanciaAtivaHandler = new SubstanciaHandler();
        this.interacaoAlimentarHandler = new InteracaoHandler();
        this.registHandler = new RegistHandler();
        this.papelUtilizador.add("Administrador"); this.papelUtilizador.add("Industria"); this.papelUtilizador.add("Farmaceutico");
    }

    /**
     * Getter para retornar o utilizador atual do sistema.
     * @return utilizadorAtual
     */
    public Utilizador getutilizadorAtual() {
        return utilizadorAtual;
    }

    /**
     * Startup do sistema
     * Recebe do ficheiro dataset.json os dados para o sistema como substâncias ativas, interações alimentares, medicamentos, alimentos e indústrias.
     * e adiciona-os ao sistema quando o sistema é iniciado.
     * @exception FileNotFoundException, caso o ficheiro não exista.
     * @exception IllegalStateException, caso o ficheiro não esteja no formato correto.
     * @exception JsonSyntaxException, caso o ficheiro não esteja no formato correto.
     * @exception IOException, caso haja um erro de IO.
     */
    public void startup() {
        Gson gson = new Gson();
        try {
            FileReader fileReader = new FileReader("Sistema/dataset.json");
            FileReader fileReaderUsers = new FileReader("Sistema/users.json");
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);
            JsonObject jsonObjectUsers = gson.fromJson(fileReaderUsers, JsonObject.class);
            JsonArray drugsArray = jsonObject.getAsJsonArray("drugs");
            JsonArray substancesArray = jsonObject.getAsJsonArray("substances");
            JsonArray interactionsArray = jsonObject.getAsJsonArray("foodInteractions");
            JsonArray foodsArray = jsonObject.getAsJsonArray("foodTypes");
            JsonArray laboratories = jsonObject.getAsJsonArray("laboratories");
            JsonArray users = jsonObjectUsers.getAsJsonArray("users");
            fileReader.close();
            fileReaderUsers.close();
            // Alimentos
            for (int i = 0; i < foodsArray.size(); i++) {
                String alimentoNome= foodsArray.get(i).getAsJsonObject().get("Type").getAsString();
                if (!alimentos.contains(alimentoNome)){
                    alimentos.add(alimentoNome);
                }
            }
            // Substâncias Ativas
            for (int i = 0; i < substancesArray.size() ; i++) {
                String substanciaNome = substancesArray.get(i).getAsJsonObject().get("Substance").getAsString();
                if (!substanciaNome.isEmpty()) {
                    adicionarSubstanciaAtiva(substanciaNome);
                }
            }
            // Interações Alimentares
            for (int i = 0; i < interactionsArray.size(); i++) {
                String referencia = interactionsArray.get(i).getAsJsonObject().get("Bibliography").getAsString();
                String efeito = interactionsArray.get(i).getAsJsonObject().get("Effect").getAsString();
                int level = interactionsArray.get(i).getAsJsonObject().get("EffectLevel").getAsInt();
                String substanciaNome = interactionsArray.get(i).getAsJsonObject().get("Substances").getAsString();
                String alimento = interactionsArray.get(i).getAsJsonObject().get("Food").getAsString();
                String explicacao = interactionsArray.get(i).getAsJsonObject().get("Explanation").getAsString();
                if (!alimentos.contains(alimento)){
                    alimentos.add(alimento);
                }
                SubstanciaAtiva s;
                if (!verificaSubstancia(substanciaNome) && !substanciaNome.equals("")) {
                    s = substanciaAtivaHandler.criarSubstancia(substanciaNome);
                    substanciasAtivas.put(substanciaNome, s);
                    adicionarInteracaoAlimentar(s, explicacao, alimento, efeito, level, referencia);
                } else {
                    if (!substanciaNome.equals("")) {
                        s = substanciasAtivas.get(substanciaNome);
                        adicionarInteracaoAlimentar(s, explicacao, alimento, efeito, level, referencia);
                    }
                }
            }
            ordenaInteracoes();
            // Indústrias
            for (int i = 0; i < laboratories.size(); i++) {
                int contacto;
                String name = laboratories.get(i).getAsJsonObject().get("Name").getAsString();
                if (laboratories.get(i).getAsJsonObject().get("Surveillance").getAsString().isEmpty()) {
                    contacto = 0;
                } else {
                    contacto = laboratories.get(i).getAsJsonObject().get("Surveillance").getAsInt();
                }
                Industria in = registHandler.registarIndustria(name, "123", "Industria", contacto);
                if (!industrias.containsKey(name)) {
                    industrias.put(name, in);
                }
                if (!verificaUtilizador(in.getEmail())) {
                    utilizadores.put(in.getEmail(), in.getUser());
                }
            }
            // Medicamentos
            for (int i = 0; i < drugsArray.size(); i++) {
                String dosagem = drugsArray.get(i).getAsJsonObject().get("Dosage").getAsString().replaceAll("\\s", "").toLowerCase();
                String forma = drugsArray.get(i).getAsJsonObject().get("Form").getAsString().replaceAll("\\s", "").toLowerCase();
                String laboratorio = drugsArray.get(i).getAsJsonObject().get("Laboratory").getAsString();
                String nome = drugsArray.get(i).getAsJsonObject().get("Name").getAsString().replaceAll("\\s", "").toLowerCase();
                String substancia = drugsArray.get(i).getAsJsonObject().get("Substances").getAsString();
                if (substancia.contains("|")) {
                    String[] substancias = substancia.split("\\|");
                    ArrayList<SubstanciaAtiva> listaSubstancias = new ArrayList<>();
                    for (String s: substancias) {
                        if (!verificaSubstancia(s) && s != null) {
                            SubstanciaAtiva substanciaAtiva = substanciaAtivaHandler.criarSubstancia(s);
                            substanciasAtivas.put(s, substanciaAtiva);
                            listaSubstancias.add(substanciaAtiva);
                        } else {
                            if (s != null) {
                                listaSubstancias.add(substanciasAtivas.get(s));
                            }
                        }
                    }
                    adicionarMedicamento(nome, forma, dosagem, listaSubstancias, laboratorio);
                } else {
                    SubstanciaAtiva s;
                    if (!verificaSubstancia(substancia) && substancia != null) {
                        s = substanciaAtivaHandler.criarSubstancia(substancia);
                        substanciasAtivas.put(substancia, s);
                    } else {
                        s = substanciasAtivas.get(substancia);
                    }
                    ArrayList<SubstanciaAtiva> listaSubstancias = new ArrayList<>();
                    listaSubstancias.add(s);
                    adicionarMedicamento(nome, forma, dosagem, listaSubstancias, laboratorio);
                }
            }
            // Utilizadores
            for (int i = 0; i < users.size(); i++) {
                String primeiroNome = users.get(i).getAsJsonObject().get("PrimeiroNome").getAsString();
                String ultimoNome = users.get(i).getAsJsonObject().get("UltimoNome").getAsString();
                String email = users.get(i).getAsJsonObject().get("Email").getAsString();
                String password = users.get(i).getAsJsonObject().get("Password").getAsString();
                String papel = users.get(i).getAsJsonObject().get("Papel").getAsString();
                Utilizador u = registHandler.registarUtilizador(primeiroNome, ultimoNome, password, email, papel);
                if (!verificaUtilizador(email)){
                    utilizadores.put(email, u);
                }
            }
        } catch (FileNotFoundException ignored) {
            System.out.println("Ficheiro não existe");
            System.exit(1);
        } catch (IllegalStateException | JsonSyntaxException ignored) {
            System.out.println("Ficheiro inválido");
            System.exit(2);
        }catch (IOException ignored){
            System.out.println("IO Exception erro");
            System.exit(3);
        }
    }

    /**
     * Método que guarda os valores das listas para o Json quando o sistema é encerrado.
     * @exception FileNotFoundException, caso o ficheiro não exista.
     * @exception IllegalStateException, caso o ficheiro não esteja no formato correto.
     * @exception JsonSyntaxException, caso o ficheiro não esteja no formato correto.
     * @exception IOException, caso haja um erro de IO.
     */
    public void shutdown() {
        try {
            FileWriter fileWriter = new FileWriter("Sistema/dataset.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            JsonObject jsonObjectTodos = new JsonObject();
            // Medicamentos
            JsonArray jsonArrayMedicamentos = new JsonArray();
            for (Medicamento m: medicamentos.values()) {
                JsonObject jsonObjectMedicamentos = new JsonObject();
                jsonObjectMedicamentos.addProperty("Dosage", m.getDosagem());
                jsonObjectMedicamentos.addProperty("Form", m.getForma());
                jsonObjectMedicamentos.addProperty("Laboratory", getIndustriaMedicamento(m.getNome()).getPrimeiroNome());
                jsonObjectMedicamentos.addProperty("Name", m.getNome());
                jsonObjectMedicamentos.addProperty("Substances", m.getSubstanciaAtivasString());
                jsonArrayMedicamentos.add(jsonObjectMedicamentos);
            }
            jsonObjectTodos.add("drugs", jsonArrayMedicamentos);
            // Interações Alimentares
            JsonArray jsonArrayInteracoes = new JsonArray();
            for (InteracaoAlimentar ia: interacoesAlimentares) {
                JsonObject jsonObjectInteracoes = new JsonObject();
                jsonObjectInteracoes.addProperty("Bibliography", ia.getReferencia());
                jsonObjectInteracoes.addProperty("Effect", ia.getEfeito());
                jsonObjectInteracoes.addProperty("EffectLevel", ia.getNivelEfeito());
                jsonObjectInteracoes.addProperty("Substances", ia.getSubstanciaAtiva().getNome());
                jsonObjectInteracoes.addProperty("Food", ia.getAlimento());
                jsonObjectInteracoes.addProperty("Explanation", ia.getExplicacao());
                jsonArrayInteracoes.add(jsonObjectInteracoes);
            }
            jsonObjectTodos.add("foodInteractions", jsonArrayInteracoes);
            // Alimentos
            JsonArray jsonArrayAlimentos = new JsonArray();
            for (String alimento: alimentos) {
                JsonObject jsonObjectAlimentos = new JsonObject();
                jsonObjectAlimentos.addProperty("Type", alimento);
                jsonArrayAlimentos.add(jsonObjectAlimentos);
            }
            jsonObjectTodos.add("foodTypes", jsonArrayAlimentos);
            // Indústrias
            JsonArray jsonArrayIndustrias = new JsonArray();
            for (Industria i: industrias.values()) {
                JsonObject jsonObjectIndustrias = new JsonObject();
                jsonObjectIndustrias.addProperty("Name", i.getPrimeiroNome());
                jsonObjectIndustrias.addProperty("Surveillance", i.getContacto());
                jsonArrayIndustrias.add(jsonObjectIndustrias);
            }
            jsonObjectTodos.add("laboratories", jsonArrayIndustrias);
            // Substâncias Ativas
            JsonArray jsonArraySubstancias = new JsonArray();
            for (SubstanciaAtiva s: substanciasAtivas.values()) {
                JsonObject jsonObjectSubstancias = new JsonObject();
                jsonObjectSubstancias.addProperty("Substance", s.getNome());
                jsonArraySubstancias.add(jsonObjectSubstancias);
            }
            jsonObjectTodos.add("substances", jsonArraySubstancias);
            String json = gson.toJson(jsonObjectTodos);
            fileWriter.write(json);
            fileWriter.close();
            // Utilizadores
            FileWriter fileWriterUsers = new FileWriter("Sistema/users.json");
            JsonArray jsonArrayUsers = new JsonArray();
            for (Utilizador u: utilizadores.values()) {
                JsonObject jsonObjectUsers = new JsonObject();
                jsonObjectUsers.addProperty("PrimeiroNome", u.getPrimeiroNome());
                jsonObjectUsers.addProperty("UltimoNome", u.getUltimoNome());
                jsonObjectUsers.addProperty("Email", u.getEmail());
                jsonObjectUsers.addProperty("Password", u.getPassword());
                jsonObjectUsers.addProperty("Papel", u.getPapel());
                jsonArrayUsers.add(jsonObjectUsers);
            }
            JsonObject jsonObjectUsers = new JsonObject();
            jsonObjectUsers.add("users", jsonArrayUsers);
            Gson gsonUsers = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String jsonUsers = gsonUsers.toJson(jsonObjectUsers);
            fileWriterUsers.write(jsonUsers);
            fileWriterUsers.close();
        } catch (FileNotFoundException ignored) {
            System.out.println("Ficheiro não existe");
            System.exit(1);
        } catch (IllegalStateException | JsonSyntaxException ignored) {
            System.out.println("Ficheiro inválido");
            System.exit(2);
        }catch (IOException ignored){
            System.out.println("IO Exception erro");
            System.exit(3);
        }
    }

    /**
     * Metodo para listar interações alimentares de 10 em 10
     * @param max, é incrementado ao max + 10
     */
    public void listarInteracoesAlimentares(int max) {
        int minimo;
        int restantes;
        if (max > interacoesAlimentares.size()) {
            minimo = max - 10;
            max = interacoesAlimentares.size();
            restantes = 0;
        } else {
            minimo = max - 10;
            restantes = interacoesAlimentares.size() - max;
        }
        if (restantes >= 0) {
            for (int i = minimo; i < max; i++) {
                System.out.println(i+1 + ":" + interacoesAlimentares.get(i));
            }
            System.out.println("Restantes: " + restantes);
        }
    }

    /**
     * Metodo para listar substâncias ativas de 10 em 10
     * @param max, é incrementado ao max + 10
     */
    public void listarSubstanciasAtivas(int max) {
       ArrayList<SubstanciaAtiva> substancias = new ArrayList<>(substanciasAtivas.values());
       int minimo;
       int restantes;
       if (max > substanciasAtivas.size()) {
           minimo = max - 10;
           max = substanciasAtivas.size();
           restantes = 0;
       } else {
           minimo = max - 10;
           restantes = substanciasAtivas.size() - max;
       }
       if (restantes >= 0) {
           for (int i = minimo; i < max; i++) {
               System.out.println(i+1 + ": " + substancias.get(i).getNome());
           }
           System.out.println("Restantes: " + restantes);
       }
    }

    /**
     * Metodo para listar medicamento de 10 em 10
     * @param max, é imcrementado ao max + 10
     */
    public void listarMedicamentos(int max) {
        ArrayList<Medicamento> m = new ArrayList<>(medicamentos.values());
        int minimo;
        int restantes;
        if (max > medicamentos.size()) {
            minimo = max - 10;
            max = medicamentos.size();
            restantes = 0;
        } else {
            minimo = max - 10;
            restantes = medicamentos.size() - max;
        }
        if (restantes >= 0) {
            for (int i = minimo; i < max; i++) {
                System.out.println(i+1 + ": " + m.get(i));
            }
            System.out.println("Restantes: " + restantes);
        }
    }

    /**
     * Método para pesquisar interações alimentares dado um nome do medicamento.
     * @param nomeMedicamento nome do medicamento
     */
    public ArrayList<InteracaoAlimentar> pesquisarInteracoes(String nomeMedicamento) {
        ArrayList<InteracaoAlimentar> ia = new ArrayList<>();
        if (verificaMedicamento(nomeMedicamento)){
            ArrayList<SubstanciaAtiva> s = medicamentos.get(nomeMedicamento).getSubstanciaAtivas();
            if (!s.isEmpty()){
                for (SubstanciaAtiva sub : s){
                    InteracaoAlimentar ii = interacoesAlimentares.stream().filter(interacaoAlimentar -> interacaoAlimentar.getSubstanciaAtiva().equals(sub)).findFirst().orElse(null);
                    if (ii != null){
                        ia.add(ii);
                    }
                }
                return ia;
            }else{
                return new ArrayList<>();
            }
        }
        return ia;
    }

    /**
     * Metodo para pesquisar um contacto através de um medicamento
     * @param nomeMedicamento nome do medicamento.
     * @return industria
     */
    public  Industria pesquisarContacto(String nomeMedicamento) {
        return industrias.values().stream()
                .filter(industria -> industria.getMedicamentos().containsKey(nomeMedicamento))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método para adicionar um medicamento ao sistema
     * @param nome nome do medicamento
     * @param forma forma do medicamento
     * @param dosagem dosagem do medicamento
     * @param listaSubstancias lista de substâncias ativas do medicamento
     * @param laboratorio laboratório do medicamento
     * @return String com a informação se o medicamento foi adicionado com sucesso ou não
     */
    public boolean adicionarMedicamento(String nome, String forma, String dosagem, ArrayList<SubstanciaAtiva> listaSubstancias, String laboratorio) {
        if (!verificaMedicamento(nome+dosagem+forma)) {
            Medicamento m = medicamentoHandler.criarMedicamento(nome, forma, dosagem, listaSubstancias);
            medicamentos.put(nome+dosagem+forma, m);
            if (industrias.containsKey(laboratorio)) {
                Industria in = industrias.get(laboratorio);
                in.setMedicamentos(m);
            }
            return true;
        } else{
            return false;
        }
    }

    /**
     * Método para adicionar uma interação alimentar ao sistema
     * @param substanciaAtiva substância ativa
     * @param explicacao explicação da interação alimentar
     * @param alimento alimento envolvido na interação alimentar
     * @param efeito efeito provocado pela interação alimentar
     * @param nivelEfeito nível do efeito provocado pela interação alimentar
     * @param referencia referência bibliográfica
     * @return String com a informação se a interação alimentar foi adicionada com sucesso ou não
     */
    public boolean adicionarInteracaoAlimentar(SubstanciaAtiva substanciaAtiva, String explicacao, String alimento, String efeito, int nivelEfeito, String referencia) {
        InteracaoAlimentar ia = interacaoAlimentarHandler.criarInteracaoAlimentar(substanciaAtiva, explicacao, alimento, efeito, nivelEfeito, referencia);
        if (!verificaInteracao(ia)){
            interacoesAlimentares.add(ia);
            return true;
        }else {
            return false;
        }
    }

    /**
     * Metodo para adicionar substância ativa.
     * @param nome, nome da substância
     * @return String, confirmação caso seja adicionada, ou de já existir.
     */
    public boolean adicionarSubstanciaAtiva(String nome) {
        if (!verificaSubstancia(nome)){
            SubstanciaAtiva s = substanciaAtivaHandler.criarSubstancia(nome);
            substanciasAtivas.put(nome, s);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para fazer login no sistema
     * @param email email do utilizador
     * @param password password do utilizador
     * @return utilizador que fez login
     */
    public Utilizador login(String email, String password) {
        return utilizadores.values().stream()
                .filter(utilizador -> utilizador.getEmail().contains(email) && utilizador.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
    /**
     * Método para registar um utilizador no sistema
     * @param primeiroNome primeiro nome do utilizador
     * @param ultimoNome último nome do utilizador
     * @param email email do utilizador
     * @param password password do utilizador
     * @param papel papel do utilizador
     * @param contacto contacto da indústria, caso o utilizador seja uma indústria
     * @return true se o utilizador for registado com sucesso, false caso contrário
     */
    public boolean registar(String primeiroNome, String ultimoNome, String email, String password, String papel, int contacto) {
        if (!verificaUtilizador(email)) {
            if (papel.equals("Industria")) {
                Industria i = registHandler.registarIndustria(primeiroNome, password, papel, contacto);
                industrias.put(i.getPrimeiroNome(), i);
                utilizadores.put(i.getEmail(), i.getUser());
            } else {
                Utilizador u = registHandler.registarUtilizador(primeiroNome, ultimoNome, password, email, papel);
                utilizadores.put(u.getEmail(), u);
            }
            return true;
        } else {
            return false;
        }
    }
    /**
     * Método para verificar se um medicamento existe no sistema ou não
     * @param nomeMedicamento nome do medicamento
     * @return true se o medicamento existir, false caso contrário
     */
    public boolean verificaMedicamento(String nomeMedicamento) {
        return medicamentos.containsKey(nomeMedicamento);
    }

    /**
     * Método para verificar se a indústria contem o medicamento.
     * @param nomeMedicamento nome do medicamento
     * @param nomeIndustria nome da indústria
     * @return true se o medicamento existir, false caso contrário
     */
    public boolean verificaMedicamentoIndustria(String nomeMedicamento, String nomeIndustria) {
        return industrias.get(nomeIndustria).getMedicamentos().containsKey(nomeMedicamento);
    }
    /**
     * Método para verificar se uma substância ativa existe no sistema ou não
     * @param nomeSubstancia nome da substância ativa
     * @return true se a substância ativa existir, false caso contrário
     */
    public boolean verificaSubstancia(String nomeSubstancia) {
        return substanciasAtivas.containsKey(nomeSubstancia);
    }

    /**
     * Método para verificar se uma interação alimentar existe no sistema ou não
     * @param ia interação alimentar
     * @return true se a interação alimentar existir, false caso contrário
     */
    public boolean verificaInteracao(InteracaoAlimentar ia) {
        for (InteracaoAlimentar interacaoAlimentar: interacoesAlimentares) {
            if (interacaoAlimentar.equals(ia)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para atualizar o utilizador atual do sistema
      * @param u, utilizador atual
     */
    public void setUtilizadorAtual(Utilizador u) {
        this.utilizadorAtual = u;
    }

    /**
     * Método para verificar se um utilizador existe no sistema ou não
     * @param email, email do utilizador
     * @return true se o utilizador existir, false caso contrário
     */
    public boolean verificaUtilizador(String email) {
        return utilizadores.containsKey(email);
    }

    /**
     * Método para obter a substância ativa dado o nome
     * @param nome, nome da substância ativa
     */
    public SubstanciaAtiva getSubstanciaAtiva(String nome){
        return substanciasAtivas.get(nome);
    }

    /**
     * Método para obter as interações alimentares
     * @return interações alimentares
     */
    public ArrayList<InteracaoAlimentar> getInteracoesAlimentares() {
        return interacoesAlimentares;
    }

    /**
     * Método para obter os medicamentos
     * @return medicamentos
     */
    public TreeMap<String, Medicamento> getMedicamentos() {
        return medicamentos;
    }

    /**
     * Método para obter as substâncias ativas
     * @return substâncias ativas
     */
    public TreeMap<String, SubstanciaAtiva> getSubstanciasAtivas() {
        return substanciasAtivas;
    }

    /**
     * Método para obter os utilizadores
     * @return utilizadores
     */
    public HashMap<String, Utilizador> getUtilizadores() {
        return utilizadores;
    }

    /**
     * Método para mostar o papel do utilizador
     */
    public void mostraPapel(){
        for (byte i = 0; i < papelUtilizador.size() ; i++) {
            System.out.print(i+1 + ": " + papelUtilizador.get(i) + " ");
        }
        System.out.println();
    }

    /**
     * Método para obter o papel do utilizador
     * @return industrias
     */
    public ArrayList<String> getPapelUtilizador() {
        return papelUtilizador;
    }

    /**
     * Método para obter a industria dado o nome
     * @param nomeIndustria nome da industria
     * @return industria
     */
    public Industria getIndustria(String nomeIndustria){
        return industrias.get(nomeIndustria);
    }

    /**
     * Método para obter os medicamentos de uma Indústria dado o nome do medicamento
     * @param nomeMedicamento nome do medicamento
     * @return industria
     */
    public Industria getIndustriaMedicamento(String nomeMedicamento) {
        return industrias.values().stream()
                .filter(industria -> industria.getMedicamentos().containsKey(nomeMedicamento))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método que ordena as interações alimentares por ordem alfabética usando o algoritmo de ordenação Insertion Sort
     */
    public void ordenaInteracoes() {
        for (int i = 1; i < interacoesAlimentares.size(); i++) {
            InteracaoAlimentar ia = interacoesAlimentares.get(i);
            int j = i - 1;
            while (j >= 0 && interacoesAlimentares.get(j).getSubstanciaAtiva().getNome().compareTo(ia.getSubstanciaAtiva().getNome()) > 0) {
                interacoesAlimentares.set(j+1, interacoesAlimentares.get(j));
                j--;
            }
            interacoesAlimentares.set(j+1, ia);
        }
    }
    /**
     * Método para exibir os alimentos
     */
    public void mostraAlimentos() {
        for (String e: alimentos){
            System.out.print(e + " ");
        }
    }
    /**
     * Método para obter a lista de Alimentos
     * @return alimentos
     */
    public ArrayList<String> getAlimentos(){
        return alimentos;
    }
}
