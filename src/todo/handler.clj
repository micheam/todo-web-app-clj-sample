(ns todo.handler
  (:require 
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
    [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
    [ring.middleware.keyword-params :refer [wrap-keyword-params]]
    [hiccup.page :as page]
    [todo.database :as db]
    [todo.response :as resp]
    [todo.uuid :as uuid]))

;;; handler functions

(defn list-task
  [req]
  (let [params (:params req)]
    (resp/ok (db/select-task params))))

(defn get-task
  [req]
  (let [params (:params req)
        task (db/select-task-by-id (:id params))]
    (if (nil? task)
      (resp/not-found)
      (resp/ok task))))

(defn new-task
  [req] 
  (-> (db/insert-new-task! (uuid/random) (:params req))
      resp/created))

(defn patch-task
  [req]
  (let [params (:params req)]
    (if (true? (db/update-task-by-id! (:id params) params))
      (resp/no-contents)
      (resp/bad-request nil))))

(defn delete-task
  [req]
  (let [params (:params req)]
    (if (true? (db/delete-task-by-id! (:id params)))
      (resp/no-contents)
      (resp/not-found))))

(defn index
  []
  (page/html5
    [:head
     [:title "Welcom"]]
    [:body
     [:div {:id "content"}
      [:h1 "Welcom to my TODO WebAPP"]]]))

;;; define routes

(defroutes app-routes
  (GET "/" [] (index))
  (GET "/api/" req list-task)
  (POST "/api/" req new-task)
  (GET "/api/:id" req get-task)
  (PATCH "/api/:id" req patch-task)
  (DELETE "/api/:id" req delete-task)
  (route/not-found "Not Found"))

;;; define app

(def app
  (-> app-routes
      wrap-keyword-params
      wrap-json-params
      wrap-json-response
      (wrap-defaults api-defaults)))
