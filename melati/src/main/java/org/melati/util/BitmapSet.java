package org.melati.util;

public class BitmapSet implements Cloneable {
  private int[] flags;

  private BitmapSet(BitmapSet other) {
    flags = (int[])other.flags.clone();
  }

  public BitmapSet(int capacity) {
    flags = new int[(capacity + 31) / 32];
  }

  public synchronized void set(int n) {
    int i = n / 32;

    if (flags.length <= i) {
      int[] newFlags = new int[Math.max(i * 2, flags.length * 2)];
      System.arraycopy(flags, 0, newFlags, 0, flags.length);
      flags = newFlags;
    }

    flags[i] |= 1 << i % 32;
  }

  public boolean get(int n) {
    int[] flags = this.flags;
    int i = n / 32;
    return flags.length <= i ? false : (flags[i] & (1 << i % 32)) != 0;
  }

  public Object clone() {
    return new BitmapSet(this);
  }
}
