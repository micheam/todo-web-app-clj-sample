(ns todo.migration
  (:require [clojure.java.jdbc :as sql]
            [todo.database :as db]))

(defn migrated? 
  [] 
  (-> (sql/query 
        db/db-spec 
        "SELECT count(*) 
         FROM information_schema.tables 
         WHERE table_name='task'")
      first :count pos?))

(defn migrate
  []
  (when (not (migrated?))
    (print "init database schema...")
    (flush)
    (sql/execute! db/db-spec 
                  "CREATE TABLE IF NOT EXISTS task (
                   id          UUID NOT NULL PRIMARY KEY,
                   title       VARCHAR(128) NOT NULL, 
                   link        TEXT DEFAULT NULL, 
                   due_date    DATE DEFAULT NULL,
                   done        BOOLEAN NOT NULL DEFAULT FALSE,
                   created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
                   updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW())
                   ;")
    (println " done")))
