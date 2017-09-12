(defproject lacinia-tut "0.1.0"
  :description "A simple Lacinia tutorial"
  :license {:name "MIT License"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha20"]
                 [honeysql "0.9.1"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [com.walmartlabs/lacinia "0.20.0" :exclusions [clojure-future-spec]]]
  :main ^:skip-aot lacinia-tut.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
