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
   *                    You should call its <TT>setIdent</TT> and/or
   *                    <TT>setValue</TT> methods to get it into a state which
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
