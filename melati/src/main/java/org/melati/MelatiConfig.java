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
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.melati.login.AccessHandler;
import org.melati.servlet.FormDataAdaptorFactory;
import org.melati.template.TemplateEngine;
import org.melati.template.TempletLoader;
import org.melati.template.SimpleDateAdaptor;
import org.melati.template.YMDDateAdaptor;
import org.melati.template.YMDHMSTimestampAdaptor;
import org.melati.util.ConfigException;
import org.melati.util.MelatiLocale;
import org.melati.util.MelatiException;
import org.melati.util.PropertiesUtils;


/**
 * A MelatiConfig loads and provides access to the configuration parameters for
 * melati.  These are held in <TT>org.melati.MelatiServlet.properties</TT>.
 *
 * After configuration, you can then get a Melati, using
 * <TT>getMelati()</T>.
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
  private String javascriptLibraryURL = null;
  private String staticURL = null;
  private String templatePath = null;
  private String defaultPropertiesName = "org.melati.MelatiServlet";

  // allows creation of a melaticonfig with default config params

  public MelatiConfig() throws MelatiException {
    init(defaultPropertiesName);
  }

  // allows creation of a melaticonfig with a specified properties file

  public MelatiConfig(String propertiesName) throws MelatiException {
    init(propertiesName);
  }
  
  public void init(String propertiesName) throws MelatiException {
    String pref = propertiesName + ".";
    String accessHandlerProp = pref + "accessHandler";
    String fdaFactoryProp = pref + "formDataAdaptorFactory";
    String templetLoaderProp = pref + "templetLoader";
    String templateEngineProp = pref + "templateEngine";
    String templatePathProp = pref + "templatePath";
    String javascriptLibraryURLProp = pref + "javascriptLibraryURL";
    String staticURLProp = pref + "staticURL";
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
              "org.melati.login.HttpBasicAuthenticationAccessHandler");

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

  // creates a melati context
  public Melati getMelati(HttpServletRequest request,
                          HttpServletResponse response) 
      throws MelatiException {
    return new Melati(this, request, response);
  }

  // the template engine in use
  public TemplateEngine getTemplateEngine() {
    return templateEngine;
  }
  
  public void setTemplateEngine(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  public AccessHandler getAccessHandler() {
    return accessHandler;
  }

  public void setAccessHandler(AccessHandler accessHandler) {
    this.accessHandler = accessHandler;
  }

  public TempletLoader getTempletLoader() {
    return templetLoader;
  }

  public void setTempletLoader(TempletLoader templetLoader) {
    this.templetLoader = templetLoader;
  }

  public void setFormDataAdaptorFactory(FormDataAdaptorFactory fdaf) {
    fdaFactory = fdaf;
  }

  public FormDataAdaptorFactory getFormDataAdaptorFactory() {
    return fdaFactory;
  }

  // location of javascript for this site
  public String getJavascriptLibraryURL() {
    return javascriptLibraryURL;
  }

  public void setJavascriptLibraryURL(String url) {
    this.javascriptLibraryURL = url;
  }

  // location of static content for this site
  public String getStaticURL() {
    return staticURL;
  }

  // location of templates
  public String getTemplatePath() {
    return templatePath;
  }

  public void setStaticURL(String url) {
    this.staticURL = url;
  }

  public static String logoutPageServletClassName() {
    return "org.melati.login.Logout";
  }

  public static MelatiLocale getMelatiLocale() {
    return MelatiLocale.here;
  }

  public MelatiLocale getLocale() {
    return MelatiLocale.here;
  }

  // get the adaptor for rendering dates as drop-downs
  public static YMDDateAdaptor getYMDDateAdaptor() {
    return YMDDateAdaptor.it;
  }

  // get the adaptor for rendering timestamps as drop-downs
  public static YMDHMSTimestampAdaptor getYMDHMSTimestampAdaptor() {
    return YMDHMSTimestampAdaptor.it;
  }

  // get the adaptor for rendering dates as normal
  public static SimpleDateAdaptor getSimpleDateAdaptor() {
    return SimpleDateAdaptor.it;
  }
}




