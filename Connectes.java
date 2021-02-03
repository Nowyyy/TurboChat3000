import javax.swing.*;

/**
 * Classe qui permet de gerer les utilisateurs connectés
 */
public class Connectes{


    /**
     * la zone de texte contenant les utilisateurs connectés
     */
    private JTextField connectes;

    /**
     * Constructeur
     */
    public Connectes(){
        this.connectes = new JTextField("Aucun Connecté");
        this.connectes.setSize(30, 50);
        this.connectes.setEditable(false);
    }

    /**
     * Getter des connecté
     * @return JTextField la zone de texte contenant les utilisateurs connectés
     */
    public JTextField getConnectes(){
        return connectes;
    }
}
