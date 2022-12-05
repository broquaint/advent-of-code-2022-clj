(ns aoc-day5-part1
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]))

(def example-input "    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

(defn parse-stack-line [{:keys [offsets names]} line]
  (let [stack-vals (map (partial get line) offsets)]
    (reduce (fn [res [name v]]
              (assoc res name v)) {} (map vector names stack-vals))))

(defn describe-stack [names]
  (let [numeric-names (map #(Integer/valueOf %) names)
        offsets (reduce (fn [acc n] (conj acc (+ 4 (last acc)))) [1] (rest numeric-names))]
    {:names names :offsets offsets}))

(defn build-stacks [stacks stack-entry]
  (reduce-kv
   (fn [s k v]
     (if (= v \space)
       s
       (update-in s [k] conj v)))
   stacks stack-entry))

(defn parse-procedure-line [line]
  (let [[move from to] (re-seq #"\d+" line)]
    {:move (Integer/valueOf move) :from from :to to}))

(defn parse-input [input]
  (let [[stack-string proc-string] (clojure.string/split example-input #"\n\n")
        stack-lines (clojure.string/split stack-string #"\n")
        proc-lines (clojure.string/split proc-string #"\n")
        stack-desc (describe-stack (re-seq #"\d+" (last stack-lines)))
        parsed-lines (map (partial parse-stack-line stack-desc) (butlast stack-lines))
        seed-stacks (reduce #(assoc %1 %2 []) {} (:names stack-desc))
        stacks (reduce build-stacks seed-stacks parsed-lines)
        procedure (map parse-procedure-line proc-lines)]
    {:stacks stacks
     :procedure procedure
     :stack-desc stack-desc}))

(defn move-stacks [stacks move from to]
  (let [[vals-to-move from-stack] (split-at move (stacks from))
        to-stack (concat (reverse vals-to-move) (stacks to))]
;    (println "given " move from to "moving" vals-to-move "result" to-stack)
    (assoc stacks from from-stack to to-stack)))

(defn execute-stack-procedure [{:keys [stacks procedure]}]
  (reduce (fn [s {:keys [move from to]}]
            (move-stacks s move from to)) stacks procedure))

(def inputs (parse-input example-input))
(def moved-stacks (execute-stack-procedure inputs))

(comment
  (clerk/serve! {:watch-paths ["src"]})
  (clerk/show! "src/dayX.clj")
  (line-seq (io/reader (io/file "resources/dayX")))
  (describe-stack ["1" "2" "3"])
  (parse-stack-line "[N] [C]    " {:names ["1" "2" "3"], :offsets [1 5 9]})
  (move-stacks {"1" [\N \Z], "2" [\D \C \M], "3" [\P]} 1 "2" "1")
)
