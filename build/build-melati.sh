#!/bin/sh

# -----------------------------------------------------------
# The targets are the different build scripts.
# The default "jar" is suggested
# and does not require any external packages
# 
# "compile"           target builds Melati core classes
# "clean"          target removes bin directory
# "jar"           target builds "core" + jar file
# "javadocs"        target builds the javadocs
#
# $Id$
# -----------------------------------------------------------
TARGET=${1}
# TARGET=javadocs
# TARGET=compile
# TARGET=clean
# TARGET=jar

PATH=$JAVA_HOME/bin:$PATH

OLDCP=$CLASSPATH

#-------------------------------------------------------------------
# Define the paths to each of the packages
#-------------------------------------------------------------------
# It is assumed that the following is on the classpath before this script is run
#   The standard Java servlet jar from Sun - usually jsdk.jar
#   The webmacro build that you are using - currently recommended to be webmacro-19-07-2000
#   The Postgres jdbc drivers - for example jdbc7.0-1.2.jar
#
# Assumed that the following are in melati/CVS
export ODMG=../poem/odmg/required-libs/odmg3.jar

# for jobs that use melati - need melati itself on classpath
export MELATI_JAR=../../../../lib/melati-1.0.jar

#--------------------------------------------
# No need to edit anything past here
#--------------------------------------------
if test -z "${JAVA_HOME}" ; then
    echo "ERROR: JAVA_HOME not found in your environment."
    echo "Please, set the JAVA_HOME variable in your environment to match the"
    echo "location of the Java Virtual Machine you want to use."
    exit
fi

if ! javaversion=`java -version 2>&1` ; then
  echo -e >&2 "When I try to run java -version, this happens:\n"
  java -version
  exit 1
fi

if echo "$javaversion" | grep -q '[^0-9.]1\.1' ; then
  echo >&2 -e "Sorry, you must use JDK1.2 with Melati now."
  exit 1
fi

javap javax.servlet.http.HttpServlet >/dev/null
RC=$?
if [ "$RC" -eq "1" ] ; then
   echo -e >&2 "I cannot find the http servlet class on the classpath..."
   exit 1
fi

javap org.postgresql.Driver >/dev/null
RC=$?
if [ "$RC" -eq "1" ] ; then
   echo -e >&2 "I cannot find the postgresql jdbc driver on the classpath..."
   exit 1
fi

javap org.webmacro.engine.WMParser >/dev/null
RC=$?
if [ "$RC" -eq "1" ] ; then
   echo -e >&2 "I cannot find the webmacro stuff on the classpath..."
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

if test -z "${TARGET}" ; then 
TARGET=jar
fi

if test -f ${JAVA_HOME}/lib/tools.jar ; then
    CLASSPATH=${CLASSPATH}:${JAVA_HOME}/lib/tools.jar
fi

# set JIKES to be non-null (eg set JIKES=Y) to use the jikes compiler
# get it from here... http://oss.software.ibm.com/developerworks/opensource/jikes/project
if test -z "${JIKES}" ; then
	JAVAC=classic
else
	JAVAC=jikes
fi

echo "Now building ${TARGET}..."

# add onto the classpath the jars for ant, that is ant.jar and an xml parser...
export CLASSPATH=${CLASSPATH}:jaxp.jar:parser.jar:ant.jar:${ODMG}:${MELATI_JAR}

echo JAVAC = ${JAVAC}
echo CLASSPATH= ${CLASSPATH}

BUILDFILE=build-melati.xml

${JAVA_HOME}/bin/java -DJAVAC=${JAVAC} org.apache.tools.ant.Main -buildfile ${BUILDFILE} ${TARGET}


CLASSPATH=${OLDCP}
