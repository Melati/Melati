#!/bin/bash
#
# $Source$
# $Revision$
#
# Goes with Melati (http://melati.org), a framework for the rapid
# development of clean, maintainable web applications.  You may do
# whatever you like with the contents of this file, but Melati itself
# is copyrighted, and released under the GNU General Public License.
#

set -e

JAVAC=javac
#JAVAC=jikes

if ! javaversion=`java -version 2>&1` ; then
  echo -e >&2 "When I try to run java -version, this happens:\n"
  java -version
  exit 1
fi

if echo "$javaversion" | grep -q '[^0-9.]1\.1' ; then
  echo >&2 -e "Sorry, you must use JDK1.2 with Melati now."
  exit 1
fi

hereRaw=`dirname $0`
if [ "$hereRaw" = . ] ; then
  here=
else
  here="$hereRaw/"
fi

echo -e "Compiling Melati using $JAVAC\n"

ldb="$here"org.melati.LogicalDatabase.properties
ldbe="$ldb.example"

if [ ! -e "$ldb" ] && [ -e "$ldbe" ] ; then
   echo >&2 -e "*** warning: $ldb not found, \nso creating new $ldb from" \
               "$ldbe\n\n"
   cp $ldbe $ldb
fi

[ -e "$ldb" ] &&
    wronglines=`grep -v '^[[:space:]]*[#!]' "$ldb" | fgrep -n jdbc1` &&
  echo -e >&2 "*** warning: your $ldb mentions jdbc1 rather than jdbc2" \
              "in the following lines:\n\n$wronglines\n\n" \
              "You must use JDK1.2 with Melati now, so you should change them to jdbc2."

find "$hereRaw" -path "$hereRaw/playing" -prune -o -name '*.java' -print0 | xargs -0 $JAVAC
