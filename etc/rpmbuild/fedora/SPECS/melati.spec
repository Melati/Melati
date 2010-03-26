Name:		melati
Version:	0.7.8.RC2
Release:	1%{?dist}
Epoch:		0
Summary:	Java Website Development Suite
Group:		Development/Libraries/Java
License:	GPLv2 or Melati Software License
URL:		http://melati.org

Source0:	Melati-0-7-8-RC2.tgz
Patch1:	melati-no-mckoi.patch
Patch2:	melati-no-odmg.patch
Patch3:	melati-no-webmacro.patch
Patch4:	melati-dsd-version.patch
Patch5:	melati-no-cyclic-poem-dependency.patch
Patch6:	melati-no-postgres-jdbc1.patch
Patch7:	melati-TableSortedMapTest.patch
Patch8:	melati-no-jetty-plugin.patch
Patch9:	melati-no-tango.patch
Patch10:	melati-no-jwebunit.patch
Patch11:	melati-no-jsp.patch
Patch12:	melati-relative-hsqldbs.patch
Patch13:	melati-openjdk-relative-file-bug.patch
Patch14:	melati-automateCreationOfVmfromWmDuringRpmDevelopment.patch
# This was a planned change but is not essential now
#Patch14:	melati-noCreationOfVmFromWm.patch
Patch15:	melati-mockHttpSession.patch
Patch16:	melati-java-1.4-1.6.patch
Patch17:	melati-no-PassbackAccessPoemExceptionHandling.patch
Patch18:	melati-missing-servletContext.patch
# This would be a lot more work now, so BuildRequires mockobjects, currently from jpackage-generic-5.0 repository
#Patch19:	melati-no-mockobjects.patch
Patch20:	melati-no-exec-plugin.patch
#Patch21:	melati-skip-example-contacts.patch
Patch22:	melati-RC1-to-RC2.patch
Patch23:	melati-velocity-config.patch
Patch24:	melati-avalon-logkit.patch
Patch25:	melati-hsql-webapp-data-dir.patch
Patch26:	melati-tomcat-static-path.patch
Patch27:	melati-webapp-startup-transaction-issue.patch
Patch28:	melati-text-length.patch
Patch29:	melati-duplicate-nested-import.patch
%if "%fc12" == "1"
Patch30:	melati-no-archetype.patch
%endif

BuildRoot:	%{_tmppath}/%{name}-%{version}-%{release}-root-%(%{__id_u} -n)

BuildArch:	noarch

BuildRequires:	jpackage-utils >= 1.7.5
BuildRequires:	java-devel >= 1:1.6.0
BuildRequires:	maven2 >= 2.0.4

BuildRequires:	maven2-plugin-compiler
BuildRequires:	maven2-plugin-install
BuildRequires:	maven2-plugin-jar
BuildRequires:	maven2-plugin-javadoc
#BuildRequires:	maven2-plugin-release
BuildRequires:	maven2-plugin-resources
BuildRequires:	maven2-plugin-surefire
BuildRequires:	maven2-plugin-war
BuildRequires:	maven2-plugin-source
BuildRequires:	maven2-plugin-pmd
BuildRequires:	maven2-plugin-assembly
BuildRequires:	maven2-plugin-site
BuildRequires:	maven2-plugin-plugin
BuildRequires:	maven-plugin-build-helper
BuildRequires:	maven-plugin-cobertura
BuildRequires:	maven-surefire-provider-junit

BuildRequires:	junit >= 3.8.1
BuildRequires:	jakarta-oro >= 2.0.8
BuildRequires:	jakarta-commons-collections >= 3.0
BuildRequires:	velocity = 1.4
BuildRequires:	mockobjects >= 0.09
BuildRequires:	log4j >= 1.2.9
BuildRequires:	jetty >= 5.1.15
BuildRequires:	servlet_2_4_api
BuildRequires:	concurrent >= 1.3.4 
BuildRequires:	jakarta-commons-codec >= 1.3
BuildRequires:	hsqldb >= 1.8.0.7
BuildRequires:	postgresql >= 7.4
BuildRequires:	postgresql-jdbc >= 7.4
BuildRequires:	mysql-connector-java >= 5.0.4

Requires:	jpackage-utils >= 1.7.5
Requires:	java >= 1:1.6.0
Requires:	jakarta-oro >= 2.0.8
Requires:	jakarta-commons-collections >= 3.0
Requires:	velocity = 1.4
Requires:	log4j >= 1.2.9
Requires:	jetty >= 5.1.15
Requires:	servlet_2_4_api
Requires:	hsqldb >= 1.8.0.7
Requires:	%{name}-poem = %{epoch}:%{version}-%{release}

