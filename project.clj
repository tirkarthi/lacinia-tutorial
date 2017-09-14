(defproject lacinia-tut "0.1.0"
  :description "A simple Lacinia tutorial"
  :license {:name "MIT License"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [honeysql "0.9.1"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [com.walmartlabs/lacinia-pedestal "0.3.0"]
                 [com.walmartlabs/lacinia "0.21.0"]
                 [environ "1.1.0"]
		 [cheshire "5.8.0"]]
  :main ^:skip-aot lacinia-tut.run
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :test {:env {:database "test.db"}}})
