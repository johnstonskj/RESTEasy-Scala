# RESTEasy-Scala

**NOTE: This project is archived, no new development is planned.**

This project provides a basic Scala bootstrap for building RESTEasy services. It 
includes a simple development environment using Jetty as the container and a 
local config for listing JAX-RS resources. The intent is to provide an 
environment where the classes you write are as Scala-native as possible and as
much of the JAX-RS plumbing is taken care of for you. The exact set of dependencies
should be modifiable to run on any other JAX-RS implementation, however you'll 
possibly need an alternate startup procedure to pull in the Jackson providers.  

![Travis Status](https://travis-ci.org/johnstonskj/RESTEasy-Scala.svg)

## Configuration

Configuration is pretty simple, a file named `application.conf` is provided (in
the folder `src/main/resources` and which is required to be on the classpath at
rime time. This file is in the [Typesafe Config](org.johnstonshome.resteasy.scala)
format, as shown in the example below. 

```
server {
  http {
    port = 8080
    contextPath = "/"
    secure {
      port = 8443
    }
  }
  services {
    providers = [
      "org.johnstonshome.resteasy.scala.ScalaObjectMapperProvider",
      "org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider"
    ]
    resources = [
      "org.johnstonshome.resteasy.scala.DateService"
    ]
  }
}
```

Clearly the `port` and `contextPath` are global (no SSL config is currently 
supported), the `providers` list shouldn't need to be modified but you can
add your own resource classes to the list of `resources`. 

## Building a Service

In general a service looks how it would in Java, with the JAX-RS annotations
applied. Note the use of `Array()` to denote the set of values the method
produces in the example below. The use of Scala-specific serializers means
that the response values do not need to be cast to Java types to work.

```
@Path("/dates")
class DateService {

  @GET
  @Path("list")
  @Produces(Array("application/json"))
  def getList() = {
    List(new Date(), new Date(), new Date())
  }
```

Model objects can be easily specified as bean classes using `@BeanProperty`.

```
abstract class BaseDate(@BeanProperty var format: String)
case class ADate(
    @BeanProperty var id: String, 
    @BeanProperty var date: Date) extends BaseDate("simple")
case class AnotherDate(
    @BeanProperty var date: Date, @BeanProperty var important: Boolean) extends BaseDate("important") 
```

## Running

The Scala main is in the object `org.johnstonshome.resteasy.scala.ServerMain` and
running it from Maven should work.
