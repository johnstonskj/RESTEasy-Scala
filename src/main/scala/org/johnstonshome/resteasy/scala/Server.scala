package org.johnstonshome.resteasy.scala

import java.util.{Set => JSet, List => JList}
import scala.collection.JavaConverters.mutableSetAsJavaSetConverter
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher
import javax.ws.rs.core.Application
import com.typesafe.config.ConfigFactory
import scala.collection.JavaConversions._

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