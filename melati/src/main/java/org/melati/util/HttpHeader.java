/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2003 Jim Wright
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     Jim Wright <jimw At paneris.org>
 *     Bohemian Enterprise
 *     Predmerice nad Jizerou 77
 *     294 74
 *     Mlada Boleslav
 *     Czech Republic
 */

package org.melati.util;

import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Enumeration;

/**
 * Representation of occurrences of an HTTP header field.
 * <p>
 * These are defined in RFC 2616 and have the same general form as in
 * RFC 822 section 3.1.
 * <P>
 * We generally assume that all continuation lines and occurrences in
 * a message are concatenated with comma separators.
 *
 * @author  Jim Wright
 */
public class HttpHeader {

  /**
   * Instance of inner {@link Tokenizer}.
   */
  protected Tokenizer tokenizer;

  /**
   * Create an instance representing the given comma separated fields.
   */
  public HttpHeader(String values) throws HttpHeaderException {
    // System.err.println("Tested 21");
    tokenizer = new Tokenizer(values);
  }

  /**
   * Abstract enumeration of fields.
   * <p>
   * Subtypes decide what type of token to return and how
   * to represent it.
   * <p>
   * This class serves to remove doubts about whether we should and can
   * implement <code>Iterator</code> or <code>Enumeration</code> and
   * proves itself unnecessary ;-). But we can factor stuff out and
   * re-use it later.
   * <p>
   * Actually, it also removes the need to think about exceptions in
   * subtypes.
   */
  public abstract class FieldIterator implements Iterator, Enumeration {

    /**
     * {@inheritDoc}
     * @see java.util.Enumeration#hasMoreElements()
     */
    public final boolean hasMoreElements() {
      return hasNext();
    }

    /**
     * {@inheritDoc}
     * @see java.util.Enumeration#nextElement()
     */
    public final Object nextElement() {
      return next();
    }

    /**
     * {@inheritDoc}
     * @see java.util.Iterator#hasNext()
     * @see #next()
     */
    public final boolean hasNext() {
      // System.err.println("Tested 24");
      return tokenizer.ttype != StreamTokenizer.TT_EOF;
    }

    /**
     * {@inheritDoc}
     * @see java.util.Iterator#remove()
     */
    public void remove() throws UnsupportedOperationException {
      // System.err.println("Tested 25");
      throw new UnsupportedOperationException("Cannot remove tokens from the HTTP header");
    }

    /**
     * Return the next element or an exception.
     *
     * @return An exception if an object of the anticipated type cannot be returned
     */
    public Object next() {
      try {
        // System.err.println("Tested 26");
        return nextToken();
      }
      catch (HttpHeaderException e) {
        // System.err.println("Tested 27");
        return e;
      }
    }

    /**
     * @return the next token or throws an exception
     */
    public abstract Object nextToken() throws HttpHeaderException;

  }

  /**
   * Iteration over {@link HttpHeader.TokenAndQValue}s.
   */
  public class WordIterator extends FieldIterator {

    /**
     * @return the next word
     */
    public String nextWord() throws HttpHeaderException {
      String result = tokenizer.readWord();
      tokenizer.skipAnyCommaSeparator();
      return result;
    }

    /**
     * {@inheritDoc}
     * @see org.melati.util.HttpHeader.FieldIterator#nextToken()
     */
    public Object nextToken() throws HttpHeaderException {
      return nextWord();
    }

  }

  /**
   * Factory method to create and return an iterator of words.
   * 
   * @return a new WordIterator
   */
  public final WordIterator wordIterator() {
    return new WordIterator();
  }

  /**
   * Iteration over {@link HttpHeader.TokenAndQValue}s.
   */
  public class TokenAndQValueIterator extends FieldIterator {

    /**
     * @return the next TokenAndQValue
     * @throws HttpHeaderException
     */
    public TokenAndQValue nextTokenAndQValue() throws HttpHeaderException {
      return HttpHeader.this.nextTokenAndQValue();
    }

    /**
     * {@inheritDoc}
     * @see org.melati.util.HttpHeader.FieldIterator#nextToken()
     */
    public Object nextToken() throws HttpHeaderException {
      return nextTokenAndQValue();
    }

  }

  /**
   * Factory method to create and return the next
   * {@link HttpHeader.TokenAndQValue}.
   * @return a new TokenAndQValue
   */
  public TokenAndQValue nextTokenAndQValue() throws HttpHeaderException {
    return new TokenAndQValue(tokenizer);
  }

  /**
   * Factory method to create and return an iterator of {@link TokenAndQValue}'s.
   * @return a new TokenAndQValueIterator
   */
  public TokenAndQValueIterator tokenAndQValueIterator() {
    return new TokenAndQValueIterator();
  }

  /**
   * A token and associated qvalue.
   */
  public static class TokenAndQValue {

    /**
     * Token followed by a semicolon separator.
     */
    public String token;

