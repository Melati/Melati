package org.melati.doc.example;

import org.melati.*;

/**
 * Manually-written part of the class representing records from the
 * <TT>Invoice</TT> table.
 */

public class Invoice extends InvoiceBase {

  /**
   * Implement the policy that the issuer and receiver of an invoice
   * can view its fields.
   */

  public void assertReadable(AccessToken token) throws AccessException {
    if (token.getUser() != getIssuer() && token.getUser() != getReceiver())
      super.assertReadable(token);
  }
}
