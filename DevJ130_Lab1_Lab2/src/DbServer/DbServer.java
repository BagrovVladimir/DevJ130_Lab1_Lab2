
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
        
        Authors pushkina = new Authors(4, "Mariya Pushkina", "Songs");
        Documents pushkinaDoc = new Documents(5, "Hero of the road", "Song and poems", "1995-06-05", 4);
        
        //Add author
        try {
            dbServer.addAuthor(pushkina);
        } catch (DocumentException ex) {
            System.out.println("Error: "+ex.getMessage());}
        
        //Add document
        try {
            dbServer.addDocument(pushkinaDoc, pushkina);
        } catch (DocumentException ex) {
            System.out.println("Error: "+ex.getMessage());}
        
        //Find document by author
        System.out.println("-------------------------------------------------");
        try {
            for (Documents d : dbServer.findDocumentByAuthor(pushkina)) {
            System.out.println(d);
        }
        } catch (DocumentException ex) {
            System.out.println("Error: "+ex.getMessage());}
        
        //Find document by content
        System.out.println("-------------------------------------------------");
        try {
            for (Documents d : dbServer.findDocumentByContent("content")) {
            System.out.println(d);
        }
        } catch (DocumentException ex) {
            System.out.println(ex.getMessage());}
        
        //Delete author
        System.out.println("-------------------------------------------------");
        try {
            boolean b = dbServer.deleteAuthor(pushkina);
            System.out.println("Is deleted something: " + b);
        } catch (DocumentException ex) {
            Logger.getLogger(DbServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Delete author by ID
        System.out.println("-------------------------------------------------");
        try {
            boolean b = dbServer.deleteAuthor(4);
            System.out.println("Is deleted somehing: " + b); 
        } catch (DocumentException ex) {
            Logger.getLogger(DbServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Table Authors
        System.out.println("-------------------------------------------------");
        System.out.println("-----------------AUTHORS-------------------------");
        for (Authors a : Authors.getAuthors()) {
            System.out.println(a);
        }
        
        //Table Documents
        System.out.println("--------------------------------------------------");
        System.out.println("-----------------DOCUMENTS-------------------------");
        for (Documents d : Documents.getDocuments()) {
            System.out.println(d);
        }
        
        System.out.println("--------------------------------------------------");  
    }  

    @Override
    public boolean addAuthor(Authors author) throws DocumentException {
       List<Authors> list = new ArrayList<>();
       Settings settings = new Settings();
       
        try (Connection connection = DriverManager.getConnection(
                                                settings.getValue(Settings.URL),
                                                settings.getValue(Settings.USER),
                                                settings.getValue(Settings.PSW));){
           
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM AUTHORS WHERE ID = " + author.getAuthor_id());
            Authors authorTemp = null;
            while (rs.next()) {
                author = new Authors(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3));
                list.add(authorTemp);
            }
            if(!list.isEmpty())
                throw new DocumentException("Author ID is not uniq");
            else{
                String sql = "INSERT INTO authors VALUES ("+author.getAuthor_id()+",'"+author.getAuthor()+"','"+author.getNotes()+"')";
                stm.executeUpdate(sql);}   
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Authors.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
    }

    @Override
    public boolean addDocument(Documents doc, Authors author) throws DocumentException {
       List<Documents> list = new ArrayList<>();
       Settings settings = new Settings();
       
        try (Connection connection = DriverManager.getConnection(
                                                settings.getValue(Settings.URL),
                                                settings.getValue(Settings.USER),
                                                settings.getValue(Settings.PSW));){
           
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM DOCUMENTS WHERE IDDOC = " + doc.getDocument_id());
            Documents docTemp = null;
            while (rs.next()) {
                docTemp = new Documents(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getInt(5));
                list.add(docTemp);
            }
            
            if(!list.isEmpty())
                throw new DocumentException("Document ID is not uniq");
            else{
                String sql = "INSERT INTO DOCUMENTS VALUES ("+doc.getDocument_id()+",'"
                        +doc.getTitle()+"','"
                        +doc.getText()+"','"
                        +doc.getDate()+"',"
                        +doc.getAuthor_id()+")";
                stm.executeUpdate(sql);}
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Authors.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Documents> findDocumentByAuthor(Authors author) throws DocumentException {
        Settings settings = new Settings();
        ResultSet rs =null;
        List<Documents> list = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection(
                                                settings.getValue(Settings.URL),
                                                settings.getValue(Settings.USER),
                                                settings.getValue(Settings.PSW));){
            Statement stm = connection.createStatement();
            
            if(author.getAuthor()!=null){
                String sql = "SELECT documents.* "
                        + "FROM authors INNER JOIN documents ON (authors.id= documents.linkToAuthors) "
                        + "WHERE authors.Authors_Name_and_Family ='" + author.getAuthor() + "'";
                rs = stm.executeQuery(sql);
                System.out.println("Author " + author.getAuthor() + " has:");
            }
            if(author.getAuthor_id()!=0){
               String sql = "SELECT documents.* "
                        + "FROM authors INNER JOIN documents ON (authors.id= documents.linkToAuthors) "
                        + "WHERE authors.ID = " + author.getAuthor_id() + "";
                rs = stm.executeQuery(sql);
                System.out.println("Author with ID " + author.getAuthor_id() + " has:");
            }
            else {throw new DocumentException("Error: Wrongs data of authors.  Or wrong connection with database");}
            
            Documents doc = null;
            
            while (rs.next()) {
                doc = new Documents(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getInt(5));
                list.add(doc);
            }
            return list;

        } catch (SQLException ex) {
            Logger.getLogger(DbServer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Documents> findDocumentByContent(String content) throws DocumentException {
        Settings settings = new Settings();
        ResultSet rs =null;
        List<Documents> list = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection(
                                                settings.getValue(Settings.URL),
                                                settings.getValue(Settings.USER),
                                                settings.getValue(Settings.PSW));){
            Statement stm = connection.createStatement();
            
            System.out.println("Documents, which contains word \"" + content + "\" are:");
            
            if(content!=null && !content.trim().isEmpty()){
                String sql = "SELECT * FROM documents "
                        + "WHERE TEXT LIKE '%" + content + "%'";
                rs = stm.executeQuery(sql);
            }
            else {throw new DocumentException("Error: Content null or empty.  Or wrong connection with database");}
            
            Documents doc = null;
            
            while (rs.next()) {
                doc = new Documents(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getInt(5));
                list.add(doc);
            }
            
            if(list.isEmpty()){System.out.println("Not found any documents");}
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DbServer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public boolean deleteAuthor(Authors author) throws DocumentException {
       Settings settings = new Settings();
       
        try (Connection connection = DriverManager.getConnection(
                                                settings.getValue(Settings.URL),
                                                settings.getValue(Settings.USER),
                                                settings.getValue(Settings.PSW));){
           
            Statement stm = connection.createStatement();
            String sql = "DELETE  FROM AUTHORS WHERE Authors_Name_and_Family = '" + author.getAuthor() + "'";
            int res = stm.executeUpdate(sql);
            if(res!=0)
                return true;
            else{  
            return false;}
        } catch (SQLException ex) {
            Logger.getLogger(Authors.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean deleteAuthor(int id) throws DocumentException {
       Settings settings = new Settings();
       
       try (Connection connection = DriverManager.getConnection(
                                                settings.getValue(Settings.URL),
                                                settings.getValue(Settings.USER),
                                                settings.getValue(Settings.PSW));){
           
            Statement stm = connection.createStatement();
            String sql = "DELETE  FROM AUTHORS WHERE ID = " + id;
            int res = stm.executeUpdate(sql);
            if(res!=0)
                return true;
            else{  
            return false;}
        } catch (SQLException ex) {
            Logger.getLogger(Authors.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public void close() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
