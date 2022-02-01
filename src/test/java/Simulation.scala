import io.gatling.core.Predef.scenario
import Request._

object Simulation {
  def getTokenScen = scenario("token request scenario")
    .exec(sessionStart)
    .exec(token)
    .exec(start)


}
