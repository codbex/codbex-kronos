# Kronos by codbex

[![Build Status](https://github.com/codbex/codbex-kronos/actions/workflows/build.yaml/badge.svg)](https://github.com/codbex/codbex-kronos/actions/workflows/build.yaml)
[![Eclipse License](https://img.shields.io/badge/License-EPL%202.0-brightgreen.svg)](https://github.com/codbex/codbex-kronos/blob/main/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.codbex.kronos/codbex-kronos-application.svg)](https://central.sonatype.com/namespace/com.codbex.kronos)
[![GitHub contributors](https://img.shields.io/github/contributors/codbex/codbex-kronos.svg)](https://github.com/codbex/codbex-kronos/graphs/contributors)

> Kronos is the official fork of the project "XSK" developed by SAP until 2022. This repository is well maintained by
> the same developers
> and free to be used by whom is interested.

<!-- TOC -->
* [Kronos by codbex](#kronos-by-codbex)
  * [Description](#description)
  * [Overview](#overview)
    * [Background](#background)
  * [Requirements](#requirements)
      * [Docker](#docker)
      * [Build](#build)
      * [Docker Build](#docker-build)
      * [ORAS artifacts](#oras-artifacts)
      * [Run](#run)
      * [Debug](#debug)
      * [Spring profiles](#spring-profiles)
      * [Web](#web)
      * [REST API](#rest-api)
  * [FAQ](#faq)
  * [Cheat Sheet](#cheat-sheet)
  * [Readiness](#readiness)
  * [How to obtain support](#how-to-obtain-support)
  * [Contributing](#contributing)
<!-- TOC -->

## Description

Compatible environment
for [SAP HANA Extended Application Services](https://help.sap.com/viewer/52715f71adba4aaeb480d946c742d1f6/2.0.03/en-US/a6c0749255d84a81a154a7fc87dd33ce.html) (
XS) based applications. It is deployed outside
of [SAP HANA](https://www.sap.com/products/hana.html?btp=991d50bf-fa15-4979-ac4b-b280b0eb951f) instance as
a [Docker](https://www.docker.com/) a container on [Kubernetes](https://kubernetes.io/). Hence, some of the features can
work against any
other JDBC compliant RDBMS such as [PostgreSQL](https://www.postgresql.org/). The compatibility stack is an extension of
the [Eclipse Dirigible](https://github.com/eclipse/dirigible) cloud development platform.

> _**Note:** the project is not yet ready to be used productively_

## Overview

_Project documentation is available at: https://www.xsk.io_

- [Project Kronos](#background)
- [Support](#how-to-obtain-support)
- [Contributing](#contributing)

### Background

In the SAP Cloud Platform Neo environment XS classic programming model (XSC) is supported via the HANA 1.x database. The
XSC engine resides
in the HANA system itself. It can scale up, but cannot scale out. It is based on the multi-threaded JavaScript runtime
Mozilla
Spydermonkey (Firefox's engine). It supports synchronous programming model. It can serve backend services in JavaScript,
HTML5 and other
static content. It supports OData v2.0 compatible services defined in an \*.xsodata descriptors. It supports automatic
tables, views,
calculationviews materialisation based on proprietary formats.

Kronos approach is to provide a custom [Eclipse Dirigible](https://www.dirigible.io/) stack coming with XSC engine type.
This engine is
based on the standard Dirigible's JavaScript engine with several enhancements such as predefined XSC API pre-loaded ($.*
APIs), execution
based on \*.xsjs instead of \*.js only, imports based on \*.xsjslib instead of \*.js.

There are corresponding APIs in Dirigible playing the same role as the ones in XSC (e.g. request, response, connection,
etc.). A thin XSC
bridge is provided for full compatibility.

The programming model is synchronous in multi-threaded environment (based
on [GraalJS](https://github.com/graalvm/graaljs)). The descriptors
for the table, views and calculationviews currently exists with the same life-cycle management, only the format is
different. OData via
descriptor approach is also available as part of the stack as well.

Kronos stack is based on Java (JVM), so all the available plugins and/or new frameworks from Apache, Eclipse, and other
open source
providers can be easily integrated as well.

Kronos stack can run within the HANA box, also in the virtual HANA system or outside in e.g. Kubernetes cluster, Kyma,
Cloud Foundry, Open
Stack.

_**To learn more go to: https://www.xsk.io**_

## Requirements

- Java 20 or later
- Maven 3.6.2 or later
- Access to SAP BTP account or another Kubernetes based environment
- Access to SAP HANA Cloud instance

#### Docker

```
docker pull ghcr.io/codbex/codbex-kronos:latest
docker run --name codbex-kronos --rm -p 80:80 ghcr.io/codbex/codbex-kronos:latest
```

- For Apple's M1: provide `--platform=linux/arm64` for better performance

#### Build

```
mvn clean install
```

#### Docker Build

```
GIT_REPO='<path_to_git_repo>'
IMAGE_TAG='codbex-kronos:dev'
ORAS_GIT_TOKEN='<token_with_read_packages_scope_for_codbex>'

cd "$GIT_REPO"

mvn -T 1C clean install -P quick-build
cd application

oras login ghcr.io -u oauth2 -p "$ORAS_GIT_TOKEN"

oras pull "ghcr.io/codbex/codbex-bw-migration/codbex-abap-bw:latest" -o oras-artifacts

docker buildx build \
  --platform linux/amd64,linux/arm64 . --tag "$IMAGE_TAG"

```

#### ORAS artifacts
You need to pull the required ORAS artifacts locally if they are needed for your scenario and the Kronos is not started as a Docker container.<br>
For example if you work on BW migration scenario where ABAP transpilation is needed.<br>
```shell
sudo su
mkdir -p /opt/codbex
cd /opt/codbex

ORAS_GIT_TOKEN='<token_for_the_repos>'
oras login ghcr.io -u oauth2 -p "$ORAS_GIT_TOKEN"

rm -rf oras-artifacts
oras pull "ghcr.io/codbex/codbex-bw-migration/codbex-abap-bw:latest" -o oras-artifacts

```

#### Run

```
java -jar application/target/codbex-kronos-application-*-executable.jar
```

#### Debug

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar application/target/codbex-kronos-application-*-executable.jar
```

#### Spring profiles
- Eclipse Dirigible profiles<br>
  To activate Eclipse Dirigible profile, you have to add profiles `common` and `app-default` explicitly.<br>
  Example for profile `snowflake`: `SPRING_PROFILES_ACTIVE=common,snowflake,app-default`

#### Web

```
http://localhost
```

#### REST API

```
http://localhost/swagger-ui/index.html
```

## FAQ

- How long will Kronos be supported?

  > Kronos is an open source project with community support. Everyone can join and make a [PR](CONTRIBUTING.md). In fact
  SAP discontinued
  official support to project "XSK", and this fork maintained by the same developers is prove that the approach is
  viable and useful. The
  company codbex provide enterprise support for Kronos based runtimes.

- Should future developments be based on Kronos?

  > Yes, you can use Kronos for future development.

- What about the tooling? Do we get state of the art tooling for maintaining and enhancing Kronos?

  > Kronos tooling is based on [Eclipse Dirigible](https://www.dirigible.io/) and in the near future it will be possible
  to maintain Kronos
  projects with any modern IDE like VSCode, Eclipse Theia, etc.

- What about the ops aspects - will Kronos be smoothly integrated into a state-of-the-art lifecycle and ops management (
  be it SAP-based or
  non-SAP based like GitHub Actions?

  > Yes, the Kronos itself uses [GitHub actions](https://github.com/codbex/codbex-kronos/actions) for CI/CD

- Will there be limitations that will not be mitigated?

  > You can get the up-to-date list of covered [features](https://github.com/codbex/codbex-kronos/wiki/Readiness) as
  well as
  the [limitations](https://github.com/codbex/codbex-kronos/wiki/Limitations)

## Cheat Sheet

Visit the cheat sheet [here](https://github.com/codbex/codbex-kronos/wiki/Cheat-Sheet).

## Readiness

Visit the readiness page [here](https://github.com/codbex/codbex-kronos/wiki/Readiness).

## How to obtain support

All the bug reports as well as the feature requests have to be registered as issues.

## Contributing

If you want to contribute, please check the [Contribution Guidelines](CONTRIBUTING.md)

