package org.melati.doc.example;

import org.melati.*;

public interface InvoicingDatabase extends Database {

  /**
   * The object representing the <TT>Invoice</TT> table.
   */

  public InvoiceTable invoiceTable();

  /**
   * The object representing the <TT>InvoiceLine</TT> table.
   */

  public InvoiceLineTable invoiceLineTable();
}
