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
 *     Jim Wright <jimw@paneris.org>
 *     Bohemian Enterprise
 *     Predmerice nad Jizerou 77
 *     294 74
 *     Mlada Boleslav
 *     Czech Republic
 */

package org.melati.util;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Representation of the Accept-Charset header fields.
 * <p>
 * Provides features for choosing a charset according to client or server
 * preferences.
 *
 * @author  jimw@paneris.org
 * @version $Version: $
 */
public class AcceptCharset extends HttpHeader {

  /**
   * Preferred and supported charsets.
   * <p>
   * These are supported and either explicitly accepted by the client or
   * preferred by the server.
   * There may be others that are supported and accepted if the client
   * included the wildcard * in its acceptable charsets.
   */
  protected HashMap supportedPreferred = new HashMap();

  /**
   * Client wildcard * specification if any.
   */
  CharsetAndQValue wildcard = null;

  /**
   * The name of the first server preferred charset that is not acceptable
   * but is supported.
   * <p>
   * This may be worth checking by the caller if there are no acceptable
   * charsets, or the caller can respond with a 406 error code.
   * <p>
   * Note that if there is a wildcard then this will be null.
   */
  String firstOther = null;

  /**
   * Create an instance from the Accept-Charset header field values and
   * a set of server preferred charset names given as an array for testing.
   */
  public AcceptCharset(String values, String[] serverPreference)
      throws HttpHeaderException {
    this(values, Arrays.asList(serverPreference));
  }
  
  /**
   * Create an instance from the Accept-Charset header field values and
   * a set of server preferred charset names.
   * <p>
   * The field values might have appeared in a single Accept-Charset header
   * or in several that were concatenated with comma separator in order.
   * This concatenation is often done for the caller, by a servlet
   * container or something, but it must be done.
   * <p>
   * <code>null</code> is taken to mean there were no Accept-Charset header
   * fields.
   * <p>
   * If there are any unsupported charsets they are just ignored.
   * If the caller wants to ensure the there are not any then it must
   * check for itself.
   * <p>
   * If the same charset is specified more than once (perhaps under
   * different names or aliases) then the first occurrence is significant.
   * <p>
   * The server preferences also provides a list of charsets
   * used if there is a wildcard specification.
   * This class does not currently try other available charsets so
   * to avoid 406 errors to reasonable clients, enough reasonable charsets
   * must be specified.
   */
  public AcceptCharset(String values, List serverPreference) throws HttpHeaderException {
    super(values);
    int position = 0;
    for (CharsetAndQValueIterator i = charsetAndQValueIterator(); 
         i.hasNext();) {
      CharsetAndQValue c = i.nextCharsetAndQValue();
      if (c.isWildcard()) {
        wildcard = c;
        // System.err.println("Tested 1");
      } else {
        try {
          String n = c.charset.name();
          if (supportedPreferred.get(c) == null) {
            supportedPreferred.put(n, c);
            c.position = position++;
            // System.err.println("Tested 2");
          }
        }
        catch (UnsupportedCharsetException uce) {
          ; // Continue with next one
        }
      }
    }
    if (wildcard == null) {
      Charset latin1 = Charset.forName("ISO-8859-1");
      if (supportedPreferred.get(latin1.name()) == null) {
        CharsetAndQValue c = new CharsetAndQValue(latin1, 1.0f);
        supportedPreferred.put(latin1.name(), c);
        // System.err.println("Tested 3");
      }
    }
    for (int i = 0; i < serverPreference.size(); i++) {
      try {
        Charset charset = Charset.forName((String)serverPreference.get(i));
        CharsetAndQValue acceptable =
          (CharsetAndQValue)supportedPreferred.get(charset.name());
        if (acceptable == null) {
          if (wildcard == null) {
            if (firstOther == null) {
              firstOther = charset.name();
              // System.err.println("Tested 4");
            }
          } else {
            CharsetAndQValue c = new CharsetAndQValue(charset, wildcard);
            supportedPreferred.put(charset.name(), c);
            c.serverPreferability = i;
            // System.err.println("Tested 5");
          }
        } else {
          if (i < acceptable.serverPreferability) {
            acceptable.serverPreferability = i;
            // System.err.println("Tested 6");
          }
        }
      }
      catch (UnsupportedCharsetException uce) {
        ; // Ignore this charset
        // System.err.println("Tested 7");
      }
      
    }
  }

  /**
   * Enumeration of {@link AcceptCharset.CharsetAndQValue}.
   */
  public class CharsetAndQValueIterator extends TokenAndQValueIterator {

    public CharsetAndQValue nextCharsetAndQValue() throws HttpHeaderException {
      // System.err.println("Tested 7a");
      return (CharsetAndQValue)AcceptCharset.this.nextTokenAndQValue();
    }
  }

  public TokenAndQValue nextTokenAndQValue() throws HttpHeaderException {
    // System.err.println("Tested 7b");
    return new CharsetAndQValue(tokenizer);
  }

  /**
   * Factory method to create and return the next
   * {@link HttpHeader.TokenAndQValue}.
   */
  public CharsetAndQValueIterator charsetAndQValueIterator() {
    // System.err.println("Tested 7c");
    return new CharsetAndQValueIterator();
  }

