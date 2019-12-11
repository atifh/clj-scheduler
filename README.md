# clj-scheduler

This project demonstrate an integration with Quartz Scheduler to run periodic jobs.

## Installation

### Setting up DB for persistent store

You need to install postgres

On MAC OS:
```
$ brew install postgres
```

Once the postgres is installed & is running successfully, you need to [setup_db.sh](https://github.com/atifh/clj-scheduler/blob/master/setup_db.sh) script which will created a database named `quartz_scheduler_demo` and dump the Quartz [PostgreSQL schema](https://github.com/atifh/clj-scheduler/blob/master/resources/quartz_tables_postgres.sql): 

```
./setup_db.sh
```
You can also find schemas for the all database [here](https://github.com/quartz-scheduler/quartz/tree/master/quartz-core/src/main/resources/org/quartz/impl/jdbcjobstore).

### Install application dependencies

```
$ lein deps

```
This will install all dependencies mentioned in `project.clj`

### Set up and load env variables
You need to create `config.edn` within config/dev folder and set all the required env vars. Please [refer sample env file](https://github.com/atifh/clj-scheduler/blob/master/config/dev/config-sample.edn).

### Setup logging 

We're using logging in this project to log information. Please make sure to add `clj_scheduler.log` file at this location /var/log/clj_scheduler/clj_scheduler.log

## Usage

#### One time job:

[Temporary hack - will fix this] In order to run one time job, please make sure to mv [resources/quartz.properties](https://github.com/atifh/clj-scheduler/blob/master/resources/quartz.properties) out of this project repo.

Here is the [code](https://github.com/atifh/clj-scheduler/blob/master/src/clj_scheduler/one_time_tasks.clj)

Fire REPL and run
`clj-scheduler.one-time-tasks> (schedule-job)`

The successful output would look like this:

```
2019-12-11 16:50:21.247 INFO  default    org.quartz.core.QuartzScheduler - Scheduler DefaultQuartzScheduler_$_NON_CLUSTERED started.
2019-12-11 16:50:21.247 INFO  default    clj-scheduler - Starting jobs.runs-once
#inst "2019-12-11T11:20:21.248-00:00"2019-12-11 16:50:21.250 INFO  default    clj-scheduler - One time job started
2019-12-11 16:50:21.251 INFO  default    clj-scheduler - x is 0
2019-12-11 16:50:21.251 INFO  default    clj-scheduler - x is 1
2019-12-11 16:50:21.251 INFO  default    clj-scheduler - x is 2
2019-12-11 16:50:21.252 INFO  default    clj-scheduler - x is 3
2019-12-11 16:50:21.252 INFO  default    clj-scheduler - x is 4
2019-12-11 16:50:21.252 INFO  default    clj-scheduler - One time job ended

```

#### Periodic job:

This triggers a job 5 times every second. Here is the [code](https://github.com/atifh/clj-scheduler/blob/master/src/clj_scheduler/periodic_tasks.clj)

`clj-scheduler.periodic-tasks> (schedule-job)`

#### Persistent job:

PS: Bring back [resources/quartz.properties](https://github.com/atifh/clj-scheduler/blob/master/resources/quartz.properties) into resources folder if you have followed One time job steps.

This triggers a job every day and repeats forever. Here is the [code](https://github.com/atifh/clj-scheduler/blob/master/src/clj_scheduler/persistent_tasks.clj)


`clj-scheduler.persistent-tasks> (schedule-job)`

#### Persistent cron job:

PS: Bring back [resources/quartz.properties](https://github.com/atifh/clj-scheduler/blob/master/resources/quartz.properties) into resources folder if you have followed One time job steps.

This triggers a job every thursday of every month at 3 PM. Here is the [code](https://github.com/atifh/clj-scheduler/blob/master/src/clj_scheduler/persistent_cron_tasks.clj)


`clj-scheduler.persistent-cron-tasks> (schedule-job)`

## Web view of the scheduled jobs.

You can view all the scheduled jobs in a web view by running the ring server:

`cd clj_scheduler`

`lein run server`

Now visit http://localhost:8080/ and you will see the below which renders all the scheduled jobs:

![All scheduled jobs](https://raw.githubusercontent.com/atifh/clj-scheduler/master/list-scheduled-jobs.png)

### Known issues
1. Need to move quartz.properties when switching between one_time_tasks and persistent tasks.

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
