package com.artemf29.core.web.menu;

import com.artemf29.core.model.Dish;
import com.artemf29.core.model.Menu;
import com.artemf29.core.repository.MenuRepository;
import com.artemf29.core.testdata.DishTestDataUtils;
import com.artemf29.core.util.json.JsonUtil;
import com.artemf29.core.web.AbstractControllerTest;
import com.artemf29.core.web.ExceptionInfoHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.artemf29.core.TestUtil.readFromJson;
import static com.artemf29.core.TestUtil.userHttpBasic;
import static com.artemf29.core.testdata.DishTestDataUtils.DISH_1_ID;
import static com.artemf29.core.testdata.DishTestDataUtils.dish1;
import static com.artemf29.core.testdata.DishTestDataUtils.dish2;
import static com.artemf29.core.testdata.MenuTestDataUtils.*;
import static com.artemf29.core.testdata.RestaurantTestDataUtils.RESTAURANT_1_ID;
import static com.artemf29.core.testdata.RestaurantTestDataUtils.RESTAURANT_2_ID;
import static com.artemf29.core.testdata.RestaurantTestDataUtils.RESTAURANT_3_ID;
import static com.artemf29.core.testdata.RestaurantTestDataUtils.restaurant1;
import static com.artemf29.core.testdata.UserTestDataUtils.admin;
import static com.artemf29.core.testdata.UserTestDataUtils.user;
import static com.artemf29.core.util.UrlUtil.MENU_URL;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MENU_URL + '/';

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_5_ID, RESTAURANT_2_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu5));
    }

    @Test
    void getWithDish() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_5_ID + "/with-dish", RESTAURANT_2_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_WITH_RESTAURANT_AND_DISHES_MATCHER.contentJson(menu5));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU_5_ID, RESTAURANT_2_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND, RESTAURANT_2_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MENU_5_ID, RESTAURANT_2_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        MENU_MATCHER.assertMatch(menuRepository.findAll(), List.of(menu1, menu2, menu3, menu4));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND, RESTAURANT_2_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Menu updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MENU_4_ID, RESTAURANT_1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(menuRepository.getByDate(LocalDate.now(), RESTAURANT_1_ID).get(), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Menu newMenu = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_3_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isCreated());

        Menu created = readFromJson(action, Menu.class);
        int newId = created.id();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuRepository.getByDate(LocalDate.now(), RESTAURANT_3_ID).get(), newMenu);
    }

    @Test
    void getAllRestaurantWithDish() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/restaurants/menu/with-dishes")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menuRepository.getAllRestaurantWithDish(LocalDate.now())));
    }

    @Test
    void createInvalid() throws Exception {
        Menu invalid = new Menu(null, null);
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        Menu invalid = new Menu(MENU_2_ID, null);
        perform(MockMvcRequestBuilders.put(REST_URL + MENU_2_ID, RESTAURANT_2_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Menu invalid = new Menu(null,LocalDate.now(),List.of(dish1));
        invalid.setRestaurant(restaurant1);
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(ExceptionInfoHandler.EXCEPTION_DUPLICATE_MENU)));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Menu invalid = new Menu(menu5.getId(), menu5.getDate(),menu5.getDishes());
        invalid.setRestaurant(restaurant1);
        perform(MockMvcRequestBuilders.put(REST_URL + MENU_5_ID, RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(ExceptionInfoHandler.EXCEPTION_DUPLICATE_MENU)));
    }
}