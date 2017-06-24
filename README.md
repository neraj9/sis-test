This is a spring boot based restful web services application created for sis test

To Build the application:  mvn clean install

To Run The application one of the following approaches can be followed:

1) right click on the Application.java and choose run as java application
2)java -jar target/sis-testing-web-1.0.0.jar
3)mvn spring-boot:run


The application is running an embedded Tomcat on port 8106

To access the application from a browser:

 http://localhost:8106/sis-test/teams
 http://localhost:8106/sis-test/teams/chelsea
