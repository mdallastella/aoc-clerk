^{:nextjournal.clerk/visibility :hide-ns}
(ns solutions.2023.day01
  {:nextjournal.clerk/toc true}
  (:require [clojure.java.io :as io]
            [util :as u]
            [nextjournal.clerk :as clerk]
            [clojure.string :as str]))

;; # Problem

(clerk/html (u/load-problem "01" "2023"))

;; # Solution
;;
;; First things first, let's load our input and parse it
{:nextjournal.clerk/visibility {:result :hide}}
(def input (->> (slurp (io/resource "inputs/2023/day01.txt")) ;; Load the resource
                str/split-lines))                             ;; Split into lines
{:nextjournal.clerk/visibility {:code :show :result :show}}

;; ## Part 1

;; For this problem it would be easier to use regexp but, since they can be
;; pretty slow, I'm going to use a different approach.
;;
;; String is a sequence of characters and every character has a numeric
;; value (ex: (int \a) = 97). Let's use this to collect all the digits in a
;; line.

;; Let's define a set with all the digits value
(def numbers-range (set (range (int \0) (inc (int \9)))))

;; Sets can be used as a function to check if an element is present in the set.
(defn numerical-char?
  [c]
  (not (nil? (numbers-range (int c)))))

;; Let try it with "1":
(numerical-char? \1)

;; and with "A"
(numerical-char? \A)

;; We can now define a function that filters the digits from a string, keeps
;; the first and the last in a vector, and then transform them in a number:
(defn collect-numbers
  [s]
  (->> s
       (filter numerical-char?)
       ((juxt first last))
       (apply str)
       Integer/parseInt))

;; Test `collect-numbers` against the examples:
(collect-numbers "1abc2")
(collect-numbers "pqr3stu8vwx")
(collect-numbers "a1b2c3d4e5f")
(collect-numbers "treb7uchet")

;;
(defn part-1
  [input]
  (->> input
       (map collect-numbers)
       (apply +)))

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
