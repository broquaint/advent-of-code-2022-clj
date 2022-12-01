(ns day1
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]))

(def example-input-lines (clojure.string/split "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000" #"\n"))

(defn lines-to-calories [lines]
  (->>
   lines
   (map (fn [l] (if (empty? l) nil (Integer/valueOf l))))
   (partition-by nil?)
   (filter #(not (nil? (first %))))))

(defn calories-to-elves [cals]
  (map (partial reduce +) cals))

(def calories (lines-to-calories (line-seq (io/reader (io/file "resources/day1")))))
(def elves (calories-to-elves calories))
(def elf-with-most-calories (apply max elves))
(def top-three-elves-calories
  (apply + (take 3 (reverse (sort elves)))))

(comment
  (clerk/serve! {:watch-paths ["src"]})
  (clerk/show! "src/day1.clj")
  (empty? "")
)
