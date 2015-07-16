Weslang
=======
Weslang: is a standalone WEb Service to detect the LANGuage of a given piece
of text.

[![Build Status](https://travis-ci.org/deezer/weslang.svg?branch=master)](https://travis-ci.org/deezer/weslang)

It works by executing both [CLD2](https://code.google.com/p/cld2/) and
[Language-Detection](https://code.google.com/p/language-detection/).

The exposed API is very simple:

```
host:8080/detect?q=<TEXT>
```

The endpoint also supports `POST` requests in case longer payloads are required.

The response will be a JSON document like the following:

```javascript
{
    "language": "en",
    "confidence": 0.99
}
```

Where language is the ISO_639-1 code of the language (except for Chinese in
which the locale is also returned, that is the result would be either zh-cn or
zh-tw).

Additional endpoints for checking the health of the webservice will be exposed
at localhost:9001.

Among the endopoints one can find

* http://localhost:9001/health
* http://localhost:9001/metrics
* http://localhost:9001/env
* http://localhost:9001/info
* http://localhost:9001/mappings
* http://localhost:9001/trace

This is done automatically by the Spring-Boot framework, via the
[Actuator plugin](http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready).

Components
==========

This project includes two components that could easily be a project on their
own.

Java Bindings for CLD2
----------------------
Using [JNA](https://github.com/twall/jna) a Java interface is exposed for
getting the language via the [CLD2 library](https://code.google.com/p/cld2/).

The code lives in `//java/com/deezer/research/cld2`

Fork of Language-Detection
--------------------------
In `//third-party/java/language-detection-v2` we have a fork of
[language-detection](https://code.google.com/p/language-detection/).

The main changes we did, was to remove the randomization and some performance
improvements. See the file THIRD_PARTY.yaml in that folder for a comprehensive
list of changes.

Building and Testing
====================

To build and test this project [BUCK](https://facebook.github.io/buck/) is
required and also Java 7. That means that it cannot be built under Windows.

```bash
$ buck test --all
$ buck build //java/com/deezer/research/language:detection_app
```

Running
=======

The build command generated a file called
`buck-out/gen/java/com/deezer/research/language/detection_app.jar`, which is a
self contained binary.

To run it just execute:

```bash
$ java -jar detection_app.jar
```

If for some reason you don't want or can't execute both detectors, you could
run:

```bash
$ java -jar detection_app.jar --spring.profiles.active=java_only
$ java -jar detection_app.jar --spring.profiles.active=cld2
$ java -jar detection_app.jar --spring.profiles.active=both
```

Currently the Cld2 bindings are only generated for `linux-x86-64`, so if your
machine is different it probably won't work. In such a case, just execute it
with the `java_only` profile.

Launch service using docker
--------------------------
The service can be also launched using docker. It requires the installation of `docker` and `docker-compose`. See [docker home page](https://docs.docker.com/) for installation references.

Once docker is installed execute
```bash
$ docker-compose up -d
```
this will build the image of the service and launch it. To see on which port the service is exposed,
execute `docker-compose ps`. This will display a table similar to the example below:
```bash
$ docker-compose ps
    Name                   Command               State                        Ports
----------------------------------------------------------------------------------------------------
weslang_api_1   java -jar buck-out/gen/jav ...   Up      0.0.0.0:32774->8080/tcp
```
in above example, the service on port `8080` is exposed on port `32774`.

On Mac OsX, docker runs on a VM. To know the actual ip, it can be executed the following command:
```bash
$ boot2docker ip
192.168.59.103
```
in above example, the service can be queried by executing:
```bash
$ python -c "import urllib;\
print(urllib.urlopen('http://192.168.59.103:32774/detect?q=hello%20world').read());" | \
python -m json.tool
{
    "confidence": 0.7706013715278043,
    "language": "en"
}
```

The health endpoint running on port 9001 can be accessed by executing the following command:
```bash
$ docker exec CONTAINER_ID python -c "import urllib;\
print(urllib.urlopen('http://127.0.0.1:9001/health').read());" | \
python -m json.tool
{
    "status": "UP"
}
```
the container id can be obtained by executing `docker ps`. For example:
```bash
$ docker ps
CONTAINER ID        IMAGE                COMMAND                CREATED             STATUS ...
a88826098461        weslang_api:latest   "java -jar buck-out/   21 hours ago        Up 21 hours
```
which outputs a CONTAINER_ID equals to `a88826098461`.

Credits
=======

This project is possible to several Open Source Projects

* [Spring-Boot](http://projects.spring.io/spring-boot/): Java Framework.
* [CLD2](https://code.google.com/p/cld2/): The language detector built into Chrome.
* [Language-Detection](https://code.google.com/p/language-detection/): Language detector provided by Cybozu Labs.
* [BUCK](https://facebook.github.io/buck/): build system released by Facebook.
* [Guava](https://code.google.com/p/guava-libraries/): Additional Java libraries provided By Google.
* [JNA](https://github.com/twall/jna): Libary to easily integrate C libraries with Java.

License
=======
This project is released under the Apache 2.0 License.
