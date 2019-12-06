# clj-scheduler

This project demonstrate an integration with Quartz Scheduler to run periodic jobs.

## Installation

### Setting up DB for persistent store

You need to install postgres

On MAC OS:
```
$ brew install postgres
```

Once the postgres is installed & is running successfully, you need to create a database.

```
$ createdb quartz_scheduler
```

https://github.com/quartz-scheduler/quartz/tree/master/quartz-core/src/main/resources/org/quartz/impl/jdbcjobstore

### Install application dependencies

```
$ lein deps

```
This will install all dependencies mentioned in `project.clj`

### Set up and load env variables
You need to create `config.edn` within config/dev folder and set all the required env vars.


## Usage

TBD

## Examples

...

### Bugs

...


## License

Copyright © 2019

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
