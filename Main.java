import java.net.Socket;
import java.time.LocalTime;
import java.io.*;

public class Main{

    private static Fenetre window;
    private static Socket socks;
    public static void main(String[] args) {
        
        window = new Fenetre();

    }

    /**
     * Methode permettant de se connecter au serveur
     */
    public static void connectionServer(){

        Connexion connexion = window.getConnexion();
        socks = null;

        try{
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "Tentative de connexion au serveur.") );

            socks = new Socket(connexion.getIpText(), Integer.parseInt(connexion.getPortText()));
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "Vous êtes connecté au serveur.") );
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

        try{
            OutputStream outputStream = socks.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            Thread envoi = new Thread(new Runnable(){
                @Override
                 public void run() {
                    while(true){
                        try{
                            objectOutputStream.writeObject(msg);
                            outputStream.flush();
                        }
                        catch(IOException e){
                            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "erreur lecture socket") );
                        }
                    }
                 }
            });
            envoi.start();
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
            socks.close();
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "Vous êtes déconnecté du serveur.") );
        }
        catch(Exception e){
            window.getChat().getModel().addElement( new Message("Serveur", LocalTime.now(), "Probleme de deconnection.") );
        }

    }
}