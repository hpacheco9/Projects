package InteracaoAlimentar;
import SubstanciaAtiva.SubstanciaAtiva;

/**
 * Classe InteracaoHandler que cria uma interação alimentar
 */
public class InteracaoHandler {

    /**
     * Cria uma interação alimentar
     * @param substanciaAtiva substância ativa
     * @param explicacao      explicação
     * @param alimento        alimento
     * @param referencia      referência bibliográfica
     * @return interação alimentar criada
     */
    public InteracaoAlimentar criarInteracaoAlimentar(SubstanciaAtiva substanciaAtiva, String explicacao, String alimento,String efeito, int nivelEfeito, String referencia ){
        InteracaoAlimentar ia = new InteracaoAlimentar();
        ia.setSubstanciaAtiva(substanciaAtiva);
        ia.setExplicacao(explicacao);
        ia.setAlimento(alimento);
        ia.setEfeito(efeito);
        ia.setNivelEfeito(nivelEfeito);
        ia.setReferenciaBibliografica(referencia);
        return ia;
    }
}
