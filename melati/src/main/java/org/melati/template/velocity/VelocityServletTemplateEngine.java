/**
 * 
 */
package org.melati.template.velocity;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.melati.Melati;
import org.melati.MelatiConfig;
import org.melati.template.ServletTemplateContext;
import org.melati.template.ServletTemplateEngine;
import org.melati.template.TemplateEngineException;
import org.melati.util.MelatiWriter;

/**
 * @author timp
 * @since 22 Aug 2007
 * 
 */
public class VelocityServletTemplateEngine extends VelocityTemplateEngine
        implements ServletTemplateEngine {

  /**
   * Constructor.
   */
  public VelocityServletTemplateEngine() {
    super();
  }

  /**
   * Construct a new Engine for use in a servlet environment. For Velocity this
   * is no different to initialisation outside of a servlets environment.
   * 
   * @see org.melati.servlet.TemplateServlet
   * @param melatiConfig
   *          a {@link MelatiConfig}
   * @param servlet
   *          the servlet we are within
   * @throws TemplateEngineException
   *           if any problem occurs with the engine
   */
  public void init(MelatiConfig melatiConfig, HttpServlet servlet)
          throws TemplateEngineException {
    init(melatiConfig);
  }

  /**
   * @param response
   *          the <code>HttpServletResponse</code> that this writer will be
   *          part of
   * @param buffered
   *          whether the writer should be buffered
   * @throws IOException
   *           if there is a problem with the filesystem.
   * @return a {@link MelatiWriter} appropriate for this engine.
   */
  public MelatiWriter getServletWriter(HttpServletResponse response,
          boolean buffered) throws IOException {
    if (buffered) {
      return new MelatiBufferedVelocityWriter(response);
    } else {
      return new MelatiVelocityWriter(response);
    }
  }

  /** Mimicking the $Form behaviour of Webmacro. */
  public static final String FORM = "Form";

  /**
   * Get the template context for Velocity, with servlet specific objects added.
   * 
   * @param melati
   *        the {@link Melati}
   * @return a {@link TemplateContext}
   */
  public ServletTemplateContext getServletTemplateContext(Melati melati) {
    VelocityContext context = new VelocityContext();
    org.melati.template.velocity.HttpServletRequestWrap req = 
      new org.melati.template.velocity.HttpServletRequestWrap(
          melati.getRequest());
    context.put(VelocityTemplateContext.REQUEST, req);
    context.put(FORM, req);
    context.put(VelocityTemplateContext.RESPONSE, melati.getResponse());
    return new VelocityTemplateContext(context);
  }

  
}
