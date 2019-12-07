(defproject clj-scheduler "0.1.0-SNAPSHOT"
  :description "A demo project on Quartz Scheduler"
  :url ""
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [yogthos/config "1.1.7"]
                 [ring/ring-core "1.7.1"]
                 [hikari-cp "2.7.1"]
                 [honeysql "0.9.4"]
                 [selmer "1.12.12"]
                 [clj-time "0.15.2"]
                 [org.clojure/tools.logging "0.5.0"]
                 [ch.qos.logback/logback-classic "1.1.1"]
                 [clojurewerkz/quartzite "2.1.0"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.postgresql/postgresql "42.2.4"]
                 [ring/ring-jetty-adapter "1.7.1"]]
  ;; :source-paths ["src"]
  :ring {:handler clj-scheduler.core/-main
         :auto-reload? true
         :open-browser? false
         :reload-paths ["src/" "resources/" "templates/"]}
  :main ^:skip-aot clj-scheduler.core
  :target-path "target/%s"
  :profiles {:dev {:resource-paths ["config/dev" "templates/"
                                    "resources/"]}
             :test {:resource-paths ["config/test"]}
             :uberjar {:aot :all
                       :resource-paths ["config/prod" "templates/"
                                        "resources/"]}})
