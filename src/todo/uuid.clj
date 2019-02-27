(ns todo.uuid)

;;; ->uuid
(defn ->uuid
  "convert x to java.util.UUID"
  [x]
  (if (uuid? x) x (java.util.UUID/fromString x)))

(defn random
  "generate random uuid"
  []
  (java.util.UUID/randomUUID))
