package org.johnstonshome.resteasy.scala

import scala.beans.{BeanProperty}
import javax.ws.rs.{ Path, GET, Produces }

class MyBean(
    @BeanProperty var id: String, 
    @BeanProperty var n: Int)

@Path("/dates")
class DateService {

  @GET
  @Path("list")
  @Produces(Array("application/json"))
  def getList() = {
    List(1, 2, 3)
  }

    @GET
  @Path("array")
  @Produces(Array("application/json"))
  def getArray() = {
    Array(1,2,3)
  }

  @GET
  @Path("bean")
  @Produces(Array("application/json"))
  def getBean() = {
    new MyBean("foo", 42)
  }
}
