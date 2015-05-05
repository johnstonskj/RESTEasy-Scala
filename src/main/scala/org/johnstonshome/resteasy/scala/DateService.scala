package org.johnstonshome.resteasy.scala

import scala.beans.{BeanProperty}
import java.util.Date
import javax.ws.rs.{ Path, GET, Produces }

abstract class BaseDate(@BeanProperty var format: String)
case class ADate(
    @BeanProperty var id: String, 
    @BeanProperty var date: Date) extends BaseDate("simple")
case class AnotherDate(
    @BeanProperty var date: Date, @BeanProperty var important: Boolean) extends BaseDate("important") 

@Path("/dates")
class DateService {

  @GET
  @Path("list")
  @Produces(Array("application/json"))
  def getList() = {
    List(new Date(), new Date(), new Date())
  }

    @GET
  @Path("array")
  @Produces(Array("application/json"))
  def getArray() = {
    Array(new Date(), new Date(), new Date())
  }

  @GET
  @Path("bean")
  @Produces(Array("application/json"))
  def getBean() = {
    new ADate("foo", new Date())
  }
}
