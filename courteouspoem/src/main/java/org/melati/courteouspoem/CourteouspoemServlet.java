
package org.melati.courteouspoem;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import org.melati.Melati;
import org.melati.servlet.TemplateServlet;
import org.melati.template.ServletTemplateContext;
//import org.melati.courtiouspoem.poem.CourtiouspoemDatabase;

/**
 * Base servlet for Courteouspoem servlets.
 */
public abstract class CourteouspoemServlet extends TemplateServlet {


  public static final String templatePrefix = "org/melati/courtiouspoem/view/";

  public String getSysAdminName () {
    return "TimP";
  }
  public String getSysAdminEmail () {
    return "timp@paneris.org";
  }

  /**
   * @see org.melati.servlet.ConfigServlet#doConfiguredRequest(org.melati.Melati)
   */
  protected void doConfiguredRequest(final Melati melati)
      throws ServletException, IOException {
    String pathInfo = melati.getRequest().getPathInfo();
    if (pathInfo == null) pathInfo = "";

    // check if pathinfo exists in filesystem
    // if so then redirect to it, unless we came from there
    while (pathInfo != "" && !fileAt(pathInfo)) {
      String s = pathInfo.substring(1);
      int i = s.indexOf('/');
      if (i != -1)
        pathInfo = s.substring(i);
      else
        pathInfo = "";
    }
    if (pathInfo != "") {
      System.err.println("pathinfo:" + pathInfo);
      System.err.println("Ref:" + melati.getRequest().getHeader("Referer"));
      String referer = melati.getRequest().getHeader("Referer");
      if (referer != null &&
          referer.indexOf(pathInfo) == -1) {
        melati.getResponse().sendRedirect(pathInfo);
        return;
      }
    }
    super.doConfiguredRequest(melati);
  }
  private boolean fileAt(String filename){
    if (filename.equals("")) return false;
    if (filename.equals("/")) return false;
    String fsName = "/dist/courtiouspoem/www" + filename;
    File it = new File(fsName);
    System.err.println("FS:" + fsName + " " + it.exists());
    return it.exists();
  }

  public String courtiouspoemTemplate(String name) {
    return addExtension(templatePrefix + name);
  }

    // Override org.melati.TemplateServlet.addExtension()
    // to cope with heterogenous naming convention :)
  protected String addExtension(String templateName) {
    int index = templateName.indexOf(".wm");
    if (index == -1)
      templateName = templateName + ".wm";
    System.err.println("Template:" + templateName);
    return templateName;
  }

  /**
   * Concrete class for {@link TemplateServlet}.
   *
   * @param melati
   * @param context
   * @return Template name
   */
  protected String doTemplateRequest(Melati melati, ServletTemplateContext context)
      throws Exception {
    return courtiouspoemTemplate(reallyDoTemplateRequest(melati, context));
  }

  /**
   * Override this method to build up output in individual servlets.
   *
   * @return Template name without path or extension
   */
  protected abstract String
    reallyDoTemplateRequest(Melati melati,
                            ServletTemplateContext templateContext)
      throws Exception;

  protected String getSetting(Melati melati, String settingName) {
    String returnString = melati.getDatabase().getSettingTable().get(settingName);
    if (returnString == null)
      throw new RuntimeException("Setting " + settingName + " not found in setting table");
    return returnString;
  }

}
