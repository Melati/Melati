package org.melati.util;

import java.util.*;

/**
 * A utility for tokenising a string made up of comma-separated
 * variables.  Unlike Tim's effort, it handles quoted variables as
 * well.  FIXME just don't try and read off the end if the last field
 * is zero-length.
 *
 * <PRE>
 *   foo, bar om,,"baz, ,oof",xyz   ->
 *     "foo", " bar om", "", "baz, , oof", "xyz"
 * </PRE>
 *
 * @author	williamc@paneris.org
 * @quality     personal
 */

public class CSVStringEnumeration implements Enumeration {

  private String line = "";
  int p = 0;

  /**
   * Look at a new string.
   */

  public void reset(String line) {
    this.line = line;
    p = 0;
  }

  /**
   * Are there any more tokens to come?
   */

  public boolean hasMoreElements() {
    return p < line.length();
  }

  /**
   * Return the next token as an <TT>Object</TT>.
   */

  public final Object nextElement() {
    return nextToken();
  }

  /**
   * Return the next token as a <TT>String</TT>.
   */

  public String nextToken() {

    if (p >= line.length()) throw new NoSuchElementException();

    if (line.charAt(p) == '"') {
      ++p;
      // we need to allow for quotes inside quoted fields, so now test for ",
      int q = line.indexOf("\",", p);
      // if it is not there, we are (hopefully) at the end of a line
      if (q == -1 && (line.indexOf('"', p) == line.length()-1)) 
      q = line.length()-1;
        
      if (q == -1) {
	      p = line.length();
	      throw new IllegalArgumentException("Unclosed quotes");
      }

      String it = line.substring(p, q);

      ++q;
      p = q+1;
      if (q < line.length()) {
	      if (line.charAt(q) != ',') {
	        p = line.length();
	        throw new IllegalArgumentException("No comma after quotes");
        }
      }
      return it;
    } else {
      int q = line.indexOf(',', p);
      if (q == -1) {
	      String it = line.substring(p);
	      p = line.length();
	      return it;
      } else {
	      String it = line.substring(p, q);
	      p = q + 1;
	      return it;
      }
    }
  }

}
