package org.melati;

public class MelatiContext {
  public String logicalDatabase;
  public String table;
  public Integer troid;
  public String method;

  public String toString() {
    return "logicalDatabase = " + logicalDatabase + ", " +
           "table = " + table + ", " +
           "troid = " + troid + ", " +
           "method = " + method;
  }
}
