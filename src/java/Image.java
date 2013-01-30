
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tony
 */
public class Image {
  
    private int id;
    private int idCategorie;
    private String nomImage;
    private Date dateCreation;
    //private Date dateModification;
    private String description;
    private String path;
    
    static final String WRITE_IMAGE = "INSERT INTO image(IDCATEGORIE, NOMIMAGE, DATECREATION, DESCRIPTION, PATH) VALUES ( ?, ?, ?, ?, ?)";
    static final String WRITE_ASSOCIATION = "INSERT INTO met_en_ligne(IDPERSONNE, IDIMAGE) VALUES ( ?, ?)";
    
    static final String format = "yy-MM-dd"; 
    static final java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
    
    public Image()
    {
        
    }
    
    public Image(int idCat, String nom, String description, String path)
    {
        this.idCategorie = idCat;
        this.nomImage = nom;
        this.description = description;
        this.path = path;       
        
        this.dateCreation = new java.util.Date();
         
    }
    
    public Image(int id, int idCat, String nom, String description, String path, String formatedCreation) throws ParseException
    {
        this.id = id;
        this.idCategorie = idCat;
        this.nomImage = nom;
        this.description = description;
        this.path = path;       
        
        this.dateCreation = formater.parse(formatedCreation);
         
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public String getPath()
    {
        return this.path;
    }
    
    public String getDescription()
    {
        return this.description;
    }
    
    public String getCreationDate()
    {
        return formater.format( this.dateCreation );
    }
    
    public String getNomImage()
    {
        return this.nomImage;
    }
    
    public int getCategorieId()
    {
        return this.idCategorie;
    }
    
    static ArrayList<Image> getLastImages() throws ClassNotFoundException, SQLException, SQLException, ParseException 
    {
        ArrayList<Image> res = new ArrayList<Image>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");
        
        Statement stmt = connec.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM IMAGE ORDER BY DATECREATION LIMIT 50");
        while (rs.next())
        {
            res.add(new Image(
                    rs.getInt("IDIMAGE"),
                    rs.getInt("IDCATEGORIE"),
                    rs.getString("NOMIMAGE"),
                    rs.getString("DESCRIPTION"),
                    rs.getString("PATH"),
                    rs.getString("DATECREATION")
            ));
        }
        
        return res;
    }
    
    void save( Personne user) throws ClassNotFoundException, SQLException 
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");
        PreparedStatement writeImage = connec.prepareStatement(WRITE_IMAGE);
        PreparedStatement writeAssociation = connec.prepareStatement(WRITE_ASSOCIATION);
        
        writeImage.setInt(1, this.getCategorieId());
        writeImage.setString(2, this.getNomImage());
        writeImage.setString(3, this.getCreationDate());
        writeImage.setString(4, this.getDescription());
        writeImage.setString(5, this.getPath());
        
        writeImage.executeUpdate();
        
        int newImageId = this.GetImageId();
        if (newImageId != 0)
        {
            writeAssociation.setInt(1, user.getId());
            writeAssociation.setInt(2, newImageId);
            writeAssociation.executeUpdate();
        }
        else
        {
            
        }
        
    }
    
    int GetImageId() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");
        
        Statement stmt = connec.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT IDIMAGE FROM image WHERE NOMIMAGE = '" + this.getNomImage() + "' AND PATH = '" + this.getPath() + "'");
        if (rs.next())
        {
            return rs.getInt(1);
        }
        
        return 0;
    }
    
    static ArrayList<String> getImageDetails(int id) throws ClassNotFoundException, SQLException 
    {
        ArrayList<String> res = new ArrayList<String>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");
        
        Statement stmt = connec.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM IMAGE WHERE IDIMAGE = " + id);
        if (rs.next())
        {
            res.add(rs.getString(3));//NOM
            res.add(rs.getString(4));//DATECREATION
            res.add(rs.getString(6));//DESCRIPTION
            
            ResultSet rs2 = stmt.executeQuery("SELECT NOMCATEGORIE FROM categorie WHERE IDCATEGORIE = " + rs.getInt(2));
            if (rs2.next())
            {
                res.add(rs2.getString(1));//CATEGORIE
            }
            else
            {
                res.add("Non trouvé");
            }
            
            rs2 = stmt.executeQuery("SELECT PSEUDO FROM personne WHERE IDPERSONNE = (SELECT IDPERSONNE FROM met_en_ligne WHERE IDIMAGE = " + id + ")");
            
            if (rs2.next())
            {
                res.add(rs2.getString(1));//PSEUDO
            }
            else
            {
                res.add("Non trouvé");
            }
        }
        
        return res;
    }
    
    static ArrayList<Image> search(String exp) throws ClassNotFoundException, SQLException, ParseException 
    {
        ArrayList<Image> res = new ArrayList<Image>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");
        
        Statement stmt = connec.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM IMAGE WHERE NOMIMAGE LIKE '%" + exp + "%'");
              
        
        while (rs.next())
        {
            res.add(new Image(
                    rs.getInt("IDIMAGE"),
                    rs.getInt("IDCATEGORIE"),
                    rs.getString("NOMIMAGE"),
                    rs.getString("DESCRIPTION"),
                    rs.getString("PATH"),
                    rs.getString("DATECREATION")
            ));
        }
        
        return res;
    }
    
    static ArrayList<Image> getImagesByCategorie(int categorie) throws ClassNotFoundException, SQLException, ParseException 
    {
        ArrayList<Image> res = new ArrayList<Image>();
        Class.forName("com.mysql.jdbc.Driver");
        Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");
        
        Statement stmt = connec.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM IMAGE WHERE IDCATEGORIE = " + categorie);
              
        
        while (rs.next())
        {
            res.add(new Image(
                    rs.getInt("IDIMAGE"),
                    rs.getInt("IDCATEGORIE"),
                    rs.getString("NOMIMAGE"),
                    rs.getString("DESCRIPTION"),
                    rs.getString("PATH"),
                    rs.getString("DATECREATION")
            ));
        }
        
        return res;
    }
    
    @Override
    public String toString()
    {
        return this.nomImage + " [ " + this.dateCreation + " ] " + " < " + this.path + " >  | " + this.description;
    }
}
