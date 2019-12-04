(defproject clj-scheduler "0.1.0-SNAPSHOT"
  :description "A demo project on Quartz Scheduler"
  :url ""
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [yogthos/config "1.1.7"]
                 [clojurewerkz/quartzite "2.1.0"]]
  :main ^:skip-aot clj-scheduler.core
  :target-path "target/%s"
  :profiles {:dev {:resource-paths ["config/dev" "templates/"
                                    "resources/"]}
             :test {:resource-paths ["config/test"]}
             :uberjar {:aot :all
                       :resource-paths ["config/prod" "templates/"
                                        "resources/"]}})
