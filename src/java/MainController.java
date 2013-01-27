/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Tony
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MainController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MainController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String data = request.getParameter("data");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        if ("categories".equals(data))
        {
            ArrayList<Categorie> cat = Categorie.getCategories();
//            out.println("lol : " + cat);
            for (Categorie c : cat)
            {
                out.println("<li onclick=\"changeCategorie(this)\" id=\"" + c.getId() + "\" >" + c.getCategorie() + "</li>");
            }
        }
        
        else if ("images".equals(data))
        {
            GImage.getImages(request, response);
        }
        
        out.close();
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        String action = request.getParameter("action");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        
        if ("login".equals(action))
        {
            if(Login.login(request.getParameter("txt_login"), request.getParameter("txt_passwd")))
            {
                out.println("ciboulette");
            }
            else
            {
                out.println("KO");
            }
        }
        
        else //UPLOAD
        {
                    try {
                        
                        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory( );
                        
                        ServletFileUpload upload = new ServletFileUpload( fileItemFactory );
                        
                        // Set upload parameters
                        int  yourMaxMemorySize = 4096 * 1024 * 8; // en bytes
                        int  yourMaxRequestSize = 8192 * 1024 * 8;
                        String yourTempDirectory = getServletContext().getRealPath("/photos");

                        fileItemFactory.setSizeThreshold( yourMaxMemorySize );
                        upload.setSizeMax( yourMaxRequestSize );
                        
                        String photo = "";
                        
                        List items = upload.parseRequest(request);
                        Iterator iter = items.iterator();
                        while (iter.hasNext()) 
                        {
                           FileItem item = (FileItem) iter.next();
                           if (item.isFormField()) {
                                 String name = item.getFieldName();
                                String value = item.getString();
                                photo = item.getString();
                                out.println(name + " | " + value);
                           }
                           else {
					// Handle Uploaded files.
					out.println("Field Name = " + item.getFieldName()
							+ ", File Name = " + item.getName()
							+ ", Content type = " + item.getContentType()
							+ ", File Size = " + item.getSize());
					/*
					 * Write file to the ultimate location.
					 */
					File uploadedFile = new File(yourTempDirectory + photo);
                                        item.write(uploadedFile);
				}
                       }
                        
                    } catch (Exception ex) {
                        out.println(ex);
                        //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
        }
        
        out.close();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
