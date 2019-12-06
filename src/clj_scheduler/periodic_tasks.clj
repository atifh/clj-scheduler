(ns clj-scheduler.periodic-tasks
  (:require
   [clj-time.core :as time]
   [clj-scheduler.log :as log]
   [clojurewerkz.quartzite.scheduler :as qs]
   [clojurewerkz.quartzite.triggers :as t]
   [clojurewerkz.quartzite.jobs :as j]
   [clojurewerkz.quartzite.schedule.simple :refer [schedule
                                                   with-repeat-count
                                                   with-interval-in-minutes]]))

(j/defjob periodic-job
  [ctx]
  (log/info "Periodic Job started")
  (dotimes [x 5]
    (log/info "x is" x))
  (log/info "Periodic Job ended"))

(defn- get-periodic-job-trigger
  "This trigger is going to periodically and
  will not be stored"
  [sched]
  (log/info "Starting jobs.runs-periodically")
  (let [job (j/build
             (j/of-type periodic-job)
             (j/with-identity (j/key "jobs.jobs.runs-periodically.1")))
        trigger (t/build
                 (t/with-identity (t/key "triggers.jobs.runs-periodically.1"))
                 (t/start-now)
                 (t/with-schedule (schedule
                                   (with-repeat-count 5)
                                   (with-interval-in-minutes 1))))]
    [job trigger]))


(defn schedule-job
  [& m]
  (let [sched (-> (qs/initialize) qs/start)]
    (let [[job trigger] (get-periodic-job-trigger sched)]
      (qs/schedule sched job trigger))))
