import java.net.Socket;
import java.time.LocalTime;
import java.io.*;
import java.util.*;
//import javax.swing.*;


public class Main{

    /**
     * La fenetre du logiciel
     */
    private static Fenetre window;

    /**
     * Le socket du client
     */
    private static Socket socks;

    /**
     * l'input Stream du client
     */
    private static InputStream inputStream;

    /**
     * l'object input stream du client
     */
    private static ObjectInputStream objectInputStream;

    /**
     * Le message recu par le serveur
     */
    private static Message recept;

    /**
     * Le thread pour l'écoute des reponses
     */
    private static Thread envoi;

    /**
     * Le type de thread pour l'écoute des reponses
     */
    private static ThreadEcouteClient threadClient;

    public static void main(String[] args) {
        
        window = new Fenetre();

    }

    /**
     * Le getter de la fenetre du logiciel
     * @return Fenetre, la fenetre du logiciel
     */
    public static Fenetre getFenetre(){
        return window;
    }

    /**
     * Methode permettant de se connecter au serveur
     */
    public static void connectionServer(){

        Connexion connexion = window.getConnexion();
        socks = null;

        //Connection au serveur
        try{
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "Tentative de connexion au serveur.") );

            socks = new Socket(connexion.getIpText(), Integer.parseInt(connexion.getPortText()));

            //Si la connexion est établie, on lance les threads d'écoute permanente
            if(socks != null){
                envoieMessage(new Message("<font color=black>Serveur</font>", LocalTime.now(), connexion.getNomText() + " vient de se connecter."));
                window.getChat().getDiscussion().ensureIndexIsVisible(window.getChat().getModel().getSize());

                threadClient = new ThreadEcouteClient();
                envoi = new Thread(threadClient);
                envoi.start();


            }
        }
        catch(Exception e){
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "Erreur de connexion au serveur.") );
        }

        
    }

    /**
     * Methode permettant d'envoyer un message au serveur
     * @param msg le message a envoyer
     */
    public static void envoieMessage(Message msg){

        //On créé le lien avec le serveur et on envoie le message
        try{
            OutputStream outputStream = socks.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(msg);
            outputStream.flush();

        }
        catch(IOException e){
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "erreur lecture socket") );
        }
    }

    /**
     * Methode permettant de se deconnecter du serveur
     */
    public static void deconnectionServer(){

        //On arrête le thread client et on ferme la fenetre
        try{
            threadClient.setStop(true);
           
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "Merci d'avoir utilisé ce chat !") );
            
            socks.close();
            window.getChat().getModel().removeAllElements();
        }
        catch(Exception e){
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "Probleme de deconnection.") );
        }

    }

    /**
     * Classe thread afin que le client écoute la reception de message
     */
    private static class ThreadEcouteClient implements Runnable{

        /**
         * Boolean permettant d'arrêter le thread
         */
        private boolean stop = false;

        /**
         * methode de lancement du thread
         */
        @Override
        public void run() {
            //Ecoute permanente et affichage a la reception d'un message
            while(!stop){
                try{
                    recept = null;
                    inputStream = socks.getInputStream();
                    objectInputStream = new ObjectInputStream(inputStream);
                    recept = (Message)objectInputStream.readObject();

                    //Si on a bien recu un message on clear la liste des clients, on l'update et on ajoute le message
                    if(recept != null){
                        ArrayList<User> listTmp = new ArrayList<>(recept.getUserList());
                        window.getConnectes().getModel().removeAllElements();

                        for(User user: listTmp){
                            window.getConnectes().getModel().addElement(user);
                        }
                        
                        window.getChat().getModel().addElement( recept);
                    }
                }
                catch(IOException e){
                    System.err.println("erreur lecture socket");
                }
                catch(ClassNotFoundException e){      
                    System.err.println("Erreur ClassNotFound");
                } 
            }
        }
        /**
         * Methode permettant d'arrêter le thread
         * @param stop le boolean a mettre a true pour arreter le thread
         */
        public void setStop(Boolean stop) {
            this.stop = stop;
        }   
    }
}