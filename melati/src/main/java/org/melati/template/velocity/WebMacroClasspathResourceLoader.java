/**
 * 
 */
package org.melati.template.velocity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.oro.text.perl.Perl5Util;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.melati.util.MelatiBugMelatiException;

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
    extends ClasspathResourceLoader 
    implements WebMacroConverter {

  /** Regular expression tool */
  private Perl5Util perl;
  
  /**
   * Get an InputStream so that the Runtime can build a
   * template with it, munge it if it is a WM template.
   *
   * @param templateName name of template to get
   * @return InputStream containing the template
   * @throws ResourceNotFoundException if template not found
   *         in the file template path.
   * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#getResourceStream(java.lang.String)
   */
  public InputStream getResourceStream(String templateName)
      throws ResourceNotFoundException
  {
    if (templateName.endsWith(".wm")) { 
      InputStream bis = super.getResourceStream(templateName);
      byte[] ca;
      try {
        ca = new byte[bis.available()];
        bis.read(ca);
        String contents = new String(ca);
        perl = new Perl5Util();
        for (int i = 0; i < regExps.length; i += 2) {
          while (perl.match("/" + regExps[i] + "/", contents)) {
            contents = perl.substitute(
                "s/" + regExps[i] + "/" + regExps[i+1] + "/", contents);
          }
        }
        //System.err.println(contents);
        return new ByteArrayInputStream(contents.getBytes());
      } catch (IOException e) {
        throw new MelatiBugMelatiException("Problem loading template", e);
      }
    } else {
      return super.getResourceStream(templateName);
    }
     
  }

}
