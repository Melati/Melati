@echo off

REM $Id$

REM -----------------------------------------------------------
REM The targets are the different build scripts.
REM The default "jar" is suggested
REM and does not require any external packages
REM 
REM "compile"           target builds Melati core classes
REM "clean"          target removes bin directory
REM "jar"           target builds "core" + jar file
REM "javadocs"        target builds the javadocs
REM
REM -----------------------------------------------------------
set TARGET=%1%
REM set TARGET=javadocs
REM set TARGET=compile
REM set TARGET=clean
REM set TARGET=jar

set OLDCP=%CLASSPATH%

REM -------------------------------------------------------------------
REM Define the paths to each of the packages.
REM -------------------------------------------------------------------
REM none currently
REM It is assumed that the following is on the classpath before this script is run
REM   The standard Java servlet jar from Sun - usually jsdk.jar
REM   The webmacro build that you are using - currently recommended to be webmacro-19-07-2000
REM   The Postgres jdbc drivers - for example jdbc7.0-1.2.jar

set ODMG=..\poem\odmg\required-libs\odmg3.jar

REM --------------------------------------------
REM No need to edit anything past here
REM --------------------------------------------
set BUILDFILE=build-melati.xml

if "%TARGET%" == "" goto setdist
goto final

:setdist
set TARGET=jar
goto final

:final

REM set JIKES to be non-null (eg set JIKES=Y) to use the jikes compiler - its a bit faster
REM get it from here... http://oss.software.ibm.com/developerworks/opensource/jikes/project
set JAVAC=classic
if not "%JIKES%" == "" set JAVAC=jikes

if "%JAVA_HOME%" == "" goto javahomeerror
if exist %JAVA_HOME%\lib\tools.jar set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib\tools.jar

echo Now building %TARGET%...

set CLASSPATH=%CLASSPATH%;ant.jar;jaxp.jar;parser.jar;%ODMG%
set CP=%CLASSPATH%

echo Classpath: %CLASSPATH%
echo JAVA_HOME: %JAVA_HOME%
echo JAVAC:     %JAVAC%

%JAVA_HOME%\bin\java.exe -DJAVAC=%JAVAC% org.apache.tools.ant.Main -buildfile %BUILDFILE% %TARGET%

goto end

REM -----------ERROR-------------
:javahomeerror
echo "ERROR: JAVA_HOME not found in your environment."
echo "Please, set the JAVA_HOME variable in your environment to match the"
echo "location of the Java Virtual Machine you want to use."

:end
set CLASSPATH=%OLDCP%
