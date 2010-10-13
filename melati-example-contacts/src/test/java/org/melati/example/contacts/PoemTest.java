package org.melati.example.contacts;

import org.melati.poem.ExportedTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Annoyingly the Maven Surefire Plugin cannot use test jars, so we have to
 * maintain an ExportedTests class in POEM.
 * 
 * See http://jira.codehaus.org/browse/SUREFIRE-569
 * 
 * NOTE that this will be discovered by the plugin as it extends TestSuite, 
 * see http://jira.codehaus.org/browse/SUREFIRE-120, but the ExportedTests 
 * should not extend it.  
 */
public class PoemTest extends TestSuite {

    public static Test suite() {
        return ExportedTests.suite();
    }
}
