(ns todo.response
  (:require 
    [ring.util.response :as r]))

(defn ok [body]
  (r/response body))

(defn created [body]
  {:status 201, :body body})

(defn no-contents []
  {:status 204, :body nil})

(defn bad-request [body]
  {:status  400
   :headers {}
   :body    body})

(defn not-found
  [] (r/not-found nil))
