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

import java.io.OutputStreamWriter;

import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.PoemContext;

import org.melati.util.MelatiException;
import org.melati.util.MelatiWriter;
import org.melati.util.MelatiSimpleWriter;
import org.melati.util.UnexpectedExceptionException;

/**
 * ConfigApp is the simplest way to use Melati.
 *
 * All a ConfigApp does is to configure a Melati.
 * Importantly it does not establish a Poem session
 * leaving you to do this for yourself.
 *
 * If you want a POEM session established, please extend {@link PoemApp}.
 *
 * ConfigApp does set up a basic {@link PoemContext} with the Method set,
 * but not the POEM logicaldatabase, table or troid.
 *
 * The arguments are expected to end with a freeform string telling 
 * your application what it is meant to do.  This
 *       is automatically made available in templates as
 *       <TT>$melati.Method</TT>.
 *
 * You can change the way these things are determined by overriding
 * {@link #poemContext}.
 */

public abstract class ConfigApp implements App {

  protected static MelatiConfig melatiConfig;
  private String defaultPropertiesName = "org.melati.MelatiApp";

  /**
   * Initialise.
   *
   * @param args the command line arguments
   * @return a newly created Melati
   * @throws MelatiException if something goes wrong during initialisation
   */
  public Melati init(String[] args) throws MelatiException  {
    try {
      melatiConfig = melatiConfig();
    } catch (MelatiException e) {
      throw new UnexpectedExceptionException(e);
    }
    MelatiWriter out = new MelatiSimpleWriter(new OutputStreamWriter(System.out));
    Melati melati = new Melati(melatiConfig, out);
    melati.setArguments(args);
    PoemContext poemContext;
    poemContext = poemContext(melati);
    melati.setPoemContext(poemContext);
    
    return melati;
  }
  
  /**
   * Clean up at end of run.
   * Place holder overridden in PoemApp.
   * 
   * @param melati the melati 
   */
  public void term(Melati melati) throws MelatiException  {
  }

  /** 
   * Set application properties from the default properties file.
   * 
   * To override any setting from MelatiApp.properties,
   * simply override this method and return a vaild MelatiConfig.
   *
   * eg to use a different AccessHandler from the default:
   *
   * <PRE>
   *   protected MelatiConfig melatiConfig() throws MelatiException {
   *     MelatiConfig config = super.melatiConfig();
   *     config.setAccessHandler(new YourAccessHandler());
   *     return config;
   *   }
   * </PRE>
   *
   * @throws MelatiException if anything goes wrong with Melati
   */
  protected MelatiConfig melatiConfig() throws MelatiException {
    return new MelatiConfig(defaultPropertiesName);
  }
  
  /**
   * Do our thing.
   */
  public void run(String[] args) {
    try {
      final Melati melati = init(args);
      doConfiguredRequest(melati);
      melati.write();
      term(melati);
    } catch (Exception e) {
      throw new UnexpectedExceptionException(e);
    }
  }

  /** 
   * This method <b>SHOULD</b> be overidden.
   * @return the System Administrators name.
   */
  public String getSysAdminName () {
    return "nobody";
  }

  /** 
   * This method <b>SHOULD</b> be overidden.
   * @return the System Administrators email address.
   */
  public String getSysAdminEmail () {
    return "nobody@nobody.com";
  }

  /**
   * Set up the (@link PoemContext}, but only the Method.
   * 
   * @param melati the current {@link Melati}
   * @return a partially configured {@link PoemContext}
   * @throws MelatiException
   */
  protected PoemContext poemContext(Melati melati) throws MelatiException { 
    PoemContext it = new PoemContext();
    String[] arguments = melati.getArguments();
    if (arguments.length > 0)
     it.setMethod(arguments[arguments.length - 1]);
   return it;
 }
  
  
 /**
  * Instantiate this method to build up your own output.
  * 
  * @param melati a configured {@link Melati}
  * @throws Exception if anything goes wrong
  */
  protected abstract void doConfiguredRequest(final Melati melati)
       throws Exception;

}
