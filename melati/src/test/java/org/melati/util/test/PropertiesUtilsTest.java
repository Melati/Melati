package org.melati.util.test;

import org.melati.util.PropertiesUtils;
/**
 * @author timp
 * @since 2013-07-14
 */
import junit.framework.TestCase;

public class PropertiesUtilsTest extends TestCase {

  public final void testFromFile() {
  }

  public final void testFromResourceClassOfQ() throws Exception {
    assertEquals("{org.melati.MelatiConfig.staticURL=/melatitest/melati-static/admin/static, org.melati.MelatiConfig.templateEngine=org.melati.template.webmacro.WebmacroServletTemplateEngine, org.melati.MelatiConfig.accessHandler=org.melati.login.HttpSessionAccessHandler, org.melati.MelatiConfig.preferredCharsets=UTF-8, UTF-16, ISO-8859-1, org.melati.MelatiConfig.javascriptLibraryURL=/melatitest/melati-static/admin/static, org.melati.MelatiConfig.formDataAdaptorFactory=org.melati.servlet.PoemFileFormDataAdaptorFactory, org.melati.MelatiConfig.locale=en-gb}", 
        PropertiesUtils.fromResource(org.melati.MelatiConfig.class).toString());
  }

  public final void testFromResourceClassOfQString() {
  }

  public final void testGetOrDie() {
  }

  public final void testGetOrDefault() {
  }

  public final void testGetOrDie_int() {
  }

  public final void testGetOrDefault_int() {
  }

  public final void testInstanceOfNamedClassStringString() {
  }

  public final void testInstanceOfNamedClassPropertiesStringStringString() {
  }

}
