(ns lacinia-tut.run
  (:require [lacinia-tut.core :refer [artist-schema]]
            [io.pedestal.http :as server]
            [com.walmartlabs.lacinia :refer [execute]]
            [com.walmartlabs.lacinia.pedestal :as lacinia])
  (:gen-class))

(def service (lacinia/pedestal-service artist-schema {:graphiql true}))

(defonce runnable-service (server/create-server service))

(defn -main
  [& args]
  (println "\nCreating your server...")
  (server/start runnable-service))
