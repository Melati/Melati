package org.melati;

/**
 * The base of all types.
 *
 * @see AtomType
 * @see ReferenceType
 */

public abstract class Type {

  /**
   * The underlying persistently stored record for the type's style parameters.
   * Since we don't support inheritance in the data structure definition, they
   * all have to be flattened!
   */

  DisplayStyleUnion style;

  /**
   * The type's name.
   *
   * @return a <TT>Phrase</TT> which can be localised as necessary
   */

  public abstract Phrase getName();
}
