^{:nextjournal.clerk/visibility :hide-ns}
(ns solutions.YEAR.dayDAY
  {:nextjournal.clerk/toc true}
  (:require [clojure.java.io :as io]
            [util :as u]
            [nextjournal.clerk :as clerk]
            [clojure.string :as str]))

;; # Problem
{:nextjournal.clerk/visibility {:code :hide :result :show}}
(clerk/html (u/load-problem "DAY" "YEAR"))
{:nextjournal.clerk/visibility {:code :show :result :show}}

;; # Solution
;;
;; First things first, let's load our input and parse it
(def input (->> (slurp (io/resource "inputs/YEAR/dayDAY.txt")) ;; Load the resource
                str/split-lines))                             ;; Split into lines
{:nextjournal.clerk/visibility {:result :hide}}

;; ## Part 1
(defn part-1
  [input]
  (println "Part 1"))

;; Which gives our answer
{:nextjournal.clerk/visibility {:code :hide :result :show}}
(part-1 input)

;; ## Part 2
{:nextjournal.clerk/visibility {:code :show :result :hide}}
(defn part-2
  [input]
  (println "Part 2"))

;; Which gives our answer
{:nextjournal.clerk/visibility {:code :hide :result :show}}
(part-2 input)
