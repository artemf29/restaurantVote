package com.artemf29.core.testdata;

import com.artemf29.core.TestMatcher;
import com.artemf29.core.model.Menu;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.artemf29.core.model.AbstractBaseEntity.START_SEQ;
import static com.artemf29.core.testdata.DishTestDataUtils.*;
import static com.artemf29.core.testdata.RestaurantTestDataUtils.restaurant1;
import static com.artemf29.core.testdata.RestaurantTestDataUtils.restaurant2;
import static com.artemf29.core.testdata.RestaurantTestDataUtils.restaurant3;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuTestDataUtils {
    public static final TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Menu.class, "dishes", "restaurant");

    public static TestMatcher<Menu> MENU_WITH_RESTAURANT_AND_DISHES_MATCHER =
            TestMatcher.usingAssertions(Menu.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("dishes.menu").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int NOT_FOUND = 104;
    public static final int MENU_1_ID = START_SEQ + 5;
    public static final int MENU_2_ID = START_SEQ + 6;
    public static final int MENU_3_ID = START_SEQ + 7;
    public static final int MENU_4_ID = START_SEQ + 8;
    public static final int MENU_5_ID = START_SEQ + 9;

    public static final Menu menu1 = new Menu(MENU_1_ID, of(2021, Month.MARCH, 8), List.of(dish3));
    public static final Menu menu2 = new Menu(MENU_2_ID, of(2021, Month.MARCH, 8), List.of(dish4));
    public static final Menu menu3 = new Menu(MENU_3_ID, of(2021, Month.MARCH, 8), List.of(dish7));
    public static final Menu menu4 = new Menu(MENU_4_ID, now(), List.of(dish1, dish2));
    public static final Menu menu5 = new Menu(MENU_5_ID, now(), List.of(dish5, dish6));

    static {
        menu1.setRestaurant(restaurant1);
        menu2.setRestaurant(restaurant2);
        menu3.setRestaurant(restaurant3);
        menu4.setRestaurant(restaurant1);
        menu5.setRestaurant(restaurant2);
    }

    public static Menu getNew() {
        Menu menu = new Menu(null, now());
        menu.setRestaurant(restaurant3);
        menu.setDishes(List.of(DishTestDataUtils.getNew()));
        return menu;
    }

    public static Menu getUpdated() {
        Menu menu = new Menu(MENU_4_ID, menu4.getDate());
        menu.setRestaurant(menu4.getRestaurant());
        menu.setDishes(List.of(DishTestDataUtils.getUpdated()));
        return menu;
    }

}
