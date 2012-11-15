package org.melati.admin;

import java.lang.IllegalArgumentException;
import java.util.HashMap;
import java.util.Map;

import org.melati.poem.PoemTypeFactory;


/**
 * @author timp
 * @since 13 Nov 2012
 * see com.google.visualization.datasource.datatable.value.ValueType
 */
public enum PoemGvisType {
  TROID(PoemTypeFactory.TROID.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "number";
    }

    @Override
    public
    String jsonValue(Object value) { 
      if (value == null )
        return "null";
      else
        return "\"" + value.toString() + "\"";
    }
  },
  DELETED(PoemTypeFactory.DELETED.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "boolean";
    }
    public String jsonValue(Object value) { 
      if (value == null )
        return "null";
      else
        return value.toString();
    }
  },
  TYPE(PoemTypeFactory.TYPE.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "number";
    }
  },
  
  /* Base type factories. */
   BOOLEAN(PoemTypeFactory.BOOLEAN.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "boolean";
    }
  },
   INTEGER(PoemTypeFactory.INTEGER.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "number";
    }

    @Override
    public
    String jsonValue(Object value) { 
      if (value == null )
        return "null";
      else
        return value.toString();
    }
  },
   DOUBLE(PoemTypeFactory.DOUBLE.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "number";
    }
    @Override
    public String jsonValue(Object value) { 
      if (value == null )
        return "null";
      else
        return value.toString();
    }
  },
   LONG(PoemTypeFactory.LONG.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "number";
    }
    @Override
    public String jsonValue(Object value) { 
      if (value == null )
        return "null";
      else
        return value.toString();
    }
  },
   BIGDECIMAL(PoemTypeFactory.BIGDECIMAL.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "number";
    }
    @Override
    public String jsonValue(Object value) { 
      if (value == null )
        return "null";
      else
        return value.toString();
    }
  },
   STRING(PoemTypeFactory.STRING.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "string";
    }
  },
   PASSWORD(PoemTypeFactory.PASSWORD.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "string";
    }
  },
   DATE(PoemTypeFactory.DATE.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "date";
    }
  },
   TIMESTAMP(PoemTypeFactory.TIMESTAMP.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "datetime";
    }
  },
   TIME(PoemTypeFactory.TIME.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "timeofday";
    }
  },
   BINARY(PoemTypeFactory.BINARY.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      throw new IllegalArgumentException("Binary poem type cannot be exported to google viualisation API.");
    }
  },

   /** Poem factories. */
   DISPLAYLEVEL(PoemTypeFactory.DISPLAYLEVEL.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "number";
    }
  },
   SEARCHABILITY(PoemTypeFactory.SEARCHABILITY.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "number";
    }
  },
   INTEGRITYFIX(PoemTypeFactory.INTEGRITYFIX.getCode()) {
    @Override
    public
    String gvisJsonTypeName() {
      return "number";
    }
  };
  
  final Integer poemType;
  PoemGvisType(Integer poemType) { 
    this.poemType = poemType;
  }
  
  public Integer getPoemType(){ 
    return poemType;
  }
  public PoemTypeFactory getPeomTypeFactory() { 
    return PoemTypeFactory.forCode(null, poemType);
  }
  
  public abstract String gvisJsonTypeName(); 
  
  public String jsonValue(Object value) { 
    if (value == null )
      return "null";
    else
      return "\"" + value.toString() + "\"";
  }
  
  public static PoemGvisType from(Integer poemType){ 
    return typeCodeToPoemGvisType.get(poemType);
  }
  private static Map<Integer, PoemGvisType> typeCodeToPoemGvisType;
  static {
    typeCodeToPoemGvisType = new HashMap<Integer,PoemGvisType>();
    for (PoemGvisType type : PoemGvisType.values()) {
      typeCodeToPoemGvisType.put(type.poemType, type);
    }
  }

}
