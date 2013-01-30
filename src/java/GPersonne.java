
import java.io.PrintWriter;
import java.sql.SQLException;
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
    
}
