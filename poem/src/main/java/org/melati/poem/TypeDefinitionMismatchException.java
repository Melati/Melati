package org.melati.poem;

public class TypeDefinitionMismatchException extends SeriousPoemException {
  public Column column;
  public PoemType newType;
  public DefinitionSource newTypeSource;

  public TypeDefinitionMismatchException(Column column,
                                         PoemType newType,
                                         DefinitionSource newTypeSource) {
    this.column = column;
    this.newType = newType;
    this.newTypeSource = newTypeSource;
  }

  public String getMessage() {
    return
        "Column " + column + " has its type overridden " +
        "incompatibly in " + newTypeSource + ": " + newType;
  }
}
