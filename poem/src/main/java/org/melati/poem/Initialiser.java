/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use WebMacro on non-GPL terms,
 * contact me!
 */

package org.melati.poem;

/**
 * A piece of code for initialising a newly created object in a POEM table.
 * Much like a Java constructor.  You will probably want to define these as
 * anonymous classes.
 *
 * @see Table#create(org.melati.poem.Initialiser)
 */

public interface Initialiser {

  /**
   * Initialise a freshly generated POEM object.
   *
   * @param object      A <TT>Persistent</TT> representing the new object, or,
   *                    if the table was defined in the DSD under the name
   *                    <TT><I>foo</I></TT>, an application-specialised
   *                    subclass <TT><I>Foo</I></TT> of <TT>Persistent</TT>.
   *                    You should call its <TT>setRaw</TT> and/or
   *                    <TT>setCooked</TT> methods to get it into a state which
   *                    is (a) legal, in that all its fields have valid values,
   *                    and (b) writeable by you (the <TT>AccessToken</TT> of
   *                    the calling thread).
   *
   * @exception AccessPoemException
   *                you will not provoke an <TT>AccessPoemException</TT> during
   *                initialisation (while this method is running) unless the
   *                application-specialised <TT>Persistent</TT> throws one
   *                explicitly, because POEM's standard checks are disabled;
   *                however, after this method returns, the object you have
   *                initialised will be checked to ensure that you have write
   *                access to it
   *
   * @see PoemThread#accessToken()
   * @see Table#create(org.melati.poem.Initialiser)
   */

  void init(Persistent object)
      throws AccessPoemException, ValidationPoemException;
}
