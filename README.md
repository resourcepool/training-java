Training: computer-database    
===========================  

#Installation

##1. Database
Create a local **MySQL** server.  
Execute scripts **1-SCHEMA.sql**, **2-PRIVILEGES.sql** and **3-ENTRIES.sql** in config/db.  
Schema created: **computer-database-db**
Tables created: **company, computer**  
User created: `admincdb`
with password: `qwerty1234`

##2. IDE  
###2.1. Eclipse  
- Add your project to the current workspace: **File** -> **Import** -> **Existing projects into workspace**    
- Create a new Tomcat 8.0 Server: Follow steps **[HERE](http://www.eclipse.org/webtools/jst/components/ws/M4/tutorials/InstallTomcat.html)**
- In your project properties, select **Project facets**, convert your project to faceted form, and tick **Dynamic Web Module** (3.0) and **Java** (1.8)
- Select **Runtime** tab (in the previous **project facets** menu)  and check the Tomcat 8.0 Server created above as your project runtime  

###2.2. IntelliJ IDEA   
- Add your project to the current workspace: **Import Project**, select **Create project from existing sources**
- Create a new Tomcat 8.0 Server: **Run** -> **Edit Configurations** and point it to your local Tomcat directory (button **Configure...**) 
- Set project structure: In **File** -> **Project Structure**, add an Artifact with default options (Artifact tab)  

##3. Git repository
- Create your own github account, and initialize a new git repository called "computer-database".  
- After the initial commit, add and commit a meaningful .gitignore file. 

You are ready to start coding.

##4. Start coding
####4.1. Layout
Your customer requested to build a computer database application. He owns about 500+ computers made by different manufacturers (companies such as Apple, Acer, Asus...).  
Ideally, each computer would contain the following: a name, the date when it was introduced, eventually the date when it was discontinued, and the manufacturer. Obviously, for some reasons, the existing data is incomplete, and he requested that only the name should remain mandatory when adding a computer, the other fields being filled when possible.  
The list of computers can be modified, meaning your customer should be able to list, add, delete, and update computers. The list of computers should also be pageable.  
The list of companies should be exhaustive, and therefore will not require any update, deletion etc...  

###4.2. Command line interface client
The first iteration will be dedicated to implement a first working version of your computer database, with a CLI-UI.  
The CLI will have the following features:

- List computers  
- List companies  
- Show computer details (the detailed information of only one computer)  
- Create a computer  
- Update a computer  
- Delete a computer  

####4.2.1. Start
You will organize your project among several packages, such as model, persistence, service, ui, mapper...  
Please use Singleton patterns where it makes sense, and implement your own Persistence management layer (for connections).

####4.2.2. Pages
Now that your app's main features work, implement the pageable feature. We recommend the use of a Page class, containing your entities and the page information.  

####4.2.3. Code review, logging (t0 + 2 days)
Important Points: Architecture (daos, mappers, services, models, exceptions etc...)? Singleton, IOC patterns? Validation (dirty checking?)? Date API? Secure inputs?  
Javadoc? Comments? Use Slf4j-api logging library, with the most common implementation: logback.  

###4.3. CLI + Web interface client 
Now that your backend skeleton is working, we want to add a second more user-friendly UI, such as a Web-UI.  
As it will require more and more libraries (more JARs to include in the build path etc...), we should consider using a build manager. Moreover, testing is a very important aspect of QA, and testing libraries should be implemented before going any further, the same for logging.  
Then, you can work on implementing all features on the provided static pages, using JSTL, Tags, Servlets, JSPs...  

####4.3.1. Maven, Logging & Unit testing
Refactor your project tree to match maven standards. (Tip: you should exit eclipse, move folders around, and reimport your project using File -> Import -> Existing maven projects).  
Include necessary libraries such as mysql-connector, JUnit, Mockito, Slf4j, and create the test classes for the backend you have already developed (N.B.: This is against TDD best practices. You should always code your tests simulteanously while developing your features).  
Creating test classes implies to take into account ALL possibilities: Illegal calls, legal calls with invalid data, and legal calls with valid data.  


####4.3.2. Implement listing and computer add features in the web-ui
Using the provided template https://github.com/loicortola/spec-cdb/tree/master/static, integrate the previous features using Servlets, JSPs, JSTL, and Tags.  
Use DTOs (Data Transfer Object) to transport only relevant data to the JSPs.  
Implement Computer listing (paginated), and add features.  
Create two tags (In your own Taglib): one for the pagination module, one for links.  
Example: 
```
<mylib:link target="dashboard" page="${requestScope.page.current + 1}" limit="${requestScope.page.limit}" ... />   
<mylib:pagination page="${requestScope.page.current}" page-count="${requestScope.page.count}" ... />  
```
Warning: All features will be implemented and tested using Selenium automated with maven.  

####4.3.3. Secure through validation
Implement both frontend (jQuery) and backend validation in the web-ui.

####4.3.4. Code review (t0 + 7 days)
Important Points: Maven structure? Library scopes? Architecture (daos, mappers, services, models, dtos, controllers, exceptions, validators)? Validation? Unit test coverage? What about selenium integration into maven?  JSTL Tags and HTML documents structure.  
Prepare a point about Threading (Connections, concurrency), and Transactions.

####4.3.5. Implement all other features in the web-ui
Implement Computer edit, delete, total count features.  
Warning: All features will be implemented and tested using Selenium automated with maven  

####4.3.6. Implement search and order by features
Search box can look for either computer or company objects.

####4.3.7. Add Company deletion feature in cli
In the command line interface, add a feature which deletes a company, and all computers related to this company. Warning: Using SQL CASCADE is forbidden. This implies the use of a transaction.  

####4.3.8. Connection pool, Transactions
Add a connection pool (BoneCP), put your credentials in an external properties file.  
Implement a solid transaction handling model.  

####4.3.9. Code review (t0 + 10 days)
Important Points: Maven structure? Library scopes? Architecture (daos, mappers, services, models, dtos, controllers, exceptions, validators)? Validation? Unit test coverage? Search and order by design choices? JSTL Tags and HTML documents structure.  
Point about Threading (Connections, concurrency), and Transactions.

####4.3.10. Threadlocal
Replace existing connection logic with a ThreadLocal object. 

###4.4. Embracing Spring Framework

####4.4.1. Spring
Enable the use of Spring to manage your objects's lifecycle, and transactions.  
Important: Be careful to use slf4j bridges to display spring logs. Do not forget to setup your logback configuration.  
Replace your connection pool by a real datasource configured in the spring context.  
Which problems did you encounter? Study and note all the possible ways of solving the dependency injection issue in servlets.  
Warning: Do not replace your Servlets by another class. Your controllers should still extend HttpServlet.

####4.4.2. Point overview: Spring integration (t0 + 11 days)
How a webapp is started, how spring initializes itself.  
Explanation of the common problems encountered with the different contexts.  
Roundtable of the solutions found, best practices.

####4.4.3. JDBCTemplate
Change your DAO Implementation and use the JDBCTemplate from spring-jdbc to make your requests

####4.4.4. Spring MVC
You can now forget about Servlets and use Spring MVC as Controller for your webapp.  
Use Spring MVC validation annotations to validate your DTOs.  
Add custom error pages.  

####4.4.5. i18n
Implement spring multilingual features (French/English).

####4.4.6. Code Review (t0 + 14 days)
Important Points: How did you split your Spring / Spring MVC contexts? How to switch from a language to another? How about javascript translation? Did you use spring-mvc annotations, forms and models?

###4.5. Multi module, ORM, and Security

####4.5.1. Hibernate
Add the Hibernate ORM to your project (managed by spring). You can choose the following APIs to implement it. HQL, JPA/Criteria, QueryDSL, Spring data JPA. 

####4.5.2. Maven multi-module
Now that your app is getting dense, it makes sense to split it into modules.  
Split your maven app into 5 different modules (we recommend exiting your IDE and making those changes by hand).  
Warning: you need to also split your applicationContext files: indeed, each module should be able to work as a standalone.  
Following modules can be created: core, persistence, service, binding, webapp, console.

####4.5.3. Security
Add Spring Security to your project. Choose a stateless approach, and use an extra UserDAO and related SQL table to store and retrieve user login info.  
Use Digest HTTP Auth.

####4.5.4. Code Review (t0 + 20 days)
Important points: Which API was the most efficient for your queries? Limitations of those APIs.
Maven and Spring contexts evaluation, unit tests evaluation.

###4.6. Web Services, REST API

####4.6.1. Jax WS / Jax RS 
Now, we want your webapp to also produce APIs so that clients could access the resources remotely.  
Refactor your CLI client to act as a remote client to your webapp, using either Jax-RS or Jax-WS libraries.

####4.6.2. Jackson
Finally, to allow the creation of AngularJS, Mobile (Android/iOS) or third party clients, you should expose the computer listing feature using Jackson and Spring RestController.

####4.6.3. Final Code Review (t0 + 24 days)

##4.7. Final Presentation (t0 + 25 days)

