# Showcase for application managed entity managers with dynamically associated database connections.

This demo web application shows, how EntityManagerFactories and EntityManagers can be opened for
databases whose URLs are specified dynamically at application runtime.

It must be noted, that the trick is done by using a JTA EntityManagerFactory without an JTA datasource,
but with javax.persistence.jdbc connection properties. This is not exactly covered by the JPA spec:

"Section 8.2.1.2: ... A transaction-type of JTA assumes that a JTA data source will be provided"

The application workes as desired on WildFly 10.0.0 using Hibernate 5.0.9. It does not work on GlassFish 4.0.1 with EclipseLink 2.5.x, though!
