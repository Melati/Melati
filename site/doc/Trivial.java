package org.melati.poem.doc;

import org.melati.poem.*;

public class Trivial {
  public static void main(String[] args) throws Exception {

    final PoemDatabase database = new PoemDatabase();
    database.connect("jdbc:postgresql:williamc", "williamc", "*");

    database.inSession(
        AccessToken.root,
        new PoemTask() {
          public void run() throws PoemException {
            User user5 = database.getUserTable().getUserObject(5);
            user5.setName("William Chesters");
          }
        });
  }
}