  private final Comparator clientComparator = new Comparator();

  /**
   * Return the first supported charset that is also acceptable to the
   * client in order of client preference.
   */
  public String clientChoice() {
    // System.err.println("Tested 8");
    return choice(clientComparator);
  }

  private final Comparator serverComparator = new Comparator() {
      protected int compareCharsetAndQValue(CharsetAndQValue one,
                                   CharsetAndQValue two) {
        int result;
        result = two.serverPreferability - one.serverPreferability;
        if (result == 0) {
          result = super.compareCharsetAndQValue(one, two);
          // System.err.println("Tested 9");
        }
        return result;   
      }
    };

  /**
   * Return the first supported charset also acceptable to the client
   * in order of server preference.
   */
  public String serverChoice() {
    // System.err.println("Tested 10");
    return choice(serverComparator);
  }

  /**
   * Return the first supported charset also acceptable to the client
   * in order defined by the given {@link Comparator}.
   * <p>
   * If there is none, return null, and the caller can either use an
   * unacceptable character set or generate a 406 error.
   *
   * @see #firstOther
   */
  public String choice(Comparator comparator) {
    CharsetAndQValue best = null;
    for (Iterator i = supportedPreferred.values().iterator(); i.hasNext();) {
      CharsetAndQValue c = (CharsetAndQValue)i.next();
      if (best == null || comparator.compare(c, best) > 0) {
        best = c;
        // System.err.println("Tested 11");
      }
    }
    if (best == null || best.q == 0.0) {
      // System.err.println("Tested 12");
      return null;
    } else {
      // System.err.println("Tested 13");
      return best.charset.name();
    }
  }

  /**
   * Comparator for comparing {@link AcceptCharset.CharsetAndQValue} objects.
   */
  protected static class Comparator implements java.util.Comparator {
    
    public final int compare(Object one, Object two) {
      // System.err.println("Tested 14");
      return compareCharsetAndQValue((CharsetAndQValue)one, (CharsetAndQValue)two);
    }
    
    /**
     * The same as {@link java.util.Comparator#compare(Object, Object)} except
     * for the type of arguments.
     * <p>
     * This default compares according to client requirements.
     */
    protected int compareCharsetAndQValue(CharsetAndQValue one, CharsetAndQValue two) {
      if (one.q == two.q) {
        // System.err.println("Tested 15");
        return two.position - one.position;
      } else if (one.q > two.q) {
        // System.err.println("Tested 16");
        return 1;
      } else {
        // System.err.println("Tested 17");
        //assert one.q < two.q : "Only this possibility";
        return -1;
      }
    }
  }
  
  /**
   * A charset and associated qvalue.
   */
  public static class CharsetAndQValue extends TokenAndQValue {

    /**
     * Java platform charset or null if this is the wildcard.
     */
    Charset charset = null;

    /**
     * An integer that is less for more preferable instances from
     * server point of view.
     * <p>
     * It might be the index of the array of supported server
     * preferences or <code>Integer.MAX_VALUE</code>.
     */
    public int serverPreferability = Integer.MAX_VALUE;
    
    /**
     * An integer that indicates where this charset was explicitly
     * specified in Accept-Charset relative to others.
     * <p>
     * This increases left to right so it could be the actual position
     * but need not be.
     * <p>
     * It is <code>Integer.MAX_VALUE</code> if the charset was not
     * explicitly specified, regardless of the position of any wildcard.
     */
    public int position = Integer.MAX_VALUE;
    
    /**
     * Create an instance and initialize it by reading a tokenizer.
     */
    public CharsetAndQValue(Tokenizer t) throws HttpHeaderException {
      super(t);
      if (! isWildcard()) {
        charset = Charset.forName(token);        
        // System.err.println("Tested 18");
      }
    }

    /**
     * Creates an instance for the given charset and q value.
     */
    public CharsetAndQValue(Charset charset, float q) {
      super();
      this.token = charset.name();
      this.charset = charset;        
      this.q = q;
      // System.err.println("Tested 19");
    }

    /**
     * Creates an instance for the given <code>Charset</code>
     * using the q value from a parsed wildcard Accept-Charset field.
     */
    public CharsetAndQValue(Charset charset, CharsetAndQValue wildcard) {
      this(charset, wildcard.q);
      // System.err.println("Tested 20");
    }

    /**
     * Is the given charset token an asterix?.
     */
    public boolean isWildcard() {
      // System.err.println("Tested 20a");
      return token.equals("*");
    }

  }

} // AcceptCharset

/*
 * MODIFICATIONS
 * $Log$
 * Revision 1.5  2005/11/19 11:13:22  timp
 * Comment out assert
 *
 * Revision 1.4  2005/01/14 13:15:51  timp
 * Stop barfing about empty catch blocks
 *
 * Revision 1.3  2004/11/25 18:44:34  timp
 * Avoid * imports
 *
 * Revision 1.2  2003/11/19 04:14:06  jimw
 * Added standard header.
 *
 * Revision 1.1  2003/11/14 03:47:41  jimw
 * Representation of the Accept-Charset header fields.
 *
 */
