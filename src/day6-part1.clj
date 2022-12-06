(ns aoc-day6-part1
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]))

(def example-input "mjqjpqmgbljsphdztnvjfqwrcgsmlb")

(def find-protocol-start
  (let [input (slurp "resources/day6")
        ]
   (loop [idx 0]
     (let [offset (+ idx 14)
           ss (subs input idx offset)]
       (if (= 14 (count (set ss)))
         offset
         (recur (+ idx 1)))))))

(comment
  (clerk/serve! {:watch-paths ["src"]})
  (clerk/show! "src/dayX.clj")
  (line-seq (io/reader (io/file "resources/dayX")))
)
