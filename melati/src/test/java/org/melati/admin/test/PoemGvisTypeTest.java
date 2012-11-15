/**
 * 
 */
package org.melati.admin.test;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.melati.admin.PoemGvisType;
import org.melati.poem.PoemTypeFactory;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 13 Nov 2012
 *
 */
public class PoemGvisTypeTest extends TestCase {

  public final void testPoemGvisType() {
  }

  public final void testGvisType() {
    assertEquals("\"fred\"", PoemGvisType.from(PoemTypeFactory.STRING.getCode()).jsonValue("fred"));
    assertEquals("\"fred\"", PoemGvisType.from(-7).jsonValue("fred"));
    assertEquals("1", PoemGvisType.from(PoemTypeFactory.INTEGER.getCode()).jsonValue(new Integer(1)));
    assertEquals("1", PoemGvisType.from(-6).jsonValue(new Integer(1)));
    
    for (PoemGvisType type : PoemGvisType.values()) {
      assertEquals(
          PoemTypeFactory.forCode(null, type.getPoemType()).getDisplayName(), 
          type.getPeomTypeFactory().getDisplayName());
    }
    assertEquals("1", PoemGvisType.from(-6).jsonValue(new Integer(1)));

    
    assertEquals("number", PoemGvisType.TROID.gvisJsonTypeName());
    assertEquals("boolean", PoemGvisType.DELETED.gvisJsonTypeName());
  }

  public final void testTypeName() {
    assertEquals("number", PoemGvisType.TROID.gvisJsonTypeName());
    assertEquals("boolean", PoemGvisType.DELETED.gvisJsonTypeName());
    assertEquals("number", PoemGvisType.TYPE.gvisJsonTypeName());
    assertEquals("boolean", PoemGvisType.BOOLEAN.gvisJsonTypeName());
    assertEquals("number", PoemGvisType.INTEGER.gvisJsonTypeName());
    assertEquals("number", PoemGvisType.DOUBLE.gvisJsonTypeName());
    assertEquals("number", PoemGvisType.LONG.gvisJsonTypeName());
    assertEquals("number", PoemGvisType.BIGDECIMAL.gvisJsonTypeName());
    assertEquals("string", PoemGvisType.STRING.gvisJsonTypeName());
    assertEquals("string", PoemGvisType.PASSWORD.gvisJsonTypeName());
    assertEquals("date", PoemGvisType.DATE.gvisJsonTypeName());
    assertEquals("datetime", PoemGvisType.TIMESTAMP.gvisJsonTypeName());
    assertEquals("timeofday", PoemGvisType.TIME.gvisJsonTypeName());
    try { 
      PoemGvisType.BINARY.gvisJsonTypeName();
      fail("Should have bombed");
    } catch (IllegalArgumentException e) { 
      e = null;      
    }
    assertEquals("number", PoemGvisType.DISPLAYLEVEL.gvisJsonTypeName());
    assertEquals("number", PoemGvisType.SEARCHABILITY.gvisJsonTypeName());
    assertEquals("number", PoemGvisType.INTEGRITYFIX.gvisJsonTypeName());
    
  }

  public void testJsonValue() {
    assertEquals("\"1\"", PoemGvisType.TROID.jsonValue(new Integer(1)));
    assertEquals("1.0", PoemGvisType.DOUBLE.jsonValue(new Double(1)));
    assertEquals("1", PoemGvisType.LONG.jsonValue(new Long(1)));
    assertEquals("1", PoemGvisType.BIGDECIMAL.jsonValue(new BigDecimal(1)));
    assertEquals("true", PoemGvisType.DELETED.jsonValue(true));

    for (PoemGvisType type : PoemGvisType.values()) {
      assertEquals("null", type.jsonValue(null));
    }
  }
  
  public void testExcerciseHiddenGeneratedCode() { 
    for (PoemGvisType type : PoemGvisType.values()) {
      assertEquals("null", type.jsonValue(null));
    }
    for (PoemGvisType type : PoemGvisType.values()) {
      assertEquals(type, PoemGvisType.valueOf(PoemGvisType.class, type.name()));
    }
    for (PoemGvisType type : PoemGvisType.values()) {
      for (PoemGvisType type2 : PoemGvisType.values()) {
        if (type.compareTo(type2) > 0)
         assertTrue(type.ordinal() > type2.ordinal());
      }
    }
    assertTrue(PoemGvisType.class.isEnum()); 

    for (Method m : PoemGvisType.class.getMethods()) {
      System.out.println(m.toGenericString());
    }
  }

}
