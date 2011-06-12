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
 */
package org.melati.template.velocity;

import org.apache.velocity.app.event.MethodExceptionEventHandler;

/**
 * Reference 'null reference' event handler. Called when a non-quiet reference
 * ($foo vs $!foo) is null (not in the TemplateContext), which would result in
 * '$foo' being rendered. Please return what you want rendered into the output
 * stream.
 * 
 * @author Geir Magnusson Jr. geirm At optonline.net
 */
public class PassbackMethodExceptionEventHandler implements
    MethodExceptionEventHandler {

  /**
   * {@inheritDoc}
   * @see org.apache.velocity.app.event.MethodExceptionEventHandler#methodException
   */
public Object methodException(@SuppressWarnings("rawtypes") Class claz, String method, Exception e)
      throws Exception {
    return e;
  }
}

