package org.melati.admin;

import org.melati.poem.*;

public class AdminUtils {

  private String adminURL;
  private String logicalDatabase;

  public AdminUtils(String adminURL, String logicalDatabase) {
    this.adminURL = adminURL;
    this.logicalDatabase = logicalDatabase;
  }

  public String editURL(Persistent object) throws AccessPoemException {
    return
        adminURL + "/" + logicalDatabase + "/" +
        object.getTable().getName() + "/" + object.troid() + "/Edit";
  }

  public String tableURL(Table table) {
    return adminURL + "/" + logicalDatabase + "/" + table.getName() + "/View";
  }
}
