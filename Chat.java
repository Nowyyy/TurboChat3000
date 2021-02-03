import javax.swing.*;
import java.awt.event.*;
import java.time.LocalTime;
import javax.swing.JList;


/**
 * Classe représente la zone de chat
 */
public class Chat implements ActionListener {
    
    /**
     * le champs de texte contenant tous les messages
     */
   
    private DefaultListModel<Message> model = new DefaultListModel<>();
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
     * Constructeur de la zone de chat
     */
    public Chat(){
        bouton = new Bouton("Envoyer");

        bouton.getBouton().addActionListener(this);

        discussion = new JList<>(model);

        message = new JTextField();
        message.setSize(20, 10);
    }

    /**
     * Getter de la discussion
     * @return JTextField la zone de texte de la discussion
     */
    public JList<Message> getDiscussion(){
        return discussion;
    }

    /**
     * Getter de message
     * @return JTextField la zone de texte des messages écrits
     */
    public JTextField getMessage(){
        return message;
    }

    /**
     * Getter du bouton
     * @return JButton le bouton envoyer
     */
    public JButton getBouton(){
        return bouton.getBouton();
    }

    public DefaultListModel<Message> getModel(){
        return model;
    }

    /**
     * L'action à effectuer quand le bouton est pressé
     * @param arg0 Paramètre inutilisé
    */
    @Override
    public void actionPerformed(ActionEvent arg0){
        System.out.println(message.getText());
        //discussion.setText(message.getText());
        //model.addElement( new Message("Axel", LocalTime.now(), message.getText()) );
        Main.envoieMessage(new Message("Axel", LocalTime.now(), message.getText()));
    }
}
