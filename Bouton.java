import javax.swing.JButton;

/**
 * Classe représentant un bouton
 */
public class Bouton {
    /**
     * variable representant le bouton
     */
    private JButton bouton;

    /**
     * Constructeur de la classe
     * @param textBouton le texte ecrit sur le bouton
     */
    public Bouton(String textBouton){
        bouton = new JButton(textBouton);
    }

    /**
     * Getter du boutton
     * @return bouton, le bouton
     */
    public JButton getBouton(){
        return bouton;
    }
    
}
