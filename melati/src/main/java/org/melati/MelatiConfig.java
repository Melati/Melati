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
 *     Tim Joyce <timj@paneris.org>
 *     http://paneris.org/
 *     68 Sandbanks Rd, Poole, Dorset. BH14 8BY. UK
 */



package org.melati;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.melati.login.AccessHandler;
import org.melati.servlet.FormDataAdaptorFactory;
import org.melati.template.TemplateEngine;
import org.melati.template.ServletTemplateEngine;
import org.melati.template.TempletLoader;
import org.melati.template.SimpleDateAdaptor;
import org.melati.template.YMDDateAdaptor;
import org.melati.template.YMDHMSTimestampAdaptor;
import org.melati.util.ConfigException;
import org.melati.util.EnumUtils;
import org.melati.util.HttpHeader;
import org.melati.util.MelatiLocale;
import org.melati.util.MelatiException;
import org.melati.util.MelatiWriter;
import org.melati.util.PropertiesUtils;
import org.melati.util.StringUtils;


/**
 * A MelatiConfig loads and provides access to the configuration parameters for
 * melati.  These are held in <TT>org.melati.MelatiServlet.properties</TT>.
 *
 * After configuration, you can then get a Melati, using
 * <TT>getMelati()</TT>.
 *
 * @see #getMelati(HttpServletRequest, HttpServletResponse)
 *
 */
public class MelatiConfig {

  private Properties configuration = null;
  private AccessHandler accessHandler = null;
  private FormDataAdaptorFactory fdaFactory = null;
  private TempletLoader templetLoader = null;
  private TemplateEngine templateEngine = null;
  private MelatiLocale melatiLocale = null;
  private Vector preferredCharset = null;
  private int maxLocales = 10;
  private Hashtable localeHash = new Hashtable(maxLocales);
  private String javascriptLibraryURL = null;
  private String staticURL = null;
  private String templatePath = null;
  private String defaultPropertiesName = "org.melati.MelatiServlet";
  /** The properties file name in use */
  public String propertiesName;

 /**
  * Allows creation of a <code>MelatiConfig</code> with default config params.
  *
  * @throws MelatiException if anything goes wrong.
  */
  public MelatiConfig() throws MelatiException {
    init(defaultPropertiesName);
  }

 /**
  * Allows creation of a <code>MelatiConfig</code> with
  * a specified properties file.
  *
  * @param propertiesName the name of a properties file
  * @throws MelatiException if anything goes wrong.
  */
  public MelatiConfig(String propertiesName) throws MelatiException {
    init(propertiesName);
  }

  void init(String propertiesNameIn) throws MelatiException {
    this.propertiesName = propertiesNameIn;
    String pref = propertiesName + ".";
    String accessHandlerProp = pref + "accessHandler";
    String fdaFactoryProp = pref + "formDataAdaptorFactory";
    String templetLoaderProp = pref + "templetLoader";
    String templateEngineProp = pref + "templateEngine";
    String templatePathProp = pref + "templatePath";
    String javascriptLibraryURLProp = pref + "javascriptLibraryURL";
    String staticURLProp = pref + "staticURL";
    String melatiLocaleProp = pref + "locale";
    String preferredCharsetsProp = pref + "preferredCharsets";

    try {
      configuration =
        PropertiesUtils.fromResource(getClass(), pref + "properties");
    }
    catch (FileNotFoundException e) {
      // i think that if we don't have a properties file, it is pretty fatal
      // configuration = new Properties();
      throw new ConfigException("The file " + pref + "properties" +
                                " could not be found." +
                                " Is it in your CLASSPATH?  Full Error: " +
                                e.toString());
    }
    catch (IOException e) {
      throw new ConfigException("The file " + pref + "properties" +
                                " could not be read." +
                                " Full Error: " + e.toString());
    }

    try {
      accessHandler = (AccessHandler)PropertiesUtils.
          instanceOfNamedClass(
              configuration,
              accessHandlerProp,
              "org.melati.login.AccessHandler",
              "org.melati.login.OpenAccessHandler");

      fdaFactory = (FormDataAdaptorFactory)PropertiesUtils.
          instanceOfNamedClass(
                       configuration,
                       fdaFactoryProp,
                       "org.melati.servlet.FormDataAdaptorFactory",
                       "org.melati.servlet.MemoryDataAdaptorFactory");

      templetLoader = (TempletLoader)PropertiesUtils.
          instanceOfNamedClass(
                          configuration,
                          templetLoaderProp,
                          "org.melati.template.TempletLoader",
                          "org.melati.template.ClassNameTempletLoader");

      templateEngine = (TemplateEngine)PropertiesUtils.
          instanceOfNamedClass(
                           configuration,
                           templateEngineProp,
                           "org.melati.template.TemplateEngine",
                           "org.melati.template.NoTemplateEngine");

      String languageTag = PropertiesUtils.getOrDefault(configuration,
                                                        melatiLocaleProp,
                                                        "en-gb");

      melatiLocale = MelatiLocale.fromLanguageTag(languageTag);
      if (melatiLocale == null)
          throw new Exception(languageTag +
                              " is not a valid language tag for " +
                              melatiLocaleProp);

      String preferredCharsets = PropertiesUtils.getOrDefault(
          configuration,
          preferredCharsetsProp,
          "ISO-8859-1, UTF-8, UTF-16");
      // This is a fancy way of splitting, trimming and checking for
      // errors such as spaces within fields. Also, it reflects the
      // fact that the config file format is like a q-less header field.
      // FIXME - if field contains quotes then melati initialisation
      // dies with OutOfMemory exception
      preferredCharset =
        EnumUtils.vectorOf(new HttpHeader(preferredCharsets).wordIterator());

      javascriptLibraryURL = PropertiesUtils.getOrDie(
                                                  configuration,
                                                  javascriptLibraryURLProp);

      staticURL = PropertiesUtils.getOrDie(configuration, staticURLProp);
      templatePath = PropertiesUtils.getOrDefault(configuration,
                                                  templatePathProp, ".");
    }
    catch (Exception e) {
      throw new ConfigException("Melati could not be configured because: " +
                                e.toString());
    }

  }


