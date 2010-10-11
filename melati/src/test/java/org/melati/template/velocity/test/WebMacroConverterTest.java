/**
 * 
 */
package org.melati.template.velocity.test;

import org.melati.template.velocity.WebMacroConverter;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 11 Oct 2010
 */
public class WebMacroConverterTest extends TestCase {

    /**
     * @param name
     */
    public WebMacroConverterTest(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for {@link org.melati.template.velocity.WebMacroConverter#convert(java.io.InputStream)}.
     */
    public final void testConvert() {
        
        // Make #if directive match the Velocity directive style.
        //"#if\\s*[(]\\s*(.*\\S)\\s*[)]\\s*(#begin|{)[ \\t]?",
        //"#if( $1 )",
        
        assertEquals(WebMacroConverter.convert("#if ( $foo ) #begin "),"#if( $foo )");

        // Remove the WM #end #else #begin usage.
        //"([ \\t])?(#end|})\\s*#else\\s*(#begin|{)([ \\t])?(\\w)",
        //"#{else}$5", // avoid touching a followup word with embedded comment

        assertEquals(WebMacroConverter.convert(" #end #else #begin do"),"#{else}do");
        
        //"[ \\t]?(#end|})\\s*#else\\s*(#begin|{)[ \\t]?",
        //"#else",
        assertEquals(WebMacroConverter.convert(" #end #else #begin "),"#else");

        // Convert nulls.
        // This converts
        // $var != null
        // to
        // $var
        //"\\s*(\\$[^\\s\\!]+)\\s*!=\\s*null\\s*",
        //" $1 ",
        assertEquals(WebMacroConverter.convert(" $var != null ")," $var ");

        // This converts
        // $var == null
        // to
        // !$var
        //"\\s*(\\$[^\\s\\!]+)\\s*==\\s*null\\s*",
        //" !$1 ",
        assertEquals(WebMacroConverter.convert(" $var == null ")," !$var ");

        // Convert WM style #foreach to Velocity directive style.
        //"#foreach\\s+(\\$\\w+)\\s+in\\s+(\\$[^\\s#]+)\\s*(#begin|{)[ \\t]?",
        //"#foreach( $1 in $2 )",
        assertEquals(WebMacroConverter.convert("#foreach $foo in $bar #begin "),"#foreach( $foo in $bar )");

        // Change the "}" to #end. Have to get more
        // sophisticated here. Will assume either {}
        // and no javascript, or #begin/#end with the
        // possibility of javascript.
        //"\n}", // assumes that javascript is indented, WMs not!!!
        //"\n#end",
        assertEquals(WebMacroConverter.convert("\n}"),"\n#end");
        assertEquals(WebMacroConverter.convert(" }")," }");

        // Convert WM style #set to Velocity directive style.
        //"#set\\s+(\\$[^\\s=]+)\\s*=\\s*(.*\\S)[ \\t]*",
        //"#set\\s+(\\$[^\\s=]+)\\s*=\\s*([\\S \\t]+)",
        //"#set( $1 = $2 )",
        assertEquals(WebMacroConverter.convert("#set $foo = $bar"),"#set( $foo = $bar )");
        
        // TPP 2010/10/11 Not convinced
        //"(##[# \\t\\w]*)\\)", // fix comments included at end of line
        //")$1",
        assertEquals(WebMacroConverter.convert("apparently legal ## comment )"),"apparently legal )## comment ");

        // Convert WM style #parse to Velocity directive style.
        //"#parse\\s+([^\\s#]+)[ \\t]?",
        //"#parse( $1 )",
        assertEquals(WebMacroConverter.convert("#parse $file "),"#parse( $file )");

        // parse is now deprecated for web macro
        // include as template is recommended.
        // Velocity supports only parse
        // added for melati conversion
        //"#include\\s+as\\s+template\\s+([^\\s#]+)[ \\t]?",
        //"#parse( $1 )",
        assertEquals(WebMacroConverter.convert("#include as template $template "),"#parse( $template )");

        // Convert WM style #include to Velocity directive style.
        //"#include\\s+([^\\s#]+)[ \\t]?",
        //"#include( $1 )",
        assertEquals(WebMacroConverter.convert("#include $file "),"#include( $file )");
        
        // Convert WM formal reference to VL syntax.
        //"\\$\\(([^\\)]+)\\)",
        //"\\${$1}",
        assertEquals(WebMacroConverter.convert("$(var)"),"${var}");

        // TPP 2010/10/11 Not convinced
        //"\\${([^}\\(]+)\\(([^}]+)}\\)", // fix encapsulated brackets: {(})
        //"\\${$1($2)}",
        assertEquals(WebMacroConverter.convert("${que($bar)}"),"${que($bar)}");

        // Velocity currently does not permit leading underscore.
        //"\\$_",
        //"\\$l_",
        assertEquals(WebMacroConverter.convert("$_"),"$l_");

        // TPP 2010/10/11 Did WM formal syntax change to ()?
        //"\\${(_[^}]+)}", // within a formal reference
        //"\\${l$1}",
        assertEquals(WebMacroConverter.convert("${_ff}"),"${l_ff}");

    
    }

}