Requires(post):		jpackage-utils >= 1.7.5
Requires(postun):	jpackage-utils >= 1.7.5

%description
Melati is a tool for producing documents from databases and is particularly
good for building websites backed by a database. It is written in Java and
provides the following features:
- a generic database administration system, allowing the site manager to edit
both the contents and the structure of the database through a simple web
interface.
- a templating engine, using "templets" provides an easy mechanism for
rendering persistent objects as interface elements (eg HTML input boxes,
dropdowns).
- a cookie-based or a HTTP-based access control mechanism.
- full integration with POEM (Persistent Object Engine for Melati). 

%package poem
Summary:	Persistent Object Engine for Melati
Group:	 	Development/Build Tools
Requires:	jpackage-utils >= 1.7.5
Requires:	java >= 1:1.6.0
Requires:	jakarta-oro >= 2.0.8
Requires:	jakarta-commons-collections >= 3.0
Requires:	log4j >= 1.2.9
Requires:	concurrent >= 1.3.4 
Requires:	jakarta-commons-codec >= 1.3
Requires:	hsqldb >= 1.8.0.7
# We should probably provide RPMs containing configuration for connecting melati or the contacts
# example to each of the supported DBs. TODO Get the contacts example RPM working, then decide.
#Requires:	postgresql-jdbc >= 7.4
#Requires:	mysql-connector-java >= 5.0.4
Requires(post):		jpackage-utils >= 1.7.5
Requires(postun):	jpackage-utils >= 1.7.5

%description poem
POEM provides:
- industrial-strength open source object database technology.
- an API which enables the database to be seen as a collection of Java objects,
 via a (genuinely!) easy-to-use transparent persistence layer on top of JDBC.
- a low-level security model allowing restrictions to be placed on tables, rows
and fields.
- a three way unification of jdbc, sql and java metadata.

%package devel
Summary:	DSD Processor and Maven archetype for development
Group:		Development/Build Tools
Requires:	%{name} = %{epoch}:%{version}-%{release}
Requires:	maven2 >= 2.0.4
Provides:	maven-dsd-plugin = %{epoch}:%{version}-%{release}
%if "%fc11" == "1"
Provides:	maven-archetype = %{epoch}:%{version}-%{release}
%endif
Provides:	throwing-jdbc = %{epoch}:%{version}-%{release}

%description devel
Tools for software development using Melati:
- The Data Structure Definition specifies details of the database structure
required by an application developed using POEM (Persistent Object Engine for
Melati). This DSD processor is then used to generate Java from the DSD.
- The maven-dsd-plugin enables the DSD processor to be used in a Maven build,
instead of executing it directly from Java or from ant.
%if "%fc11" == "1"
- The Melati Archetype provides templates for creating Melati web development
projects.
%endif
Also throwing-jdbc is bundled. It is not required to develop software using
Melati. It can be used by unit tests during JDBC-based software development.
For example, it is used for unit tests during development of POEM. 

%package javadoc
Summary:	Javadocs for %{name}, POEM and development tools
Group:		Development/Documentation
Requires:	jpackage-utils

%description javadoc
This package contains the API documentation for:
- %{name}-throwing-jdbc
- %{name}
- %{name}-poem
- %{name}-maven-dsd-plugin
- %{name}-example-contacts

%package webapp
Summary:	Web application
Group:		Development/Documentation
Requires:	jpackage-utils
Requires:	hsqldb >= 1.8.0.7
Requires:	%{name} = %{epoch}:%{version}-%{release}

%description webapp
This package contains examples and the administration functionality.

%prep
%setup -q -c
cp ${RPM_SOURCE_DIR}/melati-maven-settings.xml ${RPM_BUILD_DIR}/%{name}-%{version}/melati-maven-settings.xml
cp ${RPM_SOURCE_DIR}/melati-depmap.xml ${RPM_BUILD_DIR}/%{name}-%{version}/melati-depmap.xml
%patch1 -p1
%patch2 -p1
%patch3 -p1
cp ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/test/java/org/melati/test/MelatiTest.java /tmp
%patch4 -p1
%patch5 -p1
# FIXME The following classes in org.melati.util use other melati packages and so must be packed accordingly
# grep -nH -e import *.java | grep -v "import java"
# These use the poem module
#ChildrenDrivenMutableTree.java:48:import org.melati.poem.Treeable;
#JSStaticTree.java:45:import org.melati.poem.Treeable;
#MelatiRuntimeException.java:48:import org.melati.poem.PoemException;
#Tree.java:48:import org.melati.poem.util.Order;
#Tree.java:49:import org.melati.poem.util.SortUtils;
#Tree.java:50:import org.melati.poem.Treeable;
#TreeNode.java:47:import org.melati.poem.Treeable;
# These use the melati module so cannot be moved to poem
#MD5Util.java:49:import org.melati.Melati;
#UTF8URLEncoder.java:50:import org.melati.Melati;
# and these are used in poem
mkdir -p ${RPM_BUILD_DIR}/%{name}-%{version}/poem/src/main/java/org/melati/util
mv ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/util/CountedDumbPagedEnumeration.java \
	${RPM_BUILD_DIR}/%{name}-%{version}/poem/src/main/java/org/melati/util
