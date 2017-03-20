package io.resourcepool.computerdatabase.gatling.process

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import com.typesafe.config.ConfigFactory

/**
  * Created by Cédric Cousseran on 29/03/16.
  * Authenticate an admin or a user.
  */
class Authenticate(csvFile: String) {
  val config = ConfigFactory.load
  val feeder = csv(csvFile).random

  val authenticate: ChainBuilder = exec {
    http("Login home")
      .get(config.getString("application.urls.loginPage"))
      .check(status.is(200))
      .check(
        css(config.getString("application.urls.idElement.authenticate.csrf").get, "value").saveAs("csrf_token")
      )
  }.exitHereIfFailed
    .pause(3, 10)
    .feed(feeder)
    // Login
    .exec {
    http("Authenticate: Login form")
      .post(config.getString("application.urls.loginPost").get)
      .formParam(config.getString("application.urls.form.authenticate.username").get, "${name}")
      .formParam(config.getString("application.urls.form.authenticate.password").get, "${password}")
      .formParam(config.getString("application.urls.form.authenticate.submit").get, "Login")
      .formParam(config.getString("application.urls.form.authenticate.csrf").get, "${csrf_token}")
  }
}
