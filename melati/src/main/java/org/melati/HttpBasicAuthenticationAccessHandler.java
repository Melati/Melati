package org.melati;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.webmacro.*;
import org.webmacro.util.*;
import org.webmacro.engine.*;
import org.webmacro.servlet.*;
import org.melati.util.*;
import org.melati.*;
import org.melati.poem.*;

/**
 * Flags up when something was illegal or not supported about an incoming HTTP
 * authorization
 */

class HttpAuthorizationMelatiException extends MelatiRuntimeException {
  public HttpAuthorizationMelatiException(String message) {
    super(message, null);
  }
}

/**
 * The information contained in an HTTP authorization
 */

class HttpAuthorization {
  public String type;
  public String username;
  public String password;

  public HttpAuthorization(String type, String username, String password) {
    this.type = type;
    this.username = username;
    this.password = password;
  }

  public static HttpAuthorization from(String authHeader) {
    // FIXME single space probably not only valid sep

    if (authHeader.regionMatches(0, "Basic ", 0, 6)) {

      String logpas = Base64.decode(authHeader.substring(6));

      int colon = logpas.indexOf(':');

      if (colon == -1)
	throw new HttpAuthorizationMelatiException(
            "The browser sent Basic Authorization credentials with no colon " +
	    "(that's not legal)");

      return new HttpAuthorization("Basic",
				   logpas.substring(0, colon).trim(),
				   logpas.substring(colon + 1).trim());
    }
    else {
      int space = authHeader.indexOf(' ');
      if (space == -1)
	throw new HttpAuthorizationMelatiException(
            "The browser sent an Authorization header without a space, " +
            "so it can't be anything Melati understands: " +
            authHeader);

      String type = authHeader.substring(0, space);
      throw new HttpAuthorizationMelatiException(
          "The browser tried to authenticate using an authorization type " +
	  "`" + type + "' which Melati doesn't understand");
    }
  }

  public static HttpAuthorization from(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    return header == null ? null : from(header);
  }
}

/**
 * An <TT>AccessHandler</TT> which uses the HTTP Basic Authentication scheme to
 * elicit and maintain the user's login and password.  This implementation
 * doesn't use the servlet session at all, so it doesn't try to send cookies or
 * do URL rewriting.
 *
 * @see MelatiServlet#handle(org.webmacro.servlet.WebContext, org.melati.Melati)
 * @see MelatiServlet#init
 */

public class HttpBasicAuthenticationAccessHandler implements AccessHandler {
  private static final String className =
      new HttpBasicAuthenticationAccessHandler().getClass().getName();

  public final String REALM = className + ".realm";
  public final String USER = className + ".user";

  protected boolean useSession() {
    return false;
  }

  /**
   * Force a login by sending a 401 error back to the browser.  FIXME
   * Apache/Netscape appear not to do anything with <TT>message</TT>, which is
   * why it's just left as a <TT>String</TT>.
   */

  protected void forceLogin(HttpServletResponse resp,
			    String realm, String message) throws IOException {
    String desc = realm == null ? "<unknown>" : StringUtils.tr(realm, '"', ' ');
    resp.setHeader("WWW-Authenticate", "Basic realm=\"" + desc + "\"");
    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
  }

  public Template handleAccessException(
      WebContext context, AccessPoemException accessException)
          throws Exception {

    String capName = accessException.capability.getName();
    if (useSession())
      context.getSession().putValue(REALM, capName);
    forceLogin(context.getResponse(), capName, accessException.getMessage());
    return null;
  }

  public WebContext establishUser(WebContext context, Database database)
      throws PoemException, IOException, ServletException {

    HttpAuthorization auth = HttpAuthorization.from(context.getRequest());

    if (auth == null) {
      // No attempt to log in: become `guest'

      PoemThread.setAccessToken(database.guestAccessToken());
      return context;
    }
    else {
      // They are trying to log in

      // If allowed, we store the User in the session to avoid repeating the
      // SELECTion implied by firstWhereEq for every hit

      User sessionUser =
	  useSession() ? (User)context.getSession().getValue(USER) : null;
      User user = null;

      if (sessionUser == null ||
	  !sessionUser.getLogin().equals(auth.username))
	try {
	  user = (User)database.getUserTable().getLoginColumn().
		     firstWhereEq(auth.username);
	}
        catch (NoSuchRowPoemException e) {
	}
        catch (AccessPoemException e) {
	  // paranoia
	}
      else
	user = sessionUser;

      if (user == null || !user.getPassword().equals(auth.password)) {

	// Login/password authentication failed; we must trigger another
	// attempt.  But do we know what "realm" (= POEM capability name) for
	// which they were originally found not to be authorized?

	if (useSession()) {

	  // The "realm" is stored in the session

	  forceLogin(context.getResponse(),
		     (String)context.getSession().getValue(REALM),
		     "Login/password not recognised");
	  return null;
	}
	else {

	  // We don't know the "realm", so we just let the user try again as
	  // `guest' and hopefully trigger the same problem and get the same
	  // message all over again.  Not very satisfactory but the alternative
	  // is providing a default realm like "<unknown>".

	  PoemThread.setAccessToken(database.guestAccessToken());
	  return context;
	}
      }
      else {

	// Login/password authentication succeeded

	PoemThread.setAccessToken(user);

	if (useSession() && user != sessionUser)
	  context.getSession().putValue(USER, user);

	return context;
      }
    }
  }
}
