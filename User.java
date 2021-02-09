import java.io.Serializable;

/**
 * Classe permettant de gerer les utilisateurs
 */
public class User implements Serializable {
    /**
     * Le numero de version
     */
    private static final long serialVersionUID = 1;

    /**
     * Le nom de l'utilisateur
     */
    private String nom;

    /**
     * La couleur du nom de l'utilisateur
     */
    private String color;

    /**
     * Le constructeur d'un utilisateur
     * @param nom le nom de l'utilisateur
     * @param color la couleur de l'utilisateur
     */
    public User(String nom, String color){
        this.nom = nom;
        this.color = color;
    }

    /**
     * Le getter du nom de l'utilisateur
     * @return String, le nom de l'utilisateur
     */
    public String getName(){
        return nom;
    }

    /**
     * Le getter de la couleur de l'utilisateur
     * @return String, la couleur de l'utilisateur
     */
    public String getColor(){
        return color;
    }

    /**
     * Methode pour afficher le message
     */
    @Override
    public String toString(){
        return "<html><font color=" + color +">" + nom + "</font></html>";
    }
}
