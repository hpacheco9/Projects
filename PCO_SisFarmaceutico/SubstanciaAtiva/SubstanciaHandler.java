package SubstanciaAtiva;

/**
 * Classe SubstanciaHandler que cria uma substância ativa
 */
public class SubstanciaHandler {

    /**
     * Cria uma substância ativa
     * @param nome, nome da substância ativa
     * @return substância ativa criada
     */
    public SubstanciaAtiva criarSubstancia(String nome){
        SubstanciaAtiva s = new SubstanciaAtiva();
        s.setNome(nome);
        return s;
    }
}
