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

/*
@Author Vasily Pozhidaev <voodoo@knastu.ru; vpozhidaev@mail.ru>
this source based on RFC 1867 which describes format 
for uploading files multipart/from-data
http://www.ietf.org/rfc/rfc1867.txt
tested on IE 5.0, HotJava 3.0, Netscape Navigator 4.x
*/

public class MultipartDataDecoder {
    
    private int maxSize = 1024*1024;
    BufferedInputStream in;
    String contentType;
    Hashtable fields = null;
              
    public MultipartDataDecoder (InputStream in, String contentType) {
        this.in=new BufferedInputStream(in, maxSize);
        this.contentType=contentType;
    }
    
     //when (maxSize=-1) then content size is not limited
    public MultipartDataDecoder (InputStream in, String contentType, int maxSize) {
        this.maxSize=maxSize;
        this.in=new BufferedInputStream(in, maxSize);
        this.contentType=contentType;
    }
    
    public Hashtable parseData() throws IOException {
        try {
            return parseData(in, contentType, maxSize);
        } catch (IOException e) {
            throw e;
        }
    }
    
    private Hashtable parseData(BufferedInputStream in, String contentType, int maxSize)
    throws IOException {
        /*if(!contentType.toLowerCase().substring(0,19).equals("multipart/form-data"))
            throw new IOException("Non multipart content");*/
        ByteArrayOutputStream buf = new ByteArrayOutputStream(maxSize);
        String boundary;
        int ch=-1;
        int count=0;
        try {
            while ((ch = in.read()) !=-1 && (maxSize!=-1 && count<=maxSize)) {
                buf.write(ch);
                count++;
        } } catch (IOException e) {
            buf.close();
            if(ch==-1)
                throw new IOException("IO error");
        } finally {
            if(count>maxSize)
                throw new IOException("Maximum size exceeded");
        }
        try {
            byte[] data=buf.toByteArray();
            boundary=getBoundary(data, contentType);
            return getFields(data, boundary);
        } catch (IOException e) {
            throw e;
        } finally {
            buf.close();    
        }        
    }
    
    //divide input data into parts and get info about each part
    private Hashtable getFields(byte[] data, String boundary) 
    throws IOException {
        int i = 0, index = 0, prev_index = 0;
        Hashtable fields = new Hashtable();
        FormField field;
        //start parsing data
        while((index = indexOf(data, boundary.getBytes(), index)) != -1) {
            if(i!=0) {
                try {
                    field=getField(data, prev_index, index-2);
                    fields.put(field.getFieldName(), field);
                } catch (IOException e) {
                    throw new IOException("Parsing error");
                }
            }
            index+=boundary.length();
            prev_index=index;
            i++;
        }
        data=null;
        return fields;
    }
    
    private FormField getField(byte[] data, int startIndex, int endIndex) 
    throws IOException {
        // header separated from content by this codes
        // {13,10,13,10}
        byte[] ch = {13,10,13,10};
        FormField field = new FormField();
        String header;
        //parsing header
        int index = indexOf(data, ch, startIndex);
        if (index != -1) {
            header = new String(data, startIndex, index-startIndex);
            field.setContentDisposition(extractField(header, "content-disposition:", ";"));
            String fieldName=extractField(header, "name=",";");
            try {
                if(fieldName.charAt(0)=='\"')
                    fieldName=fieldName.substring(1,fieldName.length()-1);
            } catch (Exception e) { }
            field.setFieldName(fieldName);
            String fileName=extractField(header, "filename=", ";");
            try {
                if(fileName.charAt(0)=='\"')
                    fileName=fileName.substring(1,fileName.length()-1);
            } catch (Exception e) { }
            field.setFileName(fileName);
            field.setContentType(extractField(header, "content-type:", ";"));
        }
        startIndex    = index + 4; /*as since ch.length=4*/
        //content separated from boundary by {'\r','\n'}
        byte[] fieldContent = new byte[endIndex-startIndex];
        System.arraycopy(data, startIndex, fieldContent, 0, fieldContent.length);
        field.setData(fieldContent);
        return field;
    }
    
    // extract boundary from header or from data
    private String getBoundary(byte[] data, String str)
    throws IOException {
        String boundary="";
        int index;
        // 9 - number of symbols in "boundary="
        if ((index=str.lastIndexOf("boundary="))!=-1) {
            boundary = str.substring(index + 9);
            //as since real boundary two chars '-' longer 
            //than written in CONTENT_TYPE header
            boundary = "--" + boundary;
        } else {
            //HotJava does not send boundary within CONTENT_TYPE header:
            //and as I seen HotJava have errors with sending binary data
            int begin = 0, end = 0;
            byte[] str1={45,45}, str2={13,10};
            begin=indexOf(data, str1, 0);
            end=indexOf(data, str2, begin);
            //Boundary length should be in reasonable limits
            if(begin!=-1 && end!=-1 && ((end - begin)>5 && (end - begin) < 100)) {
                byte[] buf=new byte[end - begin];
                System.arraycopy(data, begin, buf, 0, end - begin);
                boundary = new String(buf);
            } else {
                throw new IOException("Boundary not found");
            }
        }
        return boundary;
    }

    protected String extractField(String header, String lBound, String rBound) {
        String lheader=header.toLowerCase();
        int begin=0, end=0;
        begin=lheader.indexOf(lBound);
        if(begin==-1)
            return "";
        begin=begin+lBound.length();
        end=lheader.indexOf(rBound, begin);
        if(end==-1)
            end=lheader.indexOf("\r\n", begin);
        if(end==-1)
            end=lheader.length();
        return header.substring(begin, end).trim();
    }
    
    //indexOf for byte arrays
    public int indexOf(byte[] data1, byte[] data2, int fromIndex) {
        byte[] v1 = data1;
        byte[] v2 = data2;
        int max = data1.length - data2.length;
        if (fromIndex >= data1.length) {
            if (data1.length == 0 && fromIndex == 0 && data2.length == 0)
                return 0;
            return -1;
        }
        if (fromIndex < 0)
            fromIndex = 0;
        if (data2.length == 0)
            return fromIndex;
        byte first  = v2[0];
        int i = fromIndex;

        startSearchForFirstByte:
        while (true) {
            while (i <= max && v1[i] != first)
                i++;
            if (i > max)
                return -1;
            int j = i + 1;
            int end = j + data2.length - 1;
            int k = 1;
            while (j < end) {
                if (v1[j++] != v2[k++]) {
                    i++;
                    continue startSearchForFirstByte;
                }
            }
        return i;    
        }
    }
}

