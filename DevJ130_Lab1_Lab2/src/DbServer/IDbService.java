
package DbServer;

import java.util.List;
import model.*;


public interface IDbService extends AutoCloseable{
    
    boolean addAuthor(Authors author)throws DocumentException;
    boolean addDocument(Documents doc ,Authors author) throws DocumentException;
    List<Documents> findDocumentByAuthor(Authors author) throws DocumentException;
    List<Documents> findDocumentByContent(String content) throws DocumentException;
    boolean deleteAuthor(Authors author) throws DocumentException;
    boolean deleteAuthor(int id) throws DocumentException;
    
}