    /**
     * Value between zero and one with at most 3 decimal places.
     * <p>
     * q stands for "quality" but the RFC 2616 says this is not
     * completely accurate.
     * Values closer to 1.0 are better.
     * Zero means completely unfit.
     * <p>
     * The default is 1.0 if not explicitly initialised and this
     * appears to be correct for most possible uses if not all.
     */
    public float q = 1.0f;

    /**
     * Create an uninitialised instance.
     */
    public TokenAndQValue() {
    }

    /**
     * Create an instance and initialise it by reading the given
     * tokenizer.
     */
    public TokenAndQValue(Tokenizer t) throws HttpHeaderException {
      this();
      t.readTokenAndQValue(this);
      t.skipAnyCommaSeparator();
    }

  }  
  
  /**
   * Tokenizer for parsing occurences of a field.
   * <p>
   * Header fields have format defined in RFC 2616 and have the same
   * general form as in RFC 822 section 3.1.
   * <p>
   * This is for fields consisting of tokens, quoted strings and
   * separators and not those consisting of an arbitrary sequence of
   * octets.
   * Tokens are US ASCII characters other than:
   * <ul>
   * <li> control characters 0000 to 001F and 007E;
   * <li> separators defined in RFC 2616;
   * </ul>
   * <p>
   * The convenience methods defined here provide some guidance on how
   * to interact with the super-type but you can also use inherited
   * methods.
   * <p>
   * We assume that the next token is always already read when a method
   * starts to interpret a sequence of tokens.
   * In other words the first token is read by the constructor(s) and then
   * each such
   * method returns as a result of reading a token or EOF that it cannot
   * process but without pushing it back.
   * The next token to be interpreted is hence the current token
   * described by the inherited instance variables.
   * <p>
   * Note that whitespace is automatically skipped by the supertype.
   *
   * @author  Jim Wright
   */
  public static class Tokenizer extends StreamTokenizer {

    /**
     * Create an instance from a string formed by concatenation of
     * continuation lines and all occurences of a field, with comma
     * separators.
     * <p>
     * In theory a separator can consist of one or more commas and
     * spaces and tab.
     * Fields are never empty.
     * We cope with this but I doubt typical callers ever encounter
     * such strings.
     * <p>
     * The field list should not be empty but null is
     * allowed to explicitly indicate that there are no such fields,
     * if an instance if required nevertheless to provide other
     * functionality.
     *
     * @throws HttpHeaderException Error detected in the argument.
     */
    public Tokenizer(String fields) throws HttpHeaderException {
      super(new StringReader(fields == null ? "" : fields));

      if (fields != null && fields.length() == 0) {
        // System.err.println("Tested 35");
        throw new HttpHeaderException("Empty sequence of HTTP header fields");        
      }
      resetSyntax();
      // Initially make all non-control characters token
      // characters
      wordChars('\u0020', '\u007E');
      // Now change separators back. Tab is not
      // necessary and there are some ranges but let's
      // not try and be clever.
      String separator = "()<>@,;:\\\"/[]?={} \t";
      for (int i = 0; i < separator.length(); i++) {
        ordinaryChar(separator.charAt(i));
        // System.err.println("Tested 34");
      }

      // Resetting effectively did this to whitespace chars
      // ordinaryChars('\u0000', '\u0020');
      // Set space and table characters as whitespace
      whitespaceChars(' ', ' ');
      whitespaceChars('\t', '\t');

      quoteChar('"');

      parseNumbers();

      // Here are some things we have effectively done by resetting
      // ordinaryChar('/');
      // ordinaryChar('\'');

      // Do not do any other special processing
      eolIsSignificant(false);
      lowerCaseMode(false);
      slashSlashComments(false);
      slashStarComments(false);

      // Read the first token
      nextLToken();
      if (ttype == ',') {
        // System.err.println("Tested 36");
        throw new HttpHeaderException("HTTP header fields starts with comma separator");
      }
    }

    /**
     * Same as <code>nextToken()</code> but does not throw an <code>IOException</code>
     * and handles erroneous line breaks.
     *
     * @return int value of next LToken
     * @throws HttpHeaderException Error detected in the fields.
     */
    public int nextLToken() throws HttpHeaderException {
      int result;
      try {
        result = nextToken();
        if (ttype == TT_EOL) {
          System.err.println("Not tested 38");
          throw new HttpHeaderException("HTTP header fields span unquoted line breaks");
        }
        // System.err.println("Tested 39");
        return result;
      }
      catch (IOException e) {
        //assert false : "We are reading from a string";
        return 0;
      }
    }

    /**
     * Read up to and including the next token after comma
     * separator(s) and whitespace assuming the current token is a comma.
     *
     * @return Resulting ttype.
     */
    public final int skipCommaSeparator() throws HttpHeaderException {
      if (ttype != ',') {
        throw new IllegalStateException("Not at a comma");
      }
      while (nextLToken() == ',')
        ;
      return ttype;
    }

