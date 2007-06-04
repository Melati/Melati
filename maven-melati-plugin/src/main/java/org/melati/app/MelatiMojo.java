/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2006 Tim Pizey
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
 *     Tim Pizey <timp At paneris.org>
 *     http://paneris.org/~timp
 */
package org.melati.app;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Goal which runs a Melati command.
 * 
 * @requiresDependencyResolution runtime
 * @goal run
 */
public class MelatiMojo extends AbstractMojo {
  /**
   * The maven project.
   *
   * @parameter expression="${executedProject}"
   * @required
   * @readonly
   */
  private MavenProject project;
  
  /**
   * Location of the output directory.
   * 
   * @parameter expression="${project.build.directory}"
   * @required
   */
  private File outputDirectory;

  /**
   * Name of the output file.
   * 
   * @parameter expression="output.txt"
   * @required
   */
  private String outputFile;

  /**
   * Application name.
   * 
   * @parameter expression="TemplateAppExample"
   * @required
   */
  private String appName;

  /**
   * Database name.
   * 
   * @parameter expression="melatitest"
   * @required
   */
  private String db;

  /**
   * Database table.
   * 
   * @parameter expression="user"
   * @required
   */
  private String table;

  /**
   * Table row id (troid).
   * 
   * @parameter expression="0"
   * @required
   */
  private String troid;

  /**
   * Method.
   * 
   * @parameter expression="Main"
   * @required
   */
  private String method;

  /**
   * The directory containing generated classes.
   *
   * @parameter expression="${project.build.outputDirectory}"
   * @required
   * 
   */
  private File classesDirectory;
  
  
  public File getClassesDirectory()
  {
      return this.classesDirectory;
  }


  
  public void execute()
      throws MojoExecutionException {
    File f = outputDirectory;

    if (!f.exists()) {
      f.mkdirs();
    }
    App app;
    try {
      app = (App)instanceOfNamedClass(appName, "org.melati.app.App");
    } catch (InstantiationException e) {
      throw new MojoExecutionException("Could not load main class. Terminating", e);
    }
    File out = new File(f, outputFile);
    try {
      out.createNewFile();
    } catch (IOException e) {
      throw new MojoExecutionException("Could not create output file: " + outputFile, e);
    }
    try {
      app.setOutput(new PrintStream(out));
    } catch (FileNotFoundException e) {
      throw new MojoExecutionException("Could not find created output file: " + outputFile, e);
    }
    app.run(new String[] {db,  table, troid, method});
  }
  /**
   * Instantiate an interface.
   * 
   * @param className the name of the Class
   * @param interfaceName the name of the interface Class
   * @return a new object
   * @throws InstantiationException 
   *   if the named class does not descend from the interface
   */
  public static Object instanceOfNamedClass(String className, String interfaceName)
      throws InstantiationException {
    try {
      Class clazz = Class.forName(className);
      Class interfaceRequired = Class.forName(interfaceName);
      if (!interfaceRequired.isAssignableFrom(clazz))
        throw new ClassCastException(
            clazz + " is not descended from " + interfaceName);
      return clazz.newInstance();
    }
    catch (Exception e) {
      throw new 
          InstantiationException(
              "Error instantiating " + className + ": " + e.toString());
    }
  }
  public MavenProject getProject()
  {
      return this.project;
  }

  private List getDependencyFiles ()
  {
      List dependencyFiles = new ArrayList();
      for ( Iterator iter = getProject().getArtifacts().iterator(); iter.hasNext(); )
      {
          Artifact artifact = (Artifact) iter.next();
          // Include runtime and compile time libraries, and possibly test libs too
          if (((!Artifact.SCOPE_PROVIDED.equals(artifact.getScope())) && (!Artifact.SCOPE_TEST.equals( artifact.getScope()))))
          {
              dependencyFiles.add(artifact.getFile());
              getLog().debug( "Adding artifact " + artifact.getFile().getName() + " for WEB-INF/lib " );   
          }
      }
      return dependencyFiles; 
  }
  
  
  private List setUpClassPath()
  {
      List classPathFiles = new ArrayList();       
      
      if (getClassesDirectory() != null)
          classPathFiles.add(getClassesDirectory());
      
      //now add all of the dependencies
      classPathFiles.addAll(getDependencyFiles());
      
      if (getLog().isDebugEnabled())
      {
          for (int i = 0; i < classPathFiles.size(); i++)
          {
              getLog().debug("classpath element: "+ ((File) classPathFiles.get(i)).getName());
          }
      }
      return classPathFiles;
  }

  
}
