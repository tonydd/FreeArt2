
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class Commentaire {
    private int id;
    private String commentaire;
    private Date creation;
    private Image image;
    private Personne user;
    
    static final String WRITE_COMMENT = "INSERT INTO commentaire (COMMENTAIRE, DATECREATION) VALUES ( ?, ?)";
    static final String WRITE_ASSOC_IMAGE = "INSERT INTO contient (IDIMAGE, IDCOMMENTAIRE) VALUES ( ?, ?)";
    static final String WRITE_ASSOC_USER = "INSERT INTO poste (IDCOMMENTAIRE, IDPERSONNE) VALUES ( ?, ?)";
    
    static final String DELETE_COMMENT = "DELETE FROM commentaire WHERE IDCOMMENTAIRE = ?";
    static final String DELETE_ASSOC_IMAGE = "DELETE FROM contient WHERE IDCOMMENTAIRE = ?";
    static final String DELETE_ASSOC_USER = "DELETE FROM poste WHERE IDCOMMENTAIRE = ?";
    
    static final String format = "yy-MM-dd"; 
    static final java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
    
    public Commentaire()
    {
        
    }
    
    public Commentaire(int id)
    {
        this.id = id;
    }
    
    public Commentaire(String text)
    {
        this.commentaire = text;
        this.creation = new java.util.Date();
    }
    
    public Commentaire(int id, String text, String date) throws ParseException
    {
        this.id = id;
        this.commentaire = text;
        this.creation = formater.parse(date);
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public String getComment()
    {
        return this.commentaire;
    }
    
    public Personne getUser()
    {
        return this.user;
    }
    
    public Image getImage()
    {
        return this.image;
    }
    
    public String getCreation()
    {
        return formater.format( this.creation );
    }
    
    public void setUser(Personne p)
    {
        this.user = p;
    }
    
    public void setImage(Image i)
    {
        this.image = i;
    }
    
    
    public static ArrayList<Commentaire> getCommentaires(Image i)
    {
        ArrayList<Commentaire> res = new ArrayList<Commentaire>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");

            Statement stmt = connec.createStatement();
            Statement stmt2 = connec.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM commentaire WHERE IDCOMMENTAIRE IN (SELECT IDCOMMENTAIRE FROM contient WHERE IDIMAGE = " + i.getId() + ")");
            while (rs.next())
            {
                int commentId = rs.getInt("IDCOMMENTAIRE");
                Commentaire courant = new Commentaire(commentId,rs.getString("COMMENTAIRE"),rs.getString("DATECREATION"));
                    
                courant.setImage(i);
                
                ResultSet rs2 = stmt2.executeQuery("SELECT * FROM personne WHERE IDPERSONNE = (SELECT IDPERSONNE FROM poste WHERE IDCOMMENTAIRE = " + commentId + ")");
                if (rs2.next())
                {
                    Personne p = new Personne(
                        rs2.getInt("IDPERSONNE"), 
                        rs2.getString("PSEUDO"),
                        rs2.getString("PASSWORD"),
                        rs2.getString("ADRESSEEMAIL")
                    );
                    
                    courant.setUser(p);
                }
                
                res.add(courant);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return res;
        
    }
    
    public void save()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");
            PreparedStatement writeComment = connec.prepareStatement(WRITE_COMMENT);
            PreparedStatement writeAssociationUser = connec.prepareStatement(WRITE_ASSOC_USER);
            PreparedStatement writeAssociationImage = connec.prepareStatement(WRITE_ASSOC_IMAGE);

            writeComment.setString(1, this.getComment());
            writeComment.setString(2, this.getCreation());
            writeComment.executeUpdate();

            int newCommentId = this.getIdWithTextAndDate();
            if (newCommentId != 0)
            {
                writeAssociationUser.setInt(1, newCommentId);
                writeAssociationUser.setInt(2, this.getUser().getId());

                writeAssociationImage.setInt(1, this.getImage().getId());
                writeAssociationImage.setInt(2, newCommentId);
                
                writeAssociationUser.executeUpdate();
                writeAssociationImage.executeUpdate();
            }
            else
            {

            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void delete()
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");
            PreparedStatement deleteComment = connec.prepareStatement(DELETE_COMMENT);
            PreparedStatement deleteAssociationImage = connec.prepareStatement(DELETE_ASSOC_IMAGE);
            PreparedStatement deleteAssociationUser = connec.prepareStatement(DELETE_ASSOC_USER);

            deleteAssociationImage.setInt(1, this.getId());
            deleteAssociationUser.setInt(1, this.getId());
            deleteComment.setInt(1, this.getId());
            
            deleteAssociationImage.executeUpdate();
            deleteAssociationUser.executeUpdate();
            deleteComment.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private int getIdWithTextAndDate() 
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connec = DriverManager.getConnection("jdbc:mysql://localhost:3306/FreeArt", "root", "");

            Statement stmt = connec.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT IDCOMMENTAIRE FROM commentaire WHERE COMMENTAIRE = '" + this.getComment() + "' AND DATECREATION = '" + this.getCreation() + "'");
            if (rs.next())
            {
                return rs.getInt(1);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return 0;
    }
}
