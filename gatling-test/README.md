Webapp stress-test with Gatling
=========================

##Table of contents

1. [Content](#content)
2. [Installation](#installation)
3. [Configuration](#configuration)
4. [Utilisation](#utilisation)
5. [Scenario Protocol](#scenario-protocol)
6. [Key Performance Indicators](#key-performance-indicators)
7. [Fixing Errors](#fixing-errors)
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

If you want to modify the behavior of the test, you can modify the files in the folder src/scala/com/excilys/computerdatabase/gatling

If you have too much trouble adapting your application to the test, test with only the Search and Browse process (check the file in the folder src/scala/com/excilys/computerdatabase/gatling/simulation
 ).

#Utilisation

If you have enabled Spring Security, type the command **mvn gatling:execute -Dgatling.simulationClass=com.excilys.computerdatabase.gatling.simulation.ComputerDatabaseSimulationSecurity** to launch the test. You can view the results of the test in your browser, or in the folder target/gatling/results/

If you have not yet enabled Spring Security, type the command **mvn gatling:execute -Dgatling.simulationClass=com.excilys.computerdatabase.gatling.simulation.ComputerDatabaseSimulation** to launch the test.


#Protocol

##Scenario Protocol

A java application needs some warmup so that the JVM can optimize the application (thanks to the Just-In-Time Compiler). We will consider the tests are relevant after **2 warmup runs**.

Even after those warmups runs, you can experience some differences between the tests. We will consider **3 statistic runs** will be enough. The run with the worst performance will be used for the Key Performance Indicators.

##App Profiles

If the gatling test works fine with 1000 users, you can increase the number of users, change the nbUsers configuration in application.conf.

If the gatling test with your current database has good results, you can now test with a larger database. The scripts used to populate this database are in the folder sqlScripts.

* Create a new database with the file 1-SCHEMA and 2-PRIVILEGES.
* Create and fill your Spring Security table (if you have Spring Security enabled).
* Change the value **max_allowed_packet** of the file /etc/mysql/my.cnf to 80M.
* Restart mysql: **sudo service mysql restart**
* Run the insertCompany.sql and insertComputer.sql scripts, for example: **mysql -uUSER -p computer-database-db2 < insertCompany.sql** and **mysql -uUSER -p computer-database-db2 < insertComputer.sql**

If you want to modify the insert scripts, modify the file **createComputerSql.sh**


#Key Performance Indicators
The scenario can be considered as performing and successful if all the following conditions are met:
* No request has failed.
* At least 95% of the requests response time (95th percentile) is under 1200ms.

#Fixing Errors

Gatling will display the results like this:
> Authenticate: Login form                                 (OK=1010   KO=0     )

The first word is the file where the request failed. If you have an error you can check the file in the folder src/scala/com/excilys/computerdatabase/gatling/simulation/process.

The test executes a scenario (Search, Browse, Add, Edit added computer, Delete edited computer). An error may cause multiple errors (such as an error in the Add process), so try to solve the first one.

If your webapp returns 200 when a request failed (for example if the edit doesn't work), Gatling won't see the test as failed. Always check if the computer has been added if there is an error in the Edit process, and check if the computer has been edited if there is an error in the Delete process.


#Suggested Improvements

* Configure your Mysql, Tomcat, OS.
* Change your hibernate configuration.
* Use jvisualvm to monitor your application.
* Change your connection to the database.
* Check if there is any memory leak.

# Monitoring Tools

#### VisualVM

VisualVM is bundled with the JDK. Launch it, select the tomcat process and start a gatling test. 

 * The heap graph can be used to understand how much memory is being used, how often the GC is called and to detect potential memory leaks. If the heap size after a garbage collection increases over time, it is a sign of a potential memory leak. In that case, comparing heap dump (with allocation traces) at multiple times can help pinpoint where those new objects come from.

* The thread tab will tell you how many threads your application is using and in which state they are at any given time. A thread dump will tell you what the threads are doing. This is especially useful to understand why threads are in deadlock. 

* The sampler & profiler utility will tell you how much time the application is spending in each methods. At the start, this is where you should focus your attention.

[JProfiler](https://www.ej-technologies.com/products/jprofiler/overview.html) and [YourKit](https://www.yourkit.com/) are other profilers, with a lot more features and information, but not free. If you are curious, they both have a fully functional free evaluation period.

#### MySQL

Once you scale to a bigger database, you might start encountering database related problems. Try to monitor what mysql is doing.

 * It is possible to log queries that are too slow (using the property `slow_query_log` in `my.cnf`). If a request takes more than a second to execute, you should try to execute it manually in the mysql console and find a way to fix it.

 * The mysql command `show processlist;` will show you what each connection in your connection pool is currently doing.

#### HTOP

HTOP is a basic linux program which displays the CPU/RAM usages among with the running processes. It is a simple tool yet useful for some quick monitoring information. 

#Table of results

After each improvement you make, you will save the result in a markdown table to keep track of the different ways to optimize an application and a development environment.  
Your table will look like the following:


Scenario: Basic / Security / Your custom simulation  
App Profile: Small-scale / Large-scale / Your custom profile

| Optimization | Best number of users | Performance gain |
| --- | --- | --- |
| Improved XX. Changed number from XX to XX | YYY users | ZZ% |
| Configured XX. Changed XX value to XX. | YYY users | ZZ% |
