package com.artemf29.core.testdata;

import com.artemf29.core.TestMatcher;
import com.artemf29.core.model.Dish;

import java.util.List;

import static com.artemf29.core.model.AbstractBaseEntity.START_SEQ;

public class DishTestDataUtils {
    public static final TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Dish.class, "menu");

    public static final int NOT_FOUND = 100;
    public static final int DISH_1_ID = START_SEQ + 10;
    public static final int DISH_2_ID = START_SEQ + 11;
    public static final int DISH_3_ID = START_SEQ + 12;
    public static final int DISH_4_ID = START_SEQ + 13;
    public static final int DISH_5_ID = START_SEQ + 14;
    public static final int DISH_6_ID = START_SEQ + 15;
    public static final int DISH_7_ID = START_SEQ + 16;

    public static final Dish dish1 = new Dish(DISH_1_ID, "Fresh Korea", 500, "Tomatoes, cheese, salad");
    public static final Dish dish2 = new Dish(DISH_2_ID, "Asian soup", 800, "seafood, potatoes");
    public static final Dish dish3 = new Dish(DISH_3_ID, "Bibimbap", 1000, "rice, vegetables, beef");
    public static final Dish dish4 = new Dish(DISH_4_ID, "Belgian waffles", 325, "waffles, chocolate sauce, strawberry");
    public static final Dish dish5 = new Dish(DISH_5_ID, dish4.getName(), dish4.getPrice(), dish4.getDescription());
    public static final Dish dish6 = new Dish(DISH_6_ID, "Marbled beef steak", 1254, "beef, BBQ sauce");
    public static final Dish dish7 = new Dish(DISH_7_ID, "Bavarian sausage", 999);

    public static Dish getNew() {
        return new Dish(null, "new dish", 555, "new description");
    }

    public static Dish getUpdated() {
        return new Dish(DISH_1_ID, "update dish", 111, dish1.getDescription());
    }
}
