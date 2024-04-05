package Utilizador;
import Industria.Industria;


/**
 * Classe RegistHandler que cria um utilizador ou indústria
 */
public class RegistHandler {
    /**
     * Cria um utilizador
     * @param primeiroNome primeiro nome do utilizador
     * @param ultNome último nome do utilizador
     * @param password password do utilizador
     * @param email email do utilizador
     * @param papel papel do utilizador
     * @return utilizador criado
     */
    public Utilizador registarUtilizador(String primeiroNome, String ultNome, String password, String email, String papel){
        Utilizador u = new Utilizador();
        u.setPrimeiroNome(primeiroNome);
        u.setUltimoNome(ultNome);
        u.setPassword(password);
        u.setEmail(email);
        u.setPapel(papel);
        return u;
    }

    /**
     * Cria uma indústria
     * @param primeiroNome primeiro nome da indústria
     * @param password password da indústria
     * @param papel papel da indústria
     * @param contacto contacto da indústria
     * @return indústria criada
     */
    public Industria registarIndustria(String primeiroNome, String password, String papel,int contacto){
        Industria i = new Industria(primeiroNome, password, papel);
        i.setContacto(contacto);
        return i;
    }
}
