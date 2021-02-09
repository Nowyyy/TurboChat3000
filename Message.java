import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultListModel;
import java.util.*;

/**
 * Classe gerant les messages
 */
public class Message extends DefaultListModel<Message>{

    /**
     * numéro de version
     */
    private static final long serialVersionUID = 1;

    /**
     * Le nom de l'envoyeur
     */
    private String nom;

    /**
     * La date de l'envoi
     */
    private LocalTime date;

    /**
     * Le message a envoyer
     */
    private String mess;

    /**
     * La couleur du message
     */
    private String color;

    /**
     * La liste des utilisateurs
     */
    private ArrayList<User> userList;


    /**
     * Constructeur du message
     * @param nom le nom de l'envoyeur
     * @param date la date de l'envoi
     * @param mess le message a envoyer
     */
    public Message(String nom, LocalTime date, String mess){
        this.nom = nom;
        this.date = date;
        this.mess = mess;
        this.userList = null;
    }

    /**
     * Le getter du message
     * @return String, le message
     */
    public String getMess(){
        return mess;
    }

    /**
     * Le setter de la couleur
     * @param couleur, la couleur à ajouter
     */
    public void setColor(String couleur){
        color = couleur;
    }

    /**
     * Le getter de la couleur
     * @return String, la couleur du message
     */
    public String getColor(){
        return color;
    }

    /**
     * Le getter du nom de l'envoyeur 
     * @return nom, le nom de l'envoyeur
     */
    public String getName(){
        return nom;
    }

    /**
     * Le setter du nom de l'envoyeur
     * @param message le nom de l'envoyeur
     */
    public void setName(String message){
        nom = message;
    }

    /**
     * Le setter de la liste des utilisateurs
     * @param liste la liste des utilisateurs au moment de l'envoi du message
     */
    public void setUserList(ArrayList<User> liste){
        userList = new ArrayList<>(liste);
        //Collections.copy(userList, liste);
    }

    /**
     * Le getter de la liste des utilisateurs
     * @return ArrayList, la liste des users au moment de l'envoi du message
     */
    public ArrayList<User> getUserList(){
        return userList;
    }

    /**
     * Methode pour afficher le message
     */
    @Override
    public String toString(){
        DateTimeFormatter formatt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "<html>[" + date.format(formatt) + "] <font color="+ color + ">" + nom + "</font>: " + mess+"</html>";
    }
}