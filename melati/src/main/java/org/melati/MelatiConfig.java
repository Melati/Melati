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

import org.melati.login.AccessHandler;
import org.melati.template.TemplateEngine;
import org.melati.template.TempletLoader;
import org.melati.util.MelatiLocale;
import org.melati.util.MelatiException;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.PropertiesUtils;

public class MelatiConfig {

  private Properties configuration = null;
  private AccessHandler accessHandler = null;
  private TempletLoader templetLoader = null;
  private TemplateEngine templateEngine = null;
  private String javascriptLibraryURL = null;
  private String staticURL = null;
  private static Class clazz;
  private static String propertiesName = "org.melati.MelatiServlet";

  static {
    try {
      clazz = Class.forName("org.melati.MelatiConfig");
    }
    catch (ClassNotFoundException e) {
      throw new MelatiBugMelatiException("Out of date Class.forName", e);
    }
  }

  public MelatiConfig() throws ConfigException {

    // Load org.melati.MelatiServlet.properties, or set blank configuration
    String pref = propertiesName + ".";
    String accessHandlerProp = pref + "accessHandler";
    String templetLoaderProp = pref + "templetLoader";
    String templateEngineProp = pref + "templateEngine";
    String javascriptLibraryURLProp = pref + "javascriptLibraryURL";
    String staticURLProp = pref + "staticURL";

    try {
      configuration =
          PropertiesUtils.fromResource(clazz, pref + "properties");
    }
    catch (FileNotFoundException e) {
      // i think that if we don't have a properties file, it is pretty fatal
      // configuration = new Properties();
      throw new ConfigException("The file " + pref + "properties could not be found.  Is it in your CLASSPATH?  Full Error: " + e.toString());
    }
    catch (IOException e) {
      throw new ConfigException("The file " + pref + "properties could not be read.  Full Error: " + e.toString());
    }

    try {
      accessHandler = (AccessHandler)PropertiesUtils.instanceOfNamedClass(
	    configuration, accessHandlerProp, "org.melati.login.AccessHandler",
	    "org.melati.login.HttpBasicAuthenticationAccessHandler");

      templetLoader = (TempletLoader)PropertiesUtils.instanceOfNamedClass(
	  configuration, templetLoaderProp, "org.melati.template.TempletLoader",
	  "org.melati.template.ClassNameTempletLoader");

      templateEngine = (TemplateEngine)PropertiesUtils.instanceOfNamedClass(
	  configuration, templateEngineProp, "org.melati.template.TemplateEngine",
	  "org.melati.template.NoTemplateEngine");

      javascriptLibraryURL = PropertiesUtils.getOrDie(configuration,
						      javascriptLibraryURLProp);

      staticURL = PropertiesUtils.getOrDie(configuration,
					   staticURLProp);
    }
    catch (Exception e) {
      throw new ConfigException("Melati could not be configured because: " + e.toString());
    }

  }
  
  public String getJavascriptLibraryURL() {
    return javascriptLibraryURL;
  }

  public String getStaticURL() {
    return staticURL;
  }

  public AccessHandler getAccessHandler() {
    return accessHandler;
  }

  public TempletLoader getTempletLoader() {
    return templetLoader;
  }

  public TemplateEngine getTemplateEngine() {
    return templateEngine;
  }

  public MelatiLocale getMelatiLocale() {
    return MelatiLocale.here;
  }

}
