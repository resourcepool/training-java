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

  val edit = exec(http("EditSecurity: Search for edit")
    .get(config.getString("application.urls.dashboardPage"))
    .queryParam(config.getString("application.urls.param.search").toString(), "${addComputerName}")
    .check(
      status.is(200),
      css("#results a", "href").saveAs("computerURL")
    )
  )
    .pause(3, 10)
    .exec(http("EditSecurity: Select for edit")
      .get("${computerURL}")
      .check(
        status.is(200),
        css(config.getString("application.urls.idElement.edit.csrf").get, "value").saveAs("csrf_token"),
        css(config.getString("application.urls.idElement.edit.id").get, "value").saveAs("computer_id")
      )
    )
    .exec(http("EditSecurity: Edit Post")
      .post(config.getString("application.urls.editPost").get)
      .formParam(config.getString("application.urls.form.edit.id").get, "${computer_id}")
      .formParam(config.getString("application.urls.form.edit.name").get, "${addComputerName}_edited")
      .formParam(config.getString("application.urls.form.edit.introduced").get, "${addComputerIntroduced}")
      .formParam(config.getString("application.urls.form.edit.discontinued").get, "${addComputerDiscontinued}")
      .formParam(config.getString("application.urls.form.edit.companyId").get, "${addComputerCompany}")
      .formParam(config.getString("application.urls.form.edit.csrf").get, "${csrf_token}"))
}
