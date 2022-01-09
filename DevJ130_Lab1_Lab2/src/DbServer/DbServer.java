
package DbServer;

import model.Authors;
import model.Documents;

public class DbServer implements IDbService{

    public static void main(String[] args) {
        Settings settings = new Settings();
//        System.out.println("url = " +settings.getUrl());
        System.out.println("url = " +settings.getValue(Settings.URL));
        System.out.println("user = " +settings.getValue(Settings.USER));
        System.out.println("psw = " +settings.getValue(Settings.PSW));  
        
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addDocument(Documents doc, Authors author) throws DocumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
