import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.*;
import java.net.ServerSocket;

public class Server {

    private static ServerSocket serveur;
    private static Socket client;
    public static void main(String []args){
        int port = 64998;

        serveur = null;
        client = null;

        //Creation serveur
        try{
            serveur = new ServerSocket(port);
            System.out.println("Serveur connecté");
        }
        catch(Exception e){
            System.err.println("Erreur connexion serveur");
        }

        //Connexion d'un client
        try{
            client = serveur.accept();
            System.out.println("Connecté");
        }
        catch(Exception e){
            System.err.println("Erreur acceptation client");
        }

        //Reception d'un message
        try{
            InputStream inputStream = client.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Message msg = (Message)objectInputStream.readObject();
            System.out.println(msg);
        }
        catch(IOException e){
            System.err.println("Erreur inputStream");
        }
        catch(ClassNotFoundException e){      
            System.err.println("Erreur ClassNotFound");
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
}
