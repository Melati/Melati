package org.melati.poem;

public class IndexUniquenessPoemException extends SeriousPoemException {

  public Column column;
  public String indexName;
  public boolean meantToBeUnique;

  public IndexUniquenessPoemException(Column column, String indexName,
                                      boolean meantToBeUnique) {
    this.column = column;
    this.indexName = indexName;
    this.meantToBeUnique = meantToBeUnique;
  }

  public String getMessage() {
    return
        "The column " + column + " is " +
        (meantToBeUnique ? "" : "not ") + "meant to be unique, " +
        "but its index `" + indexName + "' is " +
        (meantToBeUnique ? " not " : "") + "a unique index";
  }
}
