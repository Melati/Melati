/**
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Myles Chippendale
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
 *     Myles Chippendale <mylesc@paneris.org>
 *     http://paneris.org/
 *     29 Stanley Road, Oxford, OX4 1QY, UK
 *
 * Based on code by
 *   Vasily Pozhidaev <voodoo@knastu.ru; vpozhidaev@mail.ru>
 * */

package org.melati.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import org.melati.Melati;
import org.melati.util.DelimitedBufferedInputStream;

/**
 * Parses a multipart/form-data request into its different
 * fields, saving any files it finds along the way.
 */
public class MultipartFormDataDecoder {

  private static int MAX_SIZE = 2048;
  private int maxSize;
  private Melati melati = null;
  DelimitedBufferedInputStream in;
  String contentType;
  FormDataAdaptorFactory factory;
  Hashtable<String,MultipartFormField> fields = new Hashtable<String,MultipartFormField>();

  private static final int FIELD_START = 0;
  private static final int IN_FIELD_HEADER = 1;
  private static final int IN_FIELD_DATA = 2;
  private static final int STOP = 3;

  private int state = FIELD_START;

  /**
   * Constructor with default maximum size.
   *
   * @param melati      The {@link Melati}
   * @param in          An {@link InputStream} from which to read the data
   * @param contentType A valid MIME type
   * @param factory     A {@link FormDataAdaptorFactory} to determine how to 
   *                    store the object's data
   */
  public MultipartFormDataDecoder(Melati melati,
                              InputStream in,
                              String contentType,
                              FormDataAdaptorFactory factory) {
    this(melati,in, contentType, factory, MAX_SIZE);
  }

  /**
   * Constructor specifying maximum size.
   *
   * @param melati      The {@link Melati}
   * @param in          An {@link InputStream} from which to read the data
   * @param contentType A valid MIME type
   * @param factory     A {@link FormDataAdaptorFactory} to determine how to 
   *                    store the object's data
   * @param maxSize     The maximum size of the data
   */
  public MultipartFormDataDecoder(Melati melati,
                              InputStream in,
                              String contentType,
                              FormDataAdaptorFactory factory,
                              int maxSize) {
    this.melati = melati;
    this.in = new DelimitedBufferedInputStream(in, maxSize);
    this.contentType = contentType;
    this.factory = factory;
    this.maxSize = maxSize;
  }

 /**
  * Parse the uploaded data into its constituents. 
  * 
  * @return a <code>Hashtable</code> of the constituents
  * @throws IOException
  *         if an error occurs reading the input stream
  */
  public Hashtable<String,MultipartFormField> parseData() throws IOException {
    try {
      return parseData(in, contentType, maxSize);
    }
    catch (IOException e) {
      throw e;
    }
    finally {
      in.close();
      in = null;
    }
  }

