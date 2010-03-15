//
// tree.js
// copyright M Chippendale 22/07/2000
//
// This files contains Javascript classes defining an abstract TreeNode
// containing data, and an object/application using them - Dynamic
// Tree. This provides a DHTML implementation of an expandable/collapsible
// tree
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
//    alert("We should load in the children of this node");
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

// There can only be tree per document - and this is it
var theTree = null;

// ***********************
// DynamicTree Objects
// ***********************

function DynamicTree(roots, xpos, ypos, width, height, colour, indent,
                     openedImage, closedImage, leafImage, depthPerDownload) {

  // *******************
  // DynamicTree members
  // *******************
  this.roots = roots; // Should be an array of TreeNodes
  this.flattened = [];
  this.x = xpos;
  this.y = ypos;
  this.depthPerDownload = depthPerDownload;

  theTree = this;

  // -----------------------------
  // Subclassed TreeNode variables
  // -----------------------------
  TreeNode.prototype.index = 0; // index in theTree's flattened array
  TreeNode.prototype.isOpen = true;
  TreeNode.prototype.layer = null;
  TreeNode.prototype.drawn = false;
  TreeNode.prototype.width = width || 400;
  TreeNode.prototype.height = height || 19;
  TreeNode.prototype.colour = colour || "#ffffff";
  TreeNode.prototype.indent = indent || 20;
  TreeNode.prototype.openedImage = openedImage;
  TreeNode.prototype.closedImage = closedImage;
  TreeNode.prototype.leafImage = leafImage;

  // -----------------------------
  // Subclassed TreeNode methods
  // ---------------------------
  TreeNode.prototype.setLayer = setLayer;
  TreeNode.prototype.drawNode = drawNode;
  TreeNode.prototype.displayNode = displayNode;
  TreeNode.prototype.displayTree = displayTree;

  function displayTree(show, x, y) {
    y = this.displayNode(show, x, y);
    if (this.isOpen) {
      var kids = this.getChildren();
      for(var i=0; i<kids.length; i++)
        y = kids[i].displayTree(show, x, y);
    }
    return y;
  };

  function displayNode(show, x, y) {
    if (!this.drawn) this.drawNode();
    if (!this.layer) return;
    if (isNav4) this.layer.visibility = "hidden";
    else this.layer.style.visibility = "hidden";

    if (!show) return y

    var img = (this.isLeaf) ? this.leafImage :
                 ((this.isOpen) ? this.openedImage : this.closedImage);
    if (isNav4) {
      this.layer.top = y; this.layer.left = x;
      this.layer.document.images[0].src = img;
      this.layer.visibility = "visible";
    } else {
      this.layer.style.pixelTop = y; this.layer.style.pixelLeft = x;
      eval('document.images._img'+this.id+'.src = "'+img+'"');
      this.layer.style.visibility = "visible";
    }
    return y + this.height;
  }

  function drawNode() {
    this.drawn = true;

    // Don't bother if we can't find a layer
    if (!this.layer) {
      this.setLayer();
      if (!this.layer)
        return;
    }
    if (isNav4) this.layer.visibility = "hidden";
    else this.layer.style.visibility = "hidden";

    var str = "<TABLE NOWRAP BORDER=0 CELLPADDING=0 CELLSPACING=0><TR>";
    str += "<TD WIDTH="+this.depth*this.indent+" NOWRAP>&nbsp;</TD>";
    if (this.isLeaf) {
      str += "<TD WIDTH=15 NOWRAP VALIGN=MIDDLE><IMG BORDER=0 SRC=\"";
      str += this.leafImage+"\" NAME=\"_img"+this.id+"\" HSPACE=3></TD>";
    } else {
      str += "<TD WIDTH=15 NOWRAP VALIGN=MIDDLE><A HREF=\"javascript:expand(";
      str += this.index+");\"><IMG BORDER=0 HSPACE=3 SRC=\"";
      str += (this.isOpen) ? this.openedImage : this.closedImage;
      str += "\" NAME=\"_img"+this.id+"\"></A></TD>";
    }
    str += "<TD HEIGHT="+(this.height-3)+" WIDTH=";
    str += (this.width-15-this.depth*this.indent)+" VALIGN=MIDDLE ALIGN=LEFT><NOBR>";
    str += this.data;
    str += "</NOBR></TD></TABLE>";
    if (isNav4) {
      this.layer.document.open();
      this.layer.document.write(str);
      this.layer.document.close();
    }
    else
      this.layer.innerHTML = str;
  };

  function setLayer() {
    var testLayer = false;
    if (!document.all) document.all = document.layers;
    // NS looks for a layer with this name, IE looks for a layer
    // with this id
    testLayer = eval('document.all.Layer'+this.id);
    if (testLayer) {
      // setClip & bgColor
      if (isNav4) {
        testLayer.visibility = "hidden";
        testLayer.clip.left = 0; testLayer.clip.right = this.width;
        testLayer.clip.top = 0;  testLayer.clip.bottom = this.height;
        if(this.colour) testLayer.document.bgColor = this.colour;
      } else {
        testLayer.style.visibility = "hidden";
        testLayer.style.pixelWidth = this.width;
        testLayer.style.pixelHeight = this.height;
        testLayer.style.clip = "rect(0,"+this.width+","+this.height+",0)";
        if(this.colour) testLayer.style.backgroundColor = this.colour;
      }
      this.layer = testLayer;
    }
  };

}

//********************
// DynamicTree methods
//********************

DynamicTree.prototype.indexNodes = function ()  {
  for(i=0; i<this.roots.length; i++)
    this.flattened = this.flattened.concat(this.roots[i].flatten(true,-1,true))
  for(i=0; i<this.flattened.length; i++)
    this.flattened[i].index = i;
}

DynamicTree.prototype.display = function () {
  this.indexNodes();
  y = this.y;
  for(i=0; i<this.roots.length; i++)
    y = this.roots[i].displayTree(true, this.x, y);
}


// ****************************
// DynamicTree Global functions
// ****************************

function expand(index) {
  var node = theTree.flattened[index];
  update(node);
}

function update(node) {
  var kids = node.getChildren();
  if (kids == null)  { // We have just loaded in the children. The document
    lastnode = node;   // which loads the new values is responsible for
    return;            // calling updatelast when it's finished loading.
  }

  node.isOpen = !node.isOpen;

  var y;
  if (isNav4) y = node.layer.top;
  else y = node.layer.style.pixelTop;
  y = node.displayNode(true, theTree.x, y);

  for(i=0; i<kids.length; i++)
    y = kids[i].displayTree(node.isOpen, theTree.x, y);

  // all nodes with index greater than this node are physically
  // below it on the screen (and should need moving vertically)
  deepestTodo = node.depth;
  for(var i=node.index+1; i<theTree.flattened.length; i++)
    if (theTree.flattened[i].depth <= deepestTodo) {
      deepestTodo = theTree.flattened[i].depth;
      y = theTree.flattened[i].displayTree(true, theTree.x, y);
    }
}

var lastnode=null;
function updatelast() {
  theTree.indexNodes();
//  theTree.flattened = theTree.flattened.slice(0,lastnode.index+1).concat(
//                      lastnode.flatten(true, -1, true)).concat(
//                      theTree.flattened.slice(lastnode.index));
  update(lastnode);
}



