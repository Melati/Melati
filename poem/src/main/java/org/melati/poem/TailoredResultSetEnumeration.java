package org.melati.poem;

import java.util.*;
import java.sql.*;

public class TailoredResultSetEnumeration extends ResultSetEnumeration {

  private TailoredQuery query;

  public TailoredResultSetEnumeration(TailoredQuery query,
				      ResultSet resultSet) {
    super(resultSet);
    this.query = query;
  }

  protected Object mapped(ResultSet them)
      throws SQLException, NoSuchRowPoemException {
    Field[] fields = new Field[query.columns.length];

    AccessToken token = PoemThread.accessToken();

    try {
      for (int t = 0; t < query.canReadTables.length; ++t) {
	Capability canRead = query.canReadTables[t].getDefaultCanRead();
	if (canRead != null && !token.givesCapability(canRead))
	  throw new AccessPoemException(token, canRead);
      }

      for (int c = 0; c < fields.length; ++c) {
	Column column = query.columns[c];
	Object ident = column.getType().getIdent(them, c + 1);

	if (query.isCanReadColumn[c]) {
	  Capability canRead = (Capability)column.getType().valueOfIdent(ident);
	  if (canRead == null)
	    canRead = column.getTable().getDefaultCanRead();
	  if (canRead != null && !token.givesCapability(canRead))
	    throw new AccessPoemException(token, canRead);
	}

	fields[c] = new Field(ident, column);
      }
    }
    catch (AccessPoemException accessProblem) {
      // Blank out the whole FieldSet.  We have to do this because we don't
      // know how fields have been used in the WHERE clause without
      // interpreting it.  Walls have ears and all that.

      for (int c = 0; c < fields.length; ++c)
	fields[c] = new Field(accessProblem, query.columns[c]);
    }

    return new FieldSet(query.table_columnMap, fields);
  }
}
