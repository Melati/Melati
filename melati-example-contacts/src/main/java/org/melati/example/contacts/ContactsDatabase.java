package org.melati.doc.example.contacts;

import org.melati.doc.example.contacts.generated.*;
import java.util.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.melati.poem.*;

public class ContactsDatabase extends ContactsDatabaseBase
                            implements ContactsDatabaseTables {

  // programmer's domain-specific code here

  public boolean logSQL() {
    return true;
  }

}
