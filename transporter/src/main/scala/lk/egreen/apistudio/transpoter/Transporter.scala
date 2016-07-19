package lk.egreen.apistudio.transpoter

import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.server.ResourceConfig

/**
  * Created by dewmal on 7/14/16.
  */
trait Transporter {

  def start(location: String, port: Int, resConfig: ResourceConfig, absBinder: AbstractBinder)


  def stop()

}
