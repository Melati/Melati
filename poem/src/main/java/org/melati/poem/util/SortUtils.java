package org.melati.util;

import java.util.*;

public class SortUtils {
  public static void swap(Object[] arr, int i, int j) {
    Object t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
  }

  public static void insertionSort(Order cmp, Object[] arr) {
    for (int i = 1; i < arr.length; ++i) {
      Object val_i = arr[i];
      if (!cmp.lessOrEqual(arr[i-1], val_i)) {
        int j = i - 1;
        arr[i] = arr[j];
        while (j >= 1 && !cmp.lessOrEqual(arr[j-1], val_i)) {
          arr[j] = arr[j-1];
          --j;
        }
        arr[j] = val_i;
      }
    }
  }

  /**
   * This is nicked from ocaml 2.03's Sort.array, in turn derived from
   * Sedgewick.  ocaml is a superb object/functional language from INRIA: see
   * http://caml.inria.fr.
   */

  private static void partlyQSort(Order cmp, Object[] arr, int lo, int hi) {
    if (hi - lo >= 6) {
      int mid = (lo + hi) >> 1;
      
      /* Select median value from among LO, MID, and HI. Rearrange
         LO and HI so the three values are sorted. This lowers the
         probability of picking a pathological pivot.  It also
         avoids extra comparisons on i and j in the two tight "while"
         loops below. */

      if (cmp.lessOrEqual(arr[mid], arr[lo]))
        swap(arr, mid, lo);
      if (cmp.lessOrEqual(arr[hi], arr[mid])) {
        swap(arr, mid, hi);
        if (cmp.lessOrEqual(arr[mid], arr[lo]))
          swap(arr, mid, lo);
      }

      Object pivot = arr[mid];
      int i = lo + 1;
      int j = hi - 1;
      while (i < j) {
        while (!cmp.lessOrEqual(pivot, arr[i])) ++i;
        while (!cmp.lessOrEqual(arr[j], pivot)) --j;
        if (i < j)
          swap(arr, i, j);
        ++i;
        --j;
      }

      /* Recursion on smaller half, tail-call on larger half */

      if (j - lo <= hi - i) {
        partlyQSort(cmp, arr, lo, j);
        partlyQSort(cmp, arr, i, hi);
      }
      else {
        partlyQSort(cmp, arr, i, hi);
        partlyQSort(cmp, arr, lo, j);
      }
    }
  }

  public static void qsort(Order cmp, Object[] arr) {
    partlyQSort(cmp, arr, 0, arr.length - 1);
    /* Finish sorting by insertion sort */
    insertionSort(cmp, arr);
  }

  public static Object[] sorted(Order cmp, Object[] arr) {
    Object[] arr2 = (Object[])arr.clone();
    qsort(cmp, arr2);
    return arr2;
  }

  public static Object[] sorted(Order cmp, Vector v) {
    Object[] arr = ArrayUtils.arrayOf(v);
    qsort(cmp, arr);
    return arr;
  }

  public static Object[] sorted(Order cmp, Enumeration e) {
    return sorted(cmp, EnumUtils.vectorOf(e));
  }

  public static void main(String[] args) throws Exception {
    qsort(DictionaryOrder.vanilla, args);
    for (int i = 0; i < args.length; ++i)
      System.out.println(args[i]);
  }
}
