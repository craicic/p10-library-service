# Web Service for the library project  
  
### Description  
  
TODO
  
### Technologies  
  
This project mostly use Spring Boot to manage dependency, it has been started via [Spring Initializr](https://start.spring.io/).  
  
Detailed list :  
  
Database :  PostgreSQL 9\
Consumer :  Spring data JPA\
Service : Spring WS and WSDL4J
  
Other :  
  
Spring Security to handle passwords encryption
Mapstruct for object mapping
      
### Deployement  
 
##### 1 - Prepare postgreSQL

Install [postgreSQL9.6](https://www.postgresql.org/download/) and pgAdmin 4, remember the user/password you set during the installation\
Run pgAdmin 4, go to Server/PostgreSQL right click Login/Group Roles and Create a new one.
Fill the name (this will be your `webservice.datasource.username`) in general, in Definition enter a password (`webservice.datasource.password`). In Privileges enable 'can login?' and 'create database'

##### 3 - Create the database

Run SQL Shell (psql), press enter 3 times then fill username and password. Then type : `create database "{your-db-name}" with owner "{your-username}";`
(You can either do this step via pgAdmin)

##### 4 - Let hibernate create tables
Following steps will let the application create table

##### 5 - Import the project
- Download or clone this repository.
- Import it in your IDE then **build it**.

Now your IDE should recognize custom properties in `src/main/application.properties`

##### 6 - Configure application.properties
Fill the custom datasource properties. Then edit the wsdl `location webservice.location` : keep in mind that final location will be `localhost:{tomcat-port}/{tomcat-context}/webservice.location`.\

##### 7 - Compile the application
Using `mvn compile`. Then run
src/main/java/com.gg.proj.LibraryServiceApplication by right clicking on it. The program will be run through the embedded port (if this step fail make sure your set a `server.port` that is not already in use in `application.properties`).\
At this point hibernate should have created the tables.

##### 8 - Insert data

Run pgAdmin 4 / Right click on DB / Query Tool...
Then you can run those [queries](https://github.com/xxjokerx/library-service/tree/master/documents/sql-script/datadump).

/ ! \ Make sure to execute `db_library_public_loan.sql` and `db_library_public_topic_book.sql` as the last queries as they refer to foreign primary keys.



