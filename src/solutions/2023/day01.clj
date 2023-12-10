^{:nextjournal.clerk/visibility :hide-ns}
(ns solutions.2023.day01
  {:nextjournal.clerk/toc true}
  (:require [clojure.java.io :as io]
            [util :as u]
            [nextjournal.clerk :as clerk]
            [clojure.string :as s]))

;; # Problem
^::clerk/no-cache (clerk/html (u/load-problem "01" "2023"))

;; # Solution
;;
;; First things first, let's load our input and parse it
(def input (->> (slurp (io/resource "inputs/2023/day01.txt")) ;; Load the resource
                s/split-lines))                               ;; Split into lines

;; ## Part 1

;; I don't really like regexps, but I can't think of another way to tackle this
;; problem (especially part 2) without them.

;; Let's define a set with all the digits value
(def digits (sorted-map "one"   1
                        "two"   2
                        "three" 3
                        "four"  4
                        "five"  5
                        "six"   6
                        "seven" 7
                        "eight" 8
                        "nine"  9))

;; Let's see if the match `n` is already a number or if we need to look into out
;; `digits` map. Since the match is going to be a vector like `["" "1"]` or
;; `["" "one"]` we already destructure the provided argument.
(defn parse-number
  [[_ match]]
  (if (Character/isDigit (first match)) ;; String are collection of characters, we just test the first element.
    (parse-long match)
    (digits match)))

;; Let try it with "1":
(parse-number ["" "1"])

;; and with "two"
(parse-number ["" "two"])

;; We can now define a function that gets the digits from a string, keeps the
;; first and the last in a vector, and then transform them in a number.
;; `re-seq` returns a lazy list of matches of our regex `r` against the string
;; `s`.
(defn collect-numbers
  [s r]
  (->> s
       (re-seq r)
       ((juxt first last))
       (mapv parse-number)
       (apply str)
       parse-long))

;; Test `collect-numbers` against the examples:
(collect-numbers "1abc2" #"(\d)")
(collect-numbers "pqr3stu8vwx" #"(\d)")
(collect-numbers "a1b2c3d4e5f" #"(\d)")
(collect-numbers "treb7uchet" #"(\d)")

;; Now we can collect all the numbers from each input lines and sum them:
(defn part-1
  [input]
  (->> input
       (map #(collect-numbers % #"(\d)"))
       (apply +)))

;; Which gives our answer
(part-1 input)

;; ## Part 2

;; There's a trick here, where are using the look-ahead regex operator `?=` to
;; deal with cases like `oneight`.
(def literal-pattern
  #"(?=(one|two|three|four|five|six|seven|eight|nine|\d))")

;; Some tests:
(collect-numbers "two1nine" literal-pattern)
(collect-numbers "eightwothree" literal-pattern)
(collect-numbers "abcone2threexyz" literal-pattern)
(collect-numbers "xtwone3four" literal-pattern)
(collect-numbers "4nineeightseven2" literal-pattern)
(collect-numbers "zoneight234" literal-pattern)
(collect-numbers "7pqrstsixteen" literal-pattern)

(defn part-2
  [input]
  (->> input
       (map #(collect-numbers % literal-pattern))
       (apply +)))

;; Which gives our answer
(part-2 input)
