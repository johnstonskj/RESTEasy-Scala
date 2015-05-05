# RESTEasy-Scala

This project provides a basic Scala bootstrap for building RESTEasy services. It includes a simple development environment using Jetty as the container and a local config for listing JAX-RS resources. 

![Travis Status](https://travis-ci.org/johnstonskj/RESTEasy-Scala.svg)

## Configuration

TBD

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
      "org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider"
    ]
    resources = [
      "org.johnstonshome.resteasy.scala.DateService"
    ]
  }
}
```

## Running

TBD
