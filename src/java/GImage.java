
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        List items = upload.parseRequest(request);
        Iterator iter = items.iterator();
        while (iter.hasNext()) 
        {
           FileItem item = (FileItem) iter.next();
           if (item.isFormField()) 
           {
                photo = item.getString();
           }
           else 
           {
            //String type = item.getName().toString();
                //out.println(item.getName());
                //String[] temp = type.split(".");               
                //int index = temp.length;
                //String extension = temp[index];
                
                //out.println(type);


                File uploadedFile = new File(yourTempDirectory + '/' +  item.getName());
                item.write(uploadedFile);
                out.println(photo);
            }
       }
    }
    
    
}
