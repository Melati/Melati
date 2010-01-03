Name:			melati
Version:		0.7.8
Release:		RC2%{?dist}
Summary:		Melati Java Website Development Suite. 

Group:			Development/Libraries/Java
License:		GNU General Public License version 2/Melati Software License
URL:			http://melati.org

Source0:		Melati-0-7-8-RC2.tgz
Patch1:         melati-no-mckoi.patch
Patch2:         melati-no-odmg.patch
Patch3:         melati-no-webmacro.patch
Patch4:         melati-dsd-version.patch
Patch5:			melati-no-cyclic-poem-dependency.patch
Patch6:			melati-no-postgres-jdbc1.patch
Patch7:			melati-TableSortedMapTest.patch
Patch8:			melati-no-jetty-plugin.patch
Patch9:			melati-no-tango.patch
Patch10:		melati-no-jwebunit.patch
Patch11:		melati-no-jsp.patch
Patch12:		melati-relative-hsqldbs.patch
Patch13:		melati-openjdk-relative-file-bug.patch
Patch14:		melati-automateCreationOfVmfromWmDuringRpmDevelopment.patch
#Patch14:		melati-noCreationOfVmFromWm.patch
Patch15:		melati-mockHttpSession.patch
Patch16:		melati-java-1.4-1.6.patch
Patch17:		melati-no-PassbackAccessPoemExceptionHandling.patch
Patch18:		melati-missing-servletContext.patch
# This would be a lot more work now, so BuildRequires mockobjects, currently from jpackage-generic-5.0 repository
#Patch19:		melati-no-mockobjects.patch
Patch20:		melati-no-exec-plugin.patch
#Patch21:		melati-skip-example-contacts.patch

BuildRoot:		%{_tmppath}/%{name}-%{version}-%{release}-root-%(%{__id_u} -n)

BuildRequires:	jpackage-utils
BuildRequires:	mockobjects
BuildRequires:	java-devel
BuildRequires:	maven2

BuildRequires:	maven2-plugin-compiler
BuildRequires:	maven2-plugin-install
BuildRequires:	maven2-plugin-jar
BuildRequires:	maven2-plugin-javadoc
BuildRequires:	maven2-plugin-release
BuildRequires:	maven2-plugin-resources
BuildRequires:	maven2-plugin-surefire
BuildRequires:	maven2-plugin-war
BuildRequires:	maven2-plugin-source
BuildRequires:	maven2-plugin-pmd
BuildRequires:	maven2-plugin-assembly
BuildRequires:	maven2-plugin-site
BuildRequires:	mysql-connector-java

Requires:       jpackage-utils

Requires(post):       jpackage-utils
Requires(postun):     jpackage-utils

Requires:       java

%description

%package javadoc
Summary:        Javadocs for %{name}
Group:          Development/Documentation
Requires:       %{name}-%{version}-%{release}
Requires:       jpackage-utils

%description javadoc
This package contains the API documentation for %{name}.

%package manual
Summary:        Manual for %{name}
Group:          Development/Documentation
Requires:       jpackage-utils
Requires:       %{name}-%{version}-%{release}

%description manual
The manual for %{name}.

%prep
%setup -q -c
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
sed -e"s/\(case \)'.*'\(: return \"&pound;\";\)/\1163\2/" < ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/util/HTMLUtils.java.orig > ${RPM_BUILD_DIR}/%{name}-%{version}/melati/src/main/java/org/melati/util/HTMLUtils.java
%patch17 -p1
%patch18 -p1
%patch20 -p1
#%patch21 -p1

%build
export MAVEN_REPO_LOCAL=$(pwd)/.m2/repository
mkdir -p $MAVEN_REPO_LOCAL
#mvn-jpp -ff -Dmaven.repo.local=$MAVEN_REPO_LOCAL -Dmaven2.jpp.depmap.file=melati-depmap.xml install javadoc:javadoc
mvn-jpp -ff -P nojetty -Dmaven.repo.local=$MAVEN_REPO_LOCAL -Dmaven2.jpp.depmap.file=melati-depmap.xml install

%install
export RELEASE_TIMESTAMP=20090930.091550
export RELEASE_BUILD=RC2-${RELEASE_TIMESTAMP}

install -d -m 755 $RPM_BUILD_ROOT%{_javadir}
install -d -m 755 $RPM_BUILD_ROOT%{_javadocdir}
install -d -m 755 $RPM_BUILD_ROOT%{_datadir}/maven2/poms

install -pm 644 .m2/repository/org/melati/melati-parent/0.7.8-RC2-SNAPSHOT/melati-parent-0.7.8-RC2-SNAPSHOT.pom \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-melati-parent.pom
install -pm 644 .m2/repository/org/melati/melati-skin/1.0-SNAPSHOT/melati-skin-1.0-SNAPSHOT.pom \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-melati-skin.pom

