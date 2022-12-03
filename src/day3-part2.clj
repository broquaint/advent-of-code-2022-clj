(ns aoc-day3-part2
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]))

(def example-input (clojure.string/split "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw" #"\n"))

(def priority-map
  (into
   (reduce (fn [acc c] (assoc acc (char c) (- c 96))) {} (range (int \a) (+ 1 (int \z))))
   (reduce (fn [acc c] (assoc acc (char c) (- c 38))) {} (range (int \A) (+ 1 (int \Z))))))

(def rucksacks
  (->> (line-seq (io/reader (io/file "resources/day3"))) ; example-input
       (partition 3)))

(defn find-badges [rucksacks]
  (let [sets (map #(map set %) rucksacks)
        badges (map (partial apply clojure.set/intersection) sets)
        priorities (map priority-map (map first badges))]
    priorities))

(def result (apply + (find-badges rucksacks)))

(comment
  (clerk/serve! {:watch-paths ["src"]})
  (clerk/show! "src/day3-part1.clj")
  (split-at 12 "vJrwpWtwJgWrhcsFMMfFFhFp")
  (line-seq (io/reader (io/file "resources/day3")))
  (map #(clojure.set/intersection (set (first %)) (set (second %))) rucksacks)
  (vec [#{1 2} #{3 4}])
  (count-dupes rucksacks)
)
