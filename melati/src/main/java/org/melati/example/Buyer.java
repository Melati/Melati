package org.melati.doc.example;

import java.util.*;
import org.melati.poem.*;

public class Buyer extends BuyerBase {
  public Enumeration getItemsOrdered() {
    return ((ExampleDatabase)getDatabase()).getOrderTable().getBuyerColumn().selectionWhereEq(this.troid());
  }
}
