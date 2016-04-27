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
  val random = new util.Random

  val delete = exec(http("Search for delete")
    .get(new StringBuilder().append(config.getString("application.urls.dashboardPage")).append("?").append(config.getString("application.urls.param.search")).append("=${addComputerName}_edited").toString())
    .check(
      css("#${addComputerName}_edited_id", "value").saveAs("computerId"),
      css(config.getString("application.urls.idElement.delete.csrf").get, "value").saveAs("csrf_token")
    ))
    .pause(random.nextInt(7) + 3)
    .exec(http("Delete post")
      .post(config.getString("application.urls.deletePost").get)
      .formParam(config.getString("application.urls.form.delete.selection").get, "${computerId}")
      .formParam(config.getString("application.urls.form.delete.csrf").get, "${csrf_token}"))
    .pause(random.nextInt(7) + 3)
}
