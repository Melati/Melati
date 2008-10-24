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

import java.io.IOException;

import org.melati.poem.AccessPoemException;
import org.melati.util.MelatiWriter;
import org.melati.util.HTMLUtils;

/**
 * An HTML based markup language suitable for markup within an 
 * attribute tag.
 * 
 * The significant difference is that newlines are not translated into break 
 * tags.
 */
public class HTMLAttributeMarkupLanguage extends HTMLMarkupLanguage 
    implements MarkupLanguage, AttributeMarkupLanguage {

  /**
   * Constructor given an HTMLMarkupLanguage.
   * @param html the HTMLMarkupLanguage the attribute language is relevant to
   */
  public HTMLAttributeMarkupLanguage(HTMLMarkupLanguage html) {
    super("html_attr", html);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.AttributeMarkupLanguage#rendered(
   * org.melati.poem.AccessPoemException)
   */
  public String rendered(AccessPoemException e) throws IOException {
    try {
      melati.getWriter().write("[Access denied to ");
      render(e.token);
      melati.getWriter().write("]");
    }
    catch (Exception g) {
      melati.getWriter().write("[UNRENDERABLE EXCEPTION!]");
    }

    return "";
  }

  /**
   * Do entity substitution.
   *
   * @see org.melati.template.AttributeMarkupLanguage#render(java.lang.String, 
   *          org.melati.util.MelatiWriter)
   */
  public void render(String s, MelatiWriter writer) throws IOException {
    writer.write(HTMLUtils.entitied(s, false, melati.getEncoding(), false));
  }

  /** 
   * Attribute markup languages do not have templets, so don't look for one. 
   * {@inheritDoc}
   * @see org.melati.template.AbstractMarkupLanguage#render(java.lang.Object, org.melati.util.MelatiWriter)
   */
  protected void render(Object o, MelatiWriter writer) throws IOException {
    render(o.toString(), writer);
  }
}
