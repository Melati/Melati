//
// $Source$
// $Revision$
//
// copyright MJ Chippendale 12/02/2002
//
// This files contains Javascript classes defining an abstract TreeNode
// containing data, and an object/application using them - Static
// Tree. This provides a frames based implementation of an 
// expandable/collapsible tree
//
// Author: MJ Chippendale 12/02/2002
//         TP Pizey       12/02/2002
//
//
// See: org/melati/util/JSStaticTree.java
//      org/melati/template/webmacro/templets/html/org.melati.util.JSStaticTree.wm
//      org/melati/template/MarkupLanguage.java
//
//
//
// Browser sniffing code
//
var isNav4, isIE4;
if (parseInt(navigator.appVersion.charAt(0)) >= 4) {
  isNav4 = (navigator.appName == "Netscape") ? true : false;
  isIE4 = (navigator.appName.indexOf("Microsoft") != -1) ? true : false;
}

// ****************
// TreeNode Objects
// ****************

function TreeNode(data, depth, isLeaf, id) {
  this.data = data;
  this.depth = depth;
  this.isLeaf = isLeaf;
  this.id = id; // unique id (for communicating with the server)
  this.parent = null;
  this.children = [];
}

TreeNode.prototype.setParent = function (parent) {
  this.parent = parent;
  parent.addChild(this);
}

TreeNode.prototype.addChild = function (child) {
  this.children[this.children.length] = child;
}

TreeNode.prototype.getChildren = function (dontload) {
  if (this.children.length == 0 && !this.isLeaf && !dontload) {
    depthPerDownload = (theTree && theTree.depthPerDownload)
                       ? theTree.depthPerDownload : -1;

//    loadData("new_data.html");
    var URL="new_data.html";
    if (isNav4) document.layers["loadbuffer"].load(URL,0);
    else parent.loadframe.document.location = URL;
    return null;
  }
  return this.children;
}

TreeNode.prototype.flatten = function (depthFirst, depth, dontload) {
  agenda = [this];
  results = new Array();

  while (agenda.length != 0) {
//    current = agenda.shift();  // Only available in JScript 5.5+
    current = agenda[0];
    agenda = agenda.slice(1);
    results[results.length] = current;

    if (this.depth < depth || depth < 0) {
      kids = current.getChildren(dontload);
      if (depthFirst)
        agenda = kids.concat(agenda);
      else
        agenda = agenda.concat(kids);
    }
  }
  return results;
}

// *************************************
// TreeNode Global variable and function
// *************************************

// There can only be one tree per document - and this is it
var theTree = null;

// ***********************
// StaticTree Objects
// ***********************

// displayFrame      - the (javascript object) frame to write the tree to
// controlFrameName  - the (string) name of the frame in which this
//                     script is, _relative to the displayFrame_
var globalImageBaseRef = null;

