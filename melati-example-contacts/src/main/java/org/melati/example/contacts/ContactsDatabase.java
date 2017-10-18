package org.melati.example.contacts;

import org.melati.example.contacts.generated.ContactsDatabaseBase;
import org.melati.poem.PoemTask;

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
        Contact eve =
            getContactTable().ensure(
                "Eve",
                null,
                "Eden");
        Contact adam =
            getContactTable().ensure(
                "Adam",
                null,
                "Eden");
        Contact seth =
        getContactTable().ensure(
            "Seth",
            adam,
            "Eden");
        Contact abel =
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
        Contact adah =
            getContactTable().ensure(
                "Adah",
                lamech,
                "Enoch");
        Contact jabal =
            getContactTable().ensure(
                "Jabal",
                lamech,
                "Enoch");
        Contact jubal =
            getContactTable().ensure(
                "Jubal",
                lamech,
                "Enoch");
        Contact naamah =
            getContactTable().ensure(
                "Naamah",
                lamech,
                "Enoch");
        Contact tc =
            getContactTable().ensure(
                "Tubal-cain",
                lamech,
                "Enoch");
        Contact zilla =
            getContactTable().ensure(
                "Zilla",
                lamech,
                "Enoch");

        Category son = getCategoryTable().ensure("son");
        Category daughter = getCategoryTable().ensure("daughter");
        Category other = getCategoryTable().ensure("other");

        getContactCategoryTable().ensure(eve, other);
        getContactCategoryTable().ensure(adam, other);
        getContactCategoryTable().ensure(seth, son);
        getContactCategoryTable().ensure(abel, son);
        getContactCategoryTable().ensure(lamech, son);
        getContactCategoryTable().ensure(adah, daughter);
        getContactCategoryTable().ensure(jabal, son);
        getContactCategoryTable().ensure(jubal, son);
        getContactCategoryTable().ensure(naamah, son);
        getContactCategoryTable().ensure(tc, son);
        getContactCategoryTable().ensure(zilla, daughter);

      }
    });
  }
}
