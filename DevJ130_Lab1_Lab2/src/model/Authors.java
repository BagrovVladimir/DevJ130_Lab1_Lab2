
package model;

//import com.sun.jdi.connect.spi.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import DbServer.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Authors {
    
    public static final int VERSION = 79897;
    private final int author_id;
    private String author;
    private String notes;

    public Authors(int author_id, String author) {
        this(author_id, author, null);
    }

    public Authors(int author_id, String author, String notes) {
        this.author_id = author_id;
        this.author = author;
        this.notes = notes;
    }
    
    
    
    public static List<Authors> getAuthors(){
       List<Authors> list = new ArrayList<>();
       Settings settings = new Settings();
       
        try (Connection connection = DriverManager.getConnection(
                                                settings.getValue(Settings.URL),
                                                settings.getValue(Settings.USER),
                                                settings.getValue(Settings.PSW));){
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM AUTHORS");
            Authors author = null;
            while (rs.next()) {
                author = new Authors(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3));
                list.add(author);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Authors.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return list;   
    }

    public int getAuthor_id() {
        return author_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Authors{" + "author_id=" + author_id + ", author=" + author + ", notes=" + notes + '}';
    }
    
    

    @Override
    public int hashCode() {
        return VERSION + this.author_id + Objects.hashCode(this.author);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Authors)) {
            return false;
        }

        final Authors other = (Authors) obj;
        return !(this.author_id != other.author_id
                || !Objects.equals(this.author, other.author));
    }
    
}
