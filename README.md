[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d296c9c451554f358295d5e85066197a)](https://www.codacy.com/gh/artemf29/restaurantVote/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=artemf29/restaurantVote&amp;utm_campaign=Badge_Grade)
-  Restaurant selection voting system

REST API using:
- Maven 
- Hibernate 
- Spring Data JPA 
- Spring Security 
- Spring MVC 
- JUnit 
- EHCACHE 
- Apache Tomcat 
- Json Jackson 
- SLF4J

Description: 

Enterprise Java project with registration/authorization and access rights based on roles (USER, ADMINISTRATOR).
The administrator can create / edit / delete - dishes / restaurants / users, as well as view / delete voting results.
Users can manage their profile and vote via the REST interface with basic authorization.
The entire REST interface is covered by JUnit tests using Spring MVC Test and Spring Security Test.

Features:
- 2 types of users: administrator and regular users
- The administrator can enter the restaurant and its dishes of the day (usually 2-5 items, only the name of the dish and the price)
- Dishes change every day (updates are made by administrators)
- Every restaurant offers a new menu every day
- Users can vote on which restaurant they want to visit
- Only one vote is counted per user
- If the user votes again ,on the same day:
  *If this happens before 11am , we assume that he has changed his mind
  *If it is after 11am , then it is too late, the vote can't be changed

scheme :
- good REST API - swagger
- Travis and Codacy and ??
- url get/post/put/delete 
- cache
- curl 
