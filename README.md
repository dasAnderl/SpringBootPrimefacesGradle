Spring Boot rocks...big time.

In my opinion, spring boot will revolutionize the way java applications are built.
Springboot brings major improvements to nearly any aspect of java application development.
This app here is an example of it.
Setting up a project like this, without springboot is much more complicated.
Check it out yourself.

SpringBootPrimefacesGradle
==========================

Example project based on Java8, Groovy, Gradle, SpringBoot, Jetty, Jsf, Primefaces, Hibernate and H2 Db

to start call gradle task bootRun
to stop call gradle task bootStop (just intellijs stop button does not work)

an example jsf view is implemented, call localhost:port/index.jsf


test examples are in place, also using spring mockmvc to test the rest endpoints (localhost:port/rest/...)

as mock lib here jmockit is used and not standard spring mockito

to overcome jpa lazy loading issues, the open entitymanager in view pattern is used



Things to come:
===============

-open entitymanager in conversation fo long running business processes
-logging with log4j2 or logback
-flexible search/paging engine
-environment specific config
-configuration reloading mechanism (maybe via JMX)
-JMS endpoints

