import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Classe Fenetre, classe fille de JFrame qui permet de créer une fenetre personnalisée
*/
public class Fenetre extends JFrame{
    
    /**
     * La largeur de la fenetre
     */
    private int WIN_WIDTH = 680;

    /**
     * la hauteur de la fenetre
     */
    private int WIN_HEIGHT = 680;

    /**
     * numéro de version
     */
    private static final long serialVersionUID = 1;

    /**
     * La fenetre du logiciel
     */
    private JPanel principalPanel;

    /**
     * la connection de l'utilisateur
     */
    private Connexion connexion;

    /**
     * La section chat du logiciel
     */
    private Chat chat;

    /**
     * bouton de connection
     */
    JButton connectBtn;

    /**
     * tableau contenant les champs a ecouter pour la connexion
     */
    JTextField []llFields = new JTextField[3];

    /**
     * le champ pour rentrer le nom
     */
    JTextField nom;

    /**
     * le champ pour rentrer l'adresse ip
     */
    JTextField ip;

    /**
     * le champ pour rentrer le port
     */
    JTextField port; 
    
    /**
     * La zone des connectes
     */
    Connectes connect;

    /**
     * Constructeur de la fenetre
     */
    public Fenetre(){

        //Creation de la fenetre principale
        super("TurboChat 3000");

        this.setSize(WIN_WIDTH, WIN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        principalPanel = new JPanel();
        principalPanel.setLayout(new BorderLayout());

        this.setContentPane(principalPanel);

        //Initialisation des boxes
        Box panelOuest = Box.createVerticalBox();
        Box panelEst = Box.createVerticalBox();
        Box bottom = Box.createHorizontalBox();
        Box panelNord1 = Box.createHorizontalBox();
        Box panelNord2 = Box.createVerticalBox();
        Box panelNord3 = Box.createVerticalBox();
        Box panelNord4 = Box.createHorizontalBox();
        Box panelNord5 = Box.createHorizontalBox();
        Box panelNord6 = Box.createHorizontalBox();
        Box panelNord7 = Box.createHorizontalBox();

        panelEst.setVisible(false);
        panelOuest.setVisible(false);
        bottom.setVisible(false);

        //Positionnement des boutons du haut
        connexion = new Connexion(new JTextField(), new JTextField(), new JTextField(), panelEst, panelOuest, bottom);

        principalPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //Installation du listener de connection et creations des JTextFields correspondant au listener de connection
        //Le listener sert a verifier que tous les champs de connexions sont remplis avant d'autoriser le clic sur le boutons
        LabelListener ll = new LabelListener();
        connectBtn = connexion.getBouton();

        nom = connexion.getNom();
        llFields[0] = nom;
        llFields[0].getDocument().addDocumentListener(ll);

        ip = connexion.getIp();
        llFields[1] = ip;
        llFields[1].getDocument().addDocumentListener(ll);

        port = connexion.getPort();
        llFields[2] = port;
        llFields[2].getDocument().addDocumentListener(ll);

        
        //Ajout de la zone horizontale coupée en trois zones verticales
        principalPanel.add(panelNord1, BorderLayout.NORTH);
        panelNord1.add(panelNord2);
        panelNord1.add(Box.createRigidArea(new Dimension(50, 0)));
        panelNord1.add(panelNord3);
        panelNord1.setBorder(new EmptyBorder(0, 0, 10, 0));

        //Ajout des deux zones horizontales dans la première zone verticale
        panelNord2.add(panelNord4);
        panelNord2.add(Box.createRigidArea(new Dimension(0, 10)));
        panelNord2.add(panelNord5);

        //Ajout de la zone horizontale et du bouton dans la troisième zone verticale
        panelNord3.add(panelNord6);
        panelNord3.add(Box.createRigidArea(new Dimension(0, 10)));
        panelNord3.add(panelNord7);

        //Ajout des zones nom et ip dans la première zone verticale
        panelNord4.add(new JLabel("Nom"));
        panelNord4.add(Box.createRigidArea(new Dimension(10, 0)));
        panelNord4.add(nom);

        panelNord5.add(new JLabel("IP"));
        panelNord5.add(Box.createRigidArea(new Dimension(29, 0)));
        panelNord5.add(ip);

        //Ajout du bouton et de la zone port pour la troisième zone verticale
        panelNord6.add(connectBtn, BorderLayout.CENTER);
       
        panelNord7.add(new JLabel("Port"));
        panelNord7.add(Box.createRigidArea(new Dimension(10, 0)));
        panelNord7.add(port);

        //Emplacement de la liste des utilisateurs
        principalPanel.add(panelOuest, BorderLayout.WEST);
        panelOuest.add(new JLabel("Connectés", SwingConstants.CENTER), BorderLayout.CENTER);

        connect = new Connectes();
        JScrollPane affichageConnecte = new JScrollPane(connect.getConnectes(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        affichageConnecte.setPreferredSize(new Dimension(100, 400));

        panelOuest.add(affichageConnecte);
        panelOuest.setSize(70, 100);

        //Emplacement du chat
        chat = new Chat(connexion);
        
        panelEst.setBorder(new EmptyBorder(0, 30, 0, 0));

        principalPanel.add(panelEst, BorderLayout.CENTER);

        panelEst.add(new JLabel("Discussion", SwingConstants.CENTER), BorderLayout.CENTER);

        JScrollPane affichageChat = new JScrollPane(chat.getDiscussion(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        affichageChat.setPreferredSize(new Dimension(0, 400));

        panelEst.add(affichageChat, BorderLayout.CENTER);
        panelEst.add(Box.createRigidArea(new Dimension(0, 10)));
        panelEst.add(new JLabel("Message", SwingConstants.CENTER), BorderLayout.CENTER);

        EnvoiListener ml = new EnvoiListener();
        chat.getMessage().getDocument().addDocumentListener(ml);

        panelEst.add(chat.getMessage());
        panelEst.add(Box.createRigidArea(new Dimension(0, 20)));
        panelEst.add(bottom);

        //Bouton d'envoi de message
        bottom.add(chat.getBouton(), BorderLayout.CENTER);
        bottom.setBorder(new EmptyBorder(0, 48, 0, 0));

        //Visibilité de la fenetre
        this.setVisible(true);
    }

    /**
     * Getter de la connexion
     * @return Connexion, la connexion du client
     */
    public Connexion getConnexion(){
        return connexion;
    }

    /**
     * Getter du chat
     * @return Chat, le chat
     */
    public Chat getChat(){
        return chat;
    }

    /**
     * Getter du Connectes
     * @return Connectes, la liste des connectés
     */
    public Connectes getConnectes(){
        return connect;
    }

    /*
     * Getter du panneau principal
     * @return
    public JPanel getPrincipalPanel(){
        return principalPanel;
    }*/
    

    /**
     * Classe privée permettant de creer un listener pour la connexion
     */
    private class LabelListener implements DocumentListener{
        @Override
        public void insertUpdate(DocumentEvent e) {
            updated();
        }
    
        @Override
        public void removeUpdate(DocumentEvent e) {
            updated();
        }
    
        @Override
        public void changedUpdate(DocumentEvent e) {
            updated();
        }
    
        /**
         * On verifie que les champs sont bien remplis. Si oui, on autorise le click sur le bouton
         */
        private void updated() {

            /**
             * boolean indiquant si tous les champs sont remplis ou non
             */
            boolean enabled = true;
            
            for (int i =0; i<llFields.length; i++) {
                if (llFields[i].getText().trim().equals("")) {
                    enabled = false;
                    break;
                }
            }
            if(enabled){
                connectBtn.setEnabled(true);
            }
            else{
                connectBtn.setEnabled(false);
            }
        }
    }

    /**
     * Classe privée permettant de creer un listener pour la l'envoi de message
     */
    private class EnvoiListener implements DocumentListener{
        @Override
        public void insertUpdate(DocumentEvent e) {
            updated();
        }
    
        @Override
        public void removeUpdate(DocumentEvent e) {
            updated();
        }
    
        @Override
        public void changedUpdate(DocumentEvent e) {
            updated();
        }
    
        /**
         * On verifie que le champs est bien rempli, si oui on autorise le click
         */
        private void updated() {

            /**
             * boolean indiquant si tous les champs sont remplis ou non
             */
            boolean enabled = true;

            if (chat.getMessage().getText().trim().equals("")) {
                enabled = false;
            }
            if(enabled){
                chat.getBouton().setEnabled(true);
            }
            else{
                chat.getBouton().setEnabled(false);
            }
        }
    }
}
