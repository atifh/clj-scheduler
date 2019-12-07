(ns clj-scheduler.persistent-tasks
  (:require
   [config.core :refer [env]]
   [clj-scheduler.log :as log]
   [clojurewerkz.quartzite.scheduler :as qs]
   [clojurewerkz.quartzite.triggers :as t]
   [clojurewerkz.quartzite.jobs :as j]
   [clojurewerkz.quartzite.schedule.simple :refer [schedule
                                                   repeat-forever
                                                   with-interval-in-days]]))

(j/defjob persistent-job
  [ctx]
  (log/info "Persistent Job started")
  (dotimes [x 5]
    (log/info "x is" x))
  (log/info "Persistent Job ended"))

(defn- get-persistent-job-trigger
  [sched]
  (if (not (qs/get-job sched (j/key "jobs.runs-persistent.1")))
    (do
      (log/info "No old job named jobs.runs-persistent.1")
      (let [job (j/build
                 (j/of-type persistent-job)
                 (j/with-identity (j/key "jobs.runs-persistent.1"))
                 (j/store-durably))
            trigger (t/build
                     (t/with-identity (t/key "triggers.jobs.runs-persistent.1"))
                     (t/start-now)
                     (t/with-schedule (schedule
                                       ;; runs every 30 mins
                                       (with-interval-in-days 1)
                                       (repeat-forever))))]
        [job trigger]))
    (log/info "jobs.runs-persistent.1 already scheduled")))

(defn- initialize-props []
  (System/setProperty
   "org.quartz.dataSource.db.URL" (env :database-url))
  (System/setProperty
   "org.quartz.dataSource.db.user" (env :database-username))
  (System/setProperty
   "org.quartz.dataSource.db.password" (env :database-password)))

(defn schedule-job
  [& m]
  (initialize-props)
  (let [sched (-> (qs/initialize) qs/start)]
    (let [[job trigger] (get-persistent-job-trigger sched)]
      (qs/schedule sched job trigger))))