  private Hashtable<String,MultipartFormField> parseData(DelimitedBufferedInputStream inP,
                              String contentTypeP,
                              int maxSizeP)
      throws IOException {
    String boundary = getBoundary(contentTypeP);
    String line;
    String header = "";
    MultipartFormField field = null;
    byte[] CRLF = {13,10};
    byte[] buff = new byte[maxSizeP];
    int count;

    while (state != STOP) {

       // Look for the start of a field (a boundary)
      if (state == FIELD_START) {
        count = inP.readToDelimiter(buff, 0, buff.length, boundary.getBytes());
        if (count == buff.length) {
          throw new IOException(
              "Didn't find a boundary in the first " 
              + buff.length + " bytes");
        }
        count = inP.readToDelimiter(buff, 0, buff.length, CRLF);
        line = new String(buff, 0, count);

        if (line.equals(boundary)) {
          state = IN_FIELD_HEADER;
          header = "";
          if (inP.read(buff, 0, 2) != 2) // snarf the crlf
            throw new IOException(
                "Boundary wasn't followed by 2 bytes (\\r\\n)");
        }
        else if (line.equals(boundary+"--")) {
          state = STOP;
        }
        else
          throw new IOException(
              "Didn't find the boundary I was expecting before a field");
      }
      
       // Read headers (i.e. until the first blank line)
      if (state == IN_FIELD_HEADER) {
        count = inP.readToDelimiter(buff, 0, buff.length, CRLF);
        if (count != 0)     // a header line
          header += new String(buff, 0, count) + "\r\n";
        else {              // end of headers
          state = IN_FIELD_DATA;
          field = new MultipartFormField();
          readField(field, header);
          fields.put(field.getFieldName(), field);
        }
        if (inP.read(buff, 0, 2) != 2) // snarf the crlf
          throw new IOException(
              "Header line wasn't followed by 2 bytes (\\r\\n)");
      } 

       // Read data (i.e. until the next boundary);
      if (state == IN_FIELD_DATA) {
        String dataBoundary = "\r\n" + boundary;

        // get an adaptor to save the field data 
        FormDataAdaptor adaptor = null;
        // Field should never be null but eclipse doesn't know that 
        if (field != null && field.getUploadedFileName().equals("")) { // no file uploaded
          adaptor = new MemoryFormDataAdaptor(); // store data in memory
        }
        else {
          adaptor = factory.get(melati, field); 
        }
        adaptor.readData(field, inP, dataBoundary.getBytes());
        // Field should never be null but eclipse doesn't know that 
        if (field != null) field.setFormDataAdaptor(adaptor);
        state = FIELD_START;
      }

    }  // end of while (state != STOP)
    return fields;
  }

  private void readField(MultipartFormField field, String header) {
    field.setContentDisposition(extractField(header, 
                                             "content-disposition:", ";"));
    String fieldName = extractField(header, "name=",";");
    if (fieldName.length() != 0) {
      if(fieldName.charAt(0) == '\"')
        fieldName = fieldName.substring(1, fieldName.length() - 1);
      field.setFieldName(fieldName);
    }
    String fileName = extractField(header, "filename=", ";");
    if(fileName.length() != 0) {
      if(fileName.charAt(0) == '\"')
        fileName = fileName.substring(1, fileName.length()-1);
      field.setUploadedFilePath(fileName);
    }
    field.setContentType(extractField(header, "content-type:", ";"));
  }

 /**
  * Extract a String from header bounded by lBound and either: 
  * rBound or a "\r\n"
  * or the end of the String.
  *
  * @param header The field metadata to read
  * @param lBound Where to start reading from 
  * @param rBound where to stop reading
  * @return The substring required
  */
  protected String extractField(String header, String lBound, 
                                String rBound) {
    String lheader = header.toLowerCase();
    int begin = 0, end = 0;
    begin = lheader.indexOf(lBound);
    if(begin == -1)
      return "";
    begin = begin + lBound.length();
    end = lheader.indexOf(rBound, begin);
    if(end == -1)
      end = lheader.indexOf("\r\n", begin);
    if(end == -1)
      end = lheader.length();
    return header.substring(begin, end).trim();
  }

 /**
  * Extract boundary from the header.
  * No longer attempts to get it from the data.
  * 
  */
  private String getBoundary(/*byte[] data, */ String header) 
      throws IOException {
    String boundary="";
    int index;
    // 9 - number of symbols in "boundary="
    if ((index = header.lastIndexOf("boundary=")) != -1) {
       boundary = header.substring(index + 9);
       // as since real boundary two chars '-' longer 
       // than written in CONTENT_TYPE header
       boundary = "--" + boundary;
    } else {

/*
       // HotJava does not send boundary within CONTENT_TYPE header:
       // and as I seen HotJava has errors with sending binary data.
       int begin = 0, end = 0;
       byte[] str1 = {45, 45}, str2 = {13, 10};
       begin = indexOf(data, str1, 0);
       end = indexOf(data, str2, begin);

       // Boundary length should be in reasonable limits
       if (begin != -1 && end != -1 && 
           ((end - begin) > 5 && 
           (end - begin) < 100)) {
         byte[] buf = new byte[end - begin];
         System.arraycopy(data, begin, buf, 0, end - begin);
         boundary = new String(buf);
       } else {
         throw new IOException("Boundary not found");
       }
*/
      throw new IOException("Boundary not found in header");
    }
     return boundary;
  }

}



    



