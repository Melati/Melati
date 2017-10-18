package org.melati.example.contacts;

import org.melati.admin.AnticipatedException;
import org.melati.example.contacts.generated.ContactBase;
import org.melati.poem.*;
import org.melati.poem.util.EnumUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>Persistent</code> <code>Contact</code> object.
 * 
 * <p> 
 * Description: 
 *   A Contact. 
 * </p>
 *
 * <table>
 * <caption>
 * Field summary for SQL table <code>Contact</code>
 * </caption>
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
 * see org.melati.poem.prepro.TableDef#generatePersistentJava 
 */
public class Contact extends ContactBase implements Treeable {
  /**
   * Thrown when an attempt to make a descendant an ancestor is made.
   * @author timp
   */
  public class DescendantParentException extends AnticipatedException {
    private static final long serialVersionUID = 1L;
    /**
     * Constructor.
     * @param message the message to display
     */
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
  * see org.melati.poem.prepro.TableDef#generatePersistentJava 
  */
  public Contact() { }
  // programmer's domain-specific code here

  /**
   * @return whether contact is in the category
   */
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

  /**
   * @return the ancestors
   */
  public ArrayList<Integer> getAncestors() {
    ArrayList<Integer> l = new ArrayList<Integer>();
    Contact p = getOwner();
    while (p != null) {
        l.add(new Integer(p.troid().intValue()));
        p = p.getOwner();
    }
    return l;
}
  public void setOwner(Contact cooked)
    throws AccessPoemException {
    if (cooked != null && cooked.getAncestors().contains(this.troid())) {
      throw new DescendantParentException("Owner must not be a descendant.");
    }
    super.setOwner(cooked);
  }

  /**
   * @param v vector of Treeables
   * @return an array of Treeables
   */
  public static Treeable[] arrayOf(Vector<Persistent> v) {
    Treeable[] arr;
    synchronized (v) {
      arr = new Treeable[v.size()];
      v.copyInto(arr);
    }
    return arr;
  }
  /**
   * @param e enumeration of Treeables
   * @return an array of Treeables
   */
  public static Treeable[] arrayOf(Enumeration<Persistent> e) {
    Vector<Persistent> v = EnumUtils.vectorOf(e);
    return arrayOf(v);
  }

}

