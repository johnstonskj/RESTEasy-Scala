package org.johnstonshome.resteasy.scala

import scala.collection.JavaConverters.mutableSetAsJavaSetConverter
import scala.collection.JavaConversions._
import java.util.{Set => JSet, List => JList}
import javax.ws.rs.core.Application
import javax.ws.rs.{Consumes, Produces}
import javax.ws.rs.core.MediaType
import javax.ws.rs.ext.{ContextResolver, Provider}
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.{DeserializationFeature, SerializationFeature, ObjectMapper}
import com.typesafe.config.ConfigFactory

@Provider
@Consumes(Array(MediaType.APPLICATION_JSON))
@Produces(Array(MediaType.APPLICATION_JSON))
class ScalaObjectMapperProvider extends ContextResolver[ObjectMapper] {
  def getContext(`type`: Class[_]): ObjectMapper = {
    val objectMapper = new ObjectMapper();
    objectMapper.registerModule(DefaultScalaModule)
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return objectMapper;
  }
}

object ServerConfig {
  
  private val config =  ConfigFactory.load().getConfig("server") 
  
  val port = config.getInt("http.port")
  
  val contextPath = config.getString("http.contextPath")
  
  val resources = config.getList("services.resources").unwrapped.toList.mkString(",")
  
  val providers = config.getList("services.providers").unwrapped.toList.mkString(",")

}

object ServerMain {

  private val KeyProviders = "resteasy.providers"
  private val KeyResources = "resteasy.resources"

  def main(args : Array[String]) : Unit = {
    
    var servletHolder = new ServletHolder(new HttpServletDispatcher())
    servletHolder.setInitParameter(KeyProviders, ServerConfig.providers)
    servletHolder.setInitParameter(KeyResources, ServerConfig.resources)

    var servletCtxHandler = new ServletContextHandler()
    servletCtxHandler.addServlet(servletHolder, ServerConfig.contextPath)
    
    var server = new Server(ServerConfig.port)
    server.setHandler(servletCtxHandler)
    server.start
    server.join
  } 
}