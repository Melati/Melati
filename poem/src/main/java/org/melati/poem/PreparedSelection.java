package org.melati.poem;

import org.melati.util.*;
import java.sql.*;
import java.util.*;

public class PreparedSelection {

  private PreparedStatementFactory statements = null;
  private Vector selection = null;
  private long tableSerial;
  private Table table;
  private String whereClause;
  private String orderByClause;
  private String tableDefaultOrderByClause = null;

  public PreparedSelection(final Table table,
                           final String whereClause,
                           final String orderByClause) {
    this.table = table;
    this.whereClause = whereClause;
    this.orderByClause = orderByClause;
  }

  private PreparedStatementFactory statements() {
    if (orderByClause == null) {
      String defaultOrderByClause = table.defaultOrderByClause();
      if (defaultOrderByClause != tableDefaultOrderByClause) {
	statements = null;
	tableDefaultOrderByClause = defaultOrderByClause;
      }
    }

    if (statements == null)
      statements = new PreparedStatementFactory(
		       table.getDatabase(),
		       table.selectionSQL(whereClause, orderByClause, false));

    return statements;
  }

  public Enumeration troids() {
    Vector selection = this.selection;
    PoemTransaction transaction = PoemThread.transaction();
    long currentTableSerial = table.serial(transaction); 
    if (selection == null || currentTableSerial != tableSerial) {
      selection = new Vector();
      try {
	for (ResultSet rs = statements().resultSet(transaction); rs.next();)
	  selection.addElement(new Integer(rs.getInt(1)));
      }
      catch (SQLException e) {
	throw new SQLSeriousPoemException(e);
      }

      this.selection = selection;
      tableSerial = currentTableSerial;
    }

    return selection.elements();
  }

  public Table getTable() {
    return table;
  }

  public Enumeration objects() {
    return
        new MappedEnumeration(troids()) {
          public Object mapped(Object troid) {
            return table.getObject((Integer)troid);
          }
        };
  }
}
