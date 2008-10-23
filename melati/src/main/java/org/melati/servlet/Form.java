/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Tim Joyce
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
 *     Tim Joyce <timj At paneris.org>
 *     http://paneris.org/
 *     68 Sandbanks Rd, Poole, Dorset. BH14 8BY. UK
 */

package org.melati.servlet;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;


import javax.servlet.http.HttpServletRequest;

import org.melati.poem.Persistent;
import org.melati.poem.Column;
import org.melati.template.ServletTemplateContext;
import org.melati.template.TempletAdaptor;
import org.melati.template.TempletAdaptorConstructionMelatiException;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.UTF8URLEncoder;

/**
 * An object to hold useful static methods for manipulation of a Form in a 
 * {@link ServletTemplateContext}.
 */

public final class Form {
  
  static Hashtable adaptorCache = new Hashtable();

  private Form() {}
  
  /**
   * Retrieve updated persistent fields from a context modified in a template.
   * <p>
   * The context can specify an adaptor for each field using another HTML
   * field with name suffix &quot;-adaptor&quot; and value the classname of
   * a <code>TempletAdaptor</code>.
   * Hence the templet that renders the field can specify how
   * the result is parsed. 
   * This is currently used for dates.
   *
   * @param context the current {@link ServletTemplateContext} to get values from
   * @param object  the {@link Persistent} to update
   */
  public static void extractFields(ServletTemplateContext context, 
                                   Persistent object) {
    for (Enumeration c = object.getTable().columns(); c.hasMoreElements();) {
      Column column = (Column)c.nextElement();
      String formFieldName = "field_" + column.getName();
      String rawString = context.getForm(formFieldName);

      String adaptorFieldName = formFieldName + "-adaptor";
      String adaptorName = context.getForm(adaptorFieldName);
      if (adaptorName != null) {
        TempletAdaptor adaptor = getAdaptor(adaptorFieldName, adaptorName);
        column.setRaw(object, adaptor.rawFrom(context, formFieldName));
      } else {
        if (rawString != null) {
          rawString = rawString.trim();
          if (rawString.equals("")) {
            if (column.getType().getNullable())
              column.setRaw(object, null);
            else
              column.setRawString(object, "");
          } else {
            String branch;
            String utf8StringISO = null;
            String utf8StringUTF8 = null;
            byte[] stringBytesISO;
            byte[] stringBytesUTF8;
            try {
              stringBytesISO = rawString.getBytes("ISO-8859-1");
              utf8StringISO = new String(stringBytesISO, "ISO-8859-1");
              stringBytesUTF8 = rawString.getBytes("UTF-8");
              utf8StringUTF8 = new String(stringBytesUTF8, "UTF-8");
            
              if (utf8StringISO.equals(rawString)) {
                column.setRawString(object, utf8StringISO);
                branch = "1";
              } else if (utf8StringUTF8.equals(rawString)) {
                column.setRawString(object, utf8StringUTF8);
                branch = "2";
              } else {
                column.setRawString(object, rawString);              
                branch = "3";
              }
            } catch (UnsupportedEncodingException e) {
              throw new MelatiBugMelatiException("UTF-8 or ISO-8859-1 not supported", e);
            }
            System.err.println("Branch:"+branch+":"+rawString +":"+utf8StringUTF8+":"+utf8StringISO);
          }
        }
      }
    }
  }

  /**
   * Fill in value of a Field from a ServletTemplateContext.
   *
   * @param context    the current {@link ServletTemplateContext} to get values from
   * @param fieldName  the name of the field to extract
   * @return the value of the field 
   * @throws TempletAdaptorConstructionMelatiException 
   *             if there is a problem, for example with the class name
   */
  public static Object extractField(ServletTemplateContext context, String fieldName)
      throws TempletAdaptorConstructionMelatiException {

    String rawString = context.getForm(fieldName);

    String adaptorFieldName = fieldName + "-adaptor";
    String adaptorName = context.getForm(adaptorFieldName);

    if (adaptorName != null) {
      TempletAdaptor adaptor = getAdaptor(adaptorFieldName, adaptorName);
      return adaptor.rawFrom(context, fieldName);
    }
    return rawString;
  }


  private static TempletAdaptor getAdaptor(String adaptorFieldName, String adaptorName) {
    TempletAdaptor adaptor = (TempletAdaptor)adaptorCache.get(adaptorName);
    if(adaptor == null)
      try {
        adaptor = (TempletAdaptor)Class.forName(adaptorName).newInstance();
        adaptorCache.put(adaptorName, adaptor);
      }
      catch (Exception e) {
        throw new TempletAdaptorConstructionMelatiException(
        adaptorFieldName, adaptorName, e);
      }

    return adaptor;
  }
  
