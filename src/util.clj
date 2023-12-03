(ns util
  (:require
   [clj-http.client :as client]
   [hickory.core :as h]
   [hickory.render :as hr]
   [hickory.select :as s]
   [babashka.fs :as fs]))

(defn load-problem
  "Given a DAY and a YEAR, cache the problem definition locally. If `AOC_TOKEN`
  is set correctly, this will pull both parts if you've done part 1."
  [day year]
  (let [day (str (parse-long day))
        file-name (format "day%s-%s.html" day year)
        path (fs/path (fs/temp-dir) file-name)]
    (when-not (fs/exists? path)
      (let [resp (->> {:headers {"Cookie" (str "session=" (System/getenv "AOC_TOKEN"))
                                 "User-Agent" (System/getenv "AOC_USER_AGENT")}}
                      (client/get (format "https://adventofcode.com/%s/day/%s" year day)))]
        (when (= 200 (:status resp))
          (spit (str path) (:body resp)))))

    (let [doc (h/as-hickory (h/parse (slurp (str path))))
          parts (map #(hr/hickory-to-html %) (s/select (s/child (s/tag :article)) doc))]
      (apply str (mapcat str parts)))))

(defn load-title
  "Given a DAY and a YEAR, return the titel of the problem."
  [day year]
  (let [day (str (parse-long day))
        file-name (format "day%s-%s.html" day year)
        _ (load-problem day year)
        path (fs/path (fs/temp-dir) file-name)
        doc (h/as-hickory (h/parse (slurp (str path))))
        parts (map #(hr/hickory-to-html %) (s/select (s/child (s/tag :h2)) doc))]
    (->> parts
         first
         (re-find #"(?<=>--- )(.*)(?= ---<)")
         first)))
