package org.melati;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.webmacro.servlet.*;
import org.webmacro.*;
import org.melati.poem.*;

public interface AccessHandler {
  Template handleAccessException(WebContext context,
				 AccessPoemException accessException)
      throws Exception;

  /**
   * @return the <TT>WebContext</TT> to use in processing the request; can
   *         just be <TT>context</TT>, or something derived from
   *         <TT>context</TT>, or <TT>null</TT> if the routine has already
   *         handled the request (<I>e.g.</I> by sending back an error)
   */

  WebContext establishUser(WebContext context, Database database)
      throws PoemException, IOException, ServletException;
}
