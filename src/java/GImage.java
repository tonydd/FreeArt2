
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class GImage {
    
    public static void getImages(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
         for (int i = 0; i < 20; i++)
                {
                    out.println("<div class=\"display\" >");
                    out.println("<img src=\"img/2012-12-10 12.53.06.jpg\" class=\"display\" /> ");
                    out.println("<div class=\"actions\">");
                    out.println("<img src=\"img/comment.png\" class=\"action\" title=\"Commenter\" />");
                    out.println("<img src=\"img/addPanier.png\" class=\"action\" title=\"Ajouter au panier\" id='Photo Test' onclick='addToPanier(this)' />");
                    out.println("</div>");
                    out.println("</div>");
                }
    }
}
