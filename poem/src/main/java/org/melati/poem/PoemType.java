package org.melati.poem;

import java.sql.*;
import java.util.*;
import org.melati.util.*;

public interface PoemType {
  void assertValidRaw(Object raw)
      throws TypeMismatchPoemException, ValidationPoemException;

  Object getRaw(ResultSet rs, int col)
      throws TypeMismatchPoemException, ValidationPoemException,
             ParsingPoemException;
  void setRaw(PreparedStatement ps, int col, Object cooked)
      throws TypeMismatchPoemException;
  Enumeration possibleRaws();

  String stringOfRaw(Object raw)
      throws TypeMismatchPoemException, ValidationPoemException;
  Object rawOfString(String rawString)
      throws ParsingPoemException, ValidationPoemException;

  void assertValidCooked(Object cooked)
      throws TypeMismatchPoemException, ValidationPoemException;

  Object cookedOfRaw(Object raw)
      throws TypeMismatchPoemException, PoemException;
  Object rawOfCooked(Object cooked) throws TypeMismatchPoemException;

  /**
   * @param style       as in <TT>java.text.DateFormat.SHORT</TT>, ...
   */

  String stringOfCooked(Object cooked, MelatiLocale locale, int style)
      throws TypeMismatchPoemException, PoemException;

  boolean getNullable();
  int getWidth();
  int getHeight();

  int sqlTypeCode();
  String sqlDefinition();
  String quotedRaw(Object raw);

  boolean canBe(PoemType other);

  PoemType withNullable(boolean nullable);

  void saveColumnInfo(ColumnInfo columnInfo) throws AccessPoemException;
}