    /**
     * Read up to and including the next token after any comma
     * separator(s) and whitespace.
     * <p>
     * This is the same as {@link #skipCommaSeparator()} but it does
     * nothing if we are and EOF.
     *
     * @return Resulting ttype.
     */
    public final int skipAnyCommaSeparator() throws HttpHeaderException {
      if (ttype != TT_EOF) {
        skipCommaSeparator();
      }
      return ttype;
    }

    /**
     * Convenience method to test for token or quoted string.
     * <p>
     * If this returns true then the token value is in <code>sval</code>
     * with any quotes removed.
     * @return whether token is an SVal
     */
    public final boolean isSVal() {
      return ttype == TT_WORD || ttype == '"';
    }

    /**
     * Read the word token or quoted string that comes next.
     *
     * @return the SVal 
     * @throws HttpHeaderException Error detected in the fields.
     */
    public final String readSVal() throws HttpHeaderException {
      if (! isSVal()) {
        throw new HttpHeaderException("Next token is not a (possibly quoted) word: " +
            toString());
      }      
      String result = sval;
      nextLToken();
      return result;
    }

    /**
     * Read the word token that comes next.
     * 
     * @return the word as a String
     * @throws HttpHeaderException Error detected in the fields.
     */
    public final String readWord() throws HttpHeaderException {
      if (ttype != TT_WORD) {
        throw new HttpHeaderException("Next token is not a word token: " +
                                      toString());
      }      
      String result = sval;
      nextLToken();
      // System.err.println("Tested 47");
      return result;
    }

    /**
     * Read the given word token that comes next.
     *
     * @throws HttpHeaderException Error detected in the fields.
     */
    public final void readWord(String word) throws HttpHeaderException {
      String read = readWord();
      if (! read.equals(word)) {
        // System.err.println("Tested 48 by temporary hack");
        throw new HttpHeaderException("Expecting '" + word +
                                      "' but encountered: " + toString());
      }
    }

    /**
     * Read the given character that comes next.
     *
     * @throws HttpHeaderException Error detected in the fields.
     */
    public final void readChar(char c) throws HttpHeaderException {
      if (ttype != c) {
        // System.err.println("Tested 49");
        throw new HttpHeaderException("Expecting '" + c +
                                      "' but encountered: " +
                                      toString());
      }
      nextLToken();
    }

    /**
     * Read the number token that comes next.
     * @return the number's value as a double
     * @throws HttpHeaderException Error detected in the fields.
     */
    public final double readNVal() throws HttpHeaderException {
      if (ttype != TT_NUMBER) {
        throw new HttpHeaderException("Next token is not a number: " +
            toString());
      }      
      double result = nval;
      nextLToken();
      return result;
    }

    /**
     * Read a token sequence of the form "; q = 0.42" and return the number.
     * @return the number's value as a float
     *
     * @throws IllegalStateException Current token not semicolon.
     * @throws HttpHeaderException Error detected in the fields.
     */
    public final float readQValue() 
        throws IllegalStateException, HttpHeaderException {
      if (ttype != ';') {
        throw new IllegalStateException("Not at a semicolon");
      }
      readChar(';');
      readWord("q");
      readChar('=');
      return (float)readNVal();
    }

    /**
     * Read a word or quoted string token optionally followed by a string
     * of the form "; q = 0.42" and initialises the given object.
     * @return current TokenAndQValue
     */
    protected TokenAndQValue readTokenAndQValue(TokenAndQValue result)
          throws HttpHeaderException {
      result.token = readSVal();
      switch (ttype) {
      case TT_EOF :
      case ',' :
        break;
      case ';' :
        result.q = readQValue();
        break;
      default:
        throw new HttpHeaderException("Word token: \'" + result.token +
            "\' is followed by something unexpected: " + toString());
      }
      return result;
    }

  }

  /**
   * Exception detected in an {@link HttpHeader}.
   * <p>
   * We might want to declare some supertype as thrown or make this
   * outer.
   * <p>
   * Header fields are usually obtained from servlet containers or
   * similar after some processing.
   * But its possible that some unusual client has sent something
   * erroneous or just unusual that has not been filtered out
   * earlier and causes an error here.
   * <p>
   * In general detecting such problems requires parsing.
   * So although we could nearly always blame the caller we provide
   * a service instead (as part of the contract).
   * <p>
   * We do sometimes blame the caller because we assume that the
   * caller has checked the next token type before some call.
   * We do this by throwing an <code>IllegalStateException</code>
   * instead.
   */
  public static class HttpHeaderException extends java.lang.Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Create an instance with message.
     */
    public HttpHeaderException(String message) {
      super(message);
    }

    /**
     * Create an instance with message and cause.
     */
    public HttpHeaderException(String message, Exception e) {
      super(message, e);
    }

  }

}
