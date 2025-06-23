# mod-okapi-facade

Copyright (C) 2024-2025 The Open Library Foundation

This software is distributed under the terms of the Apache License, Version 2.0. See the file "[LICENSE](LICENSE)" for
more information.

## Table of contents

* [Introduction](#introduction)
* [Environment Variables](#environment-variables)

## Introduction

Okapi facade partially implements okapi's interface(s) via interactions w/ manager components.

The okapi facade was introduced to ease transition from okapi to Eureka for existing code which explicitly uses the okapi interface.  Modules are only doing so to obtain information; they aren't making changes in the system (Registering modules, discovery info, enabling/disable modules, etc.).  As such, the set of API endpoints implemented are limited to read-only operations.  It's discouraged writing new code which increases Folio's reliance on the Okapi facade.  It would be nice if over time we transition away from the okapi facade and eventually remove it altogether.

## Environment Variables

| Name                              | Default value | Required | Description                                                                        |
|:----------------------------------|:--------------|:--------:|:-----------------------------------------------------------------------------------|
| DB_HOST                           | localhost     |  false   | Postgres hostname                                                                  |
| DB_PORT                           | 5432          |  false   | Postgres port                                                                      |
| DB_USERNAME                       | postgres      |  false   | Postgres username                                                                  |
| DB_PASSWORD                       | postgres      |  false   | Postgres username password                                                         |
| DB_DATABASE                       | postgres      |  false   | Postgres database name                                                             |
| AM_CLIENT_URL                     |               |   true   | Applications Manager URL                                                           |
| AM_CLIENT_TLS_ENABLED             | false         |  false   | Allows to enable/disable TLS connection to mgr-applications module.                |
| AM_CLIENT_TLS_TRUSTSTORE_PATH     | -             |  false   | Truststore file path for TLS connection to mgr-applications module.                |
| AM_CLIENT_TLS_TRUSTSTORE_PASSWORD | -             |  false   | Truststore password for TLS connection to mgr-applications module.                 |
| AM_CLIENT_TLS_TRUSTSTORE_TYPE     | -             |  false   | Truststore file type for TLS connection to mgr-applications module.                |
| MT_CLIENT_URL                     |               |   true   | Tenants Manager URL                                                                |
| MT_CLIENT_TLS_ENABLED             | false         |  false   | Allows to enable/disable TLS connection to mgr-tenants module.                     |
| MT_CLIENT_TLS_TRUSTSTORE_PATH     | -             |  false   | Truststore file path for TLS connection to mgr-tenants module.                     |
| MT_CLIENT_TLS_TRUSTSTORE_PASSWORD | -             |  false   | Truststore password for TLS connection to mgr-tenants module.                      |
| MT_CLIENT_TLS_TRUSTSTORE_TYPE     | -             |  false   | Truststore file type for TLS connection to mgr-tenants module.                     |
| TE_URL                            |               |   true   | Tenant Entitlements Manager URL                                                    |
| TE_TLS_ENABLED                    | false         |  false   | Allows to enable/disable TLS connection to mgr-tenant-entitlements module.         |
| TE_TLS_TRUSTSTORE_PATH            | -             |  false   | Truststore file path for TLS connection to mgr-tenant-entitlements module.         |
| TE_TLS_TRUSTSTORE_PASSWORD        | -             |  false   | Truststore password for TLS connection to mgr-tenant-entitlements module.          |
| TE_TLS_TRUSTSTORE_TYPE            | -             |  false   | Truststore file type for TLS connection to mgr-tenant-entitlements module.         |
| TENANT_APPS_TTL                   | 600s          |  false   | Time-to-live of the elements in the cache which stores tenant enabled applications |   
