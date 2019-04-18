# Web Service for the library project  
  
## Description  
  
Based on SOAP, this service is the corner stone of this project : it must 
provide an access to multiple methods for various applications : a batch, a user webapp, and a professional webapp for internal workstation (the last one will not be developed).\
It's a single module application where packages separate responsibilities - see diagram

![Package diagram](https://raw.githubusercontent.com/xxjokerx/library-service/master/documents/uml/Package_proj.png)
  
## Technologies  
  
Java version : 8
This project mostly use Spring Boot to manage dependency, it has been started via [Spring Initializr](https://start.spring.io/).  
  
##### Detailed list :  
  
Database :  PostgreSQL 9\
Consumer :  Spring data JPA\
Service : Spring WS and WSDL4J
  
##### Others :  
  
Spring Security to handle passwords encryption\
Mapstruct for object mapping
      
## Deployement  
 
##### 1 - Prepare PostgreSQL, Maven and Tomcat
Install [postgreSQL9](https://www.postgresql.org/download/) and pgAdmin 4, remember the user/password you set during the installation\
Run pgAdmin 4, go to Server/PostgreSQL right click Login/Group Roles and Create a new one.
Fill the username in general, in Definition enter a password (`webservice.datasource.password`). In Privileges enable 'can login?' and 'create database'

Download and [install Maven](https://maven.apache.org/install.html).

Download and unzip [Tomcat](https://tomcat.apache.org/download-90.cgi). 

##### 2 - Create the database
Run SQL Shell (psql), press enter 3 times then fill username and password. Then type : `CREATE DATABASE "{your-db-name}" WITH OWNER = "{your-username}";`
(You can either do this step via pgAdmin)

##### 3 - Create the tables
Run pgAdmin 4, connect to the server, right click on your database | Queries tool and execute this [query](https://raw.githubusercontent.com/xxjokerx/library-service/master/documents/sql-script/creation/000-all_tables.sql).

##### 4 - Insert data
Run pgAdmin 4 | Right click on DB | Query Tool...
Then you can run this [query](https://raw.githubusercontent.com/xxjokerx/library-service/master/documents/sql-script/datadump/db_library_all_data.sql).

/ ! \ Make sure to execute `db_library_public_loan.sql` and `db_library_public_topic_book.sql` as the last queries as they refer to foreign primary keys.

##### 5 - Import the project
Download or clone this repository.

<!--- Import it in your IDE then **build it**.-->

<!--Now your IDE should recognize custom properties in `src/main/application.properties` for auto-completion-->

##### 6 - Configure application.properties
Edit application.properties in `library-service\src\main\resources` :

`webservice.datasource.driverClassNam=org.postgresql.Driver`\
`webservice.datasource.url` should be `jdbc:postgresql://localhost:5432/{your-db-name}`\
`webservice.datasource.username`\
`webservice.datasource.password`\

Then edit the wsdl location `webservice.location` : keep in mind that final location will be `localhost:8080/{tomcat-context-folder}/{webservice.location}` (depending on tomcat port you choose).

<!--##### 6 - Compile the application-->
<!--Using `mvn compile`. Then run-->
<!--src/main/java/com.gg.proj.LibraryServiceApplication by right clicking on it. The program will be run through the embedded tomcat server (if this step fail make sure your set a `server.port` that is not already in use in `application.properties`)\-->
<!--At this point hibernate should have created the tables. Stop the application.-->

##### 7 - Package the application
In command prompt, go to library-service root folder and run `mvn package`

##### 8 - Run tomcat manager :
- Open tomcat_home\conf\tomcat-user.xml and add the following line inside tomcat-user tag :	<user username="user" password="123" roles="standart,manager-gui" />
- Execute tomcat_home\bin\startup.bat to run Tomcat server
- Go to [http://localhost:8080/manager/html](http://localhost:8080/manager/html)

##### 9 - Deploy on tomcat

Under Deploy :

Context-path : for example `/library-service`\
This is you webservice context folder.
 
WAR or directory's URL : local path to your WAR.

Click Deploy and it's done ! 

<!--Download the last version of [Tomcat 9](https://tomcat.apache.org/download-90.cgi). Unzip it. Then go in your IDE and configure it.\-->
<!--With Intellij go to File | Settings | Build, Execution, Deployment | Application Servers. Add a tomcat server via `+` fill the tomcat home and press Ok.-->
<!--Finally go to Run | Edit Configuration... then add a local Tomcat server.\-->
<!--Go to Deployment, then add an artifact (the war you've just packaged). Set a context folder and press OK.-->
<!--/!\ Remember this context folder as it's need for deploying the webapp and run the batch.-->

You can deploy the [client web application](https://github.com/xxjokerx/library-client#deployment).

