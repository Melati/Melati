package org.melati.poem;

import java.sql.*;

public class PostgresDatabase extends Database {

/*
  protected ResultSet getColumnsMetadata(DatabaseMetaData m, String tableName)
      throws SQLException, InvalidNamePoemException {
    return m.getColumns(null, null, quotedIdent(tableName), null);
  }
*/

  public void appendQuotedName(StringBuffer buffer, String name)
      throws InvalidPostgresNamePoemException {
    if (name.indexOf('"') != -1)
      throw new InvalidPostgresNamePoemException(name);
    buffer.append(name);
  }

  public static void main(final String[] args) throws Exception {
    DriverManager.registerDriver((Driver)Class.forName("postgresql.Driver").newInstance());
    final Database database = new PostgresDatabase();
    database.connect("jdbc:postgresql:williamc", "williamc", "*");
    database.dump();

    database.inSession(
        AccessToken.root,
        new Runnable() {
          public void run() {
            try {
              Table t = database.getTable("foo");
              t.getObject(3).setValue("bar", new java.util.Date().toString().substring(0, 20));
            }
            catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
  }
}
