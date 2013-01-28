
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
    public static Personne login(HttpServletRequest request ,String usrName, String passwd)
    {
        Personne p = new Personne();
        p.setPseudo(usrName);
        p.setPassword(passwd);   
        
        System.out.println(passwd);
        
        p = Personne.getPersonneWithUsrAndPasswd(p);
        
        
        if (p != null) //exists renvoi une personne complète si existe, null sinon
        {
            HttpSession session = request.getSession();
            session.setAttribute("Logged", p);
            return p;
        }
        else
        {
            return null;
        }
        
    }
    
    public void logout(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.setAttribute("Logged", null);
    }
}