mv ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/util/PagedEnumeration.java \
	${RPM_BUILD_DIR}/%{name}-%{version}/poem/src/main/java/org/melati/util
mv ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/util/PagedEnumerationBase.java \
	${RPM_BUILD_DIR}/%{name}-%{version}/poem/src/main/java/org/melati/util
mkdir -p ${RPM_BUILD_DIR}/%{name}-%{version}/poem/src/test/java/org/melati/util/test/
mv ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/test/java/org/melati/util/test/CountedDumbPagedEnumerationTest.java \
	${RPM_BUILD_DIR}/%{name}-%{version}/poem/src/test/java/org/melati/util/test
mv ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/test/java/org/melati/util/test/PagedEnumerationSpec.java \
	${RPM_BUILD_DIR}/%{name}-%{version}/poem/src/test/java/org/melati/util/test
%patch6 -p1
%patch7 -p1
%patch8 -p1
%patch9 -p1
rm ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/template/webmacro/*.java
rm -r ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/test/java/org/melati/template/webmacro
find ${RPM_BUILD_DIR}/%{name}-%{version} -type f -name "*Webmacro*.java" -exec rm \{\} \;
rm ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/test/FlushingServletTest.java
%patch10 -p1
%patch11 -p1
rm ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/servlet/JspServlet.java
rm ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/test/java/org/melati/JettyWebTestCase.java
rm $(grep -El "extends (JettyWebTestCase|TemplateServletTestTest|ConfigServletTestTest)" ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/test/java/org/melati/{admin,test}/test/*.java)
rm ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/test/java/org/melati/test/test/HttpAuthenticationPoemServletTestTest.java
%patch12 -p1
%patch13 -p1
%patch14 -p1
%patch15 -p1
%patch16 -p1
# I think some encoding problem prevents patch from doing this, but sed works because it avoids the need to specify the exact char
mv ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/util/HTMLUtils.java{,.orig} 
# Actually use of seds s command worked in fedora 11 but not 12...
#sed -e"s/\(case \)'.*'\(: return \"&pound;\";\)/\1163\2/" < ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/util/HTMLUtils.java.orig > ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/util/HTMLUtils.java
# so use the c command instead...
sed -e"/.*': return \"&pound;\";/c\\      case 163: return \"&pound;\";" < ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/util/HTMLUtils.java.orig > ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/util/HTMLUtils.java
%patch17 -p1
%patch18 -p1
%patch20 -p1
#%patch21 -p1
%patch22 -p1
%patch23 -p1
%patch24 -p1
%patch25 -p1
%patch26 -p1
%patch27 -p1
%patch28 -p1
%patch29 -p1
%patch30 -p1

%build
%if "%{fc11}" == "1"
echo if fc11
%endif
%if "%{fc11}" != "1"
echo if not fc11
%endif
%if "%{fc12}" == "1"
echo if fc12
%endif
%if "%{fc12}" != "1"
echo if not fc12
%endif
export MAVEN_REPO_LOCAL=$(pwd)/.m2/repository
mkdir -p $MAVEN_REPO_LOCAL
mvn-jpp --fail-fast --activate-profiles nojetty --settings melati-maven-settings.xml -Dmaven.repo.local=$MAVEN_REPO_LOCAL -Dmaven2.jpp.depmap.file=melati-depmap.xml install javadoc:javadoc

%install
rm -rf $RPM_BUILD_ROOT
install -dm 755 $RPM_BUILD_ROOT%{_javadir}
install -dm 755 $RPM_BUILD_ROOT%{_javadocdir}/%{name}-%{version}
install -dm 755 $RPM_BUILD_ROOT%{_datadir}/maven2/poms
install -dm 755 $RPM_BUILD_ROOT%{_var}/lib/tomcat5/webapps
install -dm 755 $RPM_BUILD_ROOT%{_var}/lib/hsqldb/data/melati-webapp

install -pm 644 pom.xml \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-melati-parent.pom
#install -pm 644 melati-skin/pom.xml \
#	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-melati-skin.pom

install -pm 644 melati/target/melati-0.7.8-RC2-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/melati-0.7.8.jar
ln -s melati-0.7.8.jar $RPM_BUILD_ROOT%{_javadir}/melati.jar
%add_to_maven_depmap org.melati %{name} 0.7.8-RC2-SNAPSHOT JPP %{name}
install -pm 644 melati/pom.xml \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-melati.pom
cp -pr melati/target/site/apidocs \
	$RPM_BUILD_ROOT%{_javadocdir}/%{name}-%{version}/melati

install -pm 644 melati/target/melati-0.7.8-RC2-SNAPSHOT-tests.jar \
	$RPM_BUILD_ROOT%{_javadir}/melati-0.7.8-tests.jar
ln -s melati-0.7.8-tests.jar $RPM_BUILD_ROOT%{_javadir}/melati-tests.jar

%if "%fc11" == "1"
install -pm 644 melati-archetype/target/melati-archetype-1.0-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/melati-archetype-0.7.8.jar
ln -s melati-archetype-0.7.8.jar $RPM_BUILD_ROOT%{_javadir}/melati-archetype.jar
%add_to_maven_depmap org.melati %{name}-archetype 0.7.8-RC2-SNAPSHOT JPP %{name}-archetype
install -pm 644 melati-archetype/pom.xml \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-melati-archetype.pom
%endif

install -pm 644 throwing-jdbc/target/throwing-jdbc-0.7.8-RC2-SNAPSHOT-jdbc4.jar \
	$RPM_BUILD_ROOT%{_javadir}/throwing-jdbc-0.7.8-jdbc4.jar
ln -s throwing-jdbc-0.7.8-jdbc4.jar $RPM_BUILD_ROOT%{_javadir}/throwing-jdbc-jdbc4.jar
%add_to_maven_depmap org.melati throwing-jdbc-jdbc4 0.7.8-RC2-SNAPSHOT JPP throwing-jdbc-jdbc4

install -pm 644 throwing-jdbc/target/throwing-jdbc-0.7.8-RC2-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/throwing-jdbc-0.7.8.jar
ln -s throwing-jdbc-0.7.8.jar $RPM_BUILD_ROOT%{_javadir}/throwing-jdbc.jar
%add_to_maven_depmap org.melati throwing-jdbc 0.7.8-RC2-SNAPSHOT JPP throwing-jdbc
install -pm 644 throwing-jdbc/pom.xml \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-throwing-jdbc.pom
cp -pr throwing-jdbc/target/site/apidocs \
	$RPM_BUILD_ROOT%{_javadocdir}/%{name}-%{version}/throwing-jdbc

install -pm 644 poem/target/poem-0.7.8-RC2-SNAPSHOT-tests.jar \
	$RPM_BUILD_ROOT%{_javadir}/poem-0.7.8-tests.jar
ln -s poem-0.7.8-tests.jar $RPM_BUILD_ROOT%{_javadir}/poem-tests.jar

install -pm 644 poem/target/poem-0.7.8-RC2-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/poem-0.7.8.jar
ln -s poem-0.7.8.jar $RPM_BUILD_ROOT%{_javadir}/poem.jar
%add_to_maven_depmap org.melati poem 0.7.8-RC2-SNAPSHOT JPP poem
install -pm 644 poem/pom.xml \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-poem.pom
cp -pr poem/target/site/apidocs \
	$RPM_BUILD_ROOT%{_javadocdir}/%{name}-%{version}/poem

install -pm 644 maven-dsd-plugin/target/maven-dsd-plugin-1.1-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/maven-dsd-plugin-1.1.jar
ln -s maven-dsd-plugin-1.1.jar $RPM_BUILD_ROOT%{_javadir}/maven-dsd-plugin.jar
%add_to_maven_depmap org.melati maven-dsd-plugin 0.7.8-RC2-SNAPSHOT JPP maven-dsd-plugin
install -pm 644 maven-dsd-plugin/pom.xml \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-maven-dsd-plugin.pom

cp -pr maven-dsd-plugin/target/site/apidocs \
	$RPM_BUILD_ROOT%{_javadocdir}/%{name}-%{version}/maven-dsd-plugin

#install -pm 644 melati-webapp/target/melati-webapp.war \
#	$RPM_BUILD_ROOT%{_var}/lib/tomcat5/webapps/melati-webapp.war
cp -pr melati-webapp/target/melati-webapp \
	$RPM_BUILD_ROOT%{_var}/lib/tomcat5/webapps/melati-webapp
install -pm 644 melati-webapp/pom.xml \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-melati-webapp.pom

install -pm 644 melati-example-contacts/target/melati-example-contacts-0.7.8-RC2-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/melati-example-contacts-0.7.8.jar
ln -s melati-example-contacts-0.7.8.jar $RPM_BUILD_ROOT%{_javadir}/melati-example-contacts.jar
%add_to_maven_depmap org.melati %{name}-example-contacts 0.7.8-RC2-SNAPSHOT JPP %{name}-example-contacts
install -pm 644 melati-example-contacts/pom.xml \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-melati-example-contacts.pom
cp -pr melati-example-contacts/target/site/apidocs \
	$RPM_BUILD_ROOT%{_javadocdir}/%{name}-%{version}/melati-example-contacts

# manual
install -dm 755 $RPM_BUILD_ROOT%{_docdir}/%{name}-%{version}
cp -p COPYING $RPM_BUILD_ROOT%{_docdir}/%{name}-%{version}
cp -p CREDITS.txt $RPM_BUILD_ROOT%{_docdir}/%{name}-%{version}
cp -p LICENSE-GPL.txt $RPM_BUILD_ROOT%{_docdir}/%{name}-%{version}
cp -p LICENSE-MSL.txt $RPM_BUILD_ROOT%{_docdir}/%{name}-%{version}
cp -p LICENSE.txt $RPM_BUILD_ROOT%{_docdir}/%{name}-%{version}
cp -p NOTICE.txt $RPM_BUILD_ROOT%{_docdir}/%{name}-%{version}

%clean
rm -rf $RPM_BUILD_ROOT

%post
%update_maven_depmap

%postun
%update_maven_depmap

%post poem
%update_maven_depmap

%postun poem
%update_maven_depmap

%post devel
%update_maven_depmap

%postun devel
%update_maven_depmap

%post webapp
%update_maven_depmap


%postun webapp
%update_maven_depmap

%files
%defattr(-,root,root,-)
%{_datadir}/maven2/poms/JPP-melati.pom
%config %{_mavendepmapfragdir}
%{_javadir}/melati-0.7.8.jar
%{_javadir}/melati.jar
%{_javadir}/melati-0.7.8-tests.jar
%{_javadir}/melati-tests.jar
%doc %{_docdir}/%{name}-%{version}/*

%files poem
%defattr(-,root,root,-)
%{_datadir}/maven2/poms/JPP-melati-parent.pom
%{_datadir}/maven2/poms/JPP-poem.pom
%{_javadir}/poem-0.7.8.jar
%{_javadir}/poem.jar
%{_javadir}/poem-0.7.8-tests.jar
%{_javadir}/poem-tests.jar

%files devel
%defattr(-,root,root,-)
%{_datadir}/maven2/poms/JPP-throwing-jdbc.pom
%if "%fc11" == "1"
%{_datadir}/maven2/poms/JPP-melati-archetype.pom
%endif
%{_datadir}/maven2/poms/JPP-maven-dsd-plugin.pom
%{_javadir}/throwing-jdbc-0.7.8-jdbc4.jar
%{_javadir}/throwing-jdbc-jdbc4.jar
%{_javadir}/throwing-jdbc-0.7.8.jar
%{_javadir}/throwing-jdbc.jar
%if "%fc11" == "1"
%{_javadir}/melati-archetype-0.7.8.jar
%{_javadir}/melati-archetype.jar
%endif
%{_javadir}/maven-dsd-plugin-1.1.jar
%{_javadir}/maven-dsd-plugin.jar

%files javadoc
%defattr(-,root,root,-)
%{_javadocdir}/%{name}-%{version}/melati
%{_javadocdir}/%{name}-%{version}/throwing-jdbc
%{_javadocdir}/%{name}-%{version}/poem
%{_javadocdir}/%{name}-%{version}/maven-dsd-plugin
%{_javadocdir}/%{name}-%{version}/melati-example-contacts

%files webapp
%defattr(-,root,root,-)
%{_datadir}/maven2/poms/JPP-melati-webapp.pom
%{_var}/lib/tomcat5/webapps/melati-webapp
#%{_var}/lib/tomcat5/webapps/melati-webapp.war
#%{_datadir}/maven2/poms/JPP-melati-skin.pom
%{_datadir}/maven2/poms/JPP-melati-example-contacts.pom
%{_javadir}/melati-example-contacts-0.7.8.jar
%{_javadir}/melati-example-contacts.jar
%defattr(-,tomcat,tomcat,-)
%{_var}/lib/hsqldb/data/melati-webapp

%changelog
* Sat Jan 23 2010 James Michael Wright <jim@wright.cz> - 0:0.7.8.RC2-1
- First version. Committed to melati.org:/usr/cvsroot but under development.
