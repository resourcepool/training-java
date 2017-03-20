package io.resourcepool.computerdatabase.gatling.process

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import com.typesafe.config.ConfigFactory

/**
  * Created by Cédric Cousseran on 29/03/16.
  * Search for a computer.
  */
object Search {
  val config = ConfigFactory.load
  val feeder = csv("data/search.csv").random

  val search = exec {
    http("Search: Home of the application")
      .get(config.getString("application.urls.dashboardPage"))
  }.exitHereIfFailed
    .pause(3, 10)
    .feed(feeder)
    .exec {
      http("Search: Search a computer ${searchCriterion}")
        .get(config.getString("application.urls.dashboardPage"))
        .queryParam(config.getString("application.urls.param.search").toString(), "${searchCriterion}")
        .check(status.is(200))
    }.pause(3, 10)
}
