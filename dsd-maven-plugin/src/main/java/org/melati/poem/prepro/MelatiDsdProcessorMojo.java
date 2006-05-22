package org.melati.poem.prepro;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;

/**
 * Process a DSD file.
 * 
 * @goal generate
 * @description Process a DSD file to generate java sources.
 * @phase generate-sources
 */
public class MelatiDsdProcessorMojo extends AbstractMojo {
  /**
   * Location of the dsd.
   * 
   * @parameter expression=
   */
  private String dsdPackage;
  /**
   * DSD file name.
   * 
   * @parameter expression=
   */
  private String dsdFile;
  /**
   * Location of the source.
   * 
   * @parameter expression="${project.build.sourceDirectory}"
   * @required
   */
  private File sourceDirectory;
  /**
   * @parameter expression="${project.groupId}"
   * @required
   */
  private String groupId;
  /**
   * @parameter expression="${project.artifactId}"
   * @required
   */
  private String artifactId;

  private String searchedLocations = "";

  public void execute() throws MojoExecutionException {

    File f = sourceDirectory;
    if (f == null || !f.exists()) {
      throw new MojoExecutionException("Source directory could not be found");
    }

    String dsdPath = null;
    if (dsdPackage != null) {
      String lookupDir = sourceDirectory.getPath() + "/" +
                         dsdPackage.replace('.', '/') + "/";
      dsdPath = dsdFileName(lookupDir, dsdFile);
      if (dsdPath == null) {
        if (dsdFile != null) {
          throw new MojoExecutionException("Configured DSD file " + lookupDir + dsdFile +
              " could not be found.");
        } else {
          throw new MojoExecutionException(
               "DSD file could not be found on configured path " +
               dsdPackage +
               " in any of: \n" +
               searchedLocations +
               "Add an explicit dsdPackage and/or dsdFile parameter to your configuration.");

        }
      }
    } else {
      String groupDir = groupId.replace('.', '/');
      String sourceDir = sourceDirectory.getPath() + "/" + groupDir + "/";
      String foundDsdName = existingDsdFileName(sourceDir, dsdFile);
      if (foundDsdName == null)
        foundDsdName = existingDsdFileName(sourceDir + artifactId + "/", dsdFile);
      if (foundDsdName == null)
        throw new MojoExecutionException(
                      "DSD file could not be found in any of: \n" +
                      searchedLocations +
                      "Add an explicit dsdPackage and/or dsdFile parameter to your configuration.");
      getLog().info("Found DSD at " + foundDsdName + ":");
      dsdPath = foundDsdName;
    }
    String modelDir = dsdPath.substring(0,dsdPath.lastIndexOf('/'));
    String modelName = dsdPath.substring(dsdPath.lastIndexOf('/'),dsdPath.lastIndexOf('.'));
    String databaseTablesFileName = modelDir + modelName + "DatabaseTables.java";
    File databaseTablesFile = new File(databaseTablesFileName);
    long dsdTimestamp = new File(dsdPath).lastModified(); 
    long databaseTablesTimestamp = 1;
    if (databaseTablesFile != null && databaseTablesFile.exists()) {
      databaseTablesTimestamp = databaseTablesFile.lastModified(); 
    }
    if (dsdTimestamp < databaseTablesTimestamp) {
      getLog().info("Generated files are uptodate.");
    } else {
      DSD dsd;
      try {
        dsd = new DSD(dsdPath);
        dsd.generateJava();
      } catch (Exception e) {
        throw new MojoExecutionException(e.toString());
      }
    }
  }

  private String existingDsdFileName(String dir, String dsdFileName)
      throws MojoExecutionException {
    String modelDirName = dir + "poem/";
    File modelDir = new File(modelDirName);
    if (modelDir == null || !modelDir.exists()) {
      searchedLocations += " " + modelDirName + "\n";
      modelDirName = dir + "model/";
      modelDir = new File(modelDirName);
    }
    if (modelDir == null || !modelDir.exists()) {
      searchedLocations += " " + modelDirName + "\n";
      modelDirName = dir;
      modelDir = new File(modelDirName);
    }
    if (modelDir == null || !modelDir.exists()) {
      searchedLocations += " " + modelDirName + "\n";
      return null;
    }
    return dsdFileName(modelDirName, dsdFileName);
  }

  private String dsdFileName(String dir, String dsdFileName)
      throws MojoExecutionException {
    if (dsdFileName != null) {
      dsdFileName = dir + dsdFileName;
      File foundDsdFile = new File(dsdFileName);
      if (foundDsdFile == null || !foundDsdFile.exists()) {
        searchedLocations += " " + dsdFileName + "\n";
        return null;
      }
    } else {
      dsdFileName = dir + artifactId + ".dsd";
      File dsdFile = new File(dsdFileName);
      if (dsdFile == null || !dsdFile.exists()) {
        searchedLocations += " " + dsdFileName + "\n";
        dsdFileName = dir + capitalised(artifactId) + ".dsd";
        dsdFile = new File(dsdFileName);
      }
      if (dsdFile == null || !dsdFile.exists()) {
        searchedLocations += " " + dsdFileName + "\n";
        return null;
      }
    }
    return dsdFileName;
  }

  /**
   * Captialise the first character of the input string.
   * 
   * @param name
   * @return the capitalised string
   */
  public static String capitalised(String name) {
    char suffix[] = name.toCharArray();
    suffix[0] = Character.toUpperCase(suffix[0]);
    return new String(suffix);
  }
}
