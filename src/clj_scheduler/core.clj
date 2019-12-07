(ns clj-scheduler.core
  (:gen-class)
  (:require
   [clj-scheduler.log :as log]
   [clj-scheduler.db :as db]
   [config.core :refer [env]]
   [clj-time.coerce :as tc]
   [selmer.parser :refer [render-file]]
   [ring.adapter.jetty :refer [run-jetty]]))

(def tmpl "tasks.html")

(defn handler [request]
  (let [jobs (map (fn [job]
                    (assoc job
                           :start_time_p (tc/from-long (job :start_time))
                           :prev_fire_time_p (if-not (= (job :prev_fire_time) -1)
                                               (tc/from-long (job :prev_fire_time))
                                               "Not started")
                           :next_fire_time_p (tc/from-long (job :next_fire_time))))
                  (db/get-all-quartz-triggers))
        content (render-file tmpl {:jobs jobs})]
    (log/info jobs)
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body content}))

(defn -main [& args]
  (run-jetty handler {:port 8080}))
