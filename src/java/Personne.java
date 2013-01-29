
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class Personne 
{    
    
    private     int      id; //GET ONLY
    private     String   pseudo;
    private     String   password;
    private     String   mail;
    
    public Personne ()
    {
        
    }
    
    public Personne (String pseudo, String password)
    {
        this.pseudo = pseudo;
        this.password = password;
    }
    
    public Personne (int id, String pseudo, String password, String mail)
    {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.mail = mail;
    }
    
    void setPseudo(String pseudo)
    {
        this.pseudo = pseudo;
    }
    
    void setPassword(String password)
    {
        this.password = password;
    }
    
    void setMail(String mail)
    {
        this.mail = mail;
    }
    
    public String getPseudo()
    {
        return this.pseudo;
    }
    
    public String getPassword()
    {
        return this.password;
    }
    
    public String getMail()
    {
        return this.mail;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    static Personne getPersonneWithUsrAndPasswd(Personne p) 
    {
        Personne res = null;
        try 
            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");
                
                Statement stmt = connec.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PERSONNE WHERE PSEUDO = '" + p.getPseudo() + "' AND PASSWORD = '" + p.getPassword() + "'");
                if (rs.next())
                {
                    res = new Personne(
                        rs.getInt("IDPERSONNE"), 
                        rs.getString("PSEUDO"),
                        rs.getString("PASSWORD"),
                        rs.getString("ADRESSEEMAIL")
                    );
                }
            }
            catch (Exception e)
            {
                System.out.println("/\\ Fail /\\");
                System.out.println(e);
            }
        
        return res;
    }
    
    @Override
    public String toString()
    {
        return this.getPseudo() + " => " + this.getMail() + " (" + this.getPassword() + ")";
    }
    
}
