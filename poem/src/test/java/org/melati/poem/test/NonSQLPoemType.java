/**
 * 
 */
package org.melati.poem.test;

import java.util.Enumeration;

import org.melati.poem.AccessPoemException;
import org.melati.poem.ColumnInfo;
import org.melati.poem.NullTypeMismatchPoemException;
import org.melati.poem.ParsingPoemException;
import org.melati.poem.PoemException;
import org.melati.poem.PoemType;
import org.melati.poem.TypeMismatchPoemException;
import org.melati.poem.ValidationPoemException;
import org.melati.poem.PoemLocale;

/**
 * @author timp
 * @since 29 Dec 2006
 *
 */
public class NonSQLPoemType implements PoemType {

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#assertValidCooked(java.lang.Object)
     */
    public void assertValidCooked(Object cooked) throws TypeMismatchPoemException, ValidationPoemException {
      assertValidRaw(cooked);
      
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#assertValidRaw(java.lang.Object)
     */
    public void assertValidRaw(Object raw) throws TypeMismatchPoemException, ValidationPoemException {
      if (raw == null)
          throw new NullTypeMismatchPoemException(this);
      else
          throw new TypeMismatchPoemException(raw, this);
      
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#canRepresent(org.melati.poem.PoemType)
     */
    public PoemType canRepresent(PoemType other) {
      return null;
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#cookedOfRaw(java.lang.Object)
     */
    public Object cookedOfRaw(Object raw) throws TypeMismatchPoemException, PoemException {
      if (raw == null)
        throw new NullTypeMismatchPoemException(this);
      else
        throw new TypeMismatchPoemException(raw, this);
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#getNullable()
     */
    public boolean getNullable() {
      // TODO Auto-generated method stub
      return false;
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#possibleRaws()
     */
    public Enumeration possibleRaws() {
      return null;
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#rawOfCooked(java.lang.Object)
     */
    public Object rawOfCooked(Object cooked) throws TypeMismatchPoemException {
      if (cooked == null)
        throw new NullTypeMismatchPoemException(this);
      else
        throw new TypeMismatchPoemException(cooked, this);
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#rawOfString(java.lang.String)
     */
    public Object rawOfString(String rawString) throws ParsingPoemException, ValidationPoemException {
      return null;
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#saveColumnInfo(org.melati.poem.ColumnInfo)
     */
    public void saveColumnInfo(ColumnInfo columnInfo) throws AccessPoemException {
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#stringOfCooked(java.lang.Object, org.melati.util.MelatiLocale, int)
     */
    public String stringOfCooked(Object cooked, PoemLocale locale, int style) throws TypeMismatchPoemException, PoemException {
      return null;
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#stringOfRaw(java.lang.Object)
     */
    public String stringOfRaw(Object raw) throws TypeMismatchPoemException, ValidationPoemException {
      return null;
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#toDsdType()
     */
    public String toDsdType() {
      return null;
    }

    /**
     * {@inheritDoc}
     * @see org.melati.poem.PoemType#withNullable(boolean)
     */
    public PoemType withNullable(boolean nullable) {
      return null;
    }
    
  }
