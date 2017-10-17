//
// $Source$
// $Revision$
//
// Copyright MJ Chippendale 12/02/2002
//
// This files contains Javascript classes defining an abstract TreeNode
// containing data, and an object/application using them - Static
// Tree. This provides an implementation of an 
// expandable/collapsible tree
//
// You can contol whether to display checkboxes against 
// terminal nodes (leaves) or non-reminal nodes.
//
// You can contol the images used to display the tree and 
// the node labels.
//
// Author: MJ Chippendale 12/02/2002
//         TP Pizey       12/02/2002
//         TP Pizey       15/01/2008
//
//
// See: org/melati/util/JSStaticTree.java
//      org/melati/template/webmacro/templets/html/org.melati.util.JSStaticTree.wm
//      org/melati/template/webmacro/templets/html/StaticNode.wm
//      org/melati/template/MarkupLanguage.java
//
//
//  TODO
//  Generate indexes in data
//  Remove UniqueName ???
//

// Browser sniffing code

var isNav4, isIE4;
if (parseInt(navigator.appVersion.charAt(0)) >= 4) {
  isNav4 = (navigator.appName == "Netscape") ? true : false;
  isIE4 = (navigator.appName.indexOf("Microsoft") != -1) ? true : false;
}

// ****************
// TreeNode Objects
// ****************

function TreeNode(nodeLabel, depth, isLeaf, id, table, troid) {
  this.nodeLabel = nodeLabel;
  this.depth = depth;
  this.isLeaf = isLeaf;
  this.id = id; // unique id (for communicating with the server)
  this.table = table;
  this.troid = troid; 

  this.parent = null;
  this.children = new Array();
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

//    loadNodeLabel("new_data.html");
    var URL="new_data.html";
    if (isNav4) document.layers["loadbuffer"].load(URL,0);
    else document.location = URL;
    return null;
  }
  return this.children;
}

