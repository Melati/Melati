package org.melati.doc.example;

import org.melati.poem.*;

public class Product extends ProductBase {
  public void setPrice(Double value)
      throws AccessPoemException, ValidationPoemException {
    if (value == null || value.doubleValue() >= 0.0)
      super.setPrice(value);
    else
      throw new ValidationPoemException(
          getProductTable().getPriceColumn().getType(),
          value,
          new NegativePriceException());
  }
}
