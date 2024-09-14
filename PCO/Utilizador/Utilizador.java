package Utilizador;

/**
 * Classe que representa um Utilizador
 */
public class Utilizador {

    private String primeiroNome;
    private String ultimoNome;

    private String password;

    private String email;

    private String papel;

    /**
     * Construtor default da classe Utilizador
     */
    public Utilizador() {
        this.primeiroNome = "";
        this.ultimoNome = "";
        this.password = "";
        this.email = "";
        this.papel = "";
    }


    /**
     * Getter do primeiro nome do Utilizador
     * @return primeiro_nome
     */

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    /**
     * Getter da password do Utilizador
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter do email do Utlizador
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter do papel de Utilizador
     * @return papel
     */
    public String getPapel() {
        return papel;
    }

    /**
     * Setter do primeiro_nome do Utilizador
     * @param primeiroNome, primeiro nome
     */

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    /**
     * Setter do ultimo nome do Utilizador
     * @param ultimoNome, ultimo nome do Utilizador
     */
    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    /**
     * Setter da password do Utilizador
     * @param password password do Utilizador
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter email do Utilizador
     * @param email, email do utilizador
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter do papel do Utilizador
     * @param papel, papel do farmaceutico ou indústria
     */
    public void setPapel(String papel) {
        this.papel = papel;
    }

    /**
     * Getter do ultimo nome do Utilizador
     * @return ultimo_nome, ultimo nome do Utilizador
     */
     public String getUltimoNome() {
         return ultimoNome;
     }

    /**
     * Método toString do Utilizador
     * @return String com a informação do Utilizador
     */
    @Override
    public String toString() {
        return "Nome: " + primeiroNome + "\n"+
                "Email: " + email + "\n";
    }
}
