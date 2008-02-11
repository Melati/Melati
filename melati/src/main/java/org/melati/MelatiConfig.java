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
 *     http://paneris.org/
 *     68 Sandbanks Rd, Poole, Dorset. BH14 8BY. UK
 */

package org.melati;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.melati.login.AccessHandler;
import org.melati.poem.PoemLocale;
import org.melati.poem.util.EnumUtils;
import org.melati.servlet.FormDataAdaptorFactory;
import org.melati.template.ClassNameTempletLoader;
import org.melati.template.ServletTemplateEngine;
import org.melati.template.SimpleDateAdaptor;
import org.melati.template.TemplateEngine;
import org.melati.template.TempletLoader;
import org.melati.template.YMDDateAdaptor;
import org.melati.template.YMDHMSTimestampAdaptor;
import org.melati.util.ConfigException;
import org.melati.util.HttpHeader;
import org.melati.util.PropertiesUtils;

/**
 * A MelatiConfig loads and provides access to the configuration parameters for
 * melati. These are held in <TT>org.melati.MelatiConfig.properties</TT>.
 */
public class MelatiConfig {

  private Properties configuration = null;
  /** The properties file name. */
  private String propertiesName = "org.melati.MelatiConfig";

  private AccessHandler accessHandler = null;
  private FormDataAdaptorFactory fdaFactory = null;
  private TempletLoader templetLoader = null;
  private TemplateEngine templateEngine = null;
  private static PoemLocale poemLocale = null;
  private Vector preferredCharsets = null;
  private String javascriptLibraryURL = null;
  private String staticURL = null;
  private String templatePath = null;
  private static String loginPageServletClassName = "org.melati.login.Login";
  private static String logoutPageServletClassName = "org.melati.login.Logout";

  /**
   * Allows creation of a <code>MelatiConfig</code> with default config
   * params.
   */
  public MelatiConfig() {
    try {
      configuration =
        PropertiesUtils.fromResource(getClass(), propertiesName + ".properties");
    }
    catch (FileNotFoundException e) {
      configuration = new Properties();
      // TimJ: i think that if we don't have a properties file, it is pretty fatal
      // TimP: Naah
    }
    catch (IOException e) {
      throw new ConfigException("The file " + propertiesName + ".properties" +
                                " could not be read." +
                                " Full Error: " + e.toString());
    }
    init(propertiesName);
  }

  /**
   * Allows creation of a <code>MelatiConfig</code> with a specified
   * properties file.
   * 
   * @param propertiesName
   *        the name of a properties file
   */
  public MelatiConfig(String propertiesName) {
    this.propertiesName = propertiesName;
    try {
      configuration =
        PropertiesUtils.fromResource(getClass(), propertiesName + ".properties");
    }
    catch (FileNotFoundException e) {
      throw new ConfigException("The file " + propertiesName + "properties" +
                                " could not be found." +
                                " Is it in your CLASSPATH?  Full Error: " +
                                e.toString());
    }
    catch (IOException e) {
      throw new ConfigException("The file " + propertiesName + ".properties" +
                                " could not be read." +
                                " Full Error: " + e.toString());
    }
    init(propertiesName);
  }

  /**
   * Comnstructor from a given Properties object.
   * @param properties the properies object to look in 
   */
  public MelatiConfig(Properties properties) {
    configuration = properties;
    init(propertiesName);
  }

