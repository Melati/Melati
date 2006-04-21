/**
 * 
 */
package org.melati.template.velocity;

import java.io.InputStream;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * Loads templates from the classpath, mungs them if they are WM 
 * templates.
 * 
 * 
 * Note that this does not allow modern WebMacro syntax with
 * optionional #begin in #foreach.
 *
 * @author Tim Pizey based on work by Jason van Zyl and Tim Joyce.
 * @see WebMacroFileResourceLoader
 */
public class WebMacroClasspathResourceLoader 
    extends ClasspathResourceLoader {
  
  /**
   * Get an InputStream so that the Runtime can build a
   * template with it, munge it if it is a WM template.
   *
   * @param templateName name of template to get
   * @return InputStream containing the template
   * @throws ResourceNotFoundException if template not found
   *         in the file template path.
   * @see org.apache.velocity.runtime.resource.loader.ResourceLoader
   */
  public InputStream getResourceStream(String templateName)
      throws ResourceNotFoundException
  {
    if (templateName.endsWith(".wm") || templateName.endsWith(".wmm")) { 
      return WebMacroConverter.convert(super.getResourceStream(templateName));
    } else {
      return super.getResourceStream(templateName);
    }
  }

}
