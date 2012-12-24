
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
    
    private int id;
    private String cat;
    
    public Categorie(int id, String categorie)
    {
        this.id = id;
        this.cat = categorie;
    }
    
    public void setCategorie(String categorie)
    {
        this.cat = categorie;
    }
    
    public String getCategorie()
    {
        return this.cat;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    
    
    public static ArrayList<Categorie> getCategories() {
        ArrayList<Categorie> res = new ArrayList<Categorie>();
        
        
        try
        {
                System.out.println("DRIVER : " + Driver.class.getName());
                Class.forName("com.mysql.jdbc.Driver");
                Connection connec = DriverManager.getConnection("jdbc:mysql://pipit.u-strasbg.fr:3306/2012_tstocker", "2012_tstocker", "zz0euypu");
                System.out.println("/\\ Connected /\\");
                
                Statement stmt = connec.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM CATEGORIE");
                while (rs.next())
                {
                    System.out.println("sisi");
                    res.add(new Categorie(rs.getInt(0), rs.getString(1)));
                }
                
        }
        catch (Exception e)
        {
            System.out.println("/\\ Fail in getcategories /\\");
            System.out.println(e);
        }
        
        return res;
    }
    
}
