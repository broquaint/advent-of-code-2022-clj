(ns aoc-day2
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]))

(def p1-mapping {"A" :rock "B" :paper "C" :scissors})
(def p2-mapping {"X" :rock "Y" :paper "Z" :scissors})
(def score-mapping {:rock 1 :paper 2 :scissors 3})
(def win-states #{[:rock :scissors] [:scissors :paper] [:paper :rock]})

(def example-input (clojure.string/split "A Y
B X
C Z" #"\n"))

(def strategy-guide
  (->> (line-seq (io/reader (io/file "resources/day2"))) ; example-input
       (map #(clojure.string/split % #" "))
       (map (fn [[p1 p2]]
              [(p1-mapping p1) (p2-mapping p2)]))))

(defn score-turn [p1 p2]
  (let [result (cond
                 (= p1 p2) 3
                 (win-states [p1 p2]) 6
                 :else 0)]
    (+ result (score-mapping p1))))

(defn score-game [game]
  (->> game
       (reduce (fn [acc [p1 p2]]
                 (+ acc (score-turn p2 p1))) 0)))

(def part1-result (score-game strategy-guide))

(comment
  (clerk/serve! {:watch-paths ["src"]})
  (clerk/show! "src/day2.clj")
  (score-turn :rock :paper)
)
