package com.excilys.computerdatabase.gatling.simulation

import com.excilys.computerdatabase.gatling.process._
import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

/**
  * Created by Cédric Cousseran on 29/03/16.
  * Launch the gatling test for the computer database webapp without Spring Security enabled.
  */
class ComputerDatabaseSimulation extends Simulation {
  before {
    println("The computer database simulation is about to to start!")
  }

  val config = ConfigFactory.load()

  val httpConf = http
    .baseURL(config.getString("application.baseUrl")) // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_10 = Map("Content-Type" -> "application/x-www-form-urlencoded") // Note the headers specific to a given request

  val users = scenario("Users").exec(Browse.browse, Search.search, Add.add, Edit.edit, Delete.delete)

  setUp(
    users.inject(rampUsers(config.getInt("application.nbUsers")) over (30 seconds))
  ).protocols(httpConf)
    .assertions(
      global.failedRequests.count.is(0),
      global.responseTime.percentile3.lessThan(1200), //check that 95% of the response time is under 1200ms
      global.responseTime.percentile4.lessThan(800)
    )

  after {
    println("The simulation is finished, check the html file for more informations about the results.")
  }

}
