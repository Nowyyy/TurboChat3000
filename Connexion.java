import javax.swing.*;
import java.awt.event.*;


/**
 * Classe permettant de gérer la connexion de l'utilisateur
 */
public class Connexion implements ActionListener{

    /**
     * le bouton de connection
     */
    private Bouton bouton;

    /**
     * la zone de texte contenant le nom de l'utilisateur
     */
    private JTextField nom;
    
    /**
     * la zone de texte contenant l'ip de l'utilisateur
     */
    private JTextField ip;
    
    /**
     * la zone de texte contenant le port utilisé par l'utilisateur
     */
    private JTextField port;

    /**
     * le nom de l'utilisateur
     */
    private String nomText;
    
    /**
     * l'adresse ip a utiliser
     */
    private String ipText;

    /**
     * le port a utiliser
     */
    private String portText;

    /**
     * Si le client est connecté ou non
     */
    private boolean connected;

    /**
     * Lien vers la box Est de la fenêtre
     */
    private Box est;

    /**
     * Lien vers la box Ouest de la fenêtre
     */
    private Box ouest;

    /**
     * Lien vers la box Bottom de la fenêtre
     */
    private Box bottom;

    /**
     * Constructeur de la classe
     * @param nom la zone de texte contenant le nom de l'utilisateur
     * @param ip la zone de texte contenant l'ip de l'utilisateur
     * @param port la zone de texte contenant le port utilisé par l'utilisateur
     * @param est Lien vers la box Est de la fenêtre
     * @param ouest Lien vers la box Ouest de la fenêtre
     * @param bottom Lien vers la box Bottom de la fenêtre
     */
    public Connexion(JTextField nom, JTextField ip, JTextField port, Box est, Box ouest, Box bottom){
        bouton = new Bouton("Connection");
        bouton.getBouton().addActionListener(this);
        bouton.getBouton().setEnabled(false);
        this.nom = nom;
        this.nom.setColumns(2);
        this.ip = ip;
        this.ip.setColumns(2);
        this.port = port;
        this.port.setColumns(2);
        nomText = null;
        ipText = null;
        portText = null;
        connected = false;
        this.est = est;
        this.bottom = bottom;
        this.ouest = ouest;
    }

    /**
     * Getter JTextField nom
     * @return JTextField le nom de l'utilisateur
     */
    public JTextField getNom(){
        return nom;
    }

    /**
     * Getter JTextField port
     * @return JTextField le port a utiliser
     */
    public JTextField getPort(){
        return port;
    }

    /**
     * Getter JTextField ip
     * @return JTextField l'ip a utiliser
     */
    public JTextField getIp(){
        return ip;
    }

    /**
     * getter du bouton de connexion
     * @return JButton le bouton de connexion
     */
    public JButton getBouton(){
        return bouton.getBouton();
    }

    /**
     * Getter du boolean de connexion
     * @return boolean, si le client est connecté ou non
     */
    public boolean getConnected(){
        return connected;
    }

    /**
     * Getter de l'ip
     * @return String, le texte de l'ip
     */
    public String getIpText(){
        return ipText;
    }

    /**
     * Getter du nom
     * @return String, le texte du nom
     */
    public String getNomText(){
        return nomText;
    }

    /**
     * Getter du port
     * @return Stringn le texte du port
     */
    public String getPortText(){
        return portText;
    }

    /**
     * L'action à effectuer quand le bouton est pressé
     * @param arg0 Paramètre inutilisé
    */
    @Override
    public void actionPerformed(ActionEvent arg0){

        //On rend les champs non editable a la connexion et la partie basse visible
        if(connected == false){
            nomText = nom.getText();
            ipText = ip.getText();
            portText = port.getText();
            nom.setEditable(false);
            ip.setEditable(false);
            port.setEditable(false);
            bouton.getBouton().setText("Deconnection");
            connected = true;
            this.est.setVisible(true);
            this.ouest.setVisible(true);
            this.bottom.setVisible(true);
            

            Main.connectionServer();
        }

        //On rend les champs editables a la deconnexion et la partie basse invisible
        else{
            nom.setEditable(true);
            ip.setEditable(true);
            port.setEditable(true);
            bouton.getBouton().setText("Connection");
            connected = false;
            this.est.setVisible(false);
            this.ouest.setVisible(false);
            this.bottom.setVisible(false);

            Main.deconnectionServer();
        }
    }
}
