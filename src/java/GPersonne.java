
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class GPersonne {
    public static void login(HttpServletRequest request ,String usrName, String passwd, PrintWriter out)
    {
        Personne p = new Personne();
        p.setPseudo(usrName);
        p.setPassword(passwd);   
        
        System.out.println(passwd);
        
        p = p.getPersonneWithUsrAndPasswd();
        
        if(p != null)
        {
            HttpSession session = request.getSession();
            session.setAttribute("Logged", p);
            out.print(p.getPseudo());
        }
        else
        {
            out.print("KO");
        }
        
    }
    
    public static void logout(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.setAttribute("Logged", null);
    }

    static void createUser(String pseudo, String pass, String mail, PrintWriter out) 
    {
        Personne p = new Personne();
        p.setPseudo(pseudo);
        p.setPassword(pass);
        p.setMail(mail);
        
        if (p.save())
        {
            out.print("OK");
        }
        else
        {
            out.print("KO");
        }
    }

    static void getUserDetails(HttpServletRequest request, PrintWriter out) throws ClassNotFoundException, SQLException, ParseException 
    {
        try {
            HttpSession session = request.getSession();
            Personne user = (Personne)session.getAttribute("Logged"); 
            ArrayList<Image> userImages = Image.getUserImage(user);
            int comments = user.getCountOfPostedComments();

            out.println("<h2 id='galleryTitle'> Détails de l'utilisateur</h2>");
            out.println("<p><b> Pseudo : </b>" + user.getPseudo() + "</p>");
            out.println("<p><b> Adresse mail : </b>" + user.getMail() + "</p>");
            out.println("<p><b> Mot de passe du compte : </b>" + user.getPassword() + "</p>");
            out.println("<p><b> Nombre d'images uploadées : </b>" + userImages.size() +"&nbsp;&nbsp; <a href='#' onclick='managePictures();'>Afficher le détail</a></p>");
            out.println("<p><b> Nombre de commentaires postés : </b>" + comments + "</p>");
            out.println("<p><img src='img/supprimer.png' /><b><a href='#' onclick='$( \"div#div_delete_user\" ).dialog( \"open\" );'> Supprimer le compte</a></b></p>");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    static void delete(HttpServletRequest request, String yourTempDirectory) 
    {
        try 
        {
            HttpSession session = request.getSession();
            Personne user = (Personne)session.getAttribute("Logged");
            ArrayList<Image> userImages = Image.getUserImage(user);
            
            for (Image i : userImages)
            {
                GImage.delete(i.getId(), yourTempDirectory);
            }
            
            user.delete();
            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(GPersonne.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
