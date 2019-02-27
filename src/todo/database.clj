(ns todo.database
  (:require [java-time :as time]
            [clojure.java.jdbc :as jdbc]
            [todo.uuid :as uuid]))

;; database spec
(def db (or (System/getenv "DATABASE_URL")
         {:dbtype "postgresql"
         :dbname "todo"
         :host "localhost"
         :user "postgres"
         :password ""
         :ssl false}))
                                    
;; database functions
(defn insert-new-task! 
  [id params]
  (-> (jdbc/insert! 
        db :task 
        {:id id,
         :title (:title params),
         :link (:link params),
         :due_date (some-> (:due_date params) time/local-date)})
      first
      (select-keys [:id :title :link :due_date :done])))

(defn select-task-by-id
  [id] 
  (some->
    (jdbc/query 
      db 
      ["SELECT * FROM task WHERE id=?", (uuid/->uuid id)])
    first))

(defn select-task
  [param] 
  (some->>
    (#(if (nil? %1) "all" (read-string %1)) (:limit param))
    (str "SELECT * FROM task order by created_at desc limit ")
    (jdbc/query db)))

(defn update-task-by-id!
  [id param]
  (some->
    (jdbc/update! 
      db :task
      (select-keys param '(:title :link :due_date :done))
      ["id = ?", (uuid/->uuid id)])
    first
    (= 1)))

(defn delete-task-by-id!
  [id]
  (some-> 
    (jdbc/delete!  db :task ["id = ?", (uuid/->uuid id)])
    first
    (= 1)))
