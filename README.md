# Web Service for the library project  
  
### Description  
  
TODO
  
### Technologies  
  
This project mostly use Spring Boot to manage dependency, it has been started via [Spring Initializr](https://start.spring.io/).  
  
##### Detailed list :  
  
Database :  PostgreSQL 9\
Consumer :  Spring data JPA\
Service : Spring WS and WSDL4J
  
##### Others :  
  
Spring Security to handle passwords encryption\
Mapstruct for object mapping
      
### Deployement  
 
##### 1 - Prepare postgreSQL
Install [postgreSQL9.6](https://www.postgresql.org/download/) and pgAdmin 4, remember the user/password you set during the installation\
Run pgAdmin 4, go to Server/PostgreSQL right click Login/Group Roles and Create a new one.
Fill the name (this will be your `webservice.datasource.username`) in general, in Definition enter a password (`webservice.datasource.password`). In Privileges enable 'can login?' and 'create database'

##### 2 - Create the database
Run SQL Shell (psql), press enter 3 times then fill username and password. Then type : `create database "{your-db-name}" with owner "{your-username}";`
(You can either do this step via pgAdmin)

##### 3 - Let hibernate create tables
Following steps will let the application create the tables.

##### 4 - Import the project
- Download or clone this repository.
- Import it in your IDE then **build it**.

Now your IDE should recognize custom properties in `src/main/application.properties` for auto-completion

##### 5 - Configure application.properties
Edit the custom datasource properties :\
`webservice.datasource.driverClassNam=org.postgresql.Driver`\
`webservice.datasource.url` should be `jdbc:postgresql://localhost:5432/{your-db-name}`\
`webservice.datasource.username`\
`webservice.datasource.password`\
Then edit the wsdl location `webservice.location` : keep in mind that final location will be `localhost:{tomcat-port}/{tomcat-context}/webservice.location`

##### 6 - Compile the application
Using `mvn compile`. Then run
src/main/java/com.gg.proj.LibraryServiceApplication by right clicking on it. The program will be run through the embedded tomcat server (if this step fail make sure your set a `server.port` that is not already in use in `application.properties`)\
At this point hibernate should have created the tables. Stop the application.

##### 7 - Insert data
Run pgAdmin 4 / Right click on DB / Query Tool...
Then you can run those [queries](https://github.com/xxjokerx/library-service/tree/master/documents/sql-script/datadump).

/ ! \ Make sure to execute `db_library_public_loan.sql` and `db_library_public_topic_book.sql` as the last queries as they refer to foreign primary keys.

##### 8 - Package the application
By running `mvn clean package`

##### 9 - Deploy on external tomcat
Download the last version of [Tomcat 9](https://tomcat.apache.org/download-90.cgi). Unzip it. Then go in your IDE and configure it.\
With Intellij go to File | Settings | Build, Execution, Deployment | Application Servers. Add a tomcat server via `+` fill the tomcat home and press Ok.
Finally go to Run | Edit Configuration... then add a local Tomcat server.\
Go to Deployment, then add an artifact (the war you've just packaged). Set a context folder and press OK.

/!\ Remember this context folder as it's need for deploying the webapp and run the batch.

##### 10 - Run the app on tomcat
Select your configuration and run it. You should be able to call the service's method on SOAP UI

