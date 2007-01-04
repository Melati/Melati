/**
 * 
 */
package org.melati.poem.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.melati.poem.AccessPoemException;
import org.melati.poem.AtomPoemType;
import org.melati.poem.ColumnInfo;
import org.melati.poem.NullTypeMismatchPoemException;
import org.melati.poem.ParsingPoemException;
import org.melati.poem.SQLPoemType;
import org.melati.poem.TypeMismatchPoemException;
import org.melati.poem.ValidationPoemException;

public class SqlExceptionPoemType extends AtomPoemType {

  /**
   * Constructor.
   * 
   * @param nullable
   *          whether nullable or not
   */
  public SqlExceptionPoemType(boolean nullable) {
    super(Types.INTEGER, "INT", nullable);
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.poem.BasePoemType#_assertValidRaw(java.lang.Object)
   */
  protected void _assertValidRaw(Object raw) throws ValidationPoemException {
    if (raw == null)
      throw new NullTypeMismatchPoemException(this);
    if (! (raw instanceof Integer))
      throw new TypeMismatchPoemException(raw, this);
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.poem.BasePoemType#_canRepresent(org.melati.poem.SQLPoemType)
   */
  protected boolean _canRepresent(SQLPoemType other) {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.poem.BasePoemType#_getRaw(java.sql.ResultSet, int)
   */
  protected Object _getRaw(ResultSet rs, int col) throws SQLException {
    throw new SQLException("Dummy");
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.poem.BasePoemType#_rawOfString(java.lang.String)
   */
  protected Object _rawOfString(String string) throws ParsingPoemException {
    
    return new Integer(string);
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.poem.BasePoemType#_saveColumnInfo(org.melati.poem.ColumnInfo)
   */
  protected void _saveColumnInfo(ColumnInfo info) throws AccessPoemException {
    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.poem.BasePoemType#_setRaw(java.sql.PreparedStatement, int,
   *      java.lang.Object)
   */
  protected void _setRaw(PreparedStatement ps, int col, Object raw)
      throws SQLException {
    throw new SQLException("Dummy");

  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.poem.SQLType#sqlDefaultValue()
   */
  public String sqlDefaultValue() {
    // TODO Auto-generated method stub
    return "1";
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.poem.PoemType#toDsdType()
   */
  public String toDsdType() {
    // TODO Auto-generated method stub
    return null;
  }

}
