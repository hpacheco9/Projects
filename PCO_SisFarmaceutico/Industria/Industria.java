package Industria;
import Medicamento.Medicamento;
import Utilizador.Utilizador;
import java.util.HashMap;

/**
 * Classe Industria que herda de Utilizador e representa uma industria farmaceutica.
 */
public class Industria extends Utilizador {
    private int contacto;
    private HashMap<String, Medicamento> medicamentos;

    /**
     * Construtor da classe Industria
     * @param primeiroNome primeiro nome da industria
     * @param password password da industria
     * @param papel papel da industria
     */
    public Industria(String primeiroNome, String password, String papel) {
        setPrimeiroNome(primeiroNome);
        setEmail(primeiroNome.replaceAll("\\s", "")+"@"+primeiroNome.replaceAll("\\s", "")+".com");
        setPassword(password);
        setPapel(papel);
        this.contacto = 0;
        this.medicamentos = new HashMap<>();
    }

    /**
     * Método para retornar um utilizador com os dados da industria
     * @return utilizador com os dados da industria
     */
    public Utilizador getUser(){
        Utilizador u = new Utilizador();
        u.setEmail(getEmail());
        u.setPapel(getPapel());
        u.setPrimeiroNome(getPrimeiroNome());
        u.setPassword(getPassword());
        return  u;
    }

    /**
     * Getter do contacto da industria
     * @return contacto
     */
    public int getContacto() {
        return contacto;
    }

    /**
     * Setter do contacto da industria
     * @param contacto contacto da industria
     */
    public void setContacto(int contacto) {
        this.contacto = contacto;
    }

    /**
     * Getter dos medicamentos da industria
     * @return medicamentos
     */
    public HashMap<String, Medicamento> getMedicamentos() {
        return medicamentos;
    }

    /**
     * Setter dos medicamentos da industria
     * @param medicamento medicamento da industria
     */
    public void setMedicamentos(Medicamento medicamento) {
        this.medicamentos.put(medicamento.getNome(), medicamento);
   }

    /**
     * Método que retorna uma String com a informação da industria
     * @return String com a informação da industria
     */
    @Override
    public String toString() {
        return  "Nome:" + getPrimeiroNome()+ "\n"+
                "email:" + getEmail() + "\n" +
                "contacto:" + contacto + "\n";
    }
}