  /**
   * Creates a melati context.
   *
   * @param request the incoming {@link HttpServletRequest}
   * @param response the outgoing {@link HttpServletResponse}
   * @return a new {@link Melati}
   */
   public Melati getMelati(HttpServletRequest request,
                           HttpServletResponse response) {
     return new Melati(this, request, response);
   }

  /**
   * Creates a new {@link Melati}.
   *
   * @param writer a {@link MelatiWriter} to write output to
   * @return a new {@link Melati}
   */
   public Melati getMelati(MelatiWriter writer) {
     return new Melati(this, writer);
   }

  /**
   * @return {@link ServletTemplateEngine} in use.
   */
   public ServletTemplateEngine getServletTemplateEngine() {
     return (ServletTemplateEngine)templateEngine;
   }

  /**
   * @return {@link ServletTemplateEngine} in use.
   */
   public TemplateEngine getTemplateEngine() {
     return templateEngine;
   }

 /**
  * Set the {@link TemplateEngine} to use.
  *
  * @param templateEngine a {@link TemplateEngine}
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
  * @param accessHandler a {@link AccessHandler}
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
  * @param templetLoader a {@link TempletLoader}
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
  * @param fdaf a {@link FormDataAdaptorFactory}
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
  * @param url a URL to the directory containing the JavaScript for this site
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
  * @param url a URL to the directory containing the static content
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
  * @todo make this configurable
  * @return the class name of the logout servlet
  */
  public static String logoutPageServletClassName() {
    return "org.melati.login.Logout";
  }

 /**
  * @todo make this configurable
  * @return the class name of the login servlet
  */
  public static String loginPageServletClassName() {
    return "org.melati.login.Login";
  }

 /**
  * @deprecated Use getLocale if possible,
  *             otherwise you might as well use MelatiLocale.here.
  * @return British English melati locale.
  */
  public static MelatiLocale getMelatiLocale() {
    return MelatiLocale.here;
  }

 /**
  * Get the default MelatiLocale from the configuration file.
  *
  * @return The default MelatiLocale
  */
  public MelatiLocale getLocale() {
    return melatiLocale;
  }

  /**
   * Returns a MelatiLocale based on a language tag.  Locales
   * are cached for future use.
   *
   * @param languageHeader A language header from RFC 3282
   * @return a MelatiLocale based on a language tag.
   */
  public MelatiLocale getLocale(String languageHeader) {

    // language headers may have multiple language tags sperated by ,
    String tags[] = StringUtils.split(languageHeader, ',');
    MelatiLocale ml = null;

    // loop through until we find a tag we like
    for (int i=0; i<tags.length; i++) {
      String tag = tags[i];

      // remove quality value if it exists.
      // we'll just try them in order
      int indexSemicolon = tag.indexOf(';');
      if (indexSemicolon != -1)
        tag = tag.substring(0, indexSemicolon);

      String lowerTag = tag.trim().toLowerCase();

      // try our cache
      ml = (MelatiLocale) localeHash.get(lowerTag);
      if (ml != null)
        return ml;

      // try creating a locale from this tag
      ml = MelatiLocale.fromLanguageTag(lowerTag);
      if (ml != null) {
        localeHash.put(lowerTag, ml);
        return ml;
      }
    }

    // return our default locale
    return melatiLocale;
  }

  /**
   * Return the set encodings that the server prefers and supports.
   *
   * @return Array of encoding names or aliases.
   */
  public List getPreferredCharsets() {
    return preferredCharset;
  }

 /**
  * @return the adaptor for rendering dates as drop-downs.
  */
  public static YMDDateAdaptor getYMDDateAdaptor() {
    return YMDDateAdaptor.it;
  }

 /**
  * @return the adaptor for rendering timestamps as drop-downs.
  */
  public static YMDHMSTimestampAdaptor getYMDHMSTimestampAdaptor() {
    return YMDHMSTimestampAdaptor.getIt();
  }

 /**
  * @return the adaptor for rendering dates as normal.
  */
  public static SimpleDateAdaptor getSimpleDateAdaptor() {
    return SimpleDateAdaptor.it;
  }
}




