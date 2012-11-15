package org.melati.admin.test;

import org.melati.admin.PoemGvisTypeConverter;

import junit.framework.TestCase;
/**
 * 
 * @author timp
 * @since 15 Nov 2012
 *
 */
public class PoemGvisTypeConverterTest extends TestCase {

  public final void testConvert() {
    assertEquals("string",PoemGvisTypeConverter.convert(0));
    assertEquals("string",PoemGvisTypeConverter.convert(3));
    assertEquals("number",PoemGvisTypeConverter.convert(-1));
  }

}
