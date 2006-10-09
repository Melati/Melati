package org.melati.example.contacts;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import org.melati.example.contacts.generated.ContactBase;
import org.melati.poem.AccessPoemException;
import org.melati.poem.User;
import org.melati.poem.PoemThread;
import org.melati.util.EnumUtils;
import org.melati.util.MelatiRuntimeException;
import org.melati.util.Treeable;
/**
 * A <code>Contact</code> object, embellished from the original, 
 * Melati POEM generated, programmer modifiable stub.
 * 
 * <p> 
 * Description: 
 *   A Contact. 
 * </p>
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>Contact</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> name </td><td> String </td><td> Contact Name </td></tr> 
 * <tr><td> owner </td><td> Contact </td><td> Contact who owns this contact 
 * </td></tr> 
 * <tr><td> address </td><td> String </td><td> Contact Address </td></tr> 
 * <tr><td> updates </td><td> Integer </td><td> How many times has this 
 * record been updated? </td></tr> 
 * <tr><td> lastupdated </td><td> Date </td><td> When was this last updated? 
 * </td></tr> 
 * <tr><td> lastupdateuser </td><td> User </td><td> Who last updated this? 
 * </td></tr> 
 * </table> 
 * 
 * @generator org.melati.poem.prepro.TableDef#generateMainJava 
 */
public class Contact extends ContactBase implements Treeable {
  /**
   * Thrown when an attempt to make a descendant an ancestor is made.
   * @author timp
   * @param message
   */
  public class DescendantParentException extends MelatiRuntimeException {
    private static final long serialVersionUID = 1L;
    public DescendantParentException(String message) {
      super(message);
    }
  }
/**
  * Constructor 
  * for a <code>Persistent</code> <code>Contact</code> object.
  * <p>
  * Description: 
  *   A Contact. 
  * </p>
  * 
  * @generator org.melati.poem.prepro.TableDef#generateMainJava 
  */
  public Contact() { }
  // programmer's domain-specific code here
  
  public boolean isIn(Category category) {
     ContactsDatabase db = (ContactsDatabase)getContactsDatabaseTables();
     String sql = db.quotedName("contact") + " = " + getTroid() + " AND " +
       db.quotedName("category") + " = " + category.getTroid();
     return db.getContactCategoryTable().exists(sql);
  }
  
  protected void writeLock() {
    super.writeLock();
    setLastupdated_unsafe(new java.sql.Date(new java.util.Date().getTime()));
    if (PoemThread.accessToken() instanceof User)
      setLastupdateuser_unsafe(((User)PoemThread.accessToken()).getTroid());
    else
      setLastupdateuser_unsafe(new Integer(1));
      
    Integer count = getUpdates();
    if (count == null) count = new Integer(0);
    setUpdates_unsafe(new Integer(count.intValue()+1));
  }
  
  public Treeable[] getChildren() {
    return (Contact.arrayOf(getContactTable().getOwnerColumn().
               selectionWhereEq(troid())));
  }
  public ArrayList getAncestors() {
    ArrayList l = new ArrayList();
    Contact p = getOwner();
    while (p != null) {
        l.add(p);
        p = p.getOwner();
    }
    return l;
}
  public void setOwner(Contact cooked)
    throws AccessPoemException {
    if (getAncestors().contains(cooked)) {
      throw new DescendantParentException("Owner must not be a descendant.");
    }
    super.setOwner(cooked);
  }
  
  public static Treeable[] arrayOf(Vector v) {
    Treeable[] arr;
    synchronized (v) {
      arr = new Treeable[v.size()];
      v.copyInto(arr);
    }
    return arr;
  }
  public static Treeable[] arrayOf(Enumeration e) {
    Vector v = EnumUtils.vectorOf(e);
    return arrayOf(v);
  }
  
}

