/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
            for (Categorie c : cat)
            {
                out.println("<li onclick=\"changeCategorie(this)\" id=\"" + c.getId() + "\" >" + c.getCategorie() + "</li>");
            }
        }
        
        else if ("categories_select".equals(data))
        {
            ArrayList<Categorie> cat = Categorie.getCategories();
            for (Categorie c : cat)
            {
                out.println("<option value=\"" + c.getId() + "\" >" + c.getCategorie() + "</li>");
            }
        }
        
        else if ("details".equals(data))
        {
            try {
                GImage.getImageDetails(Integer.parseInt(request.getParameter("imgId")), out);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if ("user_photos".equals(data))
        {
            try {
                try {
                    GImage.getUserImage(request, out);
                } catch (ParseException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if ("user_infos".equals(data))
        {
            try {
                GPersonne.getUserDetails(request, out);
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if ("imgComments".equals(data))
        {
            try {
                GCommentaire.getImageComments(Integer.parseInt(request.getParameter("imgId")),request, out);
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if ("basket".equals(data))
        {
            String[] values = request.getParameterValues("content[]");
            try {
                GImage.getBasket(values, out);
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }      
        
        else if ("imgCategorie".equals(data))
        {
            try {
                GImage.getImagesByCategorie(Integer.parseInt(request.getParameter("categorie")), out);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if ("search".equals(data))
        {
            try {
                try {
                    GImage.search(request.getParameter("exp"), out);
                } catch (ParseException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if ("images".equals(data))
        {
            try {
                GImage.getLastImages(request, response);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            GPersonne.login(request, request.getParameter("txt_login"), request.getParameter("txt_password"), out);           
        }
        else if ("logout".equals(action))
        {
            GPersonne.logout(request);
        }
        
        else if ("subscribe".equals(action))
        {
            GPersonne.createUser(request.getParameter("login"), request.getParameter("password"), request.getParameter("mail"), out);
        }
        
        else if ("deleteImage".equals(action))
        {
            String yourTempDirectory = getServletContext().getRealPath("/photos/");
            GImage.delete(Integer.parseInt(request.getParameter("idImage")), yourTempDirectory);
        }
        
        else if ("deleteUser".equals(action))
        {
            String yourTempDirectory = getServletContext().getRealPath("/photos/");
            GPersonne.delete(request, yourTempDirectory);
        }
        
        else if ("postComment".equals(action))
        {
            GCommentaire.createComment(request.getParameter("text"),Integer.parseInt(request.getParameter("imgId")), out, request);
        }
        
        else if ("deleteComment".equals(action))
        {
            GCommentaire.deleteComment(Integer.parseInt(request.getParameter("commentId")), out, request);
        }
        
        else if ("downloadBasket".equals(action))
        {
            String[] values = request.getParameterValues("content[]");
            
            String photoDirectory = getServletContext().getRealPath("/photos/");
            String archiveDiretory = getServletContext().getRealPath("/archives/");
            
            GImage.makeArchive(values,photoDirectory, archiveDiretory, out, request);
        }
        
        else //UPLOAD
        {
            try 
            {
                String yourTempDirectory = getServletContext().getRealPath("/photos/");
                GImage.uploadImage(request, response, yourTempDirectory);
            } 
            catch (Exception ex) 
            {
                out.println(ex);
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
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
