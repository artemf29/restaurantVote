package com.artemf29.core.web.dish;

import com.artemf29.core.model.Dish;
import com.artemf29.core.repository.DishRepository;
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

import static com.artemf29.core.TestUtil.readFromJson;
import static com.artemf29.core.TestUtil.userHttpBasic;

import static com.artemf29.core.testdata.DishTestDataUtils.*;
import static com.artemf29.core.testdata.RestaurantTestDataUtils.RESTAURANT_1_ID;
import static com.artemf29.core.testdata.UserTestDataUtils.admin;
import static com.artemf29.core.util.UrlUtil.DISH_URL;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class DishRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = DISH_URL + '/';

    @Autowired
    private DishRepository dishRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_1_ID, RESTAURANT_1_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH_1_ID, RESTAURANT_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND, RESTAURANT_1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL, RESTAURANT_1_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dishKorean));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH_1_ID, RESTAURANT_1_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.get(DISH_1_ID, RESTAURANT_1_ID).isPresent());
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND, RESTAURANT_1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_1_ID, RESTAURANT_1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(dishRepository.get(DISH_1_ID, RESTAURANT_1_ID).get(), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish))
                .with(userHttpBasic(admin)))
                .andExpect(status().isCreated());

        Dish created = readFromJson(action, Dish.class);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.get(newId, RESTAURANT_1_ID).get(), newDish);
    }

    @Test
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, -10);
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(DISH_1_ID, null, -10, "I");
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_1_ID, RESTAURANT_1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Dish invalid = new Dish(null, dish2.getName(), 100);
        perform(MockMvcRequestBuilders.post(REST_URL, RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(ExceptionInfoHandler.EXCEPTION_DUPLICATE_DISH_NAME)));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Dish invalid = new Dish(DISH_1_ID, dish2.getName(), 100);
        perform(MockMvcRequestBuilders.put(REST_URL + DISH_1_ID, RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(ExceptionInfoHandler.EXCEPTION_DUPLICATE_DISH_NAME)));
    }
}