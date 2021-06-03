DELETE
FROM votes;
DELETE
FROM dishes;
DELETE
FROM menus;
DELETE
FROM restaurants;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001);

INSERT INTO restaurants (name)
VALUES ('Koreana'),
       ('Kriek'),
       ('Jager');

INSERT INTO menus (date, rest_id)
VALUES ('2021-03-08', 100002),
       ('2021-03-08', 100003),
       ('2021-03-08', 100004),
       (now, 100002),
       (now, 100003);

INSERT INTO dishes (name, price, description, menu_id)
VALUES ('Fresh Korea', 500, 'Tomatoes, cheese, salad', 100008),
       ('Asian soup', 800, 'seafood, potatoes', 100008),
       ('Bibimbap', 1000, 'rice, vegetables, beef', 100005),
       ('Belgian waffles', 325, 'waffles, chocolate sauce, strawberry', 100006),
       ('Belgian waffles', 325, 'waffles, chocolate sauce, strawberry', 100009),
       ('Marbled beef steak', 1254, 'beef, BBQ sauce', 100009);

INSERT INTO dishes (name, price, menu_id)
VALUES ('Bavarian sausage', 999, 100007);

INSERT INTO votes(vote_date, user_id, rest_id)
VALUES (now, 100001, 100003),
       ('2021-03-08', 100000, 100002),
       ('2021-03-08', 100001, 100004);