# E-Store online book market

This is the server-side program of E-Store online book market. It is a practical Java Web application during the mother-fucking college-organized Java EE training. 

Related technologies:

* **Spring MVC** for web MVC
* **Spring Rest** for view object Restful presentation
* **Spring Security** for access control & authorization
* **Spring Data JPA**+**MySQL** for database persistence
* **Redis+lettuce** for the memory storage of session
* **Hibernate Validator** for data validation checking
* **Lambda** for [functional programming](https://flyingbytes.github.io/programming/java8/functional/part0/2017/01/16/Java8-Part0.html)
* **Tomcat JDBC Pool** for database connection pooling
* **Fast Json (made by Alibaba)** for Json parser
* **Gson (made by Google)** for Json-Object transition
* **jQuery** & **Ajax** for front-end & data transmission
* **Maven** for package management
* **Docker-maven-plugin** for remote docker deployment
* **Docker** no matter the server and database, they all run with docker!
* **Git** for versioning
* **Tomcat** as a web container
* **Java9**: minimal Java platform is Java 8, recommend to Java 9

Some technologies that have been considered, but not used:

* **Shiro:** is not supporting to **OAuth2**, so it has been replaced by **Spring Security** in this program.
* **DBCP2:** Why do I use `Tomcat jdbc pool` to replace DBCP2?
  * **Commons DBCP is over 60 classes. `tomcat-jdbc-pool` core is 8 classes**, hence modifications for future requirement will require much less changes. This is all you need to run the connection pool itself. 
  * **Commons DBCP uses static interfaces**. This means you **have to use the right version for a given JRE version** or you may see `NoSuchMethodException` exceptions.
  * `Tomcat jdbc pool` is a Tomcat module, it **depends on Tomcat JULI**, a simplified logging framework used in Tomcat.
  * For more details, look at [the introduction part to The Tomcat JDBC Connection Pool](https://tomcat.apache.org/tomcat-9.0-doc/jdbc-pool.html)
* **Jedis:** is not thread-safe and [look at this article](https://github.com/spring-projects/spring-session/issues/789), so it has been replaced by **lettuce** in this program
* **Redis connection pool:** As we all know, the lettuce will only create one connection per instance by default, [it supports connection pooling](https://github.com/lettuce-io/lettuce-core/wiki/Connection-Pooling), but is it necessary to have one for this program? no, the answer here is no. Redis user-space operations are **single-threaded** so there is **no need for throughput reasons to have more than one connection**. A single thread can execute about 10,000 Redis commands (`GET`, `SET`) per second. Using 6 to 8 threads this number rapidly goes up to 80kOps/sec to 100kOps/sec. For more details [look at this article from lettuce-core issues](https://github.com/lettuce-io/lettuce-core/issues/360)

Further development:

* **Spring HATEOAS** for creating REST representations that follow the HATEOAS principle
* **Spring Security for OAuth2** for OAuth2 authorization & get rid of the usage of session (related dependencies has been imported in current version)

> **Java is the engine then Spring is the fuel**.
