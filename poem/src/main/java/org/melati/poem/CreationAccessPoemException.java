package org.melati.poem;

public class CreationAccessPoemException extends AccessPoemException {

  public Table table;

  public CreationAccessPoemException(Table table, AccessToken token,
                                     Capability capability) {
    super(token, capability);
    this.table = table;
  }

  public String getMessage() {
    return
        "Access denied trying to create a new object in the table " +
        table.getName() + "\n" + super.getMessage();
  }
}
