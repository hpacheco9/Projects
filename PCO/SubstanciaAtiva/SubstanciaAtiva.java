package SubstanciaAtiva;

/**
 * Classe que representa uma substância ativa
 */
public class SubstanciaAtiva {
    private String nome;

    /**
     * Construtor default da classe SubstanciaAtiva
     */
    public SubstanciaAtiva() {
        this.nome = "";
    }

    /**
     * Getter do nome da substância ativa
     * @return nome da substância ativa
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter do nome da substância ativa
     * @param nome, nome da substância ativa
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * Método toString da classe SubstanciaAtiva
     * @return nome da substância ativa
     */
    @Override
    public String toString() {
        return nome;
    }

    /**
     * Método que compara duas substâncias ativas
     * @return true se as substâncias ativas forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return nome.equals(((SubstanciaAtiva) o).nome);
    }
}
