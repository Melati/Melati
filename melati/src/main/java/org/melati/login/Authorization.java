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
import java.io.PrintStream;

/**
 * A store for a username and password.
 */
final class Authorization {
  /** The username. */
  public String username = null;
  /** The password. */
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
   * Interrogate the user for their details.
   *
   * @param input an open reader
   * @param output normally System.out
   * @return a new Authorisation object or null
   */
  static Authorization from(BufferedReader input, PrintStream output) throws IOException {
    String username = null;
    String password = null;

    output.print("Enter your username: ");
    username = input.readLine();
    output.print("Enter your password: ");
    password = input.readLine();
    return new Authorization(username, password);
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

    if (username != null && password != null) {
      return new Authorization(username, password);
    } else
      return null;
  }

}
