package com.excilys.computerdatabase.gatling.process

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by Cédric Cousseran on 29/03/16.
  * Search for a computer.
  */
object Search {
  val config = ConfigFactory.load()
  val feeder = csv("data/search.csv").random
  val random = new util.Random

  val search = exec(http("Home of the application")
    .get(config.getString("application.urls.dashboardPage")))
    .pause(random.nextInt(7) + 3)
    .feed(feeder)
    .exec(http("Search a computer ${searchCriterion}")
      .get(new StringBuilder().append(config.getString("application.urls.dashboardPage")).append("?").append(config.getString("application.urls.param.search")).append("=${searchCriterion}").toString())
    )
    .pause(random.nextInt(7) + 3)
}
