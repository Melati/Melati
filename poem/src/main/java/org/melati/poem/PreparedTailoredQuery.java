package org.melati.poem;

import java.util.*;
import java.sql.*;
import org.melati.util.*;

public class PreparedTailoredQuery extends TailoredQuery {
  private PreparedStatementFactory statements;

  public PreparedTailoredQuery(Column[] selectedColumns, Table[] otherTables,
			       String whereClause, String orderByClause) {
    super(selectedColumns, otherTables, whereClause, orderByClause);
    statements = new PreparedStatementFactory(database, sql);
  }

  public Enumeration selection() {
    return new TailoredResultSetEnumeration(this, statements.resultSet());
  }
}
