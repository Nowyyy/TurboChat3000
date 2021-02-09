import javax.swing.*;
import java.awt.event.*;
import java.time.LocalTime;
import javax.swing.JList;


/**
 * Classe représente la zone de chat
 */
public class Chat implements ActionListener {
    
    /**
     * le moodèle de la Jlist
     */
    private DefaultListModel<Message> model = new DefaultListModel<>();

    /**
     * La liste contenant la discussion
     */
    private JList<Message> discussion;

    /**
     * la zone de texte pour la saisie des messages de l'utilisateur
     */
    private JTextField message;

    /**
     * le bouton pour envoyer le message de l'utilisateur
     */
    private Bouton bouton;

    /**
     * Le lien vers la classe de connexion pour acceder a ses méthodes
     */
    private Connexion connexion;

    /**
     * Constructeur de la zone de chat
     * @param connec, le lien vers la classe de connexion pour acceder a ses méthodes
     */
    public Chat(Connexion connec){
        bouton = new Bouton("Envoyer");

        bouton.getBouton().setEnabled(false);
        bouton.getBouton().addActionListener(this);
        connexion = connec;
        discussion = new JList<>();
        discussion.setModel(model);

        message = new JTextField();
        message.setSize(20, 10);
    }

    /**
     * Getter de la discussion
     * @return JList, la zone de texte de la discussion
     */
    public JList<Message> getDiscussion(){
        return discussion;
    }

    /**
     * Getter de message
     * @return JTextField, la zone de texte des messages écrits
     */
    public JTextField getMessage(){
        return message;
    }

    /**
     * Getter du bouton
     * @return JButton, le bouton envoyer
     */
    public JButton getBouton(){
        return bouton.getBouton();
    }

    /**
     * Getter du model de la Jlist
     * @return DefaultListModel le modèle de la JList
     */
    public DefaultListModel<Message> getModel(){
        return model;
    }

    /**
     * L'action à effectuer quand le bouton est pressé
     * @param arg0 Paramètre inutilisé
    */
    @Override
    public void actionPerformed(ActionEvent arg0){
        
        Main.envoieMessage(new Message(connexion.getNomText(), LocalTime.now(), message.getText()));
        message.setText("");
    }
}