TreeNode.prototype.flatten = function (depthFirst, depth, dontload) {
  //agenda = [this];
  agenda = new Array();
  agenda[0]= this;
  results = new Array();

  while (agenda.length != 0) {
//    current = agenda.shift();  // Only available in JScript 5.5+
    current = agenda[0];
    agenda = agenda.slice(1);
    results[results.length] = current;

    if (this.depth < depth || depth < 0) {
      kids = current.getChildren(dontload);
      if (depthFirst) {
        if (kids.length != 0 ) // Konqueror bug 
          agenda = kids.concat(agenda);
      }
      else {
        if (agenda.length != 0)  // Konqueror bug 
          agenda = agenda.concat(kids);
        else 
          agenda = kids;
      }
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

function StaticTree(contentDiv,
                    roots, 
                    selectNodes, selectLeaves,
                    backgroundColour, stylesheet,
                    verticalLinkImage, spacerImage,
                    openedTImage, openedLImage,
                    closedTImage, closedLImage,
                    leafTImage, leafLImage,
                    openedFolderImage, closedFolderImage, leafImage) {

  // *******************
  // StaticTree members
  // *******************
  this.contentDiv = contentDiv;
  this.roots = roots; // Should be an array of TreeNodes
  this.flattened = new Array();
  this.selectNodes = selectNodes;
  this.selectLeaves = selectLeaves;
  this.backgroundColour = backgroundColour;
  this.stylesheet = stylesheet;
  this.spacerImage = spacerImage;  // needed in nodes and in tree

  // -----------------------------
  // Subclassed TreeNode variables
  // -----------------------------
  TreeNode.prototype.index = 0; // index in the Tree's flattened array
  TreeNode.prototype.isOpen = true; //Default to open; less clicking
  TreeNode.prototype.chosen = false;
  TreeNode.prototype.selected = false;
  TreeNode.prototype.spacerImage = spacerImage;
  TreeNode.prototype.verticalLinkImage = verticalLinkImage;
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

  function writeTree(lastChild, aboves) {
    var str;
    str = this.writeNode(lastChild,aboves);
    if (this.isOpen) {
      var kids = this.getChildren(true);
      var newAboves = new Array();
      for (var i=0; i<aboves.length; i++)
        newAboves[i] = aboves[i];
      newAboves[newAboves.length] = !lastChild;
      for(var i=0; i<kids.length; i++) {
        str = str + kids[i].writeTree(i==kids.length-1, newAboves);
      }
    }
    return str;
  };

  function writeNode(lastChild,aboves) {
    var str = "<TABLE NOWRAP CELLPADDING='0' CELLSPACING='0' BORDER='0'><TR><TD valign='middle'>";

    for(var i=0; i<aboves.length; i++)
    {
        if (aboves[i] == true) {
           str += "<IMG align='absmiddle' SRC='" + this.verticalLinkImage + "' border='0' HEIGHT='22' WIDTH='16'>";
        }
        else {
           str += "<IMG align='absmiddle' SRC='" + this.spacerImage + "' border='0' HEIGHT='22' WIDTH='16'>";
        }
    }
    
    if (this.isLeaf) {
      str += "<IMG align='absmiddle' BORDER=0 SRC='";
      if (lastChild == true) { 
        str += this.leafLImage;
      } else { 
        str += this.leafTImage;
      }
      
      str +="'>";
      str += "<IMG align='absmiddle' src='"+this.leafImage+"' border='0' />";
    } else {
      str += "<A HREF='javascript:{}' onclick='theTree.toggle("+this.index+");'>"
      str += "<IMG align='absmiddle' BORDER='0' SRC='";
      if (this.isOpen) {
        str += (lastChild) ? this.openedLImage : this.openedTImage;
      } else {
        str += (lastChild) ? this.closedLImage : this.closedTImage;
      }
      str += "'>";
      str += (this.isOpen) ? 
          "<IMG align='absmiddle' src='"+this.openedFolderImage+"' border='0' />" 
          : "<IMG align='absmiddle' src='"+this.closedFolderImage+"' border='0' />";
      str += "</A>";
    }
    
    str += "</TD><TD valign=middle><NOBR>";
    if (this.isLeaf) {
      if(theTree.selectLeaves) {
        str += "<input type='checkbox' name='check"+this.index+"' onClick='"+theTree.controlName+".selectLeaf("+this.index+")'";
        if (this.selected) {
          str += " checked='checked'";
        }
        str += ">";
      }
    } else {
      if (theTree.selectNodes) {
        str += "<input type=checkbox name=check"+this.index+" onClick='"+theTree.controlName+".selectNode("+this.index+")'";
        if (this.selected) {
          str += " checked='checked'";
        }
        str += ">";
      }
    }
    if (this.chosen) {
      str += "<B>";
      str += "<FONT size='3'>";
    } else {
      str += "<FONT size='1'>";
    }
    str += this.nodeLabel;

    str += "</FONT>";
    if (this.chosen)
      str += "</B>";

    str += "</NOBR>";
    str += "</TD></TR></TABLE>\n";
    return str;
  };
    
}

//********************
// StaticTree methods
//********************

StaticTree.prototype.indexNodes = function ()  {
  for(i=0; i<this.roots.length; i++) {
    var f = this.roots[i].flatten(true,-1,true);
    for(j=0; j<f.length; j++) {
      this.flattened[this.flattened.length] = f[j];  
    }
  }
  for(i=0; i<this.flattened.length; i++)
    this.flattened[i].index = i;
}

var indexed = false;
StaticTree.prototype.display = function () {
  var str;
  if (indexed == false) {
    this.indexNodes();
    indexed = true;
  }

  
  str =  "<form>\n"

  for(i=0; i<this.roots.length; i++) {

    str += this.roots[i].writeTree((i == (this.roots.length -1)), new Array());
   }
// Appended by ColinR: 
// scrollSpacer is added for browsers which do not allow JavaScript 
// to scroll the window beyond the document length
  str += "<IMG src='" + this.spacerImage + "' height='100%' width='1' ";
  str += "name='scrollSpacer' border='0'></form>";
  this.contentDiv.innerHTML = str;      
}


// ****************************
// StaticTree Global functions
// ****************************

var lastChosen = null;

// Change an open node to a closed one and visa versa
function toggle(index)  {

// Appended by ColinR: 
// pageYOffset (etc) is detected so the newly rendered tree
// is scrolled to the current tree position to avoid confusion and the need
// to scroll down the tree each time a new node is opened or closed

  var pageY = 0;
  var pageX = 0;
  if (isNav4) {
    pageY = this.contentDiv.pageYOffset;
    pageX = this.contentDiv.pageXOffset;
  } else {
    pageY = document.body.scrollTop;
    pageX = document.body.scrollLeft;
  }

  var node = theTree.flattened[index];
  node.isOpen = !node.isOpen;
  theTree.display();
  
  self.scrollTo(pageX,pageY);
  
}

StaticTree.prototype.toggle = toggle;

function selectLeaf(index) {
  var node = theTree.flattened[index];
  node.selected = !node.selected;
  if (lastChosen != null) {
    lastChosen.chosen = false;
  }
  node.chosen = true;
  lastChosen = node;
  if (!node.selected && node.parent.selected) {
    node.parent.selected = false;
    theTree.display();  
  }
}

function selectNode(index) {
  var node = theTree.flattened[index];
  node.selected = !node.selected;
  if (lastChosen != null) {
    lastChosen.chosen = false;
  }
  node.chosen = true;
  lastChosen = node;
  var kids = node.getChildren(true);
  for(var i=0; i<kids.length; i++) {
     kids[i].selected = node.selected;
  }
  theTree.display();  
}

function getIndex(hashId) {
  var i;
  for(i=0;i < theTree.flattened.length; i++) {
    var node = theTree.flattened[i];
    if(node.id == hashId) return i;
  }
  return -1;
}

function getSelectedTroids() {
 var selected = new Array();
  for(i=0;i < theTree.flattened.length; i++) {
    var node = theTree.flattened[i];
    if(node.selected) {
      selected[selected.length]=node.troid;
    }
  }
  return selected;
}

function getSelectedTroidsWithTable(table) {
 var selected = new Array();
  for(i=0;i < theTree.flattened.length; i++) {
    var node = theTree.flattened[i];
    if(node.table == table && node.selected) {
      selected[selected.length]=node.troid;
    }
  }
  return selected;
}


