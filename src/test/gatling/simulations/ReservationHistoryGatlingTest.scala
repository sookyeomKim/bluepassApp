import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the ReservationHistory entity.
 */
class ReservationHistoryGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connection("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )


    val headers_http_authentication = Map(
        "Content-Type" -> """application/x-www-form-urlencoded""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "x-auth-token" -> "${x_auth_token}"
    )

    val scn = scenario("Test the ReservationHistory entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401)))
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .formParam("username", "admin")
        .formParam("password", "admin")
        .check(jsonPath("$.token").saveAs("x_auth_token")))
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all reservationHistorys")
            .get("/api/reservationHistorys")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new reservationHistory")
            .post("/api/reservationHistorys")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "reservationId":null, "reservationMethod":"SAMPLE_TEXT", "reservationTime":"2020-01-01T00:00:00.000Z", "startTime":"2020-01-01T00:00:00.000Z", "endTime":"2020-01-01T00:00:00.000Z", "used":null, "userId":null, "userEmail":"SAMPLE_TEXT", "userName":"SAMPLE_TEXT", "userPhoneNumber":"SAMPLE_TEXT", "userGender":"SAMPLE_TEXT", "registerStatus":"SAMPLE_TEXT", "actionScheduleId":null, "actionScheduleName":"SAMPLE_TEXT", "day":"SAMPLE_TEXT", "scheduleType":"SAMPLE_TEXT", "attendeeLimit":"0", "classScheduleId":null, "classScheduleEtc":"SAMPLE_TEXT", "clubId":null, "clubName":"SAMPLE_TEXT", "clubZipcode":"SAMPLE_TEXT", "clubAddress1":"SAMPLE_TEXT", "clubAddress2":"SAMPLE_TEXT", "clubPhoneNumber":"SAMPLE_TEXT", "onlyFemail":null, "clubManagerMobile":"SAMPLE_TEXT", "notificationType":"SAMPLE_TEXT", "reservationClose":"SAMPLE_TEXT", "actionId":null, "actionTitle":"SAMPLE_TEXT", "actionDescription":"SAMPLE_TEXT", "actionUseLimitType":"SAMPLE_TEXT", "actionUseLimitValue":"0", "instructorId":null, "instructorName":"SAMPLE_TEXT", "categoryId":null, "categoryName":"SAMPLE_TEXT", "ticketId":null, "ticketName":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_reservationHistory_url")))
            .pause(10)
            .repeat(5) {
                exec(http("Get created reservationHistory")
                .get("${new_reservationHistory_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created reservationHistory")
            .delete("${new_reservationHistory_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
