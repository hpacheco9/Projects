package InteracaoAlimentar;
import SubstanciaAtiva.SubstanciaAtiva;

/**
 * Classe que representa uma interação alimentar
 */
public class InteracaoAlimentar {

    private SubstanciaAtiva substanciaAtiva;
    private String explicacao;
    private int nivelEfeito;
    private String alimento;
    private String efeito;
    private String referenciaBibliografica;


    /**
     * Construtor default da classe InteracaoAlimentar
     */
    public InteracaoAlimentar() {
        this.substanciaAtiva = new SubstanciaAtiva();
        this.explicacao = "";
        this.alimento = "";
        this.efeito = "";
        this.nivelEfeito = 0;
        this.referenciaBibliografica = "";
    }

    /**
     * Setter da substância ativa
     * @param substanciaAtiva substância ativa
     */
    public void setSubstanciaAtiva(SubstanciaAtiva substanciaAtiva) {
        this.substanciaAtiva = substanciaAtiva;
    }

    /**
     * Setter da explicação
     * @param explicacao explicação
     */
    public void setExplicacao(String explicacao) {
        this.explicacao = explicacao;
    }

    /**
     * Setter do alimento
     * @param alimento alimento
     */
    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    /**
     * Setter do efeito
     * @param efeito efeito
     */
    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    /**
     * Setter da referência bibliográfica
     * @param referenciaBibliografica referência bibliográfica
     */
    public void setReferenciaBibliografica(String referenciaBibliografica) {
        this.referenciaBibliografica = referenciaBibliografica;
    }

    /**
     * Getter do nível de efeito
     * @return nível de efeito
     */
    public int getNivelEfeito() {
        return nivelEfeito;
    }

    /**
     * Setter do nível de efeito
     * @param nivelEfeito nível de efeito
     */
    public void setNivelEfeito(int nivelEfeito) {
        this.nivelEfeito = nivelEfeito;
    }

    /**
     * Getter da substância ativa
     * @return substância ativa
     */
    public SubstanciaAtiva getSubstanciaAtiva() {
        return substanciaAtiva;
    }

    /**
     * Getter do alimento
     * @return alimento
     */
    public String getAlimento() {
        return alimento;
    }

    /**
     * Getter do efeito
     * @return efeito
     */
    public String getEfeito() {
        return efeito;
    }

    /**
     * Getter da explicação
     * @return explicação
     */
    public String getExplicacao() {
        return explicacao;
    }

    /**
     * Getter da referência bibliográfica
     * @return referência bibliográfica
     */
    public String getReferencia() {
        return referenciaBibliografica;
    }

    /**
     * Verifica se uma interação alimentar é igual a outra
     * @return true se as interações alimentares forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        boolean explication = getExplicacao().equals(((InteracaoAlimentar) o).explicacao);
        boolean subs = substanciaAtiva.equals(((InteracaoAlimentar) o).substanciaAtiva);
        boolean referencia = referenciaBibliografica.equals(((InteracaoAlimentar) o).referenciaBibliografica);
        boolean efect = efeito.equals(((InteracaoAlimentar) o).efeito);
        boolean food = alimento.equals(((InteracaoAlimentar) o).alimento);
        return food && efect && referencia && explication && subs && nivelEfeito == ((InteracaoAlimentar) o).getNivelEfeito();
    }
    /**
     * Método toString da classe InteracaoAlimentar
     * @return String com a informação da interação alimentar
     */
    @Override
    public String toString() {
        return
                "Substância Ativa: " + substanciaAtiva + '\n' +
                "   Alimento: " + alimento + '\n' +"   Efeito: " + efeito + '\n';
    }
}
