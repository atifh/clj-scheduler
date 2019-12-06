(ns clj-scheduler.persistent-cron-tasks
  (:require
   [config.core :refer [env]]
   [clj-scheduler.log :as log]
   [clojurewerkz.quartzite.scheduler :as qs]
   [clojurewerkz.quartzite.triggers :as t]
   [clojurewerkz.quartzite.jobs :as j]
   [clojurewerkz.quartzite.schedule.cron :refer [schedule
                                                 cron-schedule]]))

(j/defjob persistent-cron-job
  [ctx]
  (log/info "Persistent cron job started")
  (dotimes [x 5]
    (log/info "x is" x))
  (log/info "Persistent cron job ended"))

(defn- get-persistent-cron-job-trigger
  [sched]
  (if (not (qs/get-job sched (j/key "jobs.runs-persistent-cron.1")))
    (do
      (log/info "No old job named jobs.runs-persistent-cron.1")
      (let [job (j/build
                 (j/of-type persistent-cron-job)
                 (j/with-identity (j/key "jobs.runs-persistent-cron.1"))
                 (j/store-durably))
            trigger (t/build
                     (t/with-identity (t/key "triggers.jobs.runs-persistent-cron.1"))
                     (t/start-now)
                     (t/with-schedule (schedule
                                       (cron-schedule "0 0 15 ? * 5"))))]
        [job trigger]))
    (log/info "jobs.runs-persistent-cron.1 already scheduled")))

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
    (let [[job trigger] (get-persistent-cron-job-trigger sched)]
      (qs/schedule sched job trigger))))


(defn delete-trigger
  [& m]
  (let [sched  (-> (qs/initialize) qs/start)
        trigger-key (t/key "jobs.runs-persistent-cron.1")]
    (qs/delete-trigger sched trigger-key)))
