package org.melati.doc.example.contacts;

import org.melati.doc.example.contacts.generated.*;
import java.util.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.melati.servlet.MelatiContext;
import org.melati.poem.User;
import org.melati.poem.PoemThread;
import org.melati.util.Treeable;
import org.melati.util.EnumUtils;

public class Contact extends ContactBase implements Treeable {
  public Contact() {}

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
      setLastupdateuser_unsafe(((User)PoemThread.accessToken()).getTroid());
      Integer count = getUpdates();
      if (count == null) count = new Integer(0);
      setUpdates_unsafe(new Integer(count.intValue()+1));
    }
    

  public String getLogicalDatabase
  (MelatiContext melatiContext, String logicalDatabase) {
    return "contacts";
  }
    
  public Treeable[] getChildren() {
    return (Contact.arrayOf(getContactTable().getOwnerColumn().selectionWhereEq(troid())));
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
