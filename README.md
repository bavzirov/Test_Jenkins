[![Build Status](http://nb-jenkins.intel.com:8080/view/NBO/job/NBOCollectorService/badge/icon)](http://nb-jenkins.intel.com:8080/view/NBO/job/NBOCollectorService/)
[![Quality Gate](http://swce-sonar.intel.com:9123/sonar/api/badges/gate?key=com.intel.swiss.microservices.nbocollectorservice:NBOCollectorService)](http://swce-sonar.intel.com:9123/sonar/dashboard/index/com.intel.swiss.microservices.nbocollectorservice:NBOCollectorService)

# NBOCollectorService

This application was generated using NB Hipster, you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

Before you can build this project, you must install and configure the following dependencies on your machine:
## Building for production

To optimize the NBOCollectorService service for production, run:
`./gradlew -Pprod clean bootRepackage`

To ensure everything worked, run:
`java -jar service/build/libs/*.jar --spring.profiles.active=prod`

## Continuous Integration

To setup this project in Jenkins, use the following configuration:

* Project name: `NBOCollectorService`
* Source Code Management
    * Git Repository: `git@github.intel.com:xxxx/NBOCollectorService.git`
    * Branches to build: `*/master`
    * Additional Behaviours: `Wipe out repository & force clone`
* Build Triggers
    * Poll SCM / Schedule: `H/5 * * * *`
* Build
    * Invoke Gradle script / Use Gradle Wrapper / Tasks: `-Pprod clean test bootRepackage`
* Post-build Actions
    * Publish JUnit test result report / Test Report XMLs: `build/test-results/*.xml`

## Management Scripts
You can find stop and start scripts in `service/src/dist/sbin` directory.
You can run `./gradlew deploy` to create a working copy of your service with conf and management scripts in 
`build/deploy` dir.

### Configuration
There 2 different configurations of the service.
External configuration is configuration that are passed to the service itself and Service properties are java parameters
to the service.

For more information about it please read [ServiceManagement](https://github.intel.com/SWISS/ServiceManagement).

##### External Configuration
The allowed values  in the configuration are listed in `conf.schema.json` file under `sbin` directory.


##### Service Properties
In service properties you can config the java version that is requiered for the service, enable compiled classes if 
needed and set the java parameters of the service such as Xmx, Xms and more. 

## Tar
You can find a tar with the jar, conf and scripts in `service/build/distributions`.
You can run `./gradlew tar` to create a tar with the latest java code and scripts. 
