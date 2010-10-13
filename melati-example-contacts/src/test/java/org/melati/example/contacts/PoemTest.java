package org.melati.example.contacts;

import org.melati.poem.ExportedTests;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * Annoyingly the Maven Surefire Plugin cannot use test jars, so we have to
 * maintain an ExportedTests class in POEM.
 * 
 * See http://jira.codehaus.org/browse/SUREFIRE-569
 */
public class PoemTest extends TestCase {

    public static Test suite() {
        return ExportedTests.suite();
    }
}