function StaticTree(displayFrame, controlFrameName,
                    roots, 
                    imageBaseRef,
                    openedTImage, openedLImage,
                    closedTImage, closedLImage,
                    leafTImage, leafLImage,
                    openedFolderImage, closedFolderImage, leafImage) {

 if (globalImageBaseRef == null) globalImageBaseRef = imageBaseRef;
  // *******************
  // StaticTree members
  // *******************
  this.roots = roots; // Should be an array of TreeNodes
  this.flattened = [];
  this.frame = displayFrame;
  this.controlName = controlFrameName;

  theTree = this;

  // -----------------------------
  // Subclassed TreeNode variables
  // -----------------------------
  TreeNode.prototype.index = 0; // index in the Tree's flattened array
  TreeNode.prototype.isOpen = false;
  TreeNode.prototype.chosen = false;
  TreeNode.prototype.selected = false;
  TreeNode.prototype.openedTImage = openedTImage;
  TreeNode.prototype.openedLImage = openedLImage;
  TreeNode.prototype.closedTImage = closedTImage;
  TreeNode.prototype.closedLImage = closedLImage;
  TreeNode.prototype.leafTImage = leafTImage;
  TreeNode.prototype.leafLImage = leafLImage;
  TreeNode.prototype.openedFolderImage = openedFolderImage;
  TreeNode.prototype.closedFolderImage = closedFolderImage;
  TreeNode.prototype.leafImage = leafImage;

  // -----------------------------
  // Subclassed TreeNode methods
  // ---------------------------
  TreeNode.prototype.writeNode = writeNode;
  TreeNode.prototype.writeTree = writeTree;

  function writeTree(doc,lastChild, aboves) {
    this.writeNode(doc,lastChild,aboves);
    if (this.isOpen) {
      var kids = this.getChildren(true);
      var newAboves = [];
      for (var i=0; i<aboves.length; i++)
        newAboves[i] = aboves[i];
      newAboves[newAboves.length] = !lastChild;
      for(var i=0; i<kids.length; i++) {
        kids[i].writeTree(doc,i==kids.length-1, newAboves);
      }
    }
  };

  function writeNode(doc,lastChild,aboves) {
  
    var str = "<TABLE NOWRAP CELLPADDING=0 CELLSPACING=0 BORDER=0><TR><TD valign=middle>";

    for(var i=0; i<aboves.length; i++)
    {
        if (aboves[i] == true) {
           str += "<IMG align='absmiddle' SRC='" + globalImageBaseRef + "/vertline.gif' border='0' HEIGHT='22' WIDTH='16'>";
        }
        else {
           str += "<IMG align='absmiddle' SRC='" + globalImageBaseRef + "/spacer.gif' border='0' HEIGHT='22' WIDTH='16'>";
        }
    }
    
    if (this.isLeaf) {
      str += "<IMG align='absmiddle' BORDER=0 SRC=\"";
      str += (lastChild) ? this.leafLImage : this.leafTImage;
      str +="\">";
    } else {
      str += "<A HREF=\"javascript:"+theTree.controlName+".expand(";
      str += this.index+");\"><IMG align='absmiddle' BORDER=0 SRC=\"";
      if (this.isOpen) {
        str += (lastChild) ? this.openedLImage : this.openedTImage;
      } else {
        str += (lastChild) ? this.closedLImage : this.closedTImage;
      }
      str += "\"></A>";
    }
    
    if (this.isLeaf) {
      str += "<IMG align='absmiddle' src=\""+this.leafImage+"\">";
    } else {
      str += (this.isOpen) ? "<IMG align='absmiddle' src='"+this.openedFolderImage+"'>" : "<IMG align='absmiddle' src='"+this.closedFolderImage+"'>";
    }
    str += "</TD><TD valign=middle><NOBR>";
// FIXME - only some of this is in StaticNode.wm
//    str += "<input type=checkbox name=check"+this.index+" onClick=\""+theTree.controlName+".selectThis("+this.index+")\"";
//    if (this.selected) {
//      str += " checked";
//    }
//    str += ">";
//    if (this.chosen)
//      str += "<B>";
//    str += "<FONT size=3>"+this.data+"</FONT>";
//    if (this.chosen)
//      str += "</B>";
    str += this.data;

    str += "</NOBR>";
    str += "</TD></TR></TABLE>\n";
    doc.write(str);
    
  };
    
}

//********************
// StaticTree methods
//********************

StaticTree.prototype.indexNodes = function ()  {
  for(i=0; i<this.roots.length; i++)
    this.flattened = this.flattened.concat(this.roots[i].flatten(true,-1,true))
  for(i=0; i<this.flattened.length; i++)
    this.flattened[i].index = i;
}

var indexed = false;
StaticTree.prototype.display = function () {

  if (indexed == false)
  {
    this.indexNodes();
    indexed = true;
  }

  var doc = this.frame.document.open();
  doc.write("<html><head><STYLE>FONT {font-family:verdana; font-size:10px;}\n</STYLE></head><body bgcolor=#ffffff><form>\n");

  for(i=0; i<this.roots.length; i++) {

    y = this.roots[i].writeTree(doc, (i == (this.roots.length -1)), []);
   }
// Appended by ColinR: scrollSpacer is added for browsers which do not allow JavaScript 
// to scroll the window beyond the document length
  doc.write("<IMG src=" + globalImageBaseRef + "/spacer.gif height=100% width=1 name=scrollSpacer border=0></form></body></html>\n");
  doc.close();
    
}


// ****************************
// StaticTree Global functions
// ****************************

var lastChosen = null;

function expand(index) {

// Appended by ColinR: pageYOffset (etc) is detected so the the newly rendered tree
// is scrolled to the current tree position to avoid confusion and the need
// to scroll down the tree each time a new node is opened or closed

  if (isNav4) {
    var pageY = theTree.frame.window.pageYOffset;
    var pageX = theTree.frame.window.pageXOffset;
  } else {
    var pageY = theTree.frame.document.body.scrollTop;
    var pageX = theTree.frame.document.body.scrollLeft;
  }

  var node = theTree.flattened[index];
  if (lastChosen != null) {
    lastChosen.chosen = false;
  }
  node.isOpen = !node.isOpen;
  node.chosen = true;
  lastChosen = node;
  theTree.display(false);
  
  theTree.frame.scrollTo(pageX,pageY);
  
};

function selectThis(index) {
  var node = theTree.flattened[index];
  node.selected = !node.selected;
}


function getIndex(hashId) {
  var i;
  for(i=0;i < theTree.flattened.length; i++) {
    var node = theTree.flattened[i];
    if(node.id == hashId) return i;
  }
  return -1;
}
