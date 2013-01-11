
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class Login {
    
    public static boolean login(String usrNameOrMail, String passwd)
    {
        boolean res = false;
        
        try 
            {
                Class.forName(Driver.class.getName());
                Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");
                System.out.println("/\\ Connected /\\");
                
                Statement stmt = connec.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PERSONNE WHERE PSEUDO = '" + usrNameOrMail + "' OR ADRESSEEMAIL = '" + usrNameOrMail + "' AND PASSWORD = '" + passwd + "'");
                if (rs.next())
                {
                    res = true;
                }
            }
            catch (Exception e)
            {
                System.out.println("/\\ Fail /\\");
                System.out.println(e);
            }
        
        return res;
    }
    
}
