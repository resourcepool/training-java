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
      exec(http("Browse: Browse page: ${page},  pageSize: ${pageSize}, column: ${column}, order: ${order}")
        .get(config.getString("application.urls.dashboardPage"))
        .queryParam(config.getString("application.urls.param.page").toString(), "${page}")
        .queryParam(config.getString("application.urls.param.pageSize").toString(), "${pageSize}")
        .queryParam(config.getString("application.urls.param.column").toString(), "${column}")
        .queryParam(config.getString("application.urls.param.order").toString(), "${order}")
        .check(status.is(200))
      )
        .pause(3, 10)
    }
}
