/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use WebMacro on non-GPL terms,
 * contact me!
 */

package org.melati.util;

import java.io.*;
import java.util.*;

/**
 * This nifty facility was very kindly provided by
 *
 * <BLOCKQUOTE>
 * William B. Brogden<BR>
 * CIS: 75415,610<BR>
 * wbrogden@bga.com<BR>
 * http://www.bga.com/~wbrogden
 * </BLOCKQUOTE>
 *
 * He developed it from an algorithm published by Lawrence Philips in an
 * article entitled "Hanging on the Metaphone" Computer Language v7 n12,
 * December 1990, pp39-43.
 *
 * <P>
 *
 * It maps proper names onto a four-letter canonical pronunciation, so that you
 * can search for all names phonetically similar to the one the user typed in.
 * The matching is often considerably more discriminating than Soundex---very
 * useful for situations in which you know users will have a fair idea of the
 * name they are chasing, and will not be happy to see thirty or forty useless
 * suggestions coming back at them.
 */

public class Metaphone {

    static String vowels = "AEIOU" ;
    static String frontv = "EIY"   ;
    static String varson = "CSPTG" ;

    static final int maxCodeLen = 4 ;

    static public String pronunciation( String txt ){
      int mtsz = 0  ;
      boolean hard = false ;
      if(( txt == null ) ||
         ( txt.length() == 0 )) return "" ;
      // single character is itself
      if( txt.length() == 1 ) return txt.toUpperCase() ;
      //
      char[] inwd = txt.toUpperCase().toCharArray() ;
      //
      String tmpS ;
      StringBuffer local = new StringBuffer( 40 ); // manipulate
      StringBuffer code = new StringBuffer( 10 ) ; //   output
      // handle initial 2 characters exceptions
      switch( inwd[0] ){
        case 'K': case 'G' : case 'P' : /* looking for KN, etc*/
          if( inwd[1] == 'N')local.append(inwd, 1, inwd.length - 1 );
          else local.append( inwd );
          break;
        case 'A': /* looking for AE */
          if( inwd[1] == 'E' )local.append(inwd, 1, inwd.length - 1 );
          else local.append( inwd );
          break;
        case 'W' : /* looking for WR or WH */
          if( inwd[1] == 'R' ){   // WR -> R
            local.append(inwd, 1, inwd.length - 1 ); break ;
          }
          if( inwd[1] == 'H'){
            local.append(inwd, 1, inwd.length - 1 );
            local.setCharAt( 0,'W'); // WH -> W
          }
          else local.append( inwd );
          break;
        case 'X' : /* initial X becomes S */
          inwd[0] = 'S' ;local.append( inwd );
          break ;
        default :
          local.append( inwd );
      } // now local has working string with initials fixed
      int wdsz = local.length();
      int n = 0 ;
      while((mtsz < maxCodeLen ) && // max code size of 4 works well
            (n < wdsz ) ){
        char symb = local.charAt(n) ;
        // remove duplicate letters except C
        if(( symb != 'C' ) &&
           (n > 0 ) && ( local.charAt(n - 1 ) == symb )) n++ ;
        else{ // not dup
          switch( symb ){
            case 'A' : case 'E' : case 'I' : case 'O' : case 'U' :
              if( n == 0 ) { code.append(symb );mtsz++;
              }
              break ; // only use vowel if leading char
            case 'B' :
              if(!(n > 0 && local.charAt(n - 1) == 'M' &&
                   (n + 1 == wdsz ||
                    (n + 2 == wdsz && local.charAt(n + 1) == 'E')))) {
                   // not MB or MBE at end of word
                code.append(symb);
                mtsz++ ;
              }
              break ;
            case 'C' : // lots of C special cases
              /* discard if SCI, SCE or SCY */
              if( ( n > 0 ) &&
                  ( local.charAt(n-1) == 'S' ) &&
                  ( n + 1 < wdsz ) &&
                  ( frontv.indexOf( local.charAt(n + 1)) >= 0 )){ break ;}
              tmpS = local.toString();
              if( tmpS.indexOf("CIA", n ) == n ) { // "CIA" -> X
                 code.append('X' ); mtsz++; break ;
              }
              if( ( n + 1 < wdsz ) &&
                  (frontv.indexOf( local.charAt(n+1) )>= 0 )){
                 code.append('S');mtsz++; break ; // CI,CE,CY -> S
              }
              if(( n > 0) &&
                 ( tmpS.indexOf("SCH",n-1 )== n-1 )){ // SCH->sk
                 code.append('K') ; mtsz++;break ;
              }
              if( tmpS.indexOf("CH", n ) == n ){ // detect CH
                if((n == 0 ) &&
                   (wdsz >= 3 ) &&    // CH consonant -> K consonant
                   (vowels.indexOf( local.charAt( 2) ) < 0 )){
                     code.append('K');
                }
                else { code.append('X'); // CHvowel -> X
                }
                mtsz++;
              }
              else { code.append('K' );mtsz++;
              }
              break ;
            case 'D' :
              if(( n + 2 < wdsz )&&  // DGE DGI DGY -> J
                 ( local.charAt(n+1) == 'G' )&&
                 (frontv.indexOf( local.charAt(n+2) )>= 0)){
                    code.append('J' ); n += 2 ;
              }
              else { code.append( 'T' );
              }
              mtsz++;
              break ;
            case 'G' : // GH silent at end or before consonant
              if(( n + 2 == wdsz )&&
                 (local.charAt(n+1) == 'H' )) break ;
              if(( n + 2 < wdsz ) &&
                 (local.charAt(n+1) == 'H' )&&
                 (vowels.indexOf( local.charAt(n+2)) < 0 )) break ;
              tmpS = local.toString();
              if((n > 0) &&
                 ( tmpS.indexOf("GN", n ) == n)||
                 ( tmpS.indexOf("GNED",n) == n )) break ; // silent G
              if(( n > 0 ) &&
                 (local.charAt(n-1) == 'G')) hard = true ;
              else hard = false ;
              if((n+1 < wdsz) &&
                 (frontv.indexOf( local.charAt(n+1) ) >= 0 )&&
                 (!hard) ) code.append( 'J' );
              else code.append('K');
              mtsz++;
              break ;
            case 'H':
              if( n + 1 == wdsz ) break ; // terminal H
              if((n > 0) &&
                 (varson.indexOf( local.charAt(n-1)) >= 0)) break ;
              if( vowels.indexOf( local.charAt(n+1)) >=0 ){
                  code.append('H') ; mtsz++;// Hvowel
              }
              break;
            case 'F': case 'J' : case 'L' :
            case 'M': case 'N' : case 'R' :
              code.append( symb ); mtsz++; break ;
            case 'K' :
              if( n > 0 ){ // not initial
                if( local.charAt( n -1) != 'C' ) {
                     code.append(symb );
                     mtsz++ ;
                }
              }
              else   {
                code.append( symb ); // initial K
                mtsz++ ;
              }
              break ;
            case 'P' :
              if((n + 1 < wdsz) &&  // PH -> F
                 (local.charAt( n+1) == 'H'))code.append('F');
              else code.append( symb );
              mtsz++;
              break ;
            case 'Q' :
              code.append('K' );mtsz++; break ;
            case 'S' :
              tmpS = local.toString();
              if((tmpS.indexOf("SH", n )== n) ||
                 (tmpS.indexOf("SIO",n )== n) ||
                 (tmpS.indexOf("SIA",n )== n)) code.append('X');
              else code.append( 'S' );
              mtsz++ ;
              break ;
            case 'T' :
              tmpS = local.toString(); // TIA TIO -> X
              if((tmpS.indexOf("TIA",n )== n)||
                 (tmpS.indexOf("TIO",n )== n) ){
                    code.append('X'); mtsz++; break;
              }
              if( tmpS.indexOf("TCH",n )==n) break;
              // substitute numeral 0 for TH (resembles theta after all)
              if( tmpS.indexOf("TH", n )==n) code.append('0');
              else code.append( 'T' );
              mtsz++ ;
              break ;
            case 'V' :
              code.append('F'); mtsz++;break ;
            case 'W' : case 'Y' : // silent if not followed by vowel
              if((n+1 < wdsz) &&
                 (vowels.indexOf( local.charAt(n+1))>=0)){
                    code.append( symb );mtsz++;
              }
              break ;
            case 'X' :
              code.append('K'); code.append('S');mtsz += 2;
              break ;
            case 'Z' :
              code.append('S'); mtsz++; break ;
          } // end switch
          n++ ;
        } // end else from symb != 'C'
        if( mtsz > 4 )code.setLength( 4);
      }
      return code.toString();
    }

  public static void main(String[] args) throws Exception {
    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    Hashtable them = new Hashtable();
    for (String it = r.readLine(); it != null; it = r.readLine()) {
      String p = pronunciation(it);
      Vector v = (Vector)them.get(p);
      if (v == null) {
        them.put(p, v = new Vector());
        v.addElement(p);
      }
      v.addElement(it);
    }

    for (Enumeration e = them.elements(); e.hasMoreElements();) {
      Vector v = (Vector)e.nextElement();
      System.out.print(v.elementAt(0) + " ->");
      for (int i = 1; i < v.size(); ++i)
        System.out.print(" " + v.elementAt(i));
      System.out.println();
    }
  }
}
