
package DbServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Authors;
import model.Documents;

public class DbServer implements IDbService{

    public static void main(String[] args) {
        
        DbServer dbServer = new DbServer();
        
        Settings settings = new Settings();
        System.out.println("url = " +settings.getValue(Settings.URL));
        System.out.println("user = " +settings.getValue(Settings.USER));
        System.out.println("psw = " +settings.getValue(Settings.PSW));
        
        Authors pushkina = new Authors(4, "Mariya ushkina", "Songs");
        Documents pushkinaDoc = new Documents(5, "Hero of the road", "Song and poems", new Date(995, 6, 5), 4);
        
//        try {
//            dbServer.addAuthor(pushkina);
//        } catch (DocumentException ex) {
//            System.out.println("Error: "+ex.getMessage());}
        
        try {
            dbServer.addDocument(pushkinaDoc, pushkina);
        } catch (DocumentException ex) {
            System.out.println("Error: "+ex.getMessage());}
        
        
        
        System.out.println("-------------------------------------------------");
        
        System.out.println("-----------------AUTHORS-------------------------");
        for (Authors a : Authors.getAuthors()) {
            System.out.println(a);
        }
        
        System.out.println("--------------------------------------------------");
        
        System.out.println("-----------------DOCUMENTS-------------------------");
        for (Documents d : Documents.getDocuments()) {
            System.out.println(d);
        }
        
        System.out.println("--------------------------------------------------");
        
        
    }  

    @Override
    public boolean addAuthor(Authors author) throws DocumentException {
       Settings settings = new Settings();
       int result;
       
        try (Connection connection = DriverManager.getConnection(
                                                settings.getValue(Settings.URL),
                                                settings.getValue(Settings.USER),
                                                settings.getValue(Settings.PSW));){
           
            Statement stm = connection.createStatement();
            if(stm.execute("SELECT ID = " + author.getAuthor_id()+" FROM AUTHORS")) 
                throw new DocumentException("Author ID is not uniq");
            else{
                String sql = "INSERT INTO authors VALUES ("+author.getAuthor_id()+",'"+author.getAuthor()+"','"+author.getNotes()+"')";
                result = stm.executeUpdate(sql);}   
            return result>0;
        } catch (SQLException ex) {
            Logger.getLogger(Authors.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }

    @Override
    public boolean addDocument(Documents doc, Authors author) throws DocumentException {
       Settings settings = new Settings();
       int result;
       
        try (Connection connection = DriverManager.getConnection(
                                                settings.getValue(Settings.URL),
                                                settings.getValue(Settings.USER),
                                                settings.getValue(Settings.PSW));){
           
            Statement stm = connection.createStatement();
            if(stm.execute("SELECT * FROM DOCUMENTS WHERE IDDOC = "+doc.getDocument_id())) 
                throw new DocumentException("Document ID is not uniq");
            else{
                String sql = "INSERT INTO DOCUMENTS VALUES ("+doc.getDocument_id()+",'"
                        +doc.getTitle()+"','"
                        +doc.getText()+"','"
                        +doc.getDate()+"','"
                        +doc.getAuthor_id()+"')";
                result = stm.executeUpdate(sql);}
            return result>0;
        } catch (SQLException ex) {
            Logger.getLogger(Authors.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Documents[] findDocumentByAuthor(Authors author) throws DocumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Documents[] findDocumentByContent(String content) throws DocumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAuthor(Authors author) throws DocumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAuthor(int id) throws DocumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
