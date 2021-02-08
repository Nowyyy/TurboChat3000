import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultListModel;

/**
 * Classe gerant les messages
 */
public class Message extends DefaultListModel<Message>{
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

    private String color;


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
    }

    public void setColor(String couleur){
        color = couleur;
    }

    public String getName(){
        return nom;
    }

    public void setName(String message){
        nom = message;
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