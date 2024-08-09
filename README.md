# mod-okapi-facade

Copyright (C) 2024 The Open Library Foundation

This software is distributed under the terms of the Apache License, Version 2.0. See the file "[LICENSE](LICENSE)" for
more information.

## Table of contents

* [Introduction](#introduction)
* [Environment Variables](#environment-variables)

## Introduction

Okapi facade partially implements okapi’s interface(s) via interactions w/ manager components.

## Environment Variables

| Name                             | Default value                                                            | Required | Description                                                                                                                            |
|:---------------------------------|:-------------------------------------------------------------------------|:--------:|:---------------------------------------------------------------------------------------------------------------------------------------|
| DB_HOST                          | localhost                                                                |  false   | Postgres hostname                                                                                                                      |
| DB_PORT                          | 5432                                                                     |  false   | Postgres port                                                                                                                          |
| DB_USERNAME                      | postgres                                                                 |  false   | Postgres username                                                                                                                      |
| DB_PASSWORD                      | postgres                                                                 |  false   | Postgres username password                                                                                                             |
| DB_DATABASE                      | postgres                                                                 |  false   | Postgres database name                                                                                                                 |
