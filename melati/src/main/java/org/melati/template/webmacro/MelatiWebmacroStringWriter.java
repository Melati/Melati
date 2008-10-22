/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2003 Jim Wright - Bohemian Enterprise
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
 */

package org.melati.template.webmacro;

import org.melati.Melati;
import org.melati.template.TemplateEngine;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.MelatiStringWriter;

import org.webmacro.WM;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * {@link MelatiStringWriter} that can be used with WebMacro.
 *
 * @see WebmacroTemplate#write(org.melati.util.MelatiWriter, 
 *                             org.melati.template.TemplateContext, 
 *                             org.melati.template.TemplateEngine)
 */
public class MelatiWebmacroStringWriter extends MelatiStringWriter
    implements MelatiWebmacroWriter {

  /**
   * @return a <code>FastWriter</code> that can be used for a while.
   *
   * {@inheritDoc}
   * @see #stopUsingFastWriter(FastWriter)
   * @see org.melati.template.webmacro.MelatiWebmacroWriter#getFastWriter
   *         (org.melati.template.TemplateEngine)
   */
  public FastWriter getFastWriter(TemplateEngine engine) {
    WebmacroServletTemplateEngine wte = (WebmacroServletTemplateEngine)engine;
    WM wm = (WM)wte.getEngine();
    // All we want to do is efficiently convert to and from bytes
    // in an encoding that works for all Java characters.
    // Sun's Javadocs java.nio.charset.Charset explains this well
    // where other references fail to spell it out: UTF-16 is cool.
    // Except Webmacro does not support it due to byte order mark.
    // So Jim used UTF-16BE
    // Changed to default in the desperate hope of getting something to work
    try {
      return FastWriter.getInstance(wm.getBroker(), Melati.DEFAULT_ENCODING);
    }
    catch (UnsupportedEncodingException e) {
      throw new MelatiBugMelatiException(
              "Assumption that all JVMs and WebMacro support " + Melati.DEFAULT_ENCODING + " has not held", e);
    }
  }

  /**
   * Stop using the given <code>FastWriter</code> obtained from
   * this object.
   */
  public void stopUsingFastWriter(FastWriter fw) throws IOException {
    write(fw.toString());
    fw.close();
  }

}
