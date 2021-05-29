Система голосования за выбор ресторана

REST API с использованием Hibernate, Spring Data JPA, Spring Security, Spring MVC, JUnit, EHCACHE,
Apache Tomcat, Json Jackson, SLF4J без интерфейса.

Java Enterprise проект с регистрацией/авторизацией и правами доступа на основе ролей (USER, ADMIN).
Администратор может создавать/редактировать/удалять — блюда/рестораны/пользователей, а также смотреть/удалять итоги голосования.
Пользователи могут управлять своим профилем и голосовать через REST интерфейс с базовой авторизацией.
Весь REST интерфейс покрывается JUnit тестами, используя Spring MVC Test и Spring Security Test.

- 2 типа пользователей: администратор и обычные пользователи
- Администратор может ввести ресторан и его блюда дня (обычно 2-5 пунктов, просто название блюда и цена)
- Блюда меняются каждый день (обновления делают администраторы)
- Каждый ресторан предлагает новое меню каждый день
- Пользователи могут проголосовать за то, в каком ресторане они хотят посетить
- На каждого пользователя засчитывается только один голос
- Если пользователь снова проголосует в тот же день:
  *Если это произойдет до 11:00, мы предполагаем, что он передумал
  *Если это после 11:00, то уже слишком поздно, голосование не может быть изменено

***
###<center>REST requests:</center>
- **base URL: http://localhost:8080/**
- **Content-Type: application/json**

`ADMIN`

`{Authorization: Basic admin@gmail.com admin}`

AdminRestController

|description             |request|URL                                      |JSON|
|------------------------|-------|-----------------------------------------|----|
|get all users           |GET    |rest/admin/users                         |    |
|get user by id 100001   |GET    |rest/admin/users/100001                  |    |
|get user by email admin |GET    |rest/admin/users/by?email=admin@gmail.com|    |
|create user             |POST   |rest/admin/users                         |{"name": "new User","email": "test@icloud.com","password": "test123"}
|update user by id 100000|PUT    |rest/admin/users/100000                  |{"name": "update User","email": "updated@icloud.com","password": "updated"}
|delete user by id 100000|DELETE |rest/admin/users/100000                  |    |

AdminVoteRestController

|description                               |request|URL                                      |
|------------------------------------------|-------|-----------------------------------------|
|get all votes                             |GET    |rest/admin/vote/getAll                   |    
|get all votes by user id 100001           |GET    |rest/admin/vote/getAll/100001            |    
|get vote by id 100012 by user id 100001   |GET    |rest/admin/vote/100001/100012            |    
|delete vote by id 100012 by user id 100001|DELETE |rest/admin/vote/100001/100012            |

RestaurantRestController
