package com.artemf29.core.testdata;

import com.artemf29.core.TestMatcher;
import com.artemf29.core.model.Dish;

import java.util.List;

import static com.artemf29.core.model.AbstractBaseEntity.START_SEQ;

public class DishTestDataUtils {
    public static final TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int NOT_FOUND = 100;
    public static final int DISH_1_ID = START_SEQ + 5;
    public static final int DISH_2_ID = START_SEQ + 6;
    public static final int DISH_3_ID = START_SEQ + 7;
    public static final int DISH_4_ID = START_SEQ + 8;
    public static final int DISH_5_ID = START_SEQ + 9;
    public static final int DISH_6_ID = START_SEQ + 10;

    public static final Dish dish1 = new Dish(DISH_1_ID, "Fresh Korea", 500, "Tomatoes, cheese, salad");
    public static final Dish dish2 = new Dish(DISH_2_ID, "Asian soup", 800, "seafood, potatoes");
    public static final Dish dish3 = new Dish(DISH_3_ID, "Bibimbap", 1000, "rice, vegetables, beef");
    public static final Dish dish4 = new Dish(DISH_4_ID, "Belgian waffles", 325, "waffles, chocolate sauce, strawberry");
    public static final Dish dish5 = new Dish(DISH_5_ID, "Marbled beef steak", 1254, "beef, BBQ sauce");
    public static final Dish dish6 = new Dish(DISH_6_ID, "Bavarian sausage", 999);

    public static final List<Dish> dishKorean = List.of(dish2, dish3, dish1);
    public static final List<Dish> dishBelgian = List.of(dish5, dish4);

    public static Dish getNew() {
        return new Dish(null, "new dish", 555, "new description");
    }

    public static Dish getUpdated() {
        return new Dish(DISH_1_ID, "update dish", 111, dish1.getDescription());
    }
}
