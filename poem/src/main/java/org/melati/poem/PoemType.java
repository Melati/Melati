package org.melati.poem;

import java.sql.*;
import java.util.*;

public interface PoemType {
  void assertValidIdent(Object ident)
      throws TypeMismatchPoemException, ValidationPoemException;

  Object getIdent(ResultSet rs, int col)
      throws TypeMismatchPoemException, ValidationPoemException,
             ParsingPoemException;
  void setIdent(PreparedStatement ps, int col, Object value)
      throws TypeMismatchPoemException;
  Enumeration possibleIdents();

  String stringOfIdent(Object ident)
      throws TypeMismatchPoemException, ValidationPoemException;
  Object identOfString(String identString)
      throws ParsingPoemException, ValidationPoemException;

  void assertValidValue(Object value)
      throws TypeMismatchPoemException, ValidationPoemException;

  Object valueOfIdent(Object ident)
      throws TypeMismatchPoemException, PoemException;
  Object identOfValue(Object value) throws TypeMismatchPoemException;

  String stringOfValue(Object value)
      throws TypeMismatchPoemException, PoemException;

  boolean isNullable();
  int getWidth();
  int getHeight();

  int sqlTypeCode();
  String sqlDefinition();
  String quotedIdent(Object ident);

  boolean canBe(PoemType other);

  PoemType withNullable(boolean nullable);

  void saveColumnInfo(ColumnInfo columnInfo) throws AccessPoemException;
}
