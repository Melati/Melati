package org.melati.poem.postgresql.jdbc1;

import postgresql.*;
import postgresql.jdbc1.*;
import postgresql.jdbc1.Connection;
import org.melati.util.*;

public class IndexAwareDatabaseMetaData
    extends postgresql.jdbc1.DatabaseMetaData {

  protected Connection connection;

  public IndexAwareDatabaseMetaData(Connection connection) {
    super(connection);
    this.connection = connection;
  }

  public java.sql.ResultSet getIndexInfo(String catalog, String schema,
                                         String table, boolean unique,
                                         boolean approximate)
      throws java.sql.SQLException {

    return connection.ExecSQL(
        "SELECT " +
          "null AS TABLE_CAT, null AS TABLE_SCHEMA, t.relname AS TABLE_NAME, " +
          "NOT i.indisunique AS NON_UNIQUE, null AS INDEX_QUALIFIER, " +
          "ti.relname AS INDEX_NAME, " + tableIndexOther + " AS TYPE, " +
          "i.indkey[0] AS ORDINAL_POSITION, a.attname AS COLUMN_NAME, " +
          "NULL AS ASC_OR_DESC, 0 AS CARDINALITY, 0 AS PAGES, " +
          "NULL AS FILTER_CONDITION " +
        "FROM " +
          "pg_index i, pg_class ti, pg_class t, pg_attribute a " +
        "WHERE " +
          "ti.oid = i.indexrelid AND t.oid = i.indrelid AND " +
          (table == null ?
             "" :
             "t.relname = '" + StringUtils.escaped(table, '\'') + "' AND ") +
          (unique ? "i.indisunique AND " : "") +
          "a.attrelid = i.indrelid AND " +
          // this strange little construct is needed because
          // `a.attnum = i.indkey[0]' causes 6.4.2 (at least) to fail, losing
          // connection to backend:
          "a.attnum - i.indkey[0] = 0");
  }

  public static void main(String[] args) throws Exception {
    java.sql.DriverManager.registerDriver((Driver)Class.forName("postgresql.Driver").newInstance());
    java.sql.Connection c = java.sql.DriverManager.getConnection("jdbc:postgresql:" + args[0], "postgres", "*");
    java.sql.DatabaseMetaData m = c.getMetaData();

    java.sql.ResultSet inds = m.getIndexInfo(null, "", args[1], false, true);
    while (inds.next())
      System.out.println("TABLE_CAT " + inds.getString("TABLE_CAT") + "\n" +
                         "TABLE_SCHEMA " + inds.getString("TABLE_SCHEMA") + "\n" +
                         "TABLE_NAME " + inds.getString("TABLE_NAME") + "\n" +
                         "NON_UNIQUE " + inds.getBoolean("NON_UNIQUE") + "\n" +
                         "INDEX_QUALIFIER " + inds.getString("INDEX_QUALIFIER") + "\n" +
                         "INDEX_NAME " + inds.getString("INDEX_NAME") + "\n" +
                         "TYPE " + inds.getShort("TYPE") + "\n" +
                         "ORDINAL_POSITION " + inds.getShort("ORDINAL_POSITION") + "\n" +
                         "COLUMN_NAME " + inds.getString("COLUMN_NAME") + "\n" +
                         "ASC_OR_DESC " + inds.getString("ASC_OR_DESC") + "\n" +
                         "CARDINALITY " + inds.getInt("CARDINALITY") + "\n" +
                         "PAGES " + inds.getInt("PAGES") + "\n" +
                         "FILTER_CONDITION " + inds.getString("FILTER_CONDITION") + "\n");
  }
}
