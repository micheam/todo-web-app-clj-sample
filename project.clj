(defproject todo "0.1.0-SNAPSHOT"
  :description "sample Web API implementation with compojure and jdbc"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[clojure.java-time "0.3.2"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [org.clojure/tools.namespace "0.2.7"]
                 [org.postgresql/postgresql "42.2.5"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-json "0.4.0"]]

  :main ^:skip-aot todo.web
  :uberjar-name "todo-webapp.jar"
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler todo.handler/app}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.2"]]}
             :uberjar {:aot :all}})
