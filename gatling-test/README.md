Webapp stress-test with Gatling
=========================

##Table of contents

1. [Content](#content)
2. [Installation](#installation)
3. [Configuration](#configuration)
4. [Pre-requisites on your webapp](#pre-requisites-on-your-webapp)
5. [Utilisation](#utilisation)
6. [Scenario Protocol](#scenario-protocol)
7. [Key Performance Indicators](#key-performance-indicators)
8. [Suggested Improvements](#suggested-improvements)
9. [Table of results](#table-of-results)

#Content

This stress-test with Gatling will simulate the activity of 1000 users in the computer database webapp. It will allow you to discover some of your application's bottleneck.

Two simulations are available, depending if you have already enabled Spring Security or not. If you've enabled Spring Security, the test will simulate the activity of 1000 normal users and 10 admins.

The user simulation use the following scenario: authenticate, browse, and search for computers.

The admin simulation use the following scenario: authenticate, browse, search, add, edition and deletion of a computer.


#Installation
You can copy the content of this folder in your existing maven project, or use it as a single project.

#Configuration
This test was created for a specific computer database application, but you can easily configure it to match yours. The configuration files are in the folder src/test/resources/.

In application.conf, you must specify the URLs, forms params used in your application.

The folder src/test/resources/data contains some csv feeders which are used in the test.
* The file addComputer.csv contains values that are used when creating a new computer. The name is not present, he is created randomly.
* The file admin.csv contains the username and login of an admin, and the file user.csv contains the username and login of a user.
* The file search.csv contains a searchComputerName that should be present when a search with the searchCriterion is done.
* The file searchColumn.csv contains the column parameter used in the get request for sorting the computers.
* The file searchOrder.csv contains the order parameter used in the get request for sorting the computers.
* The file searchPageSize.csv contains the page size parameter used in the get request for sorting the computers.

You can add as many lines as you want in these csv feeders.

If you want to modify the behavior of the test, you can modify the files in the folder src.scala.com.excilys.computerdatabase.gatling

#Pre-requisites on your webapp

For the test to work, some elements in your webapp must have a specific id:
* In the dashboard, the checkbox used to choose which computer to delete must have the id: {name computer}_id
* In the dashboard, the href used to edit a computer must have the id: {name_computer}_name

If you have too much trouble adapting your application to the test, test with only the browse process.

#Utilisation

If you have enabled Spring Security, type the command **mvn gatling:execute -Dgatling.simulationClass=com.excilys.computerdatabase.gatling.simulation.ComputerDatabaseSimulationSecurity** to launch the test. You can view the results of the test in your browser, or in the folder target/gatling/results/

If you have not yet enabled Spring Security, type the command **mvn gatling:execute -Dgatling.simulationClass=com.excilys.computerdatabase.gatling.simulation.ComputerDatabaseSimulation** to launch the test.


#Protocol

##Scenario Protocol

A java application needs some warmup so that the JVM can optimize the application (thanks to the Just-In-Time Compiler). We will consider the tests are relevant after **2 warmup runs**.

Even after those warmups runs, you can experience some differences between the tests. We will consider **3 statistic runs** will be enough. The run with the worst performance will be used for the Key Performance Indicators.

##App Profiles

If the gatling test with your current database has good results, you can now test with a larger database. The scripts used to populate this database are in the folder sqlScripts.

* Create a new database with the file 1-SCHEMA and 2-PRIVILEGES.
* Create and fill your Spring Security table.
* Change the value **max_allowed_packet** of the file /etc/mysql/my.cnf to 80M.
* Run the insertCompany.sql and insertComputer.sql scripts, for example: **mysql -uroot -proot computer-database-db2 < insertCompany.sql** and **mysql -uroot -proot computer-database-db2 < insertComputer.sql**

If you want to modify the insert scripts, modify the file **createComputerSql.sh**


#Key Performance Indicators
The scenario can be considered as performing and successful if all the following conditions are met:
* No request has failed.
* At least 95% of the requests response time (95th percentile) is under 1200ms.

#Suggested Improvements

* Configure your Mysql, Tomcat, OS.
* Change your hibernate configuration.
* Use jvisualvm to monitor your application.
* Change your connection to the database.
* Check if there is any memory leak.

#Table of results

After each improvement you make, you will save the result in a markdown table to keep track of the different ways to optimize an application and a development environment.  
Your table will look like the following:


Scenario: Basic / Security / Your custom simulation  
App Profile: Small-scale / Large-scale / Your custom profile

| Optimization | Best number of users | Performance gain |
| --- | --- | --- |
| Improved XX. Changed number from XX to XX | YYY users | ZZ% |
| Configured XX. Changed XX value to XX. | YYY users | ZZ % |
