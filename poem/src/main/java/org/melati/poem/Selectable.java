package org.melati.poem;

import java.util.Enumeration;

public interface Selectable {
  public abstract Persistent getObject(Integer troid) throws NoSuchRowPoemException;
  public abstract Enumeration selection() throws SQLPoemException;
}