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
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.template;

import java.io.IOException;
import java.text.DateFormat;

import org.melati.Melati;
import org.melati.poem.Persistent;
import org.melati.util.MelatiWriter;
import org.melati.poem.PoemLocale;
import org.melati.util.HTMLUtils;
import org.melati.util.UTF8URLEncoder;

/**
 * A Markup language with syntax similar to HTML.
 */
public abstract class HTMLLikeMarkupLanguage extends AbstractMarkupLanguage 
    implements MarkupLanguage {

  /**
   * Constructor.
   */
  public HTMLLikeMarkupLanguage(String name, Melati melati,
                                TempletLoader templetLoader,
                                PoemLocale locale) {
    super(name, melati, templetLoader, locale);
  }
  
  protected HTMLLikeMarkupLanguage(String name, HTMLLikeMarkupLanguage other) {
    super(name, other);
  }

 /** 
  * Render, directly to the output stream, substituting entities for 
  * high value ASCII characters and new line characters.
  * To avoid the unnecessary overhead of writing to a string and then writing
  * that string to the output stream we render directly to the output stream.
  */
  public void render(String s, MelatiWriter writer) throws IOException {
    writer.write(HTMLUtils.entitied(s, true, melati.getEncoding()));
  }

  /**
   * Escape a String.
   * 
   * @param s the String to escape
   * @return the escaped String
   */
  public String escaped(String s) {
    return HTMLUtils.jsEscaped(s);
  }

  /**
   * Get the DisplayString of a <code>Persistent</code> and 
   * escape that using the current locale and a MEDIUM DateFormat.
   * 
   * See org/melati/admin/SelectionWindowSelection.wm
   * See org/melati/admin/Update.wm
   * @param o
   * @return the escaped DisplayString
   */
  public String escaped(Persistent o) {
    return escaped(o.displayString(locale, DateFormat.MEDIUM));
  }

  public String encoded(String s) {
    return UTF8URLEncoder.encode(s, melati.getEncoding());
  }
  public String decoded(String s) {
    return UTF8URLEncoder.decode(s, melati.getEncoding());
  }
}

