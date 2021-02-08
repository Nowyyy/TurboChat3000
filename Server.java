import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.*;
import java.net.ServerSocket;
import java.util.*;

/**
 * Classe gerant le serveur. Classe a lancer pour lancer le serveur
 */
public class Server {

    /**
     * Le socket du serveur
     */
    private static ServerSocket serveur;

    /**
     * Tableau contenant la liste des threads des clients connectés
     */
    private static ArrayList<ThreadEcouteServer> listeClients;

    public static void main(String []args){
        int port = 64998;

        serveur = null;

        listeClients = new ArrayList<ThreadEcouteServer>();

        //Creation serveur
        try{
            serveur = new ServerSocket(port);
            System.out.println("Serveur connecté");
        }
        catch(Exception e){
            System.err.println("Erreur connexion serveur");
        }

        //Connexion d'un client et envoi vers la creation du thread
        try{

            do{
                listeClients.add(new ThreadEcouteServer(serveur.accept() ) );

                System.out.println("Connecté");

            }while(true);
        }
        catch(Exception e){
            System.err.println("Erreur acceptation client");
        } 
        
        //Fermeture du serveur
        try{
            serveur.close();
            System.out.println("Serveur fermé");
        }
        catch(Exception e){
            System.err.println("Erreur fermeture serveur");
        }

    }

    /**
     * Classe permettant de gerer les threads d'écoute serveur
     */
    private static class ThreadEcouteServer extends Thread{
        private boolean stop = false;
        private Socket client;

        /**
         * Constructeur du thread
         * @param c le socket correspondant au thread
         */
        public ThreadEcouteServer(Socket c){
            client = c;
            this.start();
        }

        /**
         * Methode permettant de lancer le thread
         */
        @Override
        public void run() {

            while(!stop){

                /**
                 * On ecoute en boucle la reception de message venan du socket et on transfert le message a tout le monde
                 */
                try{
                    InputStream inputStream = null;
                    ObjectInputStream objectInputStream = null;
                    OutputStream outputStream = null;
                    ObjectOutputStream objectOutputStream = null;
                    Message msg;
                    while(client.isClosed()==false){

                        inputStream = client.getInputStream();
                        objectInputStream = new ObjectInputStream(inputStream);
                        msg = (Message)objectInputStream.readObject();

                        for(int i = 0; i< listeClients.size(); i++){
                            if(listeClients.get(i) != this){
                                outputStream = listeClients.get(i).client.getOutputStream();
                                objectOutputStream = new ObjectOutputStream(outputStream);
                                objectOutputStream.writeObject(msg);
                                outputStream.flush(); 
                            }
                        }
                    }
                    setStop(true);
                    client.close();

                    listeClients.remove(this);
                }
                catch(IOException e){
                    try{
                        client.close();
                    }
                    catch(Exception e1){
                        System.out.println("Erreur fermeture");
                    }
                    System.out.println("Erreur socket");
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
