package org.melati;

public class Session {

  private Connection connection;

  public Object[] allFields(Table table, Troid troid) {
    Object them = caches[table.troid()].get(troid);

    if (them == null) {
      them = committed().allFields(table, troid);
      if (them == null) {
	them = ;		// run SELECT
	caches[table.troid()].put(troid, them);
      }
    }

    return (Object[])them;
  }

  public void commit() {
    connection.commit();
    // copy our cache into the committed cache
  }

  public void rollback() {
    connection.rollback();
    // scrub our cache
  }

  public void set(Object[] values, int index, Object newValue) {
    // copy it into our cache, make the change, write it down if that's set
  }
}
