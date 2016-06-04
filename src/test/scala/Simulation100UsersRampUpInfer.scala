import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Simulation100UsersRampUpInfer extends Simulation {

  val httpConf = http
      .baseURL("http://localhost:8080")
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .doNotTrackHeader("1")
      .inferHtmlResources()
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("Simulation 3 - 100 users over 15 seconds with inferResources")
      .exec(
          http("index.html")
              .get("/"))
      .pause(2)

  setUp(
    scn.inject(
      rampUsers(100).over(10)
    )
  ).protocols(httpConf)
}