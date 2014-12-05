Spring Boot rocks...big time.

In my opinion, spring boot will revolutionize the way java applications are built.
Springboot brings major improvements to nearly any aspect of java application development.
This app here is an example of it.
Setting up a project like this, without springboot is much more complicated.
Check it out yourself.

SpringBootPrimefacesGradle
==========================

Example project based on Java8, Groovy, Gradle, SpringBoot, Jetty, Spring web security, Jsf, Primefaces, Hibernate and H2 Db

to start call gradle task bootRun, 
to stop call gradle task bootStop (just intellijs stop button does not work and so you are able to use debugging features)

Features so far
===============

spring and java might be mixed (only one main/test src folder for both) -> enjoy groovy coding in src/main/java

websecurity with spring

example jsf/primefaces view on localhost:port/index.jsf

jpa/hibernate on a h2 db (open session in entitymanager pattern used)

entity auditing

h2console is enabled (localhost:port/h2console)

example rest endpoints (spring driven, localhost:port/rest/...)

amqp messaging (rabbit mq)

use of Spring Profiles

tests are in place, using jmockit lib and also using spring mockmvc to test the rest endpoints 


Things to come:
===============

-open entitymanager in conversation fo long running business processes

-logging with log4j2 or logback

-flexible search/paging engine

-environment specific config

-configuration reloading mechanism (maybe via JMX)

-JMS endpoints

