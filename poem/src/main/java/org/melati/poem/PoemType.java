package org.melati.poem;

import java.sql.*;

public interface PoemType {
  void assertValidIdent(Object ident)
      throws TypeMismatchPoemException, ValidationPoemException;

  Object getIdent(ResultSet rs, int col)
      throws TypeMismatchPoemException, ValidationPoemException,
             ParsingPoemException;
  void setIdent(PreparedStatement ps, int col, Object value)
      throws TypeMismatchPoemException;

  String stringOfIdent(Object ident)
      throws TypeMismatchPoemException, ValidationPoemException;
  Object identOfString(String identString)
      throws ParsingPoemException, ValidationPoemException;

  void assertValidValue(Object value)
      throws TypeMismatchPoemException, ValidationPoemException;

  Object valueOfIdent(Object ident)
      throws TypeMismatchPoemException, PoemException;
  Object identOfValue(Object value) throws TypeMismatchPoemException;

  boolean isNullable();

  int sqlTypeCode();
  String sqlDefinition();
  String quotedIdent(Object ident);

  boolean canBe(PoemType other);

  void saveColumnInfo(ColumnInfo columnInfo) throws AccessPoemException;
}
