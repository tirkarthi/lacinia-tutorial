(ns lacinia-tut.core
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.edn :as edn]
            [environ.core :refer [env]]
            [honeysql.core :as sql]
            [clojure.java.jdbc :refer :all]
            [honeysql.helpers :refer :all :as helpers]
            [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
            [com.walmartlabs.lacinia.schema :as schema])
  (:gen-class))

(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     (env :database "app.db")})

(defn get-tracks [context arguments value]
  (let [{:keys [artistid trackname trackid]} value
        {:keys [trackname trackid first], :or {trackname trackname trackid trackid}} arguments
        query (-> (select :*)
                  (merge-where (if (some? artistid) [:= :trackartist artistid]))
                  (merge-where (if (some? trackname) [:= :trackname trackname]))
                  (merge-where (if (some? trackid) [:= :trackid trackid]))
                  (from :track)
                  (limit (if (some? first) first 10))
                  sql/format)
        result (jdbc/query db query)]
    result))

(defn get-artist [context arguments value]
  (let [{:keys [id name]} arguments
        {id :trackartist, :or {id id}} value
        query (-> (select :*)
                  (merge-where (if (some? id) [:= :artistid id]))
                  (merge-where (if (some? name) [:= :artistname name]))
                  (from :artist)
                  sql/format)
        result (jdbc/query db query)]
    result))

(defn create-artist [context arguments value]
  (let [{:keys [artistname]} arguments
        insert-stmt (-> (insert-into :artist)
                        (columns :artistname)
                        (values [[artistname]])
                        (sql/format))
        result (jdbc/execute! db insert-stmt)]
    result))

(def artist-schema
  (-> "schema.edn"
      slurp
      edn/read-string
      (attach-resolvers {:get-artist get-artist
                         :get-tracks get-tracks
                         :create-artist create-artist})
      schema/compile))

;; (execute artist-schema "query { track(trackid: 2) { trackname trackartist { artistname tracks { trackname trackartist { artistname }}} } }" nil nil)
