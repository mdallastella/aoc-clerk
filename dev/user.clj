(ns user
  (:require [nextjournal.clerk :as clerk]))

(defn go
  []
  (clerk/serve! {:watch-paths ["src"]}))
