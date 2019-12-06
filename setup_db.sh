#!/bin/bash
printf "\n*** Creating PostgreSQL DB for Quartz ***\n\n"
createdb quartz_scheduler_demo
printf "\n*** DONE ***\n\n"

printf "\n*** Creating schema for Quartz DB ***\n\n"
psql -d quartz_scheduler_demo < resources/quartz_tables_postgres.sql
printf "\n*** DONE ***\n\n"
