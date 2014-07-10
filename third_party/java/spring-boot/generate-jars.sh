# Copyright 2014-present Deezer.com
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

VERSION='1.1.4';
TMPDIR='/tmp/spring-boot';
rm -rf $TMPDIR 2> /dev/null;
mkdir $TMPDIR;
cp pom.xml $TMPDIR;
mkdir -p $TMPDIR/src/main/resources;
cp application.properties $TMPDIR/src/main/resources;

# Build basic package: $TMPDIR/target/spring-boot-buck.jar
cd $TMPDIR;
mvn package;
cd -;

# Copy library
cp $TMPDIR/target/spring-boot-buck.jar spring-boot-$VERSION.jar;

# Generate flattened jar
TMPDIR_REZIP='/tmp/spring-boot-rezip';
CUR=`pwd`;
rm -rf $TMPDIR_REZIP 2> /dev/null;
mkdir $TMPDIR_REZIP;
unzip -o $TMPDIR/target/spring-boot-buck.jar -x META-INF -d $TMPDIR_REZIP;
for jar in $TMPDIR_REZIP/lib/*.jar; do
    unzip -qq -o $jar -d $TMPDIR_REZIP;
done

rm -rf $TMPDIR_REZIP/META-INF/ECLIPSEF.* $TMPDIR_REZIP/lib; #$TMP/about_files $TMP/lib $TMP/about.html $TMP/jetty-dir.css $TMP/overview.html $TMP/plugin.properties $TMP/readme.txt;

cd $TMPDIR_REZIP;
zip -9 -qq -r $CUR/spring-boot-deps-$VERSION.jar *;
cd $CUR;
