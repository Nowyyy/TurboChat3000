import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultListModel;
//import java.io.Serializable;


public class Message extends DefaultListModel<Message>{
    private static final long serialVersionUID = 1;
    private String nom;
    private LocalTime date;
    private String mess;

    public Message(String nom, LocalTime date, String mess){
        this.nom = nom;
        this.date = date;
        this.mess = mess;
    }

    @Override
    public String toString(){
        DateTimeFormatter formatt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + date.format(formatt) + "] " + nom + ": " + mess;
    }
}