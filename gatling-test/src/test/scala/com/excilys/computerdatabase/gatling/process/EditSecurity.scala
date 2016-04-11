package com.excilys.computerdatabase.gatling.process

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by Cédric Cousseran on 29/03/16.
  * Edit the computer which was created before when Spring Security is enabled.
  */
object EditSecurity {
  val config = ConfigFactory.load()
  val random = new util.Random

  val edit = exec(http("Search for edit")
    .get(new StringBuilder().append(config.getString("application.urls.dashboardPage")).append("?").append(config.getString("application.urls.param.search")).append("=${addComputerName}").toString())
    .check(css("#${addComputerName}_name", "href").saveAs("computerURL"))
  )
  .pause(random.nextInt(7) + 3)
    .exec(http("Select for edit")
      .get("${computerURL}")
      .check(
        css(config.getString("application.urls.idElement.edit.csrf").get, "value").saveAs("csrf_token"),
        css(config.getString("application.urls.idElement.edit.id").get, "value").saveAs("computer_id")
      )
    )
    .exec(http("Edit Post")
      .post(config.getString("application.urls.editPost").get)
      .formParam(config.getString("application.urls.form.edit.id").get, "${computer_id}")
      .formParam(config.getString("application.urls.form.edit.name").get, "${addComputerName}_edited")
      .formParam(config.getString("application.urls.form.edit.introduced").get, "${addComputerIntroduced}")
      .formParam(config.getString("application.urls.form.edit.discontinued").get, "${addComputerDiscontinued}")
      .formParam(config.getString("application.urls.form.edit.companyId").get, "${addComputerCompany}")
      .formParam(config.getString("application.urls.form.edit.csrf").get, "${csrf_token}"))
}
