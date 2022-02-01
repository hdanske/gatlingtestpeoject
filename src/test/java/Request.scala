import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Request {

  def sessionStart = {
    exec(
      http("session start").
        post("/api/v4/P2PIBT706DC183EF5DA6BE1D1A6CB9D9/session/start").
        body(StringBody("""|msisdn=75551112250&password=123123123""".stripMargin)).
        header("Content-Type", "application/x-www-form-urlencoded").
        check(status.is(200)).
        check(jsonPath("$.sessionId").saveAs("session")))
  }

  def token = {
    exec(
      http("token").
        post("/api/v4/P2PIBT706DC183EF5DA6BE1D1A6CB9D9/token").
        header("X-IV-Authorization", "Session ${session}").
        check(status.is(200)).
        check(jsonPath("$.token").saveAs("ecp_token"))
    )
  }

  def start = {
    exec(
      http("getToken").
        post("/api/v4/P2PIBT706DC183EF5DA6BE1D1A6CB9D9/payment/${ecp_token}/start").
        body(StringBody("""|lang=RU&3ds2.supported=true&amount=17000&commission=170&currency=TJS&dst.addToProfile=false&dst.cardId=BXO5DVOFLBQG&dst.type=card_id&params.phoneNumber=111111111&params.quoteCurrency=RUB&params.rublRateSell=0.17&params.transferAmount=100000&paymentId=C2C_KM_RUS_TKB&returnUrl=https%3A%2F%2Fwidget3-test.intervale.ru%2Fbill%2FMOS13ZT2RXMM5ZOY%3Fportal_id%3DP2PIBT706DC183EF5DA6BE1D1A6CB9D9&src.addToProfile=false&src.cardId=BXO5EPU8HQTC&src.csc=123&src.expiry=0425&src.type=card_id""".stripMargin)).
        header("X-IV-Authorization", "Session ${session}").
        header("Content-Type", "application/x-www-form-urlencoded").
        check(status.is(200))
    )
  }
}
