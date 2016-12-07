/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2006 Tim Pizey
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
package org.melati.template.velocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * Loads templates from the classpath, mungs them if they are WM 
 * templates.
 * 
 * 
 * NOTE This does not allow modern WebMacro syntax with
 * optional #begin in #foreach.
 * 
 * NOTE Only if a curly bracket is at the start of a line will it not be turned into an #end
 *
 * @author Tim Pizey based on work by Jason van Zyl and Tim Joyce.
 */
public class WebMacroClasspathResourceLoader 
    extends ClasspathResourceLoader {
  
  private static final String MELATI_SRC_TEST_JAVA = "/dist/melati/melati/src/test/java/";
  private static final String MELATI_SRC_MAIN_JAVA = "/dist/melati/melati/src/main/java/";

  /**
   * Get an InputStream so that the Velocity Runtime can build a
   * template with it, munge it if it is a WM template.
   *
   * FIXME Hardcoded paths 
   *
   * @param templateName name of template to get
   * @return InputStream containing the template
   * @throws ResourceNotFoundException if template not found
   *         in the file template path.
   * @see org.apache.velocity.runtime.resource.loader.ResourceLoader
   */
  public InputStream getResourceStream(String templateName)
      throws ResourceNotFoundException {
    if (templateName.endsWith(".wm")) {
      InputStream converted = WebMacroConverter.convert(super.getResourceStream(templateName));
      
      try {
        String wmName = MELATI_SRC_MAIN_JAVA + templateName;
        File wmFile = new File(wmName);
        String vmName;
        if (wmFile.exists()) {
          vmName = MELATI_SRC_MAIN_JAVA + 
                templateName.substring(0,templateName.length() -3) + ".vm";
        } else { 
          wmName = MELATI_SRC_TEST_JAVA + templateName;
          wmFile = new File(wmName);
          if (wmFile.exists()) {
            vmName = MELATI_SRC_TEST_JAVA + 
                  templateName.substring(0,templateName.length() -3) + ".vm";
          } else 
            throw new RuntimeException("Cannot find file " + wmName);
        }
        File convertedFile = new File(vmName).getCanonicalFile();
        // System.err.println(vmName);
        convertedFile.createNewFile();
        PrintStream ps = new PrintStream(new FileOutputStream(convertedFile));
        int nextChar = converted.read();
        while (nextChar > 0) {
          ps.write((char)nextChar);
          nextChar = converted.read();
        }
        converted.reset();
        ps.close();
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
      return converted;
    } else
      return super.getResourceStream(templateName);
  }

}
