package org.melati.poem;

/**
 * A dummy class, used to work out where to get Poem.dsd from, 
 * instead of a circular refernce. 
 * 
 * Note that Poem.dsd is a symbolic link into the poem module. 
 *  
 * @author timp
 * @since 2011-08-22
 */
public class PoemDatabase {
  PoemDatabase() { 
    throw new RuntimeException("Instaniation of a dummy class");
  }

}
