#!/bin/bash

set -e

if ! javaversion=`java -version 2>&1` ; then
  echo -e >&2 "When I try to run java -version, this happens:\n"
  java -version
  exit 1
fi

if echo "$javaversion" | fgrep -q 1.1 ; then
  if ! jikes 2>&1 | fgrep -q Jikes ; then
    echo >&2 -e "With java 1.1 you must use Jikes: javac has a known problem." \
                "But when I try to run jikes, this happens:\n"
    jikes
    exit 1
  fi

  JAVAC=jikes
  JDBC=jdbc1
  wrongJDBC=jdbc2
  JDBC_descr='JDBC 1.1'
else
  JAVAC=javac  
  JDBC=jdbc2
  wrongJDBC=jdbc1
  JDBC_descr='JDBC 1.2'
fi

here=`dirname $0`
if [ "$here" = . ] ; then
  here=
else
  here="$here/"
fi

echo -e "Compiling Melati using $JAVAC for use with $JDBC_descr\n"

ldb="$here"org.melati.LogicalDatabase.properties
ldbe="$ldb.example"

if [ ! -e "$ldb" ] && [ -e "$ldbe" ] ; then
   echo >&2 -e "*** warning: $ldb not found, \nso creating new $ldb from" \
               "$ldbe\n\n"
   cp $ldbe $ldb
fi

[ -e "$ldb" ] &&
    wronglines=`grep -v '^[[:space:]]*[#!]' "$ldb" | fgrep -n $wrongJDBC` &&
  echo -e >&2 "*** warning: your $ldb mentions $wrongJDBC rather than $JDBC" \
              "in the following lines:\n\n$wronglines\n\n" \
              "You should probably change them to $JDBC."

$JAVAC "$here"{,admin/,poem/{,prepro/,postgresql/$JDBC/}}*.java
