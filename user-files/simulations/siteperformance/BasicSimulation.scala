package siteperformance

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  val target = "https://burhanh.github.io"

  val httpConf = http
    .baseUrl(target) // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.9")
    .acceptEncodingHeader("gzip, deflate, br")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")

  val headers = Map("Accept" -> """text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8""")
  
  val scn = scenario("Site performance Simulation") // A scenario is a chain of requests and pauses
    
    .exec(
      http("Home Page request")
        .get("/")
        .headers(headers)
        .check(status.is(200))
        .check(currentLocation.is(target + "/"))
    )

    .exec(
      http("Post 1 request")
      .get("/initial/2018/10/26/initial-post.html")
      .headers(headers)
      .check(status.is(200))
      .check(currentLocation.is(target + "/initial/2018/10/26/initial-post.html"))
    )

    .exec(
      http("Post 2 request")
      .get("/about/")
      .headers(headers)
      .check(status.is(200))
      .check(currentLocation.is(target + "/about/"))
    )

    .exec(
      http("Post 3 request")
      .get("/automaton/")
      .headers(headers)
      .check(status.is(200))
      .check(currentLocation.is(target + "/automaton/"))
    )

    // .exec { session =>
    //   // displays the content of the session in the console (debugging only)
    //   println(session)
    //   // return the original session
    //   session
    // }
    
    // .pause(7) // Note that Gatling has recorded real time pauses

  setUp(
    scn.inject(atOnceUsers(1)  // Note! Only one user. 
  ).protocols(httpConf))
}
