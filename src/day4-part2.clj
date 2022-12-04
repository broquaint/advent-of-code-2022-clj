(ns aoc-day4-part2
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [clojure.string :as s]))

(def example-input (clojure.string/split "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8" #"\n"))

(def sections
  (->> (line-seq (io/reader (io/file "resources/day4")))
       (map #(s/split % #","))
       (map (fn [[s1 s2]]
              [(s/split s1 #"-")
               (s/split s2 #"-")]))
       (map (fn [[s1 s2]]
              [(map #(Integer/valueOf %) s1)
               (map #(Integer/valueOf %) s2)]))
       (map (fn [[s1 s2]]
              [(set (range (first s1) (+ 1 (second s1))))
               (set (range (first s2) (+ 1 (second s2))))]))))

(defn does-overlap [s1 s2]
  (filter #(.contains s2 %) s1))

(defn find-overlaps [sections]
  (map (fn [[s1 s2]]
         (or (does-overlap s1 s2)
             (does-overlap s2 s1))) sections))

(def result (count (filter (complement empty?) (find-overlaps sections))))

(comment
  (clerk/serve! {:watch-paths ["src"]})
  (clerk/show! "src/day4-part1.clj")
  (line-seq (io/reader (io/file "resources/day4")))
  (map (fn [x y] {:x x :y y}) [1] [4 5 6])
  (does-overlap [5 6 7] [7 8 9])
  (.contains [5 6 7] 7)
)
