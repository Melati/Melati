/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 Vasily Pozhidaev
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
 *     Vasily Pozhidaev <voodoo@knastu.ru; vpozhidaev@mail.ru>
 *     http://paneris.org/
 */

package org.melati.servlet;

import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * Holds information parsed from a multipart/form-data file upload
 */

/*
@Author Vasily Pozhidaev 
this source based on RFC 1867 which describes format 
for uploading files multipart/from-data
http://www.ietf.org/rfc/rfc1867.txt
tested on IE 5.0, HotJava 3.0, Netscape Navigator 4.x
*/
public class MultipartFormField
{
    private String contentDisposition="";
    private String fieldName="";
    private String filePath="";
    private String contentType="";
    private FormDataAdaptor adaptor = null;

    /** Some constants */
    public static final long KILOBYTE = 1024;
    public static final long MEGABYTE = 1024 * 1024;
    public static final long GIGABYTE = 1024 * 1024 * 1024;

    /** The path to a local file */
    private File localFile = null;

    /** The url to this file */
    String url = null;

    public MultipartFormField() {}
    
    public MultipartFormField(String contentDisposition,
                              String fieldName,
                              String filePath,
                              String contentType,
                              FormDataAdaptor adaptor) {
        this.contentDisposition=contentDisposition;
        this.fieldName=fieldName;
        this.filePath=filePath;
        this.contentType=contentType;
        this.adaptor=adaptor;
    }
    
    /*
     * Mime information
     */
    public void setContentType(String contentType) {
        this.contentType=contentType;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition=contentDisposition;
    }

    public String getContentDisposition() {
        return contentDisposition;
    }

    public void setFieldName(String fieldName) {
        this.fieldName=fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
    
    public void setUploadedFilePath(String filePath) {
        this.filePath=filePath;
    }

    public String getUploadedFilePath() {
      return filePath;
    }

    public String getUploadedFileName() {
      try {
        return filePath.substring(((filePath.lastIndexOf("\\")!=-1)
                                    ? filePath.lastIndexOf("\\")
                                    : filePath.lastIndexOf("/"))+1);
      }
      catch (Exception e) {
        return "";
      }
    }

    /*
     * Work with an uploaded file/stored value
     * <p>
     * We can store uploaded files or values in different ways depending
     * on which adaptor we use.
     */
    public void setFormDataAdaptor(FormDataAdaptor adaptor) {
      this.adaptor = adaptor;
    }

    /**
     * @return the saved data as a byte array
     */
    public byte[] getData() {
      return (adaptor != null) ? adaptor.getData() : new byte[0];
    }

    /**
     * @return the saved data as a string
     */

   public String getDataString() {
      return new String(getData());
    }

    /**
     * @return the saved data as a string using the encoding supplied
     */
    public String getDataString(String enc) {
      if (enc == null || enc.equals(""))
        return getDataString();
      try {
        return new String(getData(), enc);
      }
      catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return getDataString();
      }
    }

    /**
     * @return the length of the data
     */
    public long getDataSize() {
      return (adaptor != null) ? adaptor.getSize() : 0;
    }

    /**
     * @return The data saved as a file (or null if it is not saved)
     */
    public File getDataFile() {
      return (adaptor != null) ? adaptor.getFile() : null;
    }

    /**
     * @return A URL to the saved data (or null if no such URL exists)
     */
    public String getDataURL() {
      return (adaptor != null) ? adaptor.getURL() : null;
    }

  /**
   * The size of the file as a formatted string
   */
  public String getPrettyDataSize() {
    long size = 0;
    try {
      size = getDataSize();
    }
    catch (Exception e) {
      return "Unknown";
    }
    String sizeString = null;
    if ((size / KILOBYTE) >= 1) {
      if ((size / MEGABYTE) >= 1) {
        if ((size / GIGABYTE) >= 1)
          sizeString = (size / GIGABYTE) + " Gb";
        else
          sizeString = (size / MEGABYTE) + " Mb";
      } else {
        sizeString = (size / KILOBYTE) + " Kb";
      }
    } else {
      sizeString = size + " bytes";
    }
    return sizeString;
  }

}
