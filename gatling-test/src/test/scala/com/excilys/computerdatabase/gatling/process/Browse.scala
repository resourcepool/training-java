package com.excilys.computerdatabase.gatling.process

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by Cédric Cousseran on 29/03/16.
  * Browse a random number of pages with random parameters.
  */
object Browse {
  val config = ConfigFactory.load()

  val random = new util.Random
  val numberPage = random.nextInt(10) + 9

  val feederColumn = csv("data/searchColumn.csv").random
  val feederOrder = csv("data/searchOrder.csv").random
  val feederPageSize = csv("data/searchPageSize.csv").random
  val feederPage = Iterator.continually(Map("page" -> (random.nextInt(19) + 1)))

  val browse = feed(feederColumn)
    .feed(feederOrder)
    .feed(feederPageSize)
    .feed(feederPage)
    .repeat(numberPage) {

      val browseUrl = new StringBuilder().append(config.getString("application.urls.dashboardPage")).append("?").append(config.getString("application.urls.param.page")).append("=${pageSize}&").append(config.getString("application.urls.param.pageSize")).append("=${pageSize}&").append(config.getString("application.urls.param.column")).append("=${column}&").append(config.getString("application.urls.param.order")).append("=${order}").toString()

      exec(http("Browse page: ${page},  pageSize: ${pageSize}, column: ${column}, order: ${order}")
        .get(browseUrl))
        .pause(random.nextInt(7) + 3)
    }
}
