import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.*;
import java.net.ServerSocket;
import java.util.*;
import java.time.LocalTime;

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

    /**
     * Tableau contenant les différentes couleurs non utilisées
     */
    private static ArrayList<String> couleurVide;

    /**
     * Tableau contenant les différentes couleurs utilisées
     */
    private static ArrayList<String> couleurUse;

    /**
     * Tableau contenant la liste des utilisateurs connectés
     */
    private static ArrayList<User> userList = new ArrayList<User>();

    public static void main(String []args){

        couleurUse = new ArrayList<String>();
        couleurVide = new ArrayList<String>();
        couleurVide.add("red");
        couleurVide.add("blue");
        couleurVide.add("purple");
        couleurVide.add("olive");
        couleurVide.add("maroon");
        couleurVide.add("teal");
        couleurVide.add("fuchsia");

        //Port a changer au besoin
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
        /**
         * Le boolean d'arrêt du thread
         */
        private boolean stop = false;

        /**
         * Le socket du client
         */
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

            //On choisi une couleur parmi celles non utilisées
            //Et on la met dans le tableau utilisé
            String couleurActuelle;
            User userTmp = null;

            if(!couleurVide.isEmpty()){
                couleurActuelle = couleurVide.get(0);
                couleurUse.add(couleurVide.get(0));
                couleurVide.remove(0);
            }
            else{
                couleurActuelle = couleurUse.get(0);
            }

            //On ecoute en boucle la reception de message
            while(!stop){
                try{
                    InputStream inputStream = null;
                    ObjectInputStream objectInputStream = null;
                    OutputStream outputStream = null;
                    ObjectOutputStream objectOutputStream = null;
                    Message msg;
                    int indice = -1;
                    boolean exist = false;

                    while(client.isClosed()==false){
                        inputStream = client.getInputStream();
                        objectInputStream = new ObjectInputStream(inputStream);
                        msg = (Message)objectInputStream.readObject();
                        msg.setColor(couleurActuelle);
                        
                        //Si on a pas ajouter d'utilisateur dans ce thread
                        if(exist == false){
                            //Et que l'utilisateur du message ne s'appelle pas "serveur"
                            if(!msg.getName().contains("Serveur")){
                                //On verifie si l'utilisateur existe si la liste n'est pas vide
                                if(!userList.isEmpty()){
                                    for(User user : userList){
                                        if(msg.getName().contains(user.getName()) && msg.getColor().contains(user.getColor())){
                                            exist = true;
                                        }
                                    }
                                }
                            }
                            //Si l'utilisateur s'appelle serveur, on extrait le nom de l'utilisateur a la connexion et on l'ajoute
                            else{
                                exist = true;
                                String tmp1 = msg.getMess();
                                String tmp2 = tmp1.substring(0, tmp1.indexOf(" "));
                                userTmp = new User(tmp2, msg.getColor());
                                userList.add(userTmp);
                            }
                        
                            //Si on a toujours pas ajouté d'utilisateur, on l'ajoute
                            if(exist == false){
                                exist = true;
                                userTmp = new User(msg.getName(), msg.getColor());
                                userList.add(userTmp);
                            }
                            indice = userList.size();
                            System.out.println(indice);
                        }
                        
                        //On copie la liste des utilisateurs du serveur dans la liste des utilisateurs du message
                        msg.setUserList(userList);

                        //On parcours la liste des sockets clients et on envoie le message a tout le monde
                        for(int i = 0; i< listeClients.size(); i++){
                            outputStream = listeClients.get(i).client.getOutputStream();
                            objectOutputStream = new ObjectOutputStream(outputStream);
                            objectOutputStream.writeObject(msg);
                            outputStream.flush(); 
                        }
                    }

                    //A l'interruption du thread, on créé un message de deconnexion
                    Message msgTmp = new Message("<font color=black>Serveur</font>", LocalTime.now(), userList.get(listeClients.indexOf(this)).getName() + " vient de se deconnecter.");
                    //On supprime l'utilisateur qui se deconnecte
                    userList.remove(listeClients.indexOf(this));
                    //On ajoute la liste des utilisateurs a la liste du message
                    msgTmp.setUserList(userList);

                    //On supprime le socket du client qui se déconnecte
                    listeClients.remove(this);

                    //On envoie le message de deconnection a tous les sockets restants
                    for(int i = 0; i< listeClients.size(); i++){
                        outputStream = listeClients.get(i).client.getOutputStream();
                        objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(msgTmp);
                        outputStream.flush(); 
                    }
                    
                    //On demande l'arrêt du thread
                    setStop(true);
                    client.close();
                    
                    //On libere la couleur de l'utilisateur avant la fermeture du thread
                    couleurVide.add(0, couleurActuelle);
                    if(!couleurUse.isEmpty()){
                        couleurUse.remove(couleurActuelle);
                    }

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
