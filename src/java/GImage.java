
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class GImage {
    
    public static void getLastImages(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException, ParseException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ArrayList<Image> lastImages = Image.getLastImages();
        if (lastImages.size() > 0)
        {
            out.println("<h2 id='galleryTitle'>Gallerie principale</h2>");
            for (Image i : lastImages)
            {
                out.println("<div class=\"display\" >");
                out.println("<img src=\"photos/" + i.getPath() +"\" class=\"display\" id=\"" + i.getId() + "\" onclick=\"showDetails(this);\" /> ");
                out.println("<div class=\"actions\">");
                out.println(i.getNomImage());
                out.println("<img src=\"img/comment.png\" class=\"action\" title=\"Commenter\" />");
                out.println("<img src=\"img/addPanier.png\" class=\"action\" title=\"Ajouter au panier\" id=\"" + i.getId() + "\" onclick=\"addArticleToPanier(this)\" />");
                out.println("</div>");
                out.println("</div>");
            }
        }
        else
        {
            out.println("<h3> Désolé, il n'existe actuellement aucune image dans la base,<br />Créez un compte et/ou commencez à uploader des images dès maintenant !</h3>");
        }
    }
    
    public static void uploadImage(HttpServletRequest request, HttpServletResponse response, String yourTempDirectory) throws IOException, Exception
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory( ); 
        ServletFileUpload upload = new ServletFileUpload( fileItemFactory );
        int  yourMaxMemorySize = 40960 * 1024 * 8; // en bytes
        int  yourMaxRequestSize = 81920 * 1024 * 8;
        //String yourTempDirectory = getServletContext().getRealPath("/photos/");
        fileItemFactory.setSizeThreshold( yourMaxMemorySize );
        upload.setSizeMax(yourMaxRequestSize);                 
        String photo = "null";
        int catId = 0;
        String desc = null;
        List items = upload.parseRequest(request);
        Iterator iter = items.iterator();
        while (iter.hasNext()) 
        {
           FileItem item = (FileItem) iter.next();
           if (item.isFormField()) 
           {
                String name = item.getFieldName();
                if ("txt_title".equals(name)) 
                {
                   photo = item.getString();
                }   
                else if ("id_categorie".equals(name))
                {
                    catId = Integer.parseInt(item.getString());
                }   
                else if ("desc".equals(name))
                {
                    desc = item.getString();
                }
           }
           else 
           {
                HttpSession session = request.getSession();
                Personne user = (Personne)session.getAttribute("Logged");
                
                String itemName = (String)item.getName();
                
                String[] tab = itemName.split("\\.");
                String extension = tab[tab.length - 1];
                
                String path = yourTempDirectory + '/' + user.getPseudo() +  '_' + photo + '.' + extension;
                String imgPath = user.getPseudo() +  '_' + photo + '.' + extension;
                File uploadedFile = new File(path);
                
                Image create = new Image(catId, photo, desc, imgPath );
                
                create.save(user);
                
                System.out.println(create);
                item.write(uploadedFile);
                out.println(photo);
            }
       }
    }

    static void getImageDetails(int id, PrintWriter stream) throws ClassNotFoundException 
    {
        Image i = new Image(id);
        ArrayList<String> details = new ArrayList<String>();
        try {
            details = i.getImageDetails();
        } catch (SQLException ex) {
            Logger.getLogger(GImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String[] split = details.get(1).split("-");
        
        stream.println("<h2 id='galleryTitle' >" + details.get(0) + "</h2>");
        stream.println("<p><b>Catégorie : </b>" + details.get(4) + "</p>");
        stream.println("<p><b>Ajoutée le : </b>" + split[2] + '/' + split[1] + '/' + split[0] + "</p>");
        stream.println("<p><b>Par : </b>" + details.get(5) + "</p>");
        stream.println("<p><b>Description : </b>" + details.get(2) + "</p>");
        
    }
    
    static void search(String exp, PrintWriter stream) throws ClassNotFoundException, ParseException 
    {
        ArrayList<Image> found = new ArrayList<Image>();
        try {
            found = Image.search(exp);
        } catch (SQLException ex) {
            Logger.getLogger(GImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (found.size() > 0)
        {
            stream.println("<h2 id='galleryTitle'> Résultat de recherche </h2>");
            for (Image i : found)
            {
                stream.println("<div class=\"display\" >");
                stream.println("<img src=\"photos/" + i.getPath() +"\" class=\"display\" id=\"" + i.getId() + "\" onclick=\"showDetails(this);\" /> ");
                stream.println("<div class=\"actions\">");
                stream.println(i.getNomImage());
                stream.println("<img src=\"img/comment.png\" class=\"action\" title=\"Commenter\" />");
                stream.println("<img src=\"img/addPanier.png\" class=\"action\" title=\"Ajouter au panier\" id=\"" + i.getId() + "\" onclick=\"addArticleToPanier(this)\" />");
                stream.println("</div>");
                stream.println("</div>");
            }
        }
        else
        {
            stream.println("<h3> Désolé, la recherche n'a retourné aucun résultat :(");
        }
    }

    static void getImagesByCategorie(int categorie, PrintWriter stream) throws ClassNotFoundException, ParseException 
    {
        ArrayList<Image> imgCategorie = new ArrayList<Image>();
        try {
            imgCategorie = Image.getImagesByCategorie(categorie);
        } catch (SQLException ex) {
            Logger.getLogger(GImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (imgCategorie.size() > 0)
        {
            stream.println("<h2 id='galleryTitle'> Affichage par catégorie </h2>");
            for (Image i : imgCategorie)
            {
                stream.println("<div class=\"display\" >");
                stream.println("<img src=\"photos/" + i.getPath() +"\" class=\"display\" id=\"" + i.getId() + "\" onclick=\"showDetails(this);\" /> ");
                stream.println("<div class=\"actions\">");
                stream.println(i.getNomImage());
                stream.println("<img src=\"img/comment.png\" class=\"action\" title=\"Commenter\" />");
                stream.println("<img src=\"img/addPanier.png\" class=\"action\" title=\"Ajouter au panier\" id=\"" + i.getId() + "\" onclick=\"addArticleToPanier(this)\" />");
                stream.println("</div>");
                stream.println("</div>");
            }
        }
        else
        {
            stream.println("<h3> Désolé, il n'existe actuellement aucune image appartenant à cette catégorie !");
        }
    }

    static void getUserImage(HttpServletRequest request, PrintWriter stream) throws ClassNotFoundException, ParseException 
    {
        HttpSession session = request.getSession();
        Personne user = (Personne)session.getAttribute("Logged");       
        ArrayList<Image> imgCategorie = new ArrayList<Image>();
        try {
            imgCategorie = Image.getUserImage(user);
        } catch (SQLException ex) {
            Logger.getLogger(GImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        stream.println("<h2 id='galleryTitle'>Gestion des images de <b>" + user.getPseudo() + "</b></h2>");
        stream.println("<table rules='all' border='1'><tr><th>Nom</th><th>Création</th><th>Description</th><th>Chemin</th><th>Actions</th></tr>");
        
        for (Image i : imgCategorie)
        {
            stream.println("<tr><td>" + i.getNomImage() + "</td><td>" + i.getCreationDate() + "</td><td>" + i.getDescription() + "</td><td class='path' onclick=\"window.open('photos/" + i.getPath() + "');\" >" + i.getPath() + "</td><td><img src='img/modifier.png' id='" + i.getId() + "'/><img src='img/supprimer.png' onclick='deleteImage(" + i.getId() + ");'/></td></tr>");
        }
        
        stream.println("</table>");
    }

    static void delete(int parameter, String servletPath) 
    {
        try 
        {
            Image i = new Image(parameter);
            
            ArrayList<String> infos = i.getImageDetails();
            String imgPath = infos.get(3);
            
            i.deleteImage();
            File toDelete = new File(servletPath + '/' + imgPath);
            toDelete.delete();
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(GImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