  void init(String propertiesNameIn) {
    this.propertiesName = propertiesNameIn;
    String pref = propertiesName + ".";
    
    String accessHandlerProp              = pref + "accessHandler";
    String fdaFactoryProp                 = pref + "formDataAdaptorFactory";
    String templetLoaderProp              = pref + "templetLoader";
    String templateEngineProp             = pref + "templateEngine";
    String templatePathProp               = pref + "templatePath";
    String javascriptLibraryURLProp       = pref + "javascriptLibraryURL";
    String staticURLProp                  = pref + "staticURL";
    String melatiLocaleProp               = pref + "locale";
    String preferredCharsetsProp          = pref + "preferredCharsets";
    String loginPageServletClassNameProp  = pref + "loginPageServletClassName";
    String logoutPageServletClassNameProp = pref + "logoutPageServletClassName";

    try {
      setAccessHandler((AccessHandler)PropertiesUtils.
          instanceOfNamedClass(
              configuration,
              accessHandlerProp,
              "org.melati.login.AccessHandler",
              "org.melati.login.OpenAccessHandler"));

      setFdaFactory((FormDataAdaptorFactory)PropertiesUtils.
          instanceOfNamedClass(
                       configuration,
                       fdaFactoryProp,
                       "org.melati.servlet.FormDataAdaptorFactory",
                       "org.melati.servlet.MemoryDataAdaptorFactory"));

      String templetLoaderClassName =  (String)configuration.get(templetLoaderProp);
      if(templetLoaderClassName == null || 
         templetLoaderClassName.equals("org.melati.template.ClassNameTempletLoader")) {
        setTempletLoader(ClassNameTempletLoader.getInstance());
      } else
        setTempletLoader((TempletLoader)PropertiesUtils.
          instanceOfNamedClass(
                          configuration,
                          templetLoaderProp,
                          "org.melati.template.TempletLoader",
                          "org.melati.template.ClassNameTempletLoader"));

      setTemplateEngine((TemplateEngine)PropertiesUtils.
          instanceOfNamedClass(
                           configuration,
                           templateEngineProp,
                           "org.melati.template.TemplateEngine",
                           "org.melati.template.NoTemplateEngine"));

      String languageTag = PropertiesUtils.getOrDefault(configuration,
                                                        melatiLocaleProp,
                                                        "en-gb");

      setPoemiLocale(PoemLocale.fromLanguageTag(languageTag));
      if (poemLocale == null)
          throw new ConfigException(languageTag +
                              " is not a valid language tag for " +
                              melatiLocaleProp);

      // This is a fancy way of splitting, trimming and checking for
      // errors such as spaces within fields. 
      // It reflects the fact that the config file format 
      // is like a quoteless Http header field.
      setPreferredCharsets(
        EnumUtils.vectorOf(
            new HttpHeader(PropertiesUtils.getOrDefault(
                               configuration,
                               preferredCharsetsProp,
                               "ISO-8859-1, UTF-8, UTF-16")).wordIterator()));

      setJavascriptLibraryURL(PropertiesUtils.getOrDefault(
              configuration,
              javascriptLibraryURLProp,
              "/melati-static/admin/"));

      setStaticURL(PropertiesUtils.getOrDefault(
              configuration, 
              staticURLProp,
              "/melati-static/"
              ));

      setTemplatePath(PropertiesUtils.getOrDefault(configuration,
          templatePathProp, "."));

      setLoginPageServletClassName(PropertiesUtils.getOrDefault(configuration,
          loginPageServletClassNameProp, loginPageServletClassName));

      setLogoutPageServletClassName(PropertiesUtils.getOrDefault(configuration,
          logoutPageServletClassNameProp, logoutPageServletClassName));
    }
    catch (Exception e) {
      throw new ConfigException("Melati could not be configured because: " +
                                e.toString(), e);
    }

  }

  /**
   * @return {@link ServletTemplateEngine} in use.
   */
  public ServletTemplateEngine getServletTemplateEngine() {
    return (ServletTemplateEngine)templateEngine;
  }

  /**
   * @return {@link TemplateEngine} in use.
   */
  public TemplateEngine getTemplateEngine() {
    return templateEngine;
  }

