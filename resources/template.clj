^{:nextjournal.clerk/visibility :hide-ns}
(ns solutions.YEAR.dayDAY
  {:nextjournal.clerk/toc true}
  (:require [clojure.java.io :as io]
            [util :as u]
            [nextjournal.clerk :as clerk]
            [clojure.string :as s]))

;; # Problem
^::clerk/no-cache (clerk/html (u/load-problem "DAY" "YEAR"))

;; # Solution
;;
;; First things first, let's load our input and parse it
(def input (->> (slurp (io/resource "inputs/YEAR/dayDAY.txt")) ;; Load the resource
                s/split-lines))                             ;; Split into lines

;; ## Part 1
(defn part-1
  [input]
  (println "Part 1"))

;; Which gives our answer
(part-1 input)

;; ## Part 2
(defn part-2
  [input]
  (println "Part 2"))

;; Which gives our answer
(part-2 input)
