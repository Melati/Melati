/**
 * 
 */
package org.melati.template.velocity;

/**
 * Constants for use in conversion.
 * 
 * Note that this does not allow modern WebMacro syntax with
 * optionional #begin in #foreach.
 * 
 * @author Tim Pizey based on work by Jason van Zyl and Tim Joyce.
 *
 */
public interface WebMacroConverter {

  /**
   * The regexes to use for substition. The regexes come
   * in pairs. The first is the string to match, the
   * second is the substitution to make.
   */
  public static final String[] regExps = {
        // Make #if directive match the Velocity directive style.
        "#if\\s*[(]\\s*(.*\\S)\\s*[)]\\s*(#begin|{)[ \\t]?",
        "#if( $1 )",

        // Remove the WM #end #else #begin usage.
        "[ \\t]?(#end|})\\s*#else\\s*(#begin|{)[ \\t]?(\\w)",
        "#else#**#$3", // avoid touching a followup word with embedded comment
        "[ \\t]?(#end|})\\s*#else\\s*(#begin|{)[ \\t]?",
        "#else",

        // Mess with sane null handling in webmacro :(
        // This converts
        // #if ($var != null)
        // to
        // #if ($var)
        "#if\\s*[(]\\s*(\\$[^\\s\\!]+)\\s*!=\\s*null\\s*[)]",
        "#if( $1 )",
        // This converts
        // #if ($var == null)
        // to
        // #if (!$var)
        "#if\\s*[(]\\s*(\\$[^\\s\\!]+)\\s*==\\s*null\\s*[)]",
        "#if( !$1 )",

        // Convert WM style #foreach to Velocity directive style.
        "#foreach\\s+(\\$\\w+)\\s+in\\s+(\\$[^\\s#]+)\\s*(#begin|{)[ \\t]?",
        "#foreach( $1 in $2 )",

        // Change the "}" to #end. Have to get more
        // sophisticated here. Will assume either {}
        // and no javascript, or #begin/#end with the
        // possibility of javascript.
        "\n}", // assumes that javascript is indented, WMs not!!!
        "\n#end",

        // Convert WM style #set to Velocity directive style.
        //"#set\\s+(\\$[^\\s=]+)\\s*=\\s*(.*\\S)[ \\t]*",
        "#set\\s+(\\$[^\\s=]+)\\s*=\\s*([\\S \\t]+)",
        "#set( $1 = $2 )",
        "(##[# \\t\\w]*)\\)", // fix comments included at end of line
        ")$1",

        // Convert WM style #parse to Velocity directive style.
        "#parse\\s+([^\\s#]+)[ \\t]?",
        "#parse( $1 )",

        // parse is now depricated for web macro
        // include as template is recommended.
        // Velocity supports only parse
        // added for melati conversion
        "#include\\s+as\\s+template\\s+([^\\s#]+)[ \\t]?",
        "#parse( $1 )",

        // Convert WM style #include to Velocity directive style.
        "#include\\s+([^\\s#]+)[ \\t]?",
        "#include( $1 )",

        // Convert WM formal reference to VL syntax.
        "\\$\\(([^\\)]+)\\)",
        "\\${$1}",
        "\\${([^}\\(]+)\\(([^}]+)}\\)", // fix encapsulated brakets: {(})
        "\\${$1($2)}",

        // Velocity currently does not permit leading underscore.
        "\\$_",
        "$l_",
        "\\${(_[^}]+)}", // within a formal reference
        "\\${l$1}",

  };

}
