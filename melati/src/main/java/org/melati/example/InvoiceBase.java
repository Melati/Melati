package org.melati.doc.example;

import org.melati.*;

/**
 * Base class, auto-generated from the data structure definition,
 * representing records from the <TT>Invoice</TT> table.  There are
 * named, type-safe pairs of methods for field value access, plus
 * convenience methods for getting field values wrapped up with type
 * information and display preferences.
 *
 * @see Invoice
 */

public abstract class InvoiceBase extends Record {

  /**
   * The invoice's number.
   */

  public String getNumber() {
    return null;
    // actually something like fields[1]
  }

  /**
   * The invoice's number, wrapped up with type information and
   * display preferences.
   */

  public Field getNumberField() {
    return null;
    // actually something like new Field(fields[1], table.fieldType(1))
  }

  /**
   * Set the invoice's number.
   */

  public void setNumber(String number) {}

  public Party getIssuer() {
    return null;
  }

  public Field getIssuerField() {
    return null;
  }

  public void setIssuer(Party issuer) {}

  public Party getReceiver() {
    return null;
  }

  public Field getReceiverField() {
    return null;
  }

  public void setReceiver(Party receiver) {}

  /**
   * A convenient `container' for the invoice's lines.  Each element
   * is an <TT>InvoiceLine</TT>.
   */

  public Subset lines() {
    return null;
  }


  public String getNotes() {
    return null;
  }

  public Field getNotesField() {
    return null;
  }

  public void setNotes(String notes) {}
}
