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

rules = new Array();
rulecount = 0;
extras = new Array();
extracount = 0;

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

function validate (form, allowSubmit) {
  if (window.RegExp) {
    var display = "";
    var name,heading,snippet,mandatory,value,re,func_result;

    // Loop through our rules, doing the appropriate checks
    for(i=0;i<rulecount;i++) {
      name       = rules[i].name;
      heading    = rules[i].heading;
      snippet    = rules[i].snippet;
      pattern    = rules[i].pattern;
      mandatory  = Boolean(rules[i].mandatory);
      if (!form[name]) {
      //      alert(name + " is not defined for this form");
      }
      else {
        // If this is a select box (but not a multiple select)...
        if (form[name].type == "select-one") {
          value = form[name].options[form[name].selectedIndex].value;
        } else {
            value = form[name].value;
        }

        // Check that a mandatory field isn't empty
        if (value.search(".") == -1) {
            if (mandatory == true)
                display += "* "+heading+" must be filled in\n";
        }
        // Check if we match the pattern
        else if (value.search(pattern) != -1) {
          if (snippet) {
              // If we have a code snippet, turn it into a function and call it on value
              var func = new Function("value", "form", snippet);
              func_result = func(value, form)
              if (func_result)
                display += "* "+heading+": "+func_result+"\n";
          }
        }
        else {        // failed to match pattern
          if (pattern)
            display += "* "+heading+": The value '"+value+"' is not allowed\n";
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
          return confirm("This form has not been submitted because there were problems with the following fields:\n\n"+display+"\n\nWould you like to continue anyway?");
      else {
          alert("This form has not been submitted because there were problems with the following fields:\n\n"+display+"\n\nPlease correct these and try again.");
          return false;
      }
    }
    return true;
  } else {    // no RegExp object - exit
    return true;
  }
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
           "return (RegExp.$1>31 || RegExp.$1==0 || RegExp.$2>12 || RegExp.$2==0)?'The value \"'+value+'\" is not allowed':'';"
          );
}

// DateTime         - dd/mm/yyyy hh:mm
function add_datetime(name, heading, mandatory) {
  add_rule(name, heading, mandatory, "(^\\d{1,2})[/](\\d{1,2})[/]\\d{2,4}\\s*(\\d{1,2}):(\\d{1,2})\\s*$",
           "return (RegExp.$1>31 || RegExp.$1==0 || RegExp.$2>12 || RegExp.$2==0 || RegExp.$3>23 || RegExp.$4>59 )?'The value \"'+value+'\" is not allowed':'';"
          );
}

// Timestamp         - dd/mm/yyyy hh:mm:ss.mmm
function add_timestamp(name, heading, mandatory) {
  add_rule(name, heading, mandatory, "(^\\d{1,2})[/](\\d{1,2})[/]\\d{2,4}\\s*(\\d{1,2}):(\\d{1,2}):(\\d{1,2})\\s*$",
           "return (RegExp.$1>31 || RegExp.$1==0 || RegExp.$2>12 || RegExp.$2==0 || RegExp.$3>23 || RegExp.$4>59 || RegExp.$5>59 )?'The value \"'+value+'\" is not allowed':'';"
          );
}
