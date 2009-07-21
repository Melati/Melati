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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.template;

import org.melati.poem.AccessPoemException;
import org.melati.util.MelatiWriter;

/**
 * A MarkupLanguage suitable for markup within an attribute tag.
 * @since 17-May-2006
 */
public interface AttributeMarkupLanguage extends MarkupLanguage {

  /**
   * AccessPoemException is treated differently in an Atribute than 
   * in the main body of a page.
   * @param e an Exception to render
   * @return a String representation of the Exception
   */
  String rendered(AccessPoemException e);

  /**
   * Render, not translating line ends to BR tags.
   * 
   * @param s the String to render
   * @param writer writer to render to 
   * @see org.melati.template.AbstractMarkupLanguage#render(String, MelatiWriter)
   */
  void render(String s, MelatiWriter writer);

}


