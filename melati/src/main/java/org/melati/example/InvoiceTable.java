package org.melati.doc.example;

import org.melati.*;

/**
 * The class of the object representing the <TT>Invoice</TT> table.
 */

public interface InvoiceTable extends Table {

  /**
   * The object representing an invoice with given primary key.
   *
   * @param id		the invoice's primary ID
   */

  public Invoice invoiceRecord(int id);
}
