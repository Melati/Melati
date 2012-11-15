package org.melati.admin;

public class PoemGvisTypeConverter {

  public static  String convert(int poemType){
    if (PoemGvisType.from(poemType) == null) // eg table types 
      return "string";
    else
      return PoemGvisType.from(poemType).gvisJsonTypeName();
  }
  
}
