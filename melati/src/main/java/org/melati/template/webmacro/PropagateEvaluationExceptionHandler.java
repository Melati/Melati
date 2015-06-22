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

package org.melati.template.webmacro;

import org.webmacro.PropertyException;
import org.webmacro.Context;
import org.webmacro.Broker;
import org.webmacro.engine.EvaluationExceptionHandler;
import org.webmacro.engine.Variable;
import org.webmacro.util.Settings;

/**
 * An implementation of EvaluationExceptionHandler which throws an exception
 * whenever it is called.  
 * 
 * This will generally cause the exception to be
 * displayed to the user -- useful for debugging.
 *
 * Modified to allow #if ($null).
 *
 * @author Tim Joyce
 * @since 2001-11-05
 */

public class PropagateEvaluationExceptionHandler 
  implements EvaluationExceptionHandler {

   /**
   * Constructor.
   */
  public PropagateEvaluationExceptionHandler() {
   }

  /**
   * Forced upon us by the interface, but unused.
   */
  @Override
  public void init(Broker b, Settings config) {
   }

  @Override
  public void evaluate(Variable variable,
                        Context context, 
                        Exception problem) 
   throws PropertyException {
     if (problem instanceof PropertyException.NoSuchVariableException
      || problem instanceof PropertyException.NullValueException
      || problem instanceof PropertyException.NullToStringException) 
       return;
     if (problem instanceof PropertyException)
       throw (PropertyException) problem;
     else 
       throw new PropertyException("Error evaluating variable " 
                                   + variable.getVariableName() + ": " 
                                   + problem, problem);
   }

   /**
   * {@inheritDoc}
   * @see org.webmacro.engine.EvaluationExceptionHandler#expand(
   *          org.webmacro.engine.Variable, org.webmacro.Context, java.lang.Exception)
   */
  public String expand(Variable variable, 
                        Context context, 
                        Exception problem) 
   throws PropertyException {
     if (problem instanceof PropertyException)
       throw (PropertyException) problem;
     else 
       throw new PropertyException("Error evaluating variable " 
                                   + variable.getVariableName() + ": " 
                                   + problem, problem);
   }


   /**
   * {@inheritDoc}
   * @see org.webmacro.engine.EvaluationExceptionHandler#warningString(java.lang.String)
   */
  public String warningString(String warningText) throws PropertyException {
      throw new PropertyException("Evaluation warning: " + warningText);
   }


   /**
   * {@inheritDoc}
   * 
   * I do not believe that this can be exercised, without a bug in webmacro.
   * @see org.webmacro.engine.EvaluationExceptionHandler#errorString(java.lang.String)
   */
  public String errorString(String errorText) throws PropertyException {
      throw new PropertyException("Evaluation error: " + errorText);
   }
}

