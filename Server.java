import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.*;
import java.net.ServerSocket;
import java.util.*;

public class Server {

    private static ServerSocket serveur;
    private static Socket client;
    private static List<Socket> listeClients;
    public static void main(String []args){
        int port = 64998;

        serveur = null;
        client = null;

        listeClients = new ArrayList<Socket>();

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
            InputStream inputStream;
            ObjectInputStream objectInputStream;
            Message msg;
            while(client.isClosed()==false){

                inputStream = client.getInputStream();
                objectInputStream = new ObjectInputStream(inputStream);
                msg = (Message)objectInputStream.readObject();

                OutputStream outputStream = client.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                objectOutputStream.writeObject(msg);
                outputStream.flush();    
            }
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
