package lk.egreen.apistudio.console.command

import lk.egreen.apistudio.transpoter.Transporter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.core.CommandMarker
import org.springframework.shell.core.annotation.{CliAvailabilityIndicator, CliCommand, CliOption}
import org.springframework.stereotype.Component

import scala.beans.BeanProperty

/**
  * Created by dewmal on 7/14/16.
  */
//import scala.reflect.{BeanProperty, BooleanBeanProperty}
//remove if not needed


@Component
class ServerCommands extends CommandMarker {

  private var simpleCommandExecuted: Boolean = false


  @Autowired
  @BeanProperty
  var transpoter: Transporter = null;


  @CliAvailabilityIndicator(Array("api start"))
  def isSimpleAvailable(): Boolean = true


  @CliCommand(value = Array("api start"), help = "Start Api Studio")
  def simple(
              @CliOption(key = Array("host"), mandatory = false, help = "Host", specifiedDefaultValue = "0.0.0.0") location: String,
              @CliOption(key = Array("port"), mandatory = false, help = "Api Studio Port", specifiedDefaultValue = "8080") port: String): String = {
    simpleCommandExecuted = true
//    var location: String = location;
//    var port: String = port
//
//    if (null == location) location = "0.0.0.0"
//    if (null == port) location = "7878"

    transpoter.start(location, Int.unbox(port))

    return "Started";
  }


}
