// General JavaScript Validator
//
// author: Myles Chippendale, 1999
//
// This script validates fields input from a form by checking
//
// 1) if the field is empty or not
// 2) if the field matches a given regular expression
// 3) if the field passes any other validation you wish to perform
//    in an arbitary javascript function
//
// To get a field to be validated, you need to call the add_rule
// function (see below) with appropriate arguments. You can also
// use the utility functions below for common validation functions
// e.g. add_date("field", "Your Field", 1);
//
// You also need to add the following to any submit buttons:
//
//    onClick="return validate(this.form)"

var rules = new Array();
var rulecount = 0;
var extras = new Array();
var extracount = 0;

// Strings gathered in one place for ease of internationalisation.
// Note spaces are important.
var is_not_defined_for_this_form = " is not defined for this form";
var must_be_filled_in = "Must be filled in";
var the_value = "The value";
var is_not_allowed = "is not allowed";
var there_were_problems = "This form has not been submitted because there were problems with the following fields";
var continue_anyway = "Would you like to continue anyway?";
var correct_and_try_again = "Please correct and try again.";

// Function add_rule(name, heading, mandatory, pattern, snippet)
//
// name      - name of the input field to validate
// heading   - what to call the field for the user's benefit
// mandatory - 0,"0",false or null object means the field can be blank
// pattern   - a regular expression which any value entered into the
//             HTML form must match against for validation to succeed.
//             We need to escape special characters with '\', including '\'
//             itself (e.g. use \\s in your string if you want to match a
//             whitespace char - we want the expression to contain the 2
//             characters '\s', not s. If you put brackets into your
//             expression then JavaScript will allow you to access them
//             through the global (to each window) variable RegExp as
//             RegExp.$1 ... RegExp.$9 in any javascript (for instance,
//             the next argument :-)
// snippet   - the body of JavaScript function. You can use this to perform
//             more general validation tests than just matching a regular
//             expression. It has access to one formal paramater, 'value' -
//             the value of the input field being validated. It also has
//             access to global variables such as RegExp.$1. It should
//             return nothing (or an empty string) if validation succeeds,
//             otherwise it should return a string to be shown to the user
//             explaining the problem.

function add_rule(name, heading, mandatory, pattern, snippet) {
  rules[rulecount]           = new Object();
  rules[rulecount].name      = name;
  rules[rulecount].heading   = heading;
  rules[rulecount].pattern   = pattern;
  rules[rulecount].mandatory = mandatory;
  rules[rulecount].snippet   = snippet;
  rulecount++;
}

function add_extra(expression) {
  extras[extracount++] = expression;
}

// validate
//
// This takes a form object as input and checks each rule added using
// add_rule. If there are any problems with validation, an alert box
// is popped up and the form is not submitted. If there are problems
// and allowSubmit is "yes" then the user will be presented with a
// confirm dialog box (with "OK" and "Cancel" options) which will
// submit the form anyway if OK is clicked.
// 
// Previously this function had a test for the presence of 
// the RegExp object, but this is now taken for granted, as it has 
// been implemented since Netscape 4.0
// 
function validate (form, allowSubmit) {
    var display = "";
    var name,heading,snippet,mandatory,value,re,func_result;
    // Loop through our rules, doing the appropriate checks
    for(i=0;i<rulecount;i++) {
      name       = rules[i].name;
      heading    = rules[i].heading;
      mandatory  = Boolean(rules[i].mandatory);
      pattern    = rules[i].pattern;
      snippet    = rules[i].snippet;

//      alert("Evaluating rule:" + name + 
//            "\n Heading:" + heading + 
//            "\n Mandatory:" + mandatory +
//            "\n pattern:" + pattern +
//            "\n Snippet:" + snippet );

      if (!form[name]) {
        alert(name + is_not_defined_for_this_form);
      }
      else {
        // If this is a select box (but not a multiple select)...
        if (form[name].type == "select-one") {
            value = form[name].options[form[name].selectedIndex].value;
        } else {
            value = form[name].value;
        }

        // Check that a mandatory field isn't empty
        if (trim(value) == "") {
            if (mandatory == true)
                display += "* "+heading+": " + must_be_filled_in +"\n";
        }
        // Check if we match the pattern
        else {
          if (pattern) {
            if (RegExp)  // if this browser supports RegExp
              if (value.search(pattern) == -1 ) // failed to match pattern
                if (!snippet) { // If we don't have a code snippet 
                  display += "* "+heading+": " + the_value + " '"+value+"' " + is_not_allowed +"\n";
                }
          }
          if (snippet) { // If we have a code snippet 
          // turn it into a function and call it on value
            var func = new Function("value", "form", snippet);
            func_result = func(value, form);
            if (func_result)
              display += "* "+heading+": "+func_result+"\n";
           }
        }
      }
    }       // end of loop through rules
    // Loop through our extras, evaluating each one
    for(i=0;i<extracount;i++) {
      res = eval(extras[i]);
      if (res)
        display += "* "+res+"\n";
    }
    if (display.length != 0) {
      if (allowSubmit == "yes")
          return confirm(there_were_problems +  ":\n\n"+display+"\n\n" + continue_anyway);
      else {
          alert(there_were_problems + ":\n\n"+display+"\n\n" + correct_and_try_again);
          return false;
      }
    }
    return true;
}

