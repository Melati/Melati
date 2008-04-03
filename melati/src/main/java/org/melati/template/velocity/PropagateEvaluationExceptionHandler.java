/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2008 Tim Pizey
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

import java.lang.reflect.Method;

import org.apache.velocity.app.event.InvalidReferenceEventHandler;
import org.apache.velocity.context.Context;
import org.apache.velocity.util.introspection.Info;
import org.melati.template.TemplateEngineException;

/**
 * @author timp
 * @since 1 Apr 2008
 * 
 */
public class PropagateEvaluationExceptionHandler implements
    InvalidReferenceEventHandler {

  /**
   * Constructor.
   */
  public PropagateEvaluationExceptionHandler() {
  }

  public Object invalidGetMethod(Context context, String reference,
      Object object, String property, Info info) {
    return null;
  }

  public Object invalidMethod(Context context, String reference, Object object,
      String method, Info info) {
    if(object != null) { 
      Method[] methods = object.getClass().getMethods();
      for (int i = 0; i < methods.length; i++)
        if (methods[i].getName().equals(method)) 
          return null;
    }
    String message = "Invalid method ";
    message += (method == null) ? "(null)" : method;
    message += " called ";
    message += (reference == null) ? "(null reference)" : reference;
    message += (object == null) ? " (null object)" : " on object " + object;
    message += " at " + info;
    throw new TemplateEngineException(message);
  }

  public boolean invalidSetMethod(Context context, String leftreference,
      String rightreference, Info info) {
    return true;    
    /*
    throw new TemplateEngineException("Invalid set method " + 
        (rightreference == null ? "null" : rightreference)  
        + " called setting " + leftreference + " at " + info);
    */
  }

}
