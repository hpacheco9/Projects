package Testes;
import Industria.Industria;
import InteracaoAlimentar.InteracaoAlimentar;
import SubstanciaAtiva.SubstanciaAtiva;
import Utilizador.Utilizador;
import org.junit.jupiter.api.Test;
import Sistema.Sistema;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe Testes que testa as classes do projeto.
 */
public class Testes {
    private static Sistema sistema = new Sistema();


    /**
     * Teste para verificar se o método pesquisarContacto funciona corretamente.
     */
    @Test
    public void pesquisarContacto() {
        sistema.startup();
        Industria i_existente = sistema.pesquisarContacto("Anastrozol PLS");
        assertEquals( 210996271, i_existente.getContacto());
        Industria i_inexistente = sistema.pesquisarContacto("asdkjansd");
        assertNull(i_inexistente);
    }

    /**
     * Teste para verificar se o método pesquisarInteracoes funciona corretamente.
     */
    @Test
    public void pesquisarInteracoes() {
        sistema.startup();
        // Interacao existente e medicamento existente
        ArrayList<InteracaoAlimentar> resultado = sistema.pesquisarInteracoes("Cardipril10 mgComprimido");
        assertNotNull(resultado);
        // Interacao inexistente e medicamento inexistente
        ArrayList<InteracaoAlimentar> resultado2 = sistema.pesquisarInteracoes("Nasjdasd10 mgPoro");
        assertTrue(resultado2.isEmpty());
        // Interacao inexistente e medicamento existente
        ArrayList<InteracaoAlimentar> resultado3 = sistema.pesquisarInteracoes("CicadermaAssociaçãoPomada");
        assertTrue(resultado3.isEmpty());
    }

    /**
     * Teste para verificar se o método adicionarMedicamento funciona corretamente.
     */
    @Test
    public void adicionarMedicamento() {
        sistema.startup();
        // Medicamento inextistente e Indústria existente
        ArrayList<SubstanciaAtiva> substanciaAtivas = new ArrayList<>();
        substanciaAtivas.add(new SubstanciaAtiva());
        sistema.adicionarMedicamento("Durina", "Comprimido", "1mg", substanciaAtivas, "Tecnimede");
        assertTrue(sistema.verificaMedicamento("Durina1mgComprimido"));
        assertTrue(sistema.verificaMedicamentoIndustria("Durina", "Tecnimede"));
        // Medicamento existente
        int tamanho_anterior = sistema.getMedicamentos().size();
        int tamanho_anterior_industria = sistema.getIndustria("Tecnimede").getMedicamentos().size();
        sistema.adicionarMedicamento("Durina", "Comprimido", "1mg", substanciaAtivas, "Tecnimede");
        assertEquals(tamanho_anterior, sistema.getMedicamentos().size());
        assertEquals(tamanho_anterior_industria, sistema.getIndustria("Tecnimede").getMedicamentos().size());
    }

    /**
     * Teste para verificar se o método adicionarSubstancia funciona corretamente.
     */
    @Test
    public void adicionarSubstancia() {
        sistema.startup();
        // Substancia inextistente
        sistema.adicionarSubstanciaAtiva("Pkl");
        assertTrue(sistema.verificaSubstancia("Pkl"));
        // Substancia existente
        int tamanho_anterior = sistema.getSubstanciasAtivas().size();
        sistema.adicionarSubstanciaAtiva("Pkl");
        assertEquals(tamanho_anterior, sistema.getSubstanciasAtivas().size());
    }

    /**
     * Teste para verificar se o método adicionarInteracao funciona corretamente.
     */
    @Test
    public void adicionarInteracao() {
        sistema.startup();
        // Interacao inextistente e substancia existente
        int tamanho_antes = sistema.getInteracoesAlimentares().size();
        SubstanciaAtiva substancia = new SubstanciaAtiva();
        substancia.setNome("Imidapril");
        sistema.adicionarInteracaoAlimentar(substancia, "...", "banana", "Cancelamento", 3, "Infarmed");
        assertEquals(tamanho_antes + 1, sistema.getInteracoesAlimentares().size());
        // Interacao existente e substancia existente
        int tamanho_anterior = sistema.getInteracoesAlimentares().size();
        sistema.adicionarInteracaoAlimentar(substancia, "...", "banana", "Cancelamento", 3, "Infarmed");
        assertEquals(tamanho_anterior, sistema.getInteracoesAlimentares().size());
    }

    /**
     * Teste para verificar se o login funciona corretamente.
     */
    @Test
    public void Login() {
        sistema.startup();
        // Utilizador Existente
        Utilizador u = sistema.login("Tecnimede@Tecnimede.com", "123");
        assertEquals("Tecnimede@Tecnimede.com", u.getEmail());
        // Password Errada
        assertNull(sistema.login("Tecnimede@Tecnimede.com", "1234"));
        // Email errado
        assertNull(sistema.login("Tejsd@Tejsd.com", "123"));
    }

    /**
     * Teste para verificar se o registo funciona corretamente.
     */
    @Test
    public void Registar() {
        sistema.startup();
        // Utilizador Inexistente
        sistema.registar("Luis", "Fonseca", "luisf@gmail.com", "123", "Farmaceutico", 0);
        assertTrue(sistema.verificaUtilizador("luisf@gmail.com"));
        // Utilizador Existente
        int tamanho_anterior = sistema.getUtilizadores().size();
        sistema.registar("Luis", "Fonseca", "luisf@gmail.com", "123", "Farmaceutico", 0);
        assertEquals(tamanho_anterior, sistema.getUtilizadores().size());
    }
}
