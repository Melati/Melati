package org.melati.admin;

public class PoemGvisTypeConverter {

  public static  String convert(int poemType){
    if (poemType > 0)
      return "string";
    else
      return PoemGvisType.from(poemType).gvisJsonTypeName();
  }
  
}
