
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class Categorie {
    
    public static ResultSet getCategories() {
        ResultSet rs = null;
        
        try
        {
                Class.forName(Driver.class.getName());
                Connection connec = DriverManager.getConnection("jdbc:mysql://pipit.u-strasbg.fr:3306/2012_tstocker", "2012_tstocker", "zz0euypu");
                System.out.println("/\\ Connected /\\");
                
                Statement stmt = connec.createStatement();
                rs = stmt.executeQuery("SELECT * FROM CATEGORIE");
                
        }
        catch (Exception e)
        {
            System.out.println("/\\ Fail in getcategories /\\");
            System.out.println(e);
        }
        
        return rs;
    }
    
}
