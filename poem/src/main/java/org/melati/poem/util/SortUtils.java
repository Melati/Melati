/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.poem.util;

import java.util.Vector;
import java.util.Enumeration;

/**
 * An assortment of useful sorting operations.
 */
public final class SortUtils {

  private SortUtils() {}

  /**
   * Swap two elements of an Array.
   * @param arr the Array
   * @param i will become j
   * @param j will become i
   */
  public static void swap(Object[] arr, int i, int j) {
    Object t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
  }

  /**
   * Sort an Array by a supplied ordering.
   * @param cmp an ordering
   * @param arr the Array to sort
   */
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
   * Sedgewick.  ocaml is a superb object/functional language from INRIA: 
   * see http://caml.inria.fr/ .
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

  /**
   * Quick sort an array.
   * @param cmp ordering to use
   * @param arr Array to sort 
   */
  public static void qsort(Order cmp, Object[] arr) {
    partlyQSort(cmp, arr, 0, arr.length - 1);
    /* Finish sorting by insertion sort */
    insertionSort(cmp, arr);
  }

  /**
   * Return a new sorted Array.
   * @param cmp the ordering
   * @param arr the Array to sort
   * @return the sorted Array
   */
  public static Object[] sorted(Order cmp, Object[] arr) {
    Object[] arr2 = (Object[])arr.clone();
    qsort(cmp, arr2);
    return arr2;
  }

  /**
   * Sort a Vector into a new Array.
   * @param cmp the ordering
   * @param v Vector to sort
   * @return an Array of the sorted Vector's Elements 
   */
  public static Object[] sorted(Order cmp, Vector<Object> v) {
    Object[] arr = ArrayUtils.arrayOf(v);
    qsort(cmp, arr);
    return arr;
  }

  /**
   * Sort an Enumeration into an Array.
   * @param cmp the ordering
   * @param e the Enumeration to sort
   * @return an Array of the sorted Enumeration's Elements 
   */
  public static Object[] sorted(Order cmp, Enumeration e) {
    return sorted(cmp, EnumUtils.vectorOf(e));
  }

}