//
// Some utility functions for most common validations
//

// Integer          - digits with an optional minus at the front
function add_integer(name, heading, mandatory) {
  add_rule(name, heading, mandatory, "^-?\\d*\\s*$");
}

// Number           - an integer with a dot and some digits
function add_number(name, heading, mandatory) {
  add_rule(name, heading, mandatory, "^-?\\d*(\\.\\d*)?\\s*$");
}

// Fixed Point Number - an integer with a dot and a fixed number of digits
function add_fixed_number(name, heading, mandatory, fixed) {
  add_rule(name, heading, mandatory, "^-?\\d*(\\.\\d{"+fixed+"})?\\s*$");
}


// Email           - "@" symbol and no spaces - one or more good 
// characters before and after "@" - .xx country code or .xxx (.xxxx) tld to end
function add_email(name, heading, mandatory) {
  add_rule(name, heading, mandatory, "^\\S+@\\S+\\.\\w{2,4}$");
}


// Date             - dd/mm/yyyy
// We need to put '/' chars in a char class (i.e. [/]) to prevent
// IE from complaining that our re is wrong :-(
function add_date(name, heading, mandatory) {
  add_rule(name, heading, mandatory, "(^[0123]?\\d)[/]([01]?\\d)[/]\\d{4}\\s*$",
           "return (RegExp.$1>31 || RegExp.$1==0 || RegExp.$2>12 || RegExp.$2==0)?'" + the_value + " \"'+value+'\" " + is_not_allowed + "':'';"
          );
}

// DateTime         - dd/mm/yyyy hh:mm
function add_datetime(name, heading, mandatory) {
  add_rule(name, heading, mandatory, "(^\\d{1,2})[/](\\d{1,2})[/]\\d{2,4}\\s*(\\d{1,2}):(\\d{1,2})\\s*$",
           "return (RegExp.$1>31 || RegExp.$1==0 || RegExp.$2>12 || RegExp.$2==0 || RegExp.$3>23 || RegExp.$4>59 )?'" + the_value + " \"'+value+'\" " + is_not_allowed + "':'';"
          );
}

// Timestamp         - dd/mm/yyyy hh:mm:ss.mmm
function add_timestamp(name, heading, mandatory) {
  add_rule(name, heading, mandatory, "(^\\d{1,2})[/](\\d{1,2})[/]\\d{2,4}\\s*(\\d{1,2}):(\\d{1,2}):(\\d{1,2})\\s*$",
           "return (RegExp.$1>31 || RegExp.$1==0 || RegExp.$2>12 || RegExp.$2==0 || RegExp.$3>23 || RegExp.$4>59 || RegExp.$5>59 )?'" + the_value + " \"'+value+'\" " + is_not_allowed + "':'';"
          );
}

// Useful in an onChange attribute, eg:
// <SELECT NAME="trolley_sponsored" onChange="sponsored = !sponsored; flipMandatory('trolley_sponsor|trolley_company');">

function flipMandatory(fieldList) {
  arrayOfFieldNames = fieldList.split("|");
  for (var i=0; i < arrayOfFieldNames.length; i++) { 
    for(var j=0;j<rulecount;j++) {
      if (arrayOfFieldNames[i] == rules[j].name) {
        rules[j].mandatory = !rules[j].mandatory;
        // alert(arrayOfFieldNames[i] + " is now " + rules[j].mandatory);
      }
    }
  }
}


function trim(str) {
  var whitespace = new String(" \t\n\r");
  var s = new String(str);
  if (whitespace.indexOf(s.charAt(s.length-1)) != -1) {
    var i = s.length - 1;       // Get length of string
    while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
       i--;
    s = s.substring(0, i+1);
  }
  return s;
}


