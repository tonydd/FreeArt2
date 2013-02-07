
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class GCommentaire {

    static void createComment(String parameter,int imgId, PrintWriter out, HttpServletRequest request) 
    {
        try 
        {
            Image i = new Image(imgId);
            HttpSession session = request.getSession();
            Personne user = (Personne)session.getAttribute("Logged");
            if (user != null)
            {
                Commentaire create = new Commentaire(parameter);
                create.setImage(i);
                create.setUser(user);

                create.save();
                out.print("OK");
            }
            else
            {
                out.print("Vous devez être connecté avec votre compte afin de poster un commentaire !");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            out.print("Une erreure est malencontreusement survenue, veuillez ré-éssayer ultérieurement svp !");
        }
    }

    static void getImageComments(int parseInt,HttpServletRequest request, PrintWriter out) {
        try 
        {
            Image i = new Image(parseInt);
            i.fillImageDetails();
            ArrayList<Commentaire> comments = Commentaire.getCommentaires(i);
            
            HttpSession session = request.getSession();
            Personne user = (Personne)session.getAttribute("Logged");
            
            if (user == null)
            {
                user = new Personne(0);
            }
            
            if (comments.size() > 0)
            {
                for (Commentaire c : comments)
                {
                    out.println("<div class='comment'>");
                    if (c.getUser().getId() == user.getId())
                        out.println("<label class='info'>Par : " + c.getUser().getPseudo() + "  -  Le : " + c.getCreation() + "</label><img onclick=\"deleteComment(" + c.getId() + ");\" src='img/ko.png' class='delete' title='Supprimer' alt='Supprimer' /><br>");
                    else
                        out.println("<label class='info'>Par : " + c.getUser().getPseudo() + "  -  Le : " + c.getCreation() + "</label><br>");
                    out.println("<p>" + c.getComment() + "</p>");
                    out.println("</div>");
                }
            }
            else
            {
                out.println("<p>Pas de commentaire pour cette image !</p><p>Soyez le premier à ajouter un commentaire !</p>");
            }
        }
        catch (Exception e)
        {
            
        }
    }

    static void deleteComment(int parseInt, PrintWriter out, HttpServletRequest request) 
    {
        try 
        {
            Commentaire toDelete = new Commentaire(parseInt);
            toDelete.delete();
            out.print("OK");
        }
        catch (Exception e)
        {
            out.println(e);
        }
    }
    
}
