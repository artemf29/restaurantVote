***
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d296c9c451554f358295d5e85066197a)](https://www.codacy.com/gh/artemf29/restaurantVote/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=artemf29/restaurantVote&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.com/artemf29/restaurantVote.svg?branch=master)](https://travis-ci.com/artemf29/restaurantVote)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Code of Conduct](https://img.shields.io/badge/code%20of%20conduct-contributor%20covenant-green.svg?style=flat-square)](https://github.com/artemf29/restaurantVote/blob/master/CODE_OF_CONDUCT.md)
***
# Restaurant selection voting system

Enterprise Java project with registration/authorization and access rights based on roles (USER, ADMINISTRATOR).
The administrator can create / edit / delete - dishes / restaurants / users, as well as view / delete voting results.
Users can manage their profile and vote via the REST interface with basic authorization.
The entire REST interface is covered by JUnit tests using Spring MVC Test and Spring Security Test.

### Description:
<pre>
* 2 types of users: administrator and regular users
* The administrator can enter the restaurant and its dishes of the day (usually 2-5 items, only the name of the dish and the price)
* Dishes change every day (updates are made by administrators)
* Every restaurant offers a new menu every day
* Users can vote on which restaurant they want to visit
* Only one vote is counted per user
* If the user votes again ,on the same day:
  - If this happens before 11am , we assume that he has changed his mind
  - If it is after 11am , then it is too late, the vote can't be changed
 </pre> 
***
### Technology stack:
**Maven** / **Hibernate** / **Spring Data JPA** / **Spring Security** / **Spring MVC** / **HSQLDB** / **JUnit** / **EHCACHE** / **Apache Tomcat** / **Json Jackson** / **SLF4J**

***
### Swagger REST Api Documentation:
- http://localhost:8080/swagger-ui.html
- http://localhost:8080/v2/api-docs
#### <pre>to check http requests, use <a href="HttpRequest">/HttpRequest</a> folder (<a href="HttpRequest/User.http">User</a>,<a href="HttpRequest/Vote.http">Vote</a>,<a href="HttpRequest/Restaurant.http">Restaurant</a>,<a href="HttpRequest/Dish.http">Dish</a>)</pre>
***
## curl samples:

### `Admin`

### AdminRestController
- get all users

`curl -s http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

- get user by id 100001
 
`curl -s http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin`

- get user by email admin

`curl -s http://localhost:8080/rest/admin/users/by?email=admin@gmail.com --user admin@gmail.com:admin`

- create user

`curl -s -X POST "http://localhost:8080/rest/admin/users" -H "Content-Type: application/json" -d "{\"name\":\"new User\",\"email\":\"test@icloud.com\",\"password\":\"test123\"}" --user admin@gmail.com:admin`

- update user by id 100000

`curl -s -X PUT "http://localhost:8080/rest/admin/users/100000" -H "Content-Type: application/json" -d "{\"name\":\"update User\",\"email\":\"updated@icloud.com\",\"password\":\"updated\"}" --user admin@gmail.com:admin`

- delete user by id 100000

`curl -s -X DELETE "http://localhost:8080/rest/admin/users/100000" --user admin@gmail.com:admin`

### AdminVoteRestController
- get all votes

`curl -s http://localhost:8080/rest/admin/vote/getAll --user admin@gmail.com:admin`

- get all votes by user id 100001

`curl -s http://localhost:8080/rest/admin/vote/getAll/100001 --user admin@gmail.com:admin`

- get vote by id 100012 by user id 100001

`curl -s http://localhost:8080/rest/admin/vote/100001/100012 --user admin@gmail.com:admin`

- delete vote by id 100012 by user id 100001

`curl -s -X DELETE "http://localhost:8080/rest/admin/vote/100001/100012" --user admin@gmail.com:admin`

### RestaurantRestController
- get all restaurants

`curl -s http://localhost:8080/rest/admin/restaurants --user admin@gmail.com:admin`

- get restaurant by id 100002

`curl -s http://localhost:8080/rest/admin/restaurants/100002 --user admin@gmail.com:admin`

- get with dish restaurant by id 100002

`curl -s http://localhost:8080/rest/admin/restaurants/100002/with-dish --user admin@gmail.com:admin`

- create restaurant

`curl -s -X POST "http://localhost:8080/rest/admin/restaurants" -H "Content-Type: application/json" -d "{\"name\":\"new restaurant\"}" --user admin@gmail.com:admin`

- update restaurant by id 100002

`curl -s -X PUT "http://localhost:8080/rest/admin/restaurants/100002" -H "Content-Type: application/json" -d "{\"name\":\"update restaurant\"}" --user admin@gmail.com:admin`

- delete restaurant by id 100002

`curl -s -X DELETE "http://localhost:8080/rest/admin/restaurants/100002" --user admin@gmail.com:admin`

- get all restaurants with dish

`curl -s http://localhost:8080/rest/getAllRestWithDish --user admin@gmail.com:admin`

- get restaurant not found

`curl -s http://localhost:8080/rest/admin/restaurants/100001 --user admin@gmail.com:admin`

### DishRestController
- get all dishes by restaurant id 100002

`curl -s http://localhost:8080/rest/admin/restaurants/100002/dishes --user admin@gmail.com:admin`

- get dish by id 100007 by restaurant id 100002

`curl -s http://localhost:8080/rest/admin/restaurants/100002/dishes/100007 --user admin@gmail.com:admin`

- create dish by restaurant id 100002

`curl -s -X POST "http://localhost:8080/rest/admin/restaurants/100002/dishes" -H "Content-Type: application/json" -d "{\"name\":\"new dish\",\"price\":1000,\"description\":\"new\"}" --user admin@gmail.com:admin`

- update dish by id 100007 by restaurant id 100002

`curl -s -X PUT "http://localhost:8080/rest/admin/restaurants/100002/dishes/100007" -H "Content-Type: application/json" -d "{\"name\":\"update dish\",\"price\":999,\"description\":\"update\"}" --user admin@gmail.com:admin`

- delete dish by id 100007 by restaurant id 100002

`curl -s -X DELETE "http://localhost:8080/rest/admin/restaurants/100002/dishes/100007" --user admin@gmail.com:admin`

- get dish not found

`curl -s http://localhost:8080/rest/admin/restaurants/100002/dishes/100015 --user admin@gmail.com:admin`

### `User`

### ProfileRestController
- get user profile by id 100000

`curl -s http://localhost:8080/rest/profile --user user@yandex.ru:password`

- register user

`curl -s -X POST "http://localhost:8080/rest/profile/register" -H "Content-Type: application/json" -d "{\"name\":\"new User\",\"email\":\"new@gmail.com\",\"password\":\"new123\"}"`

- update profile

`curl -s -X PUT "http://localhost:8080/rest/profile" -H "Content-Type: application/json" -d "{\"name\":\"update User\",\"email\":\"update@gmail.com\",\"password\":\"update123\"}" --user user@yandex.ru:password`

- delete profile

`curl -s -X DELETE "http://localhost:8080/rest/profile" --user user@yandex.ru:password`

### ProfileVoteRestController
- create vote by restaurant id 100004

`curl -s -X POST "http://localhost:8080/rest/profile/vote?restId=100004" --user user@yandex.ru:password`

- update vote by restaurant id 100004 by vote id 100011 if local time is before 11am

`curl -s -X PUT "http://localhost:8080/rest/profile/vote/100011?restId=100004" --user user@yandex.ru:password`

### RestaurantRestController
- get all restaurants with dish

`curl -s http://localhost:8080/rest/getAllRestWithDish --user user@yandex.ru:password`

***
### Caching(EHCACHE)
App caches 2 methods from the Restaurant for two hours (earlier, if changes are made):
1. getAll() - get all restaurants - for admins
2. getAllRestWithDish() - get all restaurants with dishes - for admins and users
