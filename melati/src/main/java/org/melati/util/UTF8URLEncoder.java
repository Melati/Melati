/*
 * $Revision$
 *
 */
package org.melati.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author tim.pizey
 *
 */
public class UTF8URLEncoder {
  
  public static String encode(String s) {
    String encoded = null;
    try {
      URLEncoder.encode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new UnexpectedExceptionException(e);
    }
    return encoded;
  }
  
}
