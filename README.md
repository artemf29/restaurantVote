***
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d296c9c451554f358295d5e85066197a)](https://www.codacy.com/gh/artfil/restaurantVote/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=artfil/restaurantVote&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.com/artfil/restaurantVote.svg?branch=master)](https://travis-ci.com/artfil/restaurantVote)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Code of Conduct](https://img.shields.io/badge/code%20of%20conduct-contributor%20covenant-green.svg?style=flat-square)](https://github.com/artfil/restaurantVote/blob/master/CODE_OF_CONDUCT.md)
***
# Restaurant selection voting system

Enterprise Java project with registration/authorization and access rights based on roles (USER, ADMINISTRATOR).
The administrator can create / view / edit / delete - dishes / menus / restaurants / users.
Users can manage their profile, view restaurants and their menus, and vote via the REST interface with basic authorization.
The entire REST interface is covered by JUnit tests using Spring MVC Test and Spring Security Test.

### Description:
<pre>
* 2 types of users: administrator and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu change every day (updates are made by administrators)
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
#### <pre>to check http requests, use <a href="HttpRequest">/HttpRequest</a> folder (<a href="HttpRequest/User.http">User</a>,<a href="HttpRequest/Vote.http">Vote</a>,<a href="HttpRequest/Restaurant.http">Restaurant</a>,<a href="HttpRequest/Menu.http">Menu</a>,<a href="HttpRequest/Dish.http">Dish</a>)</pre>
***
## curl samples:

### `All authorized`

### MenuRestController      `/rest/restaurants/menu/with-dishes`
- get menu by id 100008 with restaurant by id 100002 with dishes

`curl -s http://localhost:8080/rest/restaurants/100002/menu/100008/with-dishes`

### `Admin`

### AdminRestController     `/rest/admin/users`
- get all users

`curl -s http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

- get user by id 100001
 
`curl -s http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin`

- get user by email admin

`curl -s http://localhost:8080/rest/admin/users/by-email?email=admin@gmail.com --user admin@gmail.com:admin`

- create user

`curl -s -X POST "http://localhost:8080/rest/admin/users" -H "Content-Type: application/json" -d "{\"name\":\"new User\",\"email\":\"test@icloud.com\",\"password\":\"test123\"}" --user admin@gmail.com:admin`

- update user by id 100000

`curl -s -X PUT "http://localhost:8080/rest/admin/users/100000" -H "Content-Type: application/json" -d "{\"name\":\"update User\",\"email\":\"updated@icloud.com\",\"password\":\"updated\"}" --user admin@gmail.com:admin`

- delete user by id 100000

`curl -s -X DELETE "http://localhost:8080/rest/admin/users/100000" --user admin@gmail.com:admin`

### RestaurantRestController    `/rest/admin/restaurants`
- get all restaurants

`curl -s http://localhost:8080/rest/admin/restaurants --user admin@gmail.com:admin`

- get restaurant by id 100002

`curl -s http://localhost:8080/rest/admin/restaurants/100002 --user admin@gmail.com:admin`

- create restaurant

`curl -s -X POST "http://localhost:8080/rest/admin/restaurants" -H "Content-Type: application/json" -d "{\"name\":\"new restaurant\"}" --user admin@gmail.com:admin`

- update restaurant by id 100002

`curl -s -X PUT "http://localhost:8080/rest/admin/restaurants/100002" -H "Content-Type: application/json" -d "{\"name\":\"update restaurant\"}" --user admin@gmail.com:admin`

- delete restaurant by id 100002

`curl -s -X DELETE "http://localhost:8080/rest/admin/restaurants/100002" --user admin@gmail.com:admin`

- get restaurant not found

`curl -s http://localhost:8080/rest/admin/restaurants/100001 --user admin@gmail.com:admin`

### MenuRestController      `/rest/admin/restaurants/menu`
- get menu by id 100008 by restaurant id 100002

`curl -s http://localhost:8080/rest/admin/restaurants/100002/menu/100008 --user admin@gmail.com:admin`

- get menu by date 2021-03-08 by restaurant id 100002
  
`curl -s http://localhost:8080/rest/admin/restaurants/100002/menu/by-date?date=2021-03-08 --user admin@gmail.com:admin`

- create menu by restaurant id 100004

`curl -X POST --location "http://localhost:8080/rest/admin/restaurants/100004/menu" -H "Content-Type: application/json" -d "{\"dishes\": [{}], \"restaurant\": { \"id\": 100004 } }" --user admin@gmail.com:admin`

- delete menu by id 100008 by restaurant id 100002

`curl -s -X DELETE "http://localhost:8080/rest/admin/restaurants/100002/menu/100008" --user admin@gmail.com:admin`

- get menu not found

`curl -s http://localhost:8080/rest/admin/restaurants/100002/menu/100000 --user admin@gmail.com:admin`

### DishRestController      `/rest/admin/restaurants/menu/dishes`
- get dish by id 100011 by menu id 100008 by restaurant id 100002

`curl -s http://localhost:8080/rest/admin/restaurants/100002/menu/100008/dishes/100011 --user admin@gmail.com:admin`

- create dish by menu id 100008 by restaurant id 100002

`curl -s -X POST "http://localhost:8080/rest/admin/restaurants/100002/menu/100008/dishes -H "Content-Type: application/json" -d "{\"name\":\"new dish\",\"price\":1000,\"description\":\"new\"}" --user admin@gmail.com:admin`

- update dish by id 100011 by menu id 100008 by restaurant id 100002

`curl -s -X PUT "http://localhost:8080/rest/admin/restaurants/100002/menu/100008/dishes/100011" -H "Content-Type: application/json" -d "{\"name\":\"update dish\",\"price\":999,\"description\":\"update\"}" --user admin@gmail.com:admin`

- delete dish by id 100011 by menu id 100008 by restaurant id 100002

`curl -s -X DELETE "http://localhost:8080/rest/admin/restaurants/100002/menu/100008/dishes/100011" --user admin@gmail.com:admin`

- get dish not found

`curl -s http://localhost:8080/rest/admin/restaurants/100002/menu/100008/dishes/100001 --user admin@gmail.com:admin`

### `User`

### ProfileRestController       `/rest/profile`
- get user profile by id 100000

`curl -s http://localhost:8080/rest/profile --user user@yandex.ru:password`

- register user

`curl -s -X POST "http://localhost:8080/rest/profile/register" -H "Content-Type: application/json" -d "{\"name\":\"new User\",\"email\":\"new@gmail.com\",\"password\":\"new123\"}"`

- update profile

`curl -s -X PUT "http://localhost:8080/rest/profile" -H "Content-Type: application/json" -d "{\"name\":\"update User\",\"email\":\"update@gmail.com\",\"password\":\"update123\"}" --user user@yandex.ru:password`

- delete profile

`curl -s -X DELETE "http://localhost:8080/rest/profile" --user user@yandex.ru:password`

### ProfileVoteRestController       `/rest/profile/vote`
- create vote by restaurant id 100004

`curl -s -X POST "http://localhost:8080/rest/profile/vote?restId=100004" --user user@yandex.ru:password`

- get today's vote
  
`curl -s http://localhost:8080/rest/profile/vote/ --user user@yandex.ru:password`

- update vote by restaurant id 100004 by vote id 100017 if local time is before 11am

`curl -s -X PUT "http://localhost:8080/rest/profile/vote/100017?restId=100004" --user user@yandex.ru:password`

***
### Caching(EHCACHE)
App caches method from the Menu for two hours (earlier, if changes are made):
- getRestaurantWithDish(restId, id) - get menu by id with restaurant by restId with all its dishes
