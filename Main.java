import java.net.Socket;
import java.time.LocalTime;
import java.io.*;


public class Main{

    private static Fenetre window;
    private static Socket socks;
    private static InputStream inputStream;
    private static ObjectInputStream objectInputStream;
    private static Message recept;
    private static Thread envoi;
    private static ThreadEcouteClient threadClient;

    public static void main(String[] args) {
        
        window = new Fenetre();

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
        }
        catch(Exception e){
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "Erreur de connexion au serveur.") );
        }

        //Ecoute permanente des connectés

        if(socks != null){

            envoieMessage(new Message("<font color=black>Serveur</font>", LocalTime.now(), connexion.getNomText() + " vient de se connecter."));

            threadClient = new ThreadEcouteClient();
            envoi = new Thread(threadClient);
            envoi.start();
        }
    }

    /**
     * Methode permettant d'envoyer un message au serveur
     * @param msg le message a envoyer
     */
    public static void envoieMessage(Message msg){

        try{
            OutputStream outputStream = socks.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(msg);
            outputStream.flush();
            msg.setColor("green");

            if(msg.getName() != "<font color=black>Serveur</font>"){
                msg.setName("(You)" + msg.getName());
            }
            window.getChat().getModel().addElement( msg );
        }
        catch(IOException e){
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "erreur lecture socket") );
        }
    }

    /**
     * Methode permettant de se deconnecter du serveur
     */
    public static void deconnectionServer(){

        Connexion connexion = window.getConnexion();

        try{
            envoieMessage(new Message("Serveur", LocalTime.now(), connexion.getNomText() + " vient de se deconnecter."));
            threadClient.setStop(true);
           
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "Merci d'avoir utilisé ce chat !") );
            
            socks.close();
            System.exit(0);
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

                    if(recept != null){
                        window.getChat().getModel().addElement( recept );
                    }
                }
                catch(IOException e){
                    window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "erreur lecture socket") );
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