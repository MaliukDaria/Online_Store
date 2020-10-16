# Online Store
 The goal of this project is to create a model of an online store. The project implements user authorization with different functionality for ordinary users and administrators:
 - ordinary users can register, login/logout, add products to shopping cart, checkout and view orders history
 - administrators can login/logout, add new products and delete products from the database, delete users, view and delete orders for all users.

## Implementation details and technologies
Project based on 3-layer architecture:
- Presentation layer (controllers)
- Application layer (services)
- Data access layer (DAO)

There are two DAO layer implementations in the project: using local Storage (ordinary List) and using JDBC and local MySQL database. JSP pages are used to display information in the browser. 

### Technologies
* Apache Tomcat - version 9.0.37
* MySQL - version 8.0.21
* JDBC
* Servlet
* JSTL
* JSP
* HTML, CSS

## Setup
1. Configure Apache Tomcat 
2. Install MySQL and MySQL Workbench
3. Create a schema and all the necessary tables by using the script from resources/init_db.sql in MySQL Workbench
4. In the /util/ConnectionUtil.java class change the "user" (root) and "password" (1111) properties to the ones you specified when installing MySQL:
( dbProperties.put ("user", "root");
dbProperties.put ("password", "1111"); )
5. After starting the project, click on "Inject test data into the DB" to add the initial data. After that, the following will be added to the database:
- a user with administrator rights (Login: Admin, password: admin)
- regular user (Login: Alise, password: 1111)
- a user with administrator and regular user rights (Login: Bob, password: 1111)
- several test products
## Author
[Maliuk Daria](https://github.com/MaliukDaria)