  /**
  * A utility method that gets a value from the Form.  It will return
  * null if the value is "" or not present.
  *
  * @param context - a template context
  * @param field - the name of the field to get
  *
  * @return - the value of the field requested
  */
  public static String getFieldNulled(ServletTemplateContext context, String field) {
    return getField(context, field, null);
  }
  
    
  /**
  * A utility method that gets a value from the Form.  It will return
  * the default if the value is "" or not present.
  *
  * @param context - a template context
  * @param field - the name of the field to get
  * @param defaultValue - the default value if the field is "" or not present
  *
  * @return - the value of the field requested
  */
  public static String getField(ServletTemplateContext context, String field, 
                               String defaultValue) {
    String val = context.getForm(field);
    if (val == null) return defaultValue;
    return val.trim().equals("") ? defaultValue : val;
  }

  /**
  * A utility method that gets a value from the Form as an Integer.  
  * It will return null if the value is "" or not present.
  *
  * @param context - a template context
  * @param field - the name of the field to get
  * @param defaultValue - the default value if the field is "" or not present
  *
  * @return - the value of the field requested
  */
  public static Integer getIntegerField(ServletTemplateContext context, String field, 
                                Integer defaultValue) {
    String val = getFieldNulled(context, field);
    return val == null ? defaultValue : new Integer(val);
  }

  /**
  * A utility method that gets a value from the Form as an Integer.  
  * It will return null if the value is "" or not present.
  *
  * @param context - a template context
  * @param field - the name of the field to get
  *
  * @return - the value of the field requested
  */
  public static Integer getIntegerField(ServletTemplateContext context, String field) {
    return getIntegerField(context, field, null);
  }

  /**
  * A utility method that tests whether a field is present in a Form,
  * returning a Boolean.  
  *
  * @param context - a template context
  * @param field - the name of the field to get
  *
  * @return - TRUE or FALSE depending if the field is present
  */
  public static Boolean getBooleanField(ServletTemplateContext context, String field) {
    return getFieldNulled(context, field) ==  null ? 
                                             Boolean.FALSE : Boolean.TRUE;
  }
  
  /**
   * Modify or add a form parameter setting (query string component) in a URL.
   *
   * @param uri     A URI
   * @param query   A query string
   * @param field   The parameter's name
   * @param value   The new value for the parameter (unencoded)
   * @return        <TT><I>uri</I>?<I>query</I></TT> with <TT>field=value</TT>.
   *                If there is already a binding for <TT>field</TT> in the
   *                query string it is replaced, not duplicated.
   */

  public static String sameURLWith(String uri, String query,
                                   String field, String value) {
    return uri + "?" + sameQueryWith(query, field, value);
  }

  /**
   * Modify or add a form parameter setting (query string component) in the URL
   * for a servlet request.
   *
   * @param request A servlet request
   * @param field   The parameter's name
   * @param value   The new value for the parameter (unencoded)
   * @return        The request's URL with <TT>field=value</TT>.  If there is
   *                already a binding for <TT>field</TT> in the query string
   *                it is replaced, not duplicated.  If there is no query
   *                string, one is added.
   */

  public static String sameURLWith(HttpServletRequest request,
                                   String field, String value) {
    return sameURLWith(request.getRequestURI(), request.getQueryString(),
                       field, value);
  }

  /**
   * Modify or add a form parameter setting (query string component) in a query
   * string.
   * Note this uses the default encoding. 
   * 
   * @param qs      A query string
   * @param field   The parameter's name
   * @param value   The new value for the parameter (unencoded)
   * @return        <TT>qs</TT> with <TT>field=value</TT>.
   *                If there is already a binding for <TT>field</TT> in the
   *                query string it is replaced, not duplicated.
   */
  public static String sameQueryWith(String qs, String field, String value) {
    
    String fenc = UTF8URLEncoder.encode(field);
    String fenceq = fenc + '=';
    String fev = fenceq + UTF8URLEncoder.encode(value);

    if (qs == null || qs.equals("")) return fev;

    int i;
    if (qs.startsWith(fenceq)) i = 0;
    else {
      i = qs.indexOf('&' + fenceq);
      if (i == -1) return qs + '&' + fev;
      ++i;
    }

    int a = qs.indexOf('&', i);
    return qs.substring(0, i) + fev + (a == -1 ? "" : qs.substring(a));
  }
  

  /**
  * A utility method that gets a value from the Form.  It will return
  * null if the value is "" or not present.
  *
  * @param context - a template context
  * @param field - the name of the field to get
  *
  * @return - the value of the field requested
  */
  public static String getFormNulled(ServletTemplateContext context, String field) {
    return getForm(context, field, null);
  }

 /**
  * A utility method that gets a value from the Form.  It will return
  * the default if the value is "" or not present.
  *
  * @param context - a template context
  * @param field - the name of the field to get
  * @param def - the default value if the field is "" or not present
  *
  * @return - the value of the field requested
  */
  public static String getForm(ServletTemplateContext context, String field, 
                               String def) {
    String val = context.getForm(field);
    if (val == null) return def;
    return val.trim().equals("") ? def : val;
  }

}









