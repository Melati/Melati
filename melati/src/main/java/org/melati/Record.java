package org.melati;

import java.util.Enumeration;

/**
 * A generic persistent object.  Objects which come from tables
 * (classes) mentioned in the data structure definition will have
 * extra named, type-safe methods for each field defined there.  The
 * methods declared here are still useful for handling generic extra
 * fields.
 *
 * @see org.melati.doc.example.Invoice
 */

public class Record {

  // 
  // ---------------------
  //  Hidden magic things
  // ---------------------
  // 

  /**
   * The actual field values.
   */

  Object fields[];

  Object[] fields() {
    Implicit.session().retrieve(table, troid);
  }

  /**
   * The table the record comes from.  (In fact this is more likely to
   * point to a table schema spec dating from the time the record was
   * read in, so that we can do something sensible when the schema
   * changes.)
   */

  Table table;

  /**
   * Its primary key within the table.
   */

  int oid;

  // 
  // ---------------
  //  Identity info
  // ---------------
  // 

  /**
   * The table the record comes from.
   */

  public Table getTable() {
    return table;
  }

  /**
   * Its primary key within the table.
   */

  public int getOID() {
    return oid;
  }

  // 
  // ------------------
  //  Accessing fields
  // ------------------
  // 

  /**
   * A field's value, keyed by name.  If the field was declared in the
   * data structure definition, the corresponding named access method
   * is invoked in case it has been overridden by the programmer to do
   * something special.
   */

  public Object get(String name) {
    return null;
  }

  /**
   * A field's value wrapped up with type information and display
   * preferences, keyed by name.
   */

  public Field getField(String name) {
    return null;
  }

  /**
   * Set the value of a field keyed by name.  If the field was
   * declared in the data structure definition, the corresponding
   * named getter method is invoked in case it has been overridden by
   * the programmer to do something special.
   */

  public void set(String name, Object value) {}

  /**
   * All the record's fields.
   */

  public Enumeration fields() {
    return null;
  }

  // 
  // ----------------
  //  Access control
  // ----------------
  // 

  /**
   * Check that the capabilities afforded by an access token suffice
   * for reading this record's data.
   */

  public void assertReadable(AccessToken token) throws AccessException {
    // if there are access capability fields defined, use them
  }

  /**
   * Check that the capabilities afforded by an access token suffice
   * for writing this record's data.
   */

  public void assertWriteable(AccessToken token) throws AccessException {
    // if there are access capability fields defined, use them
  }
}
