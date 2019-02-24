(ns todo.handler
  (:require 
    [compojure.core :refer :all]
    [compojure.route :as route]
    [clojure.java.jdbc :as jdbc]
    [java-time :as time]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
    [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
    [ring.middleware.keyword-params :refer [wrap-keyword-params]]
    [ring.util.response :refer [response content-type status header]]))

;; Helpers for ring-response
(defn created [body]
  {:status 201,
   :body body})

;; database spec
(def db {:dbtype "postgresql"
         :dbname "todo"
         :host "localhost"
         :user "postgres"
         :password ""
         :ssl false})

;; database functions
(defn insert-new-task! 
  [id params]
  (-> (jdbc/insert! db :task 
                    {:id id,
                     :title (:title params),
                     :link (:link params),
                     :due_date (some-> (:due_date params) time/local-date)})
      first
      (select-keys [:id :title :link :due_date :done])))

;; handler functions
(defn new-task
  [req] 
  (-> (insert-new-task! (java.util.UUID/randomUUID) (:params req))
      created))

;; routes
(defroutes app-routes
  (POST "/" req new-task)
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      wrap-keyword-params
      wrap-json-params
      wrap-json-response
      (wrap-defaults api-defaults)))
