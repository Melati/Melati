package org.melati;

/**
 * A database table.  This is the minimal set of methods available; tables
 * defined in a data structure definition will have a richer, automatically
 * generated design pattern.
 *
 * @see org.melati.doc.example.InvoiceTable
 */

public interface Table {

  /**
   * The object representing the table row with given primary key.  Tables
   * mentioned in the data structure definition will have an extra method
   * returning an up-cast specialised record object.
   *
   * @param id			the record's primary ID
   * @see org.melati.doc.example.InvoiceTable#invoiceRecord
   */

  public Record record(int id) throws PersistenceException;

  /**
   * The results of a database search.  A wrapper round a <TT>SELECT * FROM
   * <I>table</I> WHERE</TT> ... query.
   *
   * Probably we want to add other features, and a facility for preparing
   * these queries.
   *
   * @param whereConditions	the part of the <TT>SELECT</TT> after the
   *                            <TT>WHERE</TT>
   *
   * @return an <TT>Enumeration</TT> of <TT>Record</TT>s
   */

  public Enumeration selection(String whereConditions);
}
