/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.template;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;

import org.melati.poem.Field;
import org.melati.servlet.Form;

/**
 * A SimpleDateAdaptor is used to format a date field into 
 * a dd/mm/yyyy format for display. 
 *
 * It also adapts the input (given in dd/mm/yyyy or yyyyMMdd)
 * back to a java.sql.Date.
 */

public class SimpleDateAdaptor implements TempletAdaptor {

  /** The instance. */
  public static final SimpleDateAdaptor it = new SimpleDateAdaptor();

  private static SimpleDateFormat dateFormatter1 = 
      new SimpleDateFormat("dd/MM/yyyy");

  // allow other forms of date - this one yyyyMMdd

  private static SimpleDateFormat dateFormatter2 =
      new SimpleDateFormat("yyyyMMdd");

  @Override
  public Object rawFrom(ServletTemplateContext context, String fieldName) {
    String value = Form.getFieldNulled(context,fieldName);
    if (value == null) return null;
    java.util.Date date = dateFormatter1.parse(value, new ParsePosition(0));
    // give it another go using a different format
    if (date == null)
      date = dateFormatter2.parse(value, new ParsePosition(0));
    return new Date(date.getTime());
  }

  /**
   * @param dateField the input Field
   * @return the rendered String
   */
  public String rendered(Field<Date> dateField) {
    return dateField.getRaw() == null ?
               "" :
               dateFormatter1.format((java.util.Date)dateField.getRaw());
  }
}
