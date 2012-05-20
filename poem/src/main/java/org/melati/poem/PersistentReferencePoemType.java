package org.melati.poem;

public interface PersistentReferencePoemType {

  /**
   * @return Table this type references
   */
  public abstract Table<?> targetTable();

}