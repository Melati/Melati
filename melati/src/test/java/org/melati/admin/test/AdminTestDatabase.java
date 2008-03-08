package org.melati.admin.test;

import org.melati.admin.test.generated.AdminTestDatabaseBase;
import org.melati.poem.AccessToken;
import org.melati.poem.PoemTask;

/**
 * Melati POEM generated, programmer modifiable stub.
 */
public class AdminTestDatabase extends AdminTestDatabaseBase
                            implements AdminTestDatabaseTables {
  // programmer's domain-specific code here
  
  public void connect(
      String name,
      String dbmsclass,
      String url,
      String username,
      String password,
      int maxConnections) {
      super.connect(name, dbmsclass, url, username, password, maxConnections);

      inSession(AccessToken.root, new PoemTask() {
        public void run() {
          getSettingTable().ensure("UploadDir", 
              "/melati-static/admin/static/", 
              "Upload Directory",
              "Directory to upload to");
          getSettingTable().ensure("UploadURL",
              "/melati-static/admin/static/", 
              "Uploaded URL",
              "URL of uploaded files, defaults to Melati Static ");
        }
      });
    }
  }