install -pm 644 melati/target/melati-0.7.8-RC2-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/melati-0.7.8.jar
ln -s melati-0.7.8.jar $RPM_BUILD_ROOT%{_javadir}/melati.jar
%add_to_maven_depmap %{name} melati 0.7.8 JPP melati
install -pm 644 .m2/repository/org/melati/melati/0.7.8-RC2-SNAPSHOT/melati-0.7.8-RC2-SNAPSHOT.pom \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-melati.pom

install -pm 644 melati/target/melati-0.7.8-RC2-SNAPSHOT-tests.jar \
	$RPM_BUILD_ROOT%{_javadir}/melati-0.7.8-tests.jar
ln -s melati-0.7.8-tests.jar $RPM_BUILD_ROOT%{_javadir}/melati-tests.jar

install -pm 644 melati-archetype/target/melati-archetype-0.7.8-RC2-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/melati-archetype-0.7.2.jar
ln -s melati-archetype-0.7.2.jar $RPM_BUILD_ROOT%{_javadir}/melati-archetype.jar
%add_to_maven_depmap %{name} melati-archetype 0.7.8 JPP melati-archetype
install -pm 644 .m2/repository/org/melati/melati-archetype/0.7.8-RC2-SNAPSHOT/melati-archetype-0.7.8-RC2-SNAPSHOT.pom \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-melati-archetype.pom

install -pm 644 throwing-jdbc/target/throwing-jdbc-0.7.8-RC2-SNAPSHOT-jdbc4.jar \
	$RPM_BUILD_ROOT%{_javadir}/throwing-jdbc-0.7.2-jdbc4.jar
ln -s throwing-jdbc-0.7.2-jdbc4.jar $RPM_BUILD_ROOT%{_javadir}/throwing-jdbc-jdbc4.jar
%add_to_maven_depmap %{name} throwing-jdbc-jdbc4 0.7.8 JPP throwing-jdbc-jdbc4

install -pm 644 throwing-jdbc/target/throwing-jdbc-0.7.8-RC2-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/throwing-jdbc-0.7.8.jar
ln -s throwing-jdbc-0.7.8.jar $RPM_BUILD_ROOT%{_javadir}/throwing-jdbc.jar
%add_to_maven_depmap %{name} throwing-jdbc 0.7.8 JPP throwing-jdbc
install -pm 644 .m2/repository/org/melati/throwing-jdbc/0.7.8-RC2-SNAPSHOT/throwing-jdbc-0.7.8-RC2-SNAPSHOT.pom \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-throwing-jdbc.pom

install -pm 644 poem/target/poem-0.7.8-RC2-SNAPSHOT-tests.jar \
	$RPM_BUILD_ROOT%{_javadir}/poem-0.7.8-tests.jar
ln -s poem-0.7.8-tests.jar $RPM_BUILD_ROOT%{_javadir}/poem-tests.jar

install -pm 644 poem/target/poem-0.7.8-RC2-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/poem-0.7.8.jar
ln -s poem-0.7.8.jar $RPM_BUILD_ROOT%{_javadir}/poem.jar
%add_to_maven_depmap %{name} poem 0.7.8 JPP poem
install -pm 644 .m2/repository/org/melati/poem/0.7.8-RC2-SNAPSHOT/poem-0.7.8-RC2-SNAPSHOT.pom \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-poem.pom

install -pm 644 maven-dsd-plugin/target/maven-dsd-plugin-1.1-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/maven-dsd-plugin-1.1.jar
ln -s maven-dsd-plugin-1.1.jar $RPM_BUILD_ROOT%{_javadir}/maven-dsd-plugin.jar
%add_to_maven_depmap %{name} maven-dsd-plugin 1.1 JPP maven-dsd-plugin
install -pm 644 .m2/repository/org/melati/maven-dsd-plugin/1.1-SNAPSHOT/maven-dsd-plugin-1.1-SNAPSHOT.pom \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-maven-dsd-plugin.pom

install -pm 644 melati-example-contacts/target/melati-example-contacts-0.7.8-RC2-SNAPSHOT.jar \
	$RPM_BUILD_ROOT%{_javadir}/melati-example-contacts-0.7.2.jar
ln -s melati-example-contacts-0.7.2.jar $RPM_BUILD_ROOT%{_javadir}/melati-example-contacts.jar
%add_to_maven_depmap %{name} melati-example-contacts 0.7.8 JPP melati-example-contacts
install -pm 644 .m2/repository/org/melati/melati-example-contacts/0.7.8-RC2-SNAPSHOT/melati-example-contacts-0.7.8-RC2-SNAPSHOT.pom \
	$RPM_BUILD_ROOT%{_datadir}/maven2/poms/JPP-melati-example-contacts.pom

# This is just a test
install -pm 644 melati/target/melati-0.7.8-RC2-SNAPSHOT.jar \
$RPM_BUILD_ROOT%{_javadocdir}/%{name}

%add_to_maven_depmap org.apache.maven %{name} %{version} JPP %{name}



%clean
rm -rf $RPM_BUILD_ROOT


%files
%defattr(-,root,root,-)
%doc



%changelog
