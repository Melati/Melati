/* 
 * The contents of this file are subject to the Mozilla Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 * 
 * The Original Code is the Melati JTemplater.
 * 
 * The Initial Developer of the Original Code is Sundayta Ltd.
 * Portions created by Sundayta  are Copyright (C) 2000 Sundayta Ltd.
 * All Rights Reserved.
 * 
 * Contributor(s):
 *   David Warnock (Sundayta Ltd)
 *   Erik Eskine (Sundayta Ltd)
 *
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the GNU General Public License Version 2 or later (the
 * "GPL"), in which case the provisions of the GPL are applicable 
 * instead of those above.  If you wish to allow use of your 
 * version of this file only under the terms of the GPL and not to
 * allow others to use your version of this file under the MPL,
 * indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by
 * the GPL.  If you do not delete the provisions above, a recipient
 * may use your version of this file under either the MPL or the
 * GPL.
 */
 
package org.melati.template.jtemplater;

import java.util.Hashtable;

import javax.servlet.http.HttpSession;

import org.melati.Melati;
import org.melati.template.TemplateContext;
import org.melati.servlet.MultipartFormField;

/*
* this is just an example of a parameters class for use with jtemplater
* i am very unsure of how this works, and so it largely doesn't at present :)
*/

public class JTemplaterTemplateContext implements TemplateContext {

  public Melati melati;
  public Hashtable vars = new Hashtable();

  public JTemplaterTemplateContext(Melati m) {
    melati = m;
  }

  public JTemplaterTemplateContext() {
  }
  
  public void put(String s, Object o) {
    vars.put(s,o);
  }

  public String getForm(String s) {
    return melati.getRequest().getParameter(s);
  }

  public MultipartFormField getMultipartForm(String s) {
    return null;
  }

  public Object get(String s) {
    return vars.get(s);
  }

  public HttpSession getSession() {
    return melati.getSession();
  }

  public Object getContext() {
    return melati;
  }

  
  public static Class getClazz() {
    JTemplaterTemplateContext c = new JTemplaterTemplateContext();
    return c.getClass();
  }

  public void setVariableExceptionHandler(Object veh) {};

}



