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

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Representation of the Accept-Charset header fields.
 * <p>
 * Provides features for choosing a charset according to client or server
 * preferences.
 * 
 * @author jimw At paneris.org
 */
public class AcceptCharset extends HttpHeader {


  /**
   * Charsets supported by the jvm and accepted by the client or preferred by
   * the server.
   */
  protected HashMap<String, CharsetAndQValue> supportedAcceptedOrPreferred = new HashMap<String, CharsetAndQValue>();

  /**
   * Client wildcard * specification if any.
   */
  CharsetAndQValue wildcard = null;

  /**
   * The name of the first server preferred charset that is not acceptable to
   * the client but is supported by the jvm.
   * <p>
   * This may be worth checking by the caller if there are no acceptable
   * charsets, or the caller can respond with a 406 error code.
   * <p>
   * Note that if there is a wildcard then this will be null.
   */
  String firstOther = null;

  /**
   * Create an instance from the Accept-Charset header field values and a set of
   * server preferred charset names.
   * <p>
   * The field values might have appeared in a single Accept-Charset header or
   * in several that were concatenated with comma separator in order. This
   * concatenation is often done for the caller, by a servlet container or
   * something, but it must be done.
   * <p>
   * <code>null</code> is taken to mean there were no Accept-Charset header
   * fields.
   * <p>
   * If a client supported charset is unsupported by the JVM it is ignored. If
   * the caller wants to ensure that there are none then it must check for
   * itself.
   * <p>
   * If the same charset is specified more than once (perhaps under different
   * names or aliases) then the first occurrence is significant.
   * <p>
   * The server preferences provides a list of charsets used if there is a
   * wildcard specification.
   * 
   * This class does not currently try other available charsets so to avoid 406
   * errors to reasonable clients, enough reasonable charsets must be listed in
   * serverPreferences.
   */
  public AcceptCharset(String values, List<String> serverPreference) {
    super(values);
    int position = 0;
    for (CharsetAndQValueIterator i = charsetAndQValueIterator(); i.hasNext();) {
      try {
        CharsetAndQValue c = i.nextCharsetAndQValue();
        if (c.isWildcard()) {
          wildcard = c;
        } else {
          String n = c.charset.name();
          if (supportedAcceptedOrPreferred.get(c) == null) {
            supportedAcceptedOrPreferred.put(n, c);
            c.position = position++;
          }
        }
      } catch (UnsupportedCharsetException uce) {
        // Continue with next one
        uce = null; // shut PMD up
      }
    }
    if (wildcard == null) {
      Charset latin1 = Charset.forName("ISO-8859-1");
      if (supportedAcceptedOrPreferred.get(latin1.name()) == null) {
        CharsetAndQValue c = new CharsetAndQValue(latin1, 1.0f);
        supportedAcceptedOrPreferred.put(latin1.name(), c);
      }
    }
    for (int i = 0; i < serverPreference.size(); i++) {
      try {
        Charset charset = Charset.forName(serverPreference.get(i));
        CharsetAndQValue acceptable = (CharsetAndQValue) supportedAcceptedOrPreferred
            .get(charset.name());
        if (acceptable == null) {
          if (wildcard == null) {
            if (firstOther == null) {
              firstOther = charset.name();
            }
          } else {
            CharsetAndQValue c = new CharsetAndQValue(charset, wildcard);
            supportedAcceptedOrPreferred.put(charset.name(), c);
            c.serverPreferability = i;
          }
        } else {
          supportedAcceptedOrPreferred.put(charset.name(), acceptable);
          if (i < acceptable.serverPreferability) {
            acceptable.serverPreferability = i;
          }
        }
      } catch (UnsupportedCharsetException uce) {
        // Ignore this charset, go on to next
        uce = null; // shut PMD up
      }
    }
  }

  /**
   * Enumeration of {@link AcceptCharset.CharsetAndQValue}.
   */
  public class CharsetAndQValueIterator extends TokenAndQValueIterator {

