package org.melati;

/**
 * A database.  This is the minimal set of methods available;
 * databases defined by a data structure definition will have a
 * richer, automatically generated design pattern.
 *
 * @see org.melati.doc.example.InvoicingDatabase
*/

public interface Database {

  /**
   * The object representing one of the database's tables.  Tables
   * mentioned in the data structure definition can be accessed by
   * named methods returning up-cast specialised table objects.
   *
   * @param name	the table's programmatic name
   * @see org.melati.doc.example.InvoicingDatabase#invoiceTable
   */

  public Table table(String name);

  /**
   * All the database's tables.
   *
   * @return an <TT>Enumeration</TT> of <TT>Table</TT>s
   */

  public Enumeration tables();
}
