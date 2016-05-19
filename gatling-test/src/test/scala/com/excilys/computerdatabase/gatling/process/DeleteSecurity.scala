package com.excilys.computerdatabase.gatling.process

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by Cédric Cousseran on 29/03/16.
  * Delete the computer which was edited before when Spring Security is enabled.
  */
object DeleteSecurity {
  val config = ConfigFactory.load()

  val delete = exec(http("DeleteSecurity: Search for delete")
    .get(config.getString("application.urls.dashboardPage"))
    .queryParam(config.getString("application.urls.param.search").toString(), "${addComputerName}_edited")
    .check(
      status.is(200),
      css("#results input", "value").saveAs("computerId"),
      css(config.getString("application.urls.idElement.delete.csrf").get, "value").saveAs("csrf_token")
    ))
    .pause(3, 10)
    .exec(http("DeleteSecurity: Delete post")
      .post(config.getString("application.urls.deletePost").get)
      .formParam(config.getString("application.urls.form.delete.selection").get, "${computerId}")
      .formParam(config.getString("application.urls.form.delete.csrf").get, "${csrf_token}"))
    .pause(3, 10)
}