  /**
   * Set the {@link TemplateEngine} to use.
   * 
   * @param templateEngine
   *        a {@link TemplateEngine}
   */
  public void setTemplateEngine(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  /**
   * @return the configured {@link AccessHandler}
   */
  public AccessHandler getAccessHandler() {
    return accessHandler;
  }

  /**
   * Set the <code>AccessHandler</code> for use by this Melati.
   * 
   * @param accessHandler
   *        a {@link AccessHandler}
   */
  public void setAccessHandler(AccessHandler accessHandler) {
    this.accessHandler = accessHandler;
  }

  /**
   * @return the configured {@link TempletLoader}
   */
  public TempletLoader getTempletLoader() {
    return templetLoader;
  }

  /**
   * Set the {@link TempletLoader} for use by this Melati.
   * 
   * @param templetLoader
   *        a {@link TempletLoader}
   */
  public void setTempletLoader(TempletLoader templetLoader) {
    this.templetLoader = templetLoader;
  }

  /**
   * @return the configured {@link FormDataAdaptorFactory}
   */
  public FormDataAdaptorFactory getFormDataAdaptorFactory() {
    return fdaFactory;
  }

  /**
   * Set the {@link FormDataAdaptorFactory} for use by this Melati.
   * 
   * @param fdaf
   *        a {@link FormDataAdaptorFactory}
   */
  public void setFormDataAdaptorFactory(FormDataAdaptorFactory fdaf) {
    fdaFactory = fdaf;
  }

  /**
   * @return the location of javascript for this site.
   */
  public String getJavascriptLibraryURL() {
    return javascriptLibraryURL;
  }

  /**
   * Set the <code>JavascriptLibraryURL</code> for use by this Melati.
   * 
   * @param url
   *        a URL to the directory containing the JavaScript for this site
   */
  public void setJavascriptLibraryURL(String url) {
    this.javascriptLibraryURL = url;
  }

  /**
   * Normally set to <code>melati-static</code>.
   * 
   * @return the location of static content for this site.
   */
  public String getStaticURL() {
    return staticURL;
  }

  /**
   * Set the <code>StaticURL</code> for use by this Melati.
   * 
   * @param url
   *        a URL to the directory containing the static content
   */
  public void setStaticURL(String url) {
    this.staticURL = url;
  }

  /**
   * @return the location of templates.
   */
  public String getTemplatePath() {
    return templatePath;
  }

  /**
   * @param templatePath
   *        The templatePath to set.
   */
  public void setTemplatePath(String templatePath) {
    this.templatePath = templatePath;
  }

  /**
   * @return the class name of the logout servlet
   */
  public static String getLogoutPageServletClassName() {
    return logoutPageServletClassName;
  }

  /**
   * @param logoutPageServletClassName
   *        The logoutPageServletClassName to set.
   */
  public static void setLogoutPageServletClassName(
      String logoutPageServletClassName) {
    MelatiConfig.logoutPageServletClassName = logoutPageServletClassName;
  }

  /**
   * @return the class name of the login servlet
   */
  public static String getLoginPageServletClassName() {
    return loginPageServletClassName;
  }

  /**
   * @param loginPageServletClassName
   *        The loginPageServletClassName to set.
   */
  public static void setLoginPageServletClassName(
      String loginPageServletClassName) {
    MelatiConfig.loginPageServletClassName = loginPageServletClassName;
  }

  /**
   * @return The configured locale, defaults to British English melati locale.
   */
  public static PoemLocale getPoemLocale() {
    return poemLocale;
  }

  /**
   * @param poemLocale
   *        The PoemLocale to set.
   */
  public void setPoemiLocale(PoemLocale poemLocale) {
    MelatiConfig.poemLocale = poemLocale;
  }


  /**
   * Return the set encodings that the server prefers and supports.
   * 
   * @return List of encoding names or aliases.
   */
  public List getPreferredCharsets() {
    return preferredCharsets;
  }

  /**
   * @param preferredCharsets
   *        The preferredCharsets to set.
   */
  public void setPreferredCharsets(Vector preferredCharsets) {
    this.preferredCharsets = preferredCharsets;
  }

  /**
   * @return Returns the fdaFactory.
   */
  public FormDataAdaptorFactory getFdaFactory() {
    return fdaFactory;
  }

  /**
   * @param fdaFactory
   *        The fdaFactory to set.
   */
  public void setFdaFactory(FormDataAdaptorFactory fdaFactory) {
    this.fdaFactory = fdaFactory;
  }
  
  //
  // Non configurable but here to make them available in a template context.
  //
  
  /**
   * Called from within templets using 
   * <code>
   * #set $yearField = $melati.Config.YMDDateAdaptor.yearField($field)
   * </code>
   * idiom.
   * Perhaps this should be elsewhere.
   * @return the adaptor for rendering dates as drop-downs.
   */
  public static YMDDateAdaptor getYMDDateAdaptor() {
    return YMDDateAdaptor.it;
  }

  /**
   * Called from within templets using 
   * <code>
   * #set $secondField = $melati.Config.YMDHMSTimestampAdaptor.secondField($field)
   * </code>
   * idiom.
   * Perhaps this should be elsewhere.
   * @return the adaptor for rendering timestamps as drop-downs.
   */
  public static YMDHMSTimestampAdaptor getYMDHMSTimestampAdaptor() {
    return YMDHMSTimestampAdaptor.getIt();
  }

  /**
   * Called from within templets.
   * Perhaps this should be elsewhere.
   * @return the adaptor for rendering dates as normal.
   */
  public static SimpleDateAdaptor getSimpleDateAdaptor() {
    return SimpleDateAdaptor.it;
  }

}
