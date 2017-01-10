# NBOCollectorService API

This gradle module should contain all your service DTOs (Data Transfer Object) and the interfaces that your rest 
controllers implements. This way you can easily generate client code for your service and use it in other services
which need to call your service.


## Publishing the client api to Maven

`gradle -papi publish` - Publish current version to the snapshot repository

`gradle -papi -DbuildType=release publish` - Publish current version to the release repository

## Important Stuff

* Don't use `@RequestMapping` annotation on the interface as Feign does not support it [GitHub Issue](https://github.com/spring-cloud/spring-cloud-netflix/issues/861)
* When you use `@PathVariable` annotation, give the name of the var to the value property of the annotation, e.g: 
`@PathVariable(value = "name") String name`, see the same issue

## Swagger Support
Use the `SpringFox` annotation on your interface to supply information for Swagger in your interface. This way you 
will be able to use swagger for client generation in all languages. 

## Validation
In order for the controller to validate your DTO, you should use the `@Valid` annotation on incoming DTOs
and use `javax.validation.constraints` annotations on your DTOs

## Documentation

[Spring Web MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html)

[Spring Cloud Feign](http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html#spring-cloud-feign)

[Spring Fox](http://springfox.github.io/springfox/docs/current/)
