package org.melati;

/**
 * A subset of the elements of some table: all of those for which
 * certain fields take certain fixed values.  <I>I.e.</I>
 * <TT><I>table</I> WHERE <I>f</I> = <I>v</I> AND <I>g</I> =
 * <I>w</I></TT> ...
 */

public interface Subset {

  /**
   * What this actually does, cacheing aside, is set the defining
   * fields of <TT>record</TT> to the defining values.
   */

  public void claim(Record record);

  /**
   * The current members of the subset.
   */

  public Enumeration elements();
}
