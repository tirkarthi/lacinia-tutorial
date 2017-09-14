(ns lacinia-tut.core-test
  (:require [clojure.test :refer :all]
            [com.walmartlabs.lacinia :refer [execute]]
            [lacinia-tut.core :refer :all]))

(deftest track-by-id-test
  (testing "Query for a track by id "
    (is (= {:data {:track '({:trackname "Shadow of the day"})}}
           (execute artist-schema "query { track(trackid: 1) { trackname }}" nil nil)))))

(deftest track-by-name-test
  (testing "Query for a track by name "
    (is (= {:data {:track '({:trackname "Numb"})}}
           (execute artist-schema "{track(trackname: \"Numb\") { trackname }}" nil nil)))))

(deftest artist-by-id-test
  (testing "Query for an artist by id "
    (is (= {:data {:artist '({:artistname "Kiara"})}}
           (execute artist-schema "query { artist(id: 1) { artistname }}" nil nil)))))

(deftest artist-by-name-test
  (testing "Query for an artist by name"
    (is (= {:data {:artist '({:artistname "Chester"})}}
           (execute artist-schema "query { artist(name: \"Chester\") { artistname }}" nil nil)))))
