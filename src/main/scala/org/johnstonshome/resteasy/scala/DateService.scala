package org.johnstonshome.resteasy.scala

import javax.ws.rs.{ Path, GET, Produces }
import javax.ws.rs.core.{MediaType, MultivaluedMap}
import javax.ws.rs.ext.{Provider, MessageBodyWriter}

import java.io.{OutputStream, Writer, OutputStreamWriter}
import java.lang.reflect.Type

import org.codehaus.jackson.jaxrs.JacksonJsonProvider

import scala.beans.{BeanInfo, BeanProperty}
import scala.collection.JavaConverters._

class MyBean(@BeanProperty var id: String, @BeanProperty var n: Int) {

  private def this() = this(null, 0)

}

@Path("/dates")
class DateService {

  @GET
  @Path("list")
  @Produces(Array("application/json"))
  def getList() = {
    List(1, 2, 3).asJava
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
