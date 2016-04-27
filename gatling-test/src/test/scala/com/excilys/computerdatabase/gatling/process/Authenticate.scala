package com.excilys.computerdatabase.gatling.process

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

/**
  * Created by Cédric Cousseran on 29/03/16.
  * Authenticate an admin or a user.
  */
class Authenticate(csvFile : String) {
  val config = ConfigFactory.load()
  val feeder = csv(csvFile).random
  val random = new util.Random

  val authenticate: ChainBuilder = exec(http("Login home")
    .get(config.getString("application.urls.loginPage"))
    .check(
      css(config.getString("application.urls.idElement.authenticate.csrf").get, "value").saveAs("csrf_token")
    )
    .resources(http("Login home")
      .get(config.getString("application.urls.static.css.bootstrap")),
      http("Login home")
        .get(config.getString("application.urls.static.css.fontAwesome")),
      http("Get css")
        .get(config.getString("application.urls.static.css.main")),
      http("Get jquery js")
        .get(config.getString("application.urls.static.js.jquery")),
      http("Get bootstrap js")
        .get(config.getString("application.urls.static.js.bootstrap")),
      http("Get jquery validate")
        .get(config.getString("application.urls.static.js.jqueryValidate")),
      http("Get login js")
        .get(config.getString("application.urls.static.js.login")),
      http("Get uk png")
        .get(config.getString("application.urls.static.font.ukFlag")),
      http("Get fr png")
        .get(config.getString("application.urls.static.font.frFlag")))
  )
    .pause(random.nextInt(7) + 3)
    .feed(feeder)
    // Login
    .exec(http("Login form")
    .post(config.getString("application.urls.loginPost").get)
    .formParam(config.getString("application.urls.form.authenticate.username").get, "${name}")
    .formParam(config.getString("application.urls.form.authenticate.password").get, "${password}")
    .formParam(config.getString("application.urls.form.authenticate.submit").get, "Login")
    .formParam(config.getString("application.urls.form.authenticate.csrf").get, "${csrf_token}"))
}
