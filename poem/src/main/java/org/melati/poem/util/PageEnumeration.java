package org.melati.util;

import java.util.Enumeration;

public interface PageEnumeration extends Enumeration {

  /**
   * The start record of the page, indexed from 1.
   */

  int getPageStart();

  /**
   * The end record of the page, indexed from 1.
   */

  int getPageEnd();
  int getTotalCount();
  boolean getTotalCountIsMinimum();

  /**
   * The start record of the previous page, indexed from 1.
   */

  Integer getPrevPageStart();

  /**
   * The start record of the next page, indexed from 1.
   */

  Integer getNextPageStart();
}
