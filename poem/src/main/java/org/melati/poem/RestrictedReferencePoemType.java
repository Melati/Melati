package org.melati.poem;

import java.util.*;
import org.melati.util.*;

/**
 * FIXME this doesn't do quite what you'd expect, and can't unless it parses
 * the selection's whereClause in order to determine what a valid value is ...
 */

public class RestrictedReferencePoemType extends ReferencePoemType {

  private final PreparedSelection selection;

  public RestrictedReferencePoemType(PreparedSelection selection,
				     boolean nullable) {
    super(selection.getTable(), nullable);
    this.selection = selection;
  }

  protected Enumeration _possibleIdents() {
    return selection.troids();
  }
}
