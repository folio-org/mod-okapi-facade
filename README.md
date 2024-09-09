# mod-okapi-facade

Copyright (C) 2024 The Open Library Foundation

This software is distributed under the terms of the Apache License, Version 2.0. See the file "[LICENSE](LICENSE)" for
more information.

## Table of contents

* [Introduction](#introduction)
* [Environment Variables](#environment-variables)

## Introduction

Okapi facade partially implements okapi�s interface(s) via interactions w/ manager components.

## Environment Variables

| Name                       | Default value | Required | Description                                                                        |
|:---------------------------|:--------------|:--------:|:-----------------------------------------------------------------------------------|
| DB_HOST                    | localhost     |  false   | Postgres hostname                                                                  |
| DB_PORT                    | 5432          |  false   | Postgres port                                                                      |
| DB_USERNAME                | postgres      |  false   | Postgres username                                                                  |
| DB_PASSWORD                | postgres      |  false   | Postgres username password                                                         |
| DB_DATABASE                | postgres      |  false   | Postgres database name                                                             |
| AM_URL                     |               |   true   | Applications Manager URL                                                           |
| AM_TLS_ENABLED             | false         |  false   | Allows to enable/disable TLS connection to mgr-applications module.                |
| AM_TLS_TRUSTSTORE_PATH     | -             |  false   | Truststore file path for TLS connection to mgr-applications module.                |
| AM_TLS_TRUSTSTORE_PASSWORD | -             |  false   | Truststore password for TLS connection to mgr-applications module.                 |
| AM_TLS_TRUSTSTORE_TYPE     | -             |  false   | Truststore file type for TLS connection to mgr-applications module.                |
| TM_URL                     |               |   true   | Tenants Manager URL                                                                |
| TM_TLS_ENABLED             | false         |  false   | Allows to enable/disable TLS connection to mgr-tenants module.                     |
| TM_TLS_TRUSTSTORE_PATH     | -             |  false   | Truststore file path for TLS connection to mgr-tenants module.                     |
| TM_TLS_TRUSTSTORE_PASSWORD | -             |  false   | Truststore password for TLS connection to mgr-tenants module.                      |
| TM_TLS_TRUSTSTORE_TYPE     | -             |  false   | Truststore file type for TLS connection to mgr-tenants module.                     |
| TE_URL                     |               |   true   | Tenant Entitlements Manager URL                                                    |
| TE_TLS_ENABLED             | false         |  false   | Allows to enable/disable TLS connection to mgr-tenant-entitlements module.         |
| TE_TLS_TRUSTSTORE_PATH     | -             |  false   | Truststore file path for TLS connection to mgr-tenant-entitlements module.         |
| TE_TLS_TRUSTSTORE_PASSWORD | -             |  false   | Truststore password for TLS connection to mgr-tenant-entitlements module.          |
| TE_TLS_TRUSTSTORE_TYPE     | -             |  false   | Truststore file type for TLS connection to mgr-tenant-entitlements module.         |
| TENANT_APPS_TTL            | 600s          |  false   | Time-to-live of the elements in the cache which stores tenant enabled applications |   
