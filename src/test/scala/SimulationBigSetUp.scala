import io.gatling.core.Predef._
import io.gatling.http.Predef._

class SimulationBigSetUp extends Simulation {

  val httpConf = http
      .baseURL("http://localhost:8080")
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .doNotTrackHeader("1")
      .inferHtmlResources(BlackList("https://www.google-analytics.com/.*",
                                      "http://thecatapi.com/.*"))
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("Simulation 5 - Big Setup")
      .exec(
          http("index.html")
              .get("/"))
      .pause(2)

  setUp(
    scn.inject(
      constantUsersPerSec(1000) during 5,
      rampUsersPerSec(100) to 200 during 10,
      rampUsers(2000) over 10
    ).protocols(httpConf)
  )
}