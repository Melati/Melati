/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Myles Chippendale
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
 *     Myles Chippendale <mylesc@paneris.org>
 *     http://paneris.org/
 *     29 Stanley Road, Oxford, OX4 1QY, UK
 */

/**
 * Interface for a Template engine for use with Melati
 */

package org.melati.template;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import org.melati.*;
import org.melati.servlet.*;


/**
 * A TemplateContext which allows access to the filename and
 * body of any file which is uploaded from a HTML form field (by setting
 * its ENCTYPE to ``multipart/form-data'' and setting the field's type to
 * ``file'').
 * <p>
 * You can retrive the value of any field variable as usual by
 * using getForm(s).
 * <p>
 * Note that you need to pass in a TemplateContext to the contructor
 */
public class MultipartTemplateContext implements TemplateContext 
{
  TemplateContext peer;
  Hashtable fields;
  Melati melati;

  public MultipartTemplateContext(Melati melati, TemplateContext context) {
    peer = context;
    this.melati = melati;
    try {
      InputStream in = melati.getRequest().getInputStream();
      MultipartDataDecoder decoder=new MultipartDataDecoder(in,
                                     melati.getRequest().getContentType(),
                                     melati.getRequest().getContentLength());
      fields = decoder.parseData();
    } catch (IOException ioe) {
      fields = new Hashtable();
    }
  }

  public void put(String s, Object o) {
    peer.put(s,o);
  }

  public String getForm(String s) {
    FormField field = (FormField)fields.get(s);
    if (field == null)
      return peer.getForm(s);
    return new String(field.getData());
  }

  public FormFile getFormFile(String s) {
    return new UploadFormFile((FormField)fields.get(s), melati);
  }

  public Object get(Object o) {
    return peer.get(o);
  }

  public HttpSession getSession() {
    return peer.getSession();
  }

  public Object getContext() {
    return peer.getContext();
  }

}






