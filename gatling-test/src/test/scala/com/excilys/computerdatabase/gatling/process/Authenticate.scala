package com.excilys.computerdatabase.gatling.process

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

/**
  * Created by Cédric Cousseran on 29/03/16.
  * Authenticate an admin or a user.
  */
class Authenticate(csvFile: String) {
  val config = ConfigFactory.load()
  val feeder = csv(csvFile).random

  val authenticate: ChainBuilder = exec(http("Login home")
    .get(config.getString("application.urls.loginPage"))
    .check(status.is(200))
    .check(
      css(config.getString("application.urls.idElement.authenticate.csrf").get, "value").saveAs("csrf_token")
    )
    .resources(http("Authenticate: Login home")
      .get(config.getString("application.urls.static.css.bootstrap")),
      http("Authenticate: Login home")
        .get(config.getString("application.urls.static.css.fontAwesome")),
      http("Authenticate: Get css")
        .get(config.getString("application.urls.static.css.main")),
      http("Authenticate: Get jquery js")
        .get(config.getString("application.urls.static.js.jquery")),
      http("Authenticate: Get bootstrap js")
        .get(config.getString("application.urls.static.js.bootstrap")),
      http("Authenticate: Get jquery validate")
        .get(config.getString("application.urls.static.js.jqueryValidate")),
      http("Authenticate: Get login js")
        .get(config.getString("application.urls.static.js.login")),
      http("Authenticate: Get uk png")
        .get(config.getString("application.urls.static.font.ukFlag")),
      http("Authenticate: Get fr png")
        .get(config.getString("application.urls.static.font.frFlag")))
  )
    .pause(3, 10)
    .feed(feeder)
    // Login
    .exec(http("Authenticate: Login form")
    .post(config.getString("application.urls.loginPost").get)
    .formParam(config.getString("application.urls.form.authenticate.username").get, "${name}")
    .formParam(config.getString("application.urls.form.authenticate.password").get, "${password}")
    .formParam(config.getString("application.urls.form.authenticate.submit").get, "Login")
    .formParam(config.getString("application.urls.form.authenticate.csrf").get, "${csrf_token}"))
}
