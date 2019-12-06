(ns clj-scheduler.one-time-tasks
  (:require
   [clj-time.core :as time]
   [clj-scheduler.log :as log]
   [clojurewerkz.quartzite.scheduler :as qs]
   [clojurewerkz.quartzite.triggers :as t]
   [clojurewerkz.quartzite.jobs :as j]
   [clojurewerkz.quartzite.schedule.simple :refer [schedule
                                                   with-repeat-count]]))

(j/defjob one-time-job
  [ctx]
  (log/info "One time job started")
  (dotimes [x 5]
    (log/info "x is" x))
  (log/info "One time job ended"))

(defn- get-job-trigger-for-once
  "This trigger is going to run once and
  will not be stored"
  [sched]
  (log/info "Starting jobs.runs-once")
  (let [job (j/build
             (j/of-type one-time-job)
             (j/with-identity (j/key "jobs.jobs.runs-once.1")))
        trigger (t/build
                 (t/with-identity (t/key "triggers.jobs.runs-once.1"))
                 (t/start-now)
                 (t/with-schedule (schedule
                                   (with-repeat-count 0))))]
    [job trigger]))


(defn schedule-job
  [& m]
  (let [sched (-> (qs/initialize) qs/start)]
    (let [[job trigger] (get-job-trigger-for-once sched)]
      (qs/schedule sched job trigger))))
