;; # 🎄 Advent of Clerk
;;
;; [Advent of Code](https://adventofcode.com) with
;; [Clerk](https://clerk.vision).
;;
;; Solutions authored by [@mdallastella](https://github.com/mdallastella/) with love in Clojure. 💕
;;
;; Below is a listing of all my solutions grouped by year.
;;
;; All the solutions also include the original problem spec, along with my commentary.
;;
;; Greatly inspired by [advent of clerk](https://github.com/nextjournal/advent-of-clerk).
;;
;; This project is a complete ripoff of [@elken](https://github.com/elken/aoc) beautiful project. ❤️
(ns index
  {:nextjournal.clerk/visibility {:code :hide :result :hide}}
  (:require
   [babashka.fs :as fs]
   [nextjournal.clerk :as clerk]
   [nextjournal.clerk.view :as clerk.view]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [util :as u]))

(alter-var-root #'clerk.view/include-css+js
                (fn [include-css+js-orig extra-includes]
                  (fn [state]
                    (concat (include-css+js-orig state)
                            extra-includes)))
                (list [:style#extra-styles (slurp (clojure.java.io/resource "style.css"))]))

(defn build-paths []
  (-> "src/solutions"
      fs/list-dir
      (fs/list-dirs "*.clj")
      sort))

(defn group-solutions []
  (->> (build-paths)
       (group-by
        (fn [path]
          (let [[_ _ year _] (fs/components path)]
            (str year))))
       (sort-by first)))

{:nextjournal.clerk/visibility {:result :show}}
^::clerk/no-cache
(clerk/html
 (into [:div]
       (mapv (fn [[year paths]]
               [:details
                [:summary
                 [:span.text-2xl.font-bold year]
                 [:span.ml-10 (format "(%s solutions)" (count paths))]]
                (into [:ul]
                      (mapv (fn [path]
                              (when-let [day (second (re-matches #".*day(\d+).clj" path))]
                                [:li
                                 [:a {:href (-> path
                                                (str/replace ".clj" "")
                                                clerk/doc-url)}
                                  (util/load-title day year)]]))
                            (map str paths)))])
             (group-solutions))))
