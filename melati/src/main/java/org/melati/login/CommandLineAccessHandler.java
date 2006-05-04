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
 *     Tim Pizey <timp@paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.melati.Melati;
import org.melati.poem.AccessPoemException;
import org.melati.poem.NoSuchRowPoemException;
import org.melati.poem.PoemThread;
import org.melati.poem.User;
import org.melati.util.ArrayUtils;
import org.melati.util.MelatiException;


/**
 * A store for a username and password.
 *
 */
final class Authorization {
  public String username = null;
  public String password = null;

  /**
   * Do not allow public instantiation.
   */
  private  Authorization() {
  }

  /**
   * Private constructor.
   * 
   * @param username
   * @param password
   */
  private Authorization(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * Interogate the user for thier details.
   * 
   * @param input notmally System.in
   * @return a new Authorisation object or null
   */
  static Authorization from(InputStream input) {
    String username = null;
    String password = null;

    System.out.print("Enter your username: ");
    BufferedReader in = new BufferedReader(new InputStreamReader(input));
    try {
       username = in.readLine();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    System.out.print("Enter your password: ");
    in = new BufferedReader(new InputStreamReader(input));
    try {
       password = in.readLine();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    if (username != null && password != null)
      return new Authorization(username, password);
    else
      return null;
  }

  /**
   * Create an Authorisation from an array, typically the stored 
   * arguments.
   * @param args Command line arguments, stored in Melati
   * @return a new Authorization object or null
   */
  static Authorization from(String[] args) {
    String username = null;
    String password = null;
    boolean nextValue = false;
    for (int i = 0; i < args.length; i++) {
      if (nextValue) {
        username = args[i];
        break;
      }
      if (args[i].equalsIgnoreCase("-u"))
        nextValue = true;
      if (args[i].equalsIgnoreCase("-user"))
        nextValue = true;
      if (args[i].equalsIgnoreCase("-username"))
        nextValue = true;
      if (args[i].equalsIgnoreCase("--username"))
        nextValue = true;
    }
    nextValue = false;
    for (int i = 0; i < args.length; i++) {
      if (nextValue) {
        password = args[i];
        break;
      }
      if (args[i].equalsIgnoreCase("-p"))
        nextValue = true;
      if (args[i].equalsIgnoreCase("-pass"))
        nextValue = true;
      if (args[i].equalsIgnoreCase("-password"))
        nextValue = true;
      if (args[i].equalsIgnoreCase("--password"))
        nextValue = true;
    }

    if (username != null && password != null)
      return new Authorization(username, password);
    else
      return null;
  }

}


/**
 * A handler invoked when an {@link AccessPoemException} is thrown.
 *
 * @see org.melati.login.AccessHandler
 */
public class CommandLineAccessHandler implements AccessHandler {

  /**
   * Constructor.
   */
  public CommandLineAccessHandler() {
    super();
  }

  /** 
   * Actually handle the {@link AccessPoemException}.
   * 
   * @see org.melati.login.AccessHandler
   */
  public void handleAccessException(Melati melati,
                                    AccessPoemException accessException)
      throws Exception {
    System.out.println(accessException.getMessage());
    Authorization auth = Authorization.from(System.in);
    if (auth != null) {
      // They have tried to log in

      User user = null;
      try {
        user = (User)melati.getDatabase().getUserTable().getLoginColumn().
                   firstWhereEq(auth.username);
      }
      catch (NoSuchRowPoemException e) {
        // user will still be null
        user = null; // shut checkstyle up
      }
      catch (AccessPoemException e) {
        // paranoia
        user = null; // shut checkstyle up
      }

      if (user != null && user.getPassword_unsafe().equals(auth.password)) {
        // Login/password authentication succeeded
        // Add the arguments to the stored Melati
        String[] args = melati.getArguments();
        args = (String[])ArrayUtils.added(args,"-u");
        args = (String[])ArrayUtils.added(args,auth.username);
        args = (String[])ArrayUtils.added(args,"-p");
        args = (String[])ArrayUtils.added(args,auth.password);
        melati.setArguments(args);
      }
    }
  }

  /**
   * Called when the PoemTask is initialised, recalled after a login.
   * @see org.melati.login.AccessHandler#establishUser(org.melati.Melati)
   */
  public Melati establishUser(Melati melati) {
    Authorization auth = Authorization.from(melati.getArguments());
    if (auth == null) {
      // No attempt to log in: become `guest'

      PoemThread.setAccessToken(melati.getDatabase().guestAccessToken());
      return melati;
    }
    else {
      // They have tried to login or set command line parameters

      User user = null;
      try {
        user = (User)melati.getDatabase().getUserTable().getLoginColumn().
                   firstWhereEq(auth.username);
      }
      catch (NoSuchRowPoemException e) {
        ; // user will still be null
      }
      catch (AccessPoemException e) {
        ; // paranoia
      }

      if (user == null || !user.getPassword_unsafe().equals(auth.password)) {

          // We  just let the user try again as
          // `guest' and hopefully trigger the same problem and get the same
          // message all over again.
        PoemThread.setAccessToken(melati.getDatabase().guestAccessToken());
          return melati;
      }
      else {

        // Login/password authentication succeeded

        PoemThread.setAccessToken(user);
        return melati;
      }
    }
  }

  /**
   * A no-op in a command line application.
   * 
   * @see org.melati.login.AccessHandler#buildRequest(org.melati.Melati)
   */
  public void buildRequest(Melati melati) throws MelatiException, IOException {
     // Nothing to do here
  }

}


