^{:nextjournal.clerk/visibility :hide-ns}
(ns solutions.2023.day02
  {:nextjournal.clerk/toc true}
  (:require [clojure.java.io :as io]
            [util :as u]
            [nextjournal.clerk :as clerk]
            [clojure.string :as s]))

;; # Problem
^::clerk/no-cache (clerk/html (u/load-problem "02" "2023"))

;; # Solution
;;
;; First things first, let's load our input and parse it.
(def input (->> (slurp (io/resource "inputs/2023/day02.txt")) ;; Load the resource
                s/split-lines))                             ;; Split into lines

;; ## Part 1

;; So first of all we need to parse a game: old fashion way, no regexps... It will be a little verbose:
;;
;; Split plays by `;` and then split the single elements by `,`:
(defn split-plays
  [plays]
  (as-> plays $
    (s/split $ #"; ")
    (map #(s/split % #", ") $)))

(split-plays "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")

;; Parse each draw, splitting by space the single elements and turn them into a map:
(defn parse-draws
  [set]
  (reduce
   (fn [acc el]
     (let [[n color] (s/split el #"\s")
           n (parse-long n)]
       (assoc acc (keyword color) n)))
   {}
   set))

(map parse-draws (split-plays "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"))

;; Keep game id from the plays, split the plays and parse them:
(defn parse-game
  [s]
  (let [[game plays] (s/split s #": ")
        id (parse-long (second (s/split game #"\s")))]
    (->> plays
         split-plays
         (map parse-draws)
         (into [id]))))

(parse-game "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")

;; To answer the first part of the question, we need to “calculate” the max
;; number of cubes for each draw and compare them to a boundary. We can do this
;; using `merge-with`.
(def boundaries
  {:red 12 :green 13 :blue 14})

(defn max-draw
  [[id & draws]]
  (->> draws
       (apply (partial merge-with max))
       (vector id)))

;; Predicate that checks a draw against boundaries
(defn respect-boundaries?
  [[id draw]]
  (->> (keys draw)
       (map #(>= (% boundaries) (% draw)))
       (every? true?)))

;; So we parse all the games, find out the max number of cubes for each one,
;; keep the ones that respect the boundaries and sum their ids.
(defn part-1
  [input]
  (->> input
       (map parse-game)
       (map max-draw)
       (filter respect-boundaries?)
       (map first)
       (apply +)))

;; Which gives our answer
(part-1 input)

;; ## Part 2
;;
;; Part two sounds easy given the approach we took: we still need to calculate
;; the max-draw, but this time we multiply the number of red, green, and blue
;; cubes together... we should keep an eye on `nil`...
(defn part-2
  [input]
  (->> input
       (map parse-game)        ;; Parse all the games
       (map max-draw)          ;; Get the highest possible draw for each game
       (map second)            ;; Discard the game id
       (map vals)              ;; Get only the cubes values
       (map (partial apply *)) ;; Multiply them
       (apply +)))             ;; Sum them

;; Which gives our answer
(part-2 input)
