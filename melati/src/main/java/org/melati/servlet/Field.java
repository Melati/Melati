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
@Author Vasily Pozhidaev 
this source based on RFC 1867 which describes format 
for uploading files multipart/from-data
http://www.ietf.org/rfc/rfc1867.txt
tested on IE 5.0, HotJava 3.0, Netscape Navigator 4.x
*/
public class Field
{
	private String contentDisposition="", fieldName="", fileName="", contentType="";
	private byte[] data=null;
	
	public Field() {
	}
	
	public Field(String contentDisposition, String fieldName, String fileName,
				 String contentType, byte[] data) {
		this.contentDisposition=contentDisposition;
		this.fieldName=fieldName;
		this.fileName=fileName;
		this.contentType=contentType;
		this.data=data;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public String getContentDisposition() {
		return contentDisposition;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public String getFileName() {
		try {
		return fileName.substring(((fileName.lastIndexOf("\\")!=-1) ? 
									fileName.lastIndexOf("\\") : fileName.lastIndexOf("/"))+1);
		} catch (Exception e) {
			return "";
		}
	}
	
	public String getFilePath() {
		try {
		return fileName.substring(0, ((fileName.lastIndexOf("\\")!=-1) ? 
									fileName.lastIndexOf("\\") : fileName.lastIndexOf("/"))+1);
		} catch (Exception e) {
			return "";
		}
	}
	
	public byte[] getData() {
		return data;
	}
	
	public int getContentLength() {
		return (int)data.length;
	}
	
	public void setContentType(String contentType) {
		this.contentType=contentType;
	}
	
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition=contentDisposition;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName=fieldName;
	}
	
	public void setFileName(String fileName) {
		this.fileName=fileName;
	}
	
	public void setData(byte[] data) {
		this.data=data;
	}
}


