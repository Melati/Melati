package org.melati.poem;

public class UnificationPoemException extends SeriousPoemException {
  public UnificationPoemException(Exception e) {
    super(e);
  }

  public String getMessage() {
    return
        "Something went wrong while the DSD was being reconciled \n" + 
        "with the datadictionary and JDBC metadata\n" +
        subException;
  }
}
