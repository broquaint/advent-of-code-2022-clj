(ns aoc-dayX
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]))

(def example-input (clojure.string/split "" #"\n"))


(comment
  (clerk/serve! {:watch-paths ["src"]})
  (clerk/show! "src/dayX.clj")
  (line-seq (io/reader (io/file "resources/dayX")))
)
