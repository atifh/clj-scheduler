(ns clj-scheduler.db
  (:require [hikari-cp.core :refer :all]
            [clojure.java.jdbc :as jdbc]
            [config.core :refer [env]]
            [honeysql.core :as sql]
            [hikari-cp.core :refer :all]
            [honeysql.helpers :refer :all :as helpers]))

(def datasource-options {:auto-commit        true
                         :read-only          false
                         :connection-timeout 30000
                         :validation-timeout 5000
                         :idle-timeout       600000
                         :max-lifetime       1800000
                         :minimum-idle       10
                         ;; :maximum-pool-size  10
                         :pool-name          "db-pool"
                         :adapter            (:database-adapter env)
                         :username           (:database-username env)
                         :password           (:database-password env)
                         :database-name      (:database-name env)
                         :server-name        (:database-host env)
                         :port-number        (:database-port env)
                         :register-mbeans    false})

(defonce datasource
  (delay (make-datasource datasource-options)))

(defn execute-select-query [sql]
  (jdbc/with-db-connection [conn {:datasource @datasource}]
    (jdbc/query conn sql {:return-keys true})))

(defn get-all-quartz-triggers [& args]
  (execute-select-query (sql/format {:select [:*]
                                     :from [:qrtz_triggers]})))
