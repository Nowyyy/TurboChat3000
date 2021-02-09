import javax.swing.*;

/**
 * Classe qui permet de gerer les utilisateurs connectés
 */
public class Connectes{

    /**
     * le moodèle de la Jlist
     */
    private DefaultListModel<User> model = new DefaultListModel<>();

    /**
     * La liste contenant la discussion
     */
    private JList<User> connectes;

    /**
     * Constructeur
     */
    public Connectes(){
        this.connectes = new JList<>(model);
        this.connectes.setSize(30, 50);
    }

    /**
     * Getter des connecté
     * @return JList la zone de texte contenant les utilisateurs connectés
     */
    public JList<User> getConnectes(){
        return connectes;
    }

    /**
     * Getter du model de la Jlist
     * @return DefaultListModel le modèle de la JList
     */
    public DefaultListModel<User> getModel(){
        return model;
    }
}
