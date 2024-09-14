package Medicamento;
import SubstanciaAtiva.SubstanciaAtiva;
import java.util.ArrayList;


/**
 * Classe Medicamento que representa um medicamento.
 */
public class Medicamento {

    private String nome;
    private String forma;
    private String dosagem;
    private ArrayList<SubstanciaAtiva> substanciaAtivas;


    /**
     * Construtor default da classe Medicamento
     */
    public Medicamento(){
        nome = "";
        forma = "";
        dosagem = "";
        substanciaAtivas = new ArrayList<>();
    }

    /**
     * Setter do nome do medicamento
     * @param nome nome do medicamento
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Setter da forma do medicamento
     * @param forma forma do medicamento
     */
    public void setForma(String forma) {
        this.forma = forma;
    }

    /**
     * Getter do nome do medicamento
     * @return nome do medicamento
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter da dosagem do medicamento
     * @param dosagem dosagem do medicamento
     */
    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    /**
     * Adiciona uma substância ativa à lista de substâncias ativas do medicamento
     * @param substanciaAtiva substância ativa a adicionar
     */
    public void addSubstancia(SubstanciaAtiva substanciaAtiva){
        if (!substanciaAtivas.contains(substanciaAtiva)){
            this.substanciaAtivas.add(substanciaAtiva);
        }
    }

    /**
     * Getter da forma do medicamento
     * @return forma do medicamento
     */
    public String getForma() {
        return forma;
    }

    /**
     * Getter da dosagem do medicamento
     * @return dosagem do medicamento
     */
    public String getDosagem() {
        return dosagem;
    }

    /**
     * Getter da lista de substancias ativas do medicamento
     * @return lista de substâncias ativas do medicamento
     */
    public ArrayList<SubstanciaAtiva> getSubstanciaAtivas() {
        return substanciaAtivas;
    }

    public String getSubstanciaAtivasString(){
        StringBuilder s = new StringBuilder();
        if (substanciaAtivas.size() == 1){
            if (substanciaAtivas.getFirst().getNome() == null){
                s.append("");
            } else{
                s.append(substanciaAtivas.getFirst().getNome());
            }
        } else {
            for (int i = 0; i < substanciaAtivas.size(); i++) {
                if (i == substanciaAtivas.size() - 1) {
                    s.append(substanciaAtivas.get(i).getNome());
                } else {
                    s.append(substanciaAtivas.get(i).getNome()).append("|");
                }
            }
        }
        return s.toString();
    }
    /**
     * Método toString da classe Medicamento
     * @return nome, forma, dosagem e substâncias ativas do medicamento
     */
    @Override
    public String toString() {
        return
                "Nome: " + nome + '\n' +
                "   Forma: " + forma + '\n' +
                "   Dosagem: " + dosagem + '\n' +
                "   Substâncias Ativas: " + substanciaAtivas + "\n";
    }
}