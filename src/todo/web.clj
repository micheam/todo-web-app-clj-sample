(ns todo.web
  (:require
    [ring.adapter.jetty :as ring]
    [todo.handler :as handler]
    [todo.migration :as schema])
  (:gen-class))

(defn start [port]
  (ring/run-jetty #'handler/app {:port port :join? false}))

(defn -main []
  (schema/migrate)
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))

