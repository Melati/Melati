package org.melati.template.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.event.EventCartridge;
import org.melati.template.TemplateContext;

public class VelocityTemplateContext implements TemplateContext {

  /** The webcontext. */
  public VelocityContext velContext;

  public VelocityTemplateContext() {
    super();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.TemplateContext#put(java.lang.String, java.lang.Object)
   */
  public void put(String s, Object o) {    
    velContext.put(s,o);    
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.TemplateContext#get(java.lang.String)
   */
  public Object get(String s) {    
    return velContext.get(s);    
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.TemplateContext#getContext()
   */
  public Object getContext() {    
    return velContext;    
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.TemplateContext#setPassbackExceptionHandling()
   */
  public void setPassbackExceptionHandling() {
    EventCartridge ec = velContext.getEventCartridge();    
    if (ec == null) {    
      ec = new EventCartridge();    
      velContext.attachEventCartridge(ec);    
    }    
    ec.addEventHandler(new PassbackEvaluationExceptionHandler(velContext));        
  }

  /**
   * {@inheritDoc}
   * @see org.melati.template.TemplateContext#setPropagateExceptionHandling()
   */
  public void setPropagateExceptionHandling() {
    EventCartridge ec = velContext.getEventCartridge();    
    if (ec == null) {    
      ec = new EventCartridge();    
      velContext.attachEventCartridge(ec);    
    }    
    ec.addEventHandler(new PropagateEvaluationExceptionHandler());        
  }

}