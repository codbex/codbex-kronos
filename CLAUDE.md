# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this project is

Kronos is a compatibility runtime for **SAP HANA XS classic (XSC)** applications, and the maintained fork of SAP's discontinued "XSK" project. It lets `*.xsjs` / `*.xsjslib` services, `*.hdb*` database artifacts, `*.xsodata` services, and `*.xsjob` jobs run outside a HANA instance (e.g. on Kubernetes against any JDBC RDBMS). It is built as an **extension of the [Eclipse Dirigible](https://github.com/eclipse/dirigible) platform**: a Spring Boot application that runs the Dirigible runtime plus Kronos-specific engines. The JS programming model runs on GraalJS, with `$.*` XSC APIs pre-loaded and a thin bridge mapping XSC concepts onto Dirigible equivalents.

In codbex's [product line](https://www.codbex.com/products/), Kronos is "the XS compatibility platform" — the standard codbex/Dirigible components (Enterprise JavaScript, databases, jobs, security, etc.) plus the **XS and ABAP compatibility plugins** that this repository adds (the `xs` and `abap` modules). That framing maps directly onto the module layout below.

Requirements: **Java 20+**, **Maven 3.6.2+**.

## Build & run

```bash
mvn clean install                 # full build + tests
mvn -T 1C clean install -P quick-build   # parallel, skips slow steps (used for Docker builds)

# Run the assembled app (Spring Boot fat jar):
java -jar application/target/codbex-kronos-application-*-executable.jar
# Debug (JDWP on :8000):
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar application/target/codbex-kronos-application-*-executable.jar
```

Web UI: `http://localhost` · REST/Swagger: `http://localhost/swagger-ui/index.html`

**Spring profiles**: to activate a Dirigible profile you must explicitly include `common` and `app-default`. Example: `SPRING_PROFILES_ACTIVE=common,snowflake,app-default`.

**ORAS artifacts**: BW/ABAP-transpilation scenarios need artifacts pulled from `ghcr.io` into `/opt/codbex/oras-artifacts` (see README "ORAS artifacts"). Not needed for the common XS path.

## Tests

```bash
mvn test                                              # unit tests (Surefire, JUnit; junit-vintage on classpath)
mvn -pl xs/modules/parsers/parser-hdbtable test       # tests for a single module
mvn -pl <module> test -Dtest=HDBTableParserTest       # a single test class
mvn -pl integration-tests verify                      # integration tests (*IT classes, Selenide/browser-driven)
```

Integration tests live in `integration-tests/` (class names end in `IT`, e.g. `XsjsIT`, `HomePageIT`) and boot the full application; they depend on `dirigible-tests-integrations`.

## Module layout

Root `pom.xml` aggregates `application`, `core`, `integration-tests`. The **`xs`** and **`abap`** modules are Maven profiles, both **active by default** (so a plain `mvn install` builds everything).

- **`application/`** — the Spring Boot entrypoint (`com.codbex.kronos.KronosApplication`). Component-scans both `org.eclipse.dirigible` and `com.codbex.kronos`; excludes Spring's DataSource auto-config (Dirigible manages datasources). Produces the `-executable.jar` and the Docker image (`application/Dockerfile`).
- **`xs/components/`** — the XSC engines and APIs (the bulk of the project). See the engine pattern below.
- **`xs/modules/parsers/`** — ANTLR4 grammars (`*.g4`) and generated parsers for each HANA artifact format (hdbdd/CDS, hdbtable, hdbview, hdbti, hdbsequence, hdbschema, xsodata, hana). Grammar changes regenerate parsers at build time via `antlr4-maven-plugin`.
- **`abap/`** — ABAP/Snowflake transpilation APIs and templates (BW migration scenario).
- **`core/`** — branding, welcome page, and help-menu content overlaid onto the Dirigible UI.
- **`integration-tests/`**, **`legacy/`** — full-app integration tests and legacy coverage.
- **`samples/`** — example XSC apps (`api-db`, `api-hdb`, `api-jobs`, etc.) demonstrating the `$.*` APIs.

## Engine architecture (key pattern to understand)

Each XS artifact type is handled by an engine under `xs/components/engine-*` (`engine-xsjs`, `engine-hdb`, `engine-hdi`, `engine-xsodata`, `engine-xsjob`, `engine-xssecurity`, `engine-mail-destination`, `engine-core`, `engine-commons`). Engines follow Dirigible's component model, so a typical engine package contains:

- **`domain/`** — JPA-backed model of the artifact.
- **`synchronizer/`** — the entry point. Dirigible periodically synchronizes the workspace/registry; a `*Synchronizer` parses matching files (via the corresponding `xs/modules/parsers` parser) and reconciles the desired state into the database/runtime. This is how `*.hdbtable`, `*.hdbview`, `*.xsjs`, etc. get materialized.
- **`service/`** — REST services exposing the artifacts.
- **`processors/`** — discrete deploy/grant/drop operations (clearest in `engine-hdi`).

So the flow for a new artifact type is generally: **grammar (`xs/modules/parsers`) → parsed model → engine `domain` + `synchronizer` → `service`/`processors`**. When changing how an artifact is parsed or deployed, expect to touch both the parser module and the matching engine.

The `$.*` XSC JavaScript bridge and runtime helpers live in `engine-xsjs` under `src/main/resources` (`js/` and `META-INF/dirigible/exports/` — e.g. `XSJSLibCompiler.mjs`, `XSJSLibRunner.mjs`).

## Conventions

- Parent POM is `com.codbex.platform:codbex-platform-parent`; Dirigible and platform versions are inherited from it — bump there, not per-module.
- Every source file carries the EPL-2.0 SPDX header (see any `.java`); keep it on new files.
- Formatting is governed by `.editorconfig` at the repo root.
