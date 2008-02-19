/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2006 Tim Pizey
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
package org.melati.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.melati.Melati;
import org.melati.poem.AccessPoemException;
import org.melati.poem.PoemThread;
import org.melati.poem.User;
import org.melati.poem.util.ArrayUtils;
import org.melati.util.MelatiException;

/**
 * A handler invoked when an {@link AccessPoemException} is thrown.
 * 
 * If the application is invoked without a username and password on the command
 * line then the user will be prompted for them.
 * 
 * If the usename is supplied then the user will not be prompted as this migth
 * interfere with use in a scripting environment.
 * 
 * @see org.melati.login.AccessHandler
 */
public class CommandLineAccessHandler implements AccessHandler {

  private PrintStream output = System.out;

  private InputStream input = System.in;

  private boolean commandLineUserCredentialsSet = false;

  BufferedReader inputReader = null;

  /**
   * Constructor.
   */
  public CommandLineAccessHandler() {
    super();
    commandLineUserCredentialsSet = false;
  }


  /**
   * Actually handle the {@link AccessPoemException}.
   * 
   * {@inheritDoc}
   * 
   * @see org.melati.login.AccessHandler#handleAccessException
   *      (org.melati.Melati, org.melati.poem.AccessPoemException)
   */
  public void handleAccessException(Melati melati,
      AccessPoemException accessException) throws Exception {
    Authorization auth = null;
    boolean loggedIn = false;
    if (!commandLineUserCredentialsSet) {
      inputReader = new BufferedReader(new InputStreamReader(input));
      System.err.println(accessException.getMessage());
      int goes = 0;
      while (goes < 3 && loggedIn == false) {
        goes++;
        auth = Authorization.from(inputReader, output);
        if (auth != null) {

          User user = null;
          // They have tried to log in
          if (auth.username != null) {
            user = (User) melati.getDatabase().getUserTable().getLoginColumn()
                  .firstWhereEq(auth.username);
          }
          if (user != null && user.getPassword_unsafe().equals(auth.password)) {
            // Login/password authentication succeeded
            // Add the arguments to the stored Melati
            String[] args = melati.getArguments();
            args = (String[]) ArrayUtils.added(args, "-u");
            args = (String[]) ArrayUtils.added(args, auth.username);
            args = (String[]) ArrayUtils.added(args, "-p");
            args = (String[]) ArrayUtils.added(args, auth.password);
            melati.setArguments(args);
            loggedIn = true;
          } else {
            System.err.println("Unknown username");  // ;)
          }
        }
      }
    }
    if (!loggedIn)
      throw accessException;
  }

  /**
   * Called when the PoemTask is initialised, recalled after a login.
   * {@inheritDoc}
   * 
   * @see org.melati.login.AccessHandler#establishUser(org.melati.Melati)
   */
  public Melati establishUser(Melati melati) {
    Authorization auth = Authorization.from(melati.getArguments());
    if (auth == null) {
      // No attempt to log in: become `guest'
      PoemThread.setAccessToken(melati.getDatabase().guestAccessToken());
      return melati;
    } else {
      commandLineUserCredentialsSet = true;
      // They have tried to login or set command line parameters
      User user = null;
      user = (User) melati.getDatabase().getUserTable().getLoginColumn()
            .firstWhereEq(auth.username);
      if (user == null || !user.getPassword_unsafe().equals(auth.password)) {
        // We just let the user try again as
        // `guest' and hopefully trigger the same problem and get the same
        // message all over again.
        PoemThread.setAccessToken(melati.getDatabase().guestAccessToken());
        return melati;
      } else {
        // Login/password authentication succeeded
        PoemThread.setAccessToken(user);
        return melati;
      }
    }
  }

  /**
   * A no-op in a command line application.
   * 
   * {@inheritDoc}
   * 
   * @see org.melati.login.AccessHandler#buildRequest(org.melati.Melati)
   */
  public void buildRequest(Melati melati) throws MelatiException, IOException {
    // Nothing to do here
  }

  /**
   * @param input
   *          The input to set.
   */
  public void setInput(InputStream input) {
    this.input = input;
  }

  /**
   * @param output
   *          The output to set.
   */
  public void setOutput(PrintStream output) {
    this.output = output;
  }

}