    /**
     * @return the next one
     */
    public CharsetAndQValue nextCharsetAndQValue() throws HttpHeaderException {
      return (CharsetAndQValue) AcceptCharset.this.nextTokenAndQValue();
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.util.HttpHeader#nextTokenAndQValue()
   */
  public TokenAndQValue nextTokenAndQValue() {
    return new CharsetAndQValue(tokenizer);
  }

  /**
   * Factory method to create and return the next
   * {@link HttpHeader.TokenAndQValue}.
   * 
   * @return a new Iterator
   */
  public CharsetAndQValueIterator charsetAndQValueIterator() {
    return new CharsetAndQValueIterator();
  }

  private final Comparator<CharsetAndQValue> clientComparator = new Comparator<CharsetAndQValue>();

  /**
   * @return the first supported charset that is also acceptable to the client
   *         in order of client preference.
   * 
   */
  public String clientChoice() {
    return choice(clientComparator);
  }

  private final Comparator<CharsetAndQValue> serverComparator = new Comparator<CharsetAndQValue>() {
    protected int compareCharsetAndQValue(CharsetAndQValue one,
        CharsetAndQValue two) {
      int result;
      result = two.serverPreferability - one.serverPreferability;
      if (result == 0) {
        result = super.compareCharsetAndQValue(one, two);
      }
      return result;
    }
  };

  /**
   * @return the first supported charset also acceptable to the client in order
   *         of server preference.
   */
  public String serverChoice() {
    return choice(serverComparator);
  }

  /**
   * If there is none, return null, and the caller can either use an
   * unacceptable character set or generate a 406 error.
   * 
   * see #firstOther
   * 
   * @return the first supported charset also acceptable to the client in order
   *         defined by the given {@link Comparator}
   */
  public String choice(Comparator<CharsetAndQValue> comparator) {
    CharsetAndQValue best = null;
    for (Iterator<CharsetAndQValue> i = supportedAcceptedOrPreferred.values()
        .iterator(); i.hasNext();) {
      CharsetAndQValue c = (CharsetAndQValue) i.next();
      if (best == null || comparator.compare(c, best) > 0) {
        best = c;
      }
    }
    if (best == null || best.q == 0.0) {
      return null;
    } else {
      return best.charset.name();
    }
  }

  /**
   * Comparator for comparing {@link AcceptCharset.CharsetAndQValue} objects.
   */
  protected static class Comparator<T> implements java.util.Comparator<T> {

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Comparator#compare(T, T)
     */
    public final int compare(Object one, Object two) {
      return compareCharsetAndQValue((CharsetAndQValue) one,
          (CharsetAndQValue) two);
    }

    /**
     * This default compares according to client requirements.
     */
    protected int compareCharsetAndQValue(CharsetAndQValue one,
        CharsetAndQValue two) {
      if (one.q == two.q) {
        return two.position - one.position;
      } else if (one.q > two.q) {
        return 1;
      } else {
        // assert one.q < two.q : "Only this possibility";
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
     * An integer that is less for more preferable instances from server point
     * of view.
     * <p>
     * It might be the index of the array of supported server preferences or
     * <code>Integer.MAX_VALUE</code>.
     */
    public int serverPreferability = Integer.MAX_VALUE;

    /**
     * An integer that indicates where this charset was explicitly specified in
     * Accept-Charset relative to others.
     * <p>
     * This increases left to right so it could be the actual position but need
     * not be.
     * <p>
     * It is <code>Integer.MAX_VALUE</code> if the charset was not explicitly
     * specified, regardless of the position of any wildcard.
     */
    public int position = Integer.MAX_VALUE;

    /**
     * Create an instance and initialize it by reading a tokenizer.
     * 
     * @param t
     *          tokenizer
     */
    public CharsetAndQValue(Tokenizer t) {
      super(t);
      if (!isWildcard()) {
        charset = Charset.forName(token);
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
    }

    /**
     * Creates an instance for the given <code>Charset</code> using the q value
     * from a parsed wildcard Accept-Charset field.
     */
    public CharsetAndQValue(Charset charset, CharsetAndQValue wildcard) {
      this(charset, wildcard.q);
    }

    /**
     * @return whether the given charset token is an asterix
     */
    public boolean isWildcard() {
      return token.equals("*");
    }

  }

}
