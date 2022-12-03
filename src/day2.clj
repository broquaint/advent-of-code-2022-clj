(ns aoc-day2
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]))

(def p1-mapping {"A" :rock "B" :paper "C" :scissors})
(def p2-mapping {"X" :rock "Y" :paper "Z" :scissors})
(def hand-score-mapping {:rock 1 :paper 2 :scissors 3})
(def result-mapping {"X" :loss "Y" :draw "Z" :win})
(def score-mapping {:loss 0 :draw 3 :win 6})
(def loss-mapping {:rock :paper :paper :scissors :scissors :rock})
(def win-mapping (clojure.set/map-invert loss-mapping))
(def win-states #{[:rock :scissors] [:scissors :paper] [:paper :rock]})

(def example-input (clojure.string/split "A Y
B X
C Z" #"\n"))

(def strategy-guide
  (->> (line-seq (io/reader (io/file "resources/day2")))
       (map #(clojure.string/split % #" "))
       (map (fn [[c1 c2]]
              [(p1-mapping c1) (result-mapping c2)]))))

(defn score-turn [p1 result]
  (let [hand (case result
                 :loss (win-mapping p1)
                 :draw p1
                 :win (loss-mapping p1))]
        (+ (score-mapping result) (hand-score-mapping hand))))

(defn score-game [game]
  (->> game
       (reduce (fn [acc [c1 c2]]
                 (+ acc (score-turn c1 c2))) 0)))

(def part2-result (score-game strategy-guide))

;; Keeping part1 implementation for reference.

(def strategy-guide-part1
  (->> (line-seq (io/reader (io/file "resources/day2"))) ; example-input
       (map #(clojure.string/split % #" "))
       (map (fn [[p1 p2]]
              [(p1-mapping p1) (p2-mapping p2)]))))

(defn score-turn-part1 [p1 p2]
  (let [result (cond
                 (= p1 p2) 3
                 (win-states [p1 p2]) 6
                 :else 0)]
    (+ result (score-mapping p1))))

(def part1-result (score-game strategy-guide))

(comment
  (clerk/serve! {:watch-paths ["src"]})
  (clerk/show! "src/day2.clj")
  (score-turn :rock :paper)
  (map #(apply score-turn %) strategy-guide)
)
