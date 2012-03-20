/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2005 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */

package org.melati.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Properties;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;

import org.melati.login.AccessHandler;
import org.melati.login.HttpBasicAuthenticationAccessHandler;
import org.melati.login.HttpSessionAccessHandler;
import org.melati.login.OpenAccessHandler;
import org.melati.poem.PoemDatabaseFactory;
import org.melati.poem.util.ArrayUtils;
import org.melati.template.webmacro.WebmacroTemplateEngine;
import org.melati.util.InstantiationPropertyException;
import org.melati.util.MelatiException;
import org.melati.util.MelatiWriter;
import org.melati.util.MelatiSimpleWriter;

/**
 * ConfigApp is the simplest way to use Melati.
 * 
 * All a ConfigApp does is to configure a Melati. Importantly it does not
 * establish a Poem session leaving you to do this for yourself.
 * 
 * If you want a POEM session established, please extend {@link AbstractPoemApp}.
 * 
 * ConfigApp does set up a basic {@link PoemContext} with the Method set, but
 * not the POEM logicaldatabase, table or troid.
 * 
 * The arguments are expected to end with a freeform string telling your
 * application what it is meant to do. This is automatically made available in
 * templates as <TT>$melati.Method</TT>.
 * 
 * You can change the way these things are determined by overriding
 * {@link #poemContext}.
 */

public abstract class AbstractConfigApp implements App {

  protected static MelatiConfig melatiConfig;

  protected PrintStream output = System.out;

  /**
   * Initialise.
   * 
   * @param args
   *          the command line arguments
   * @return a newly created Melati
   * @throws MelatiException
   *           if something goes wrong during initialisation
   */
  public Melati init(String[] args) throws MelatiException {
      melatiConfig = melatiConfig();
    String[] argumentsWithoutOutput = applyNamedArguments(args);
    MelatiWriter out = new MelatiSimpleWriter(new OutputStreamWriter(output));
    Melati melati = new Melati(melatiConfig, out);
    melati.setArguments(argumentsWithoutOutput);
    melati.setPoemContext(poemContext(melati));

    return melati;
  }

  /**
   * Clean up at end of run. Overridden in PoemApp.
   * 
   * @param melati
   *          the melati
   * @throws IOException if there is an io problem
   */
  public void term(Melati melati) throws IOException {
    melati.write();
  }

  /**
   * Set application properties from the default properties file.
   * 
   * This method will look for a properties file called
   * <tt>org.melati.MelatiConfig.properties</tt>; if it finds that the
   * AccessHandler is an Http handler it will set the access handler to
   * <code>OpenAccessHandler</code>.
   * 
   * Similarly ServletTemplateEngine is changed to TemplateEngine.
   *  
   * To override any setting from MelatiConfig.properties, simply override this
   * method and return a vaild MelatiConfig.
   * 
   * eg to use a different AccessHandler from the default:
   * 
   * <PRE>
   * protected MelatiConfig melatiConfig() throws MelatiException {
   *   MelatiConfig config = super.melatiConfig();
   *   config.setAccessHandler(new YourAccessHandler());
   *   return config;
   * }
   * </PRE>
   * 
   * @throws MelatiException
   *           if anything goes wrong with Melati
   */
  protected MelatiConfig melatiConfig() throws MelatiException {
    Properties servletProperties = MelatiConfig.getProperties();

    if (servletProperties.getProperty("org.melati.MelatiConfig.templateEngine").equals(
        "org.melati.template.webmacro.WebmacroServletTemplateEngine"))
      servletProperties.setProperty("org.melati.MelatiConfig.templateEngine", 
          "org.melati.template.webmacro.WebmacroTemplateEngine");
    if (servletProperties.getProperty("org.melati.MelatiConfig.templateEngine").equals(
        "org.melati.template.webmacro.VelocityServletTemplateEngine"))
      servletProperties.setProperty("org.melati.MelatiConfig.templateEngine", 
          "org.melati.template.webmacro.VelocityTemplateEngine");
    if (servletProperties.getProperty("org.melati.MelatiConfig.templateEngine").equals(
        "org.melati.template.webmacro.FreemarkerServletTemplateEngine"))
      servletProperties.setProperty("org.melati.MelatiConfig.templateEngine", 
          "org.melati.template.webmacro.FreemarkerTemplateEngine");
       
    if (servletProperties.getProperty("org.melati.MelatiConfig.accessHandler").equals(
        "org.melati.login.HttpBasicAuthenticationAccessHandler"))
      servletProperties.setProperty("org.melati.MelatiConfig.accessHandler", 
          "org.melati.login.OpenAccessHandler");
    if (servletProperties.getProperty("org.melati.MelatiConfig.accessHandler").equals(
        "org.melati.login.HttpSessionAccessHandler"))
      servletProperties.setProperty("org.melati.MelatiConfig.accessHandler", 
          "org.melati.login.OpenAccessHandler");
       
    
    MelatiConfig config = new MelatiConfig(servletProperties);

    return config;
  }

  /**
   * Do our thing.
   */
  public void run(String[] args) throws Exception {
    Melati melati = init(args);
    try { 
      doConfiguredRequest(melati);
    } finally {  
      term(melati);
      PoemDatabaseFactory.getPoemShutdownThread().run();
    }
  }

  /**
   * This method <b>SHOULD</b> be overidden.
   * 
   * @return the System Administrators name.
   */
  public String getSysAdminName() {
    return "nobody";
  }

  /**
   * This method <b>SHOULD</b> be overidden.
   * 
   * @return the System Administrators email address.
   */
  public String getSysAdminEmail() {
    return "nobody@nobody.com";
  }

  /**
   * Set up the (@link PoemContext}, but only the Method.
   * 
   * @param melati
   *          the current {@link Melati}
   * @return a partially configured {@link PoemContext}
   */
  protected PoemContext poemContext(Melati melati) {
    PoemContext it = new PoemContext();
    String[] arguments = melati.getArguments();
    if (arguments.length > 0)
      it.setMethod(arguments[arguments.length - 1]);
    return it;
  }

  protected String[] applyNamedArguments(String[] arguments) {
    String[] unnamedArguments = new String[] {};
    boolean nextIsOutput = false;
    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i].startsWith("-o"))
        nextIsOutput = true;
      else if (nextIsOutput)
        try {
          setOutput(arguments[i]);
          nextIsOutput = false;
        } catch (IOException e) {
          throw new RuntimeException("Problem setting output to "
                  + arguments[i], e);
        }
      else {
        unnamedArguments = (String[])ArrayUtils.added(unnamedArguments,
                arguments[i]);
      }
    }

    return unnamedArguments;
  }

  private void setOutput(String path) throws IOException {
    File outputFile = new File(path).getCanonicalFile();
    File parent = new File(outputFile.getParent());
    parent.mkdirs();
    outputFile.createNewFile();
    setOutput(new PrintStream(new FileOutputStream(outputFile)));
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.melati.app.App#setOutput(java.io.PrintStream)
   */
  public void setOutput(PrintStream out) {
    output = out;
  }

  /**
   * Instantiate this method to build up your own output.
   * 
   * @param melati
   *          a configured {@link Melati}
   * @throws Exception
   *           if anything goes wrong
   */
  protected abstract void doConfiguredRequest(final Melati melati)
          throws Exception;

}
