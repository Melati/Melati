package org.melati.example.contacts;

import org.melati.poem.PoemTask;
import org.melati.example.contacts.generated.ContactsDatabaseBase;

/**
 * Melati POEM generated, programmer modifiable stub.
 */
public class ContactsDatabase extends ContactsDatabaseBase
                            implements ContactsDatabaseTables {
  // programmer's domain-specific code here
  public void connect(String name, String dbmsclass, String url, String username,
                      String password, int maxConnections) {
    super.connect(name, dbmsclass, url, username, password, maxConnections);

    // We can't use the normal AccessToken.root
    // as we need the troid to set lastupdateuser
    inSession(getUserTable().administratorUser(), new PoemTask() {
      public void run() {

        Contact adam =
          getContactTable().ensure(
            "Adam",
            null,
            "Eden");
        getContactTable().ensure(
            "Seth",
            adam,
            "Eden");
        getContactTable().ensure(
            "Abel",
            adam,
            "Eden");
        Contact lamech =
          getContactTable().ensure(
            "Lamech",
             getContactTable().ensure(
              "Methusael",
              getContactTable().ensure(
                "Mehujael",
                getContactTable().ensure(
                  "Irad",
                  getContactTable().ensure(
                    "Enoch",
                    getContactTable().ensure(
                      "Cain",
                      adam,
                      "Nod"),
                    "Enoch"),
                  "Enoch"),
                "Enoch"),
              "Enoch"),
            "Enoch");
        getContactTable().ensure(
            "Adah",
            lamech,
            "Enoch");
        getContactTable().ensure(
            "Jabal",
            lamech,
            "Enoch");
        getContactTable().ensure(
            "Jubal",
            lamech,
            "Enoch");
        getContactTable().ensure(
            "Naamah",
            lamech,
            "Enoch");
        getContactTable().ensure(
            "Tubal-cain",
            lamech,
            "Enoch");
        getContactTable().ensure(
            "Zilla",
            lamech,
            "Enoch");



      }
    });
  }
}
