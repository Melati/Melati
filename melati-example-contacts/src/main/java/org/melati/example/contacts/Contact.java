package org.melati.doc.example.contacts;

import org.melati.doc.example.contacts.generated.*;
import java.util.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.melati.poem.*;

public class Contact extends ContactBase {
  public Contact() {}

  // programmer's domain-specific code here
  
    public boolean isIn(Category category) {
      return getContactsDatabase().getContactCategoryTable()
           .exists("contact = " + getTroid() + " AND category = " + category.getTroid());
  }
  
    protected void writeLock() {
      super.writeLock();
      setLastupdated_unsafe(new java.sql.Date(new java.util.Date().getTime()));
      setLastupdateuser_unsafe(((User)PoemThread.accessToken()).getTroid());
      Integer count = getUpdates();
      if (count == null) count = new Integer(0);
      setUpdates_unsafe(new Integer(count.intValue()+1));
    }
}
