package com.artemf29.core.web.dish;

import com.artemf29.core.model.Dish;
import com.artemf29.core.repository.DishRepository;
import com.artemf29.core.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.artemf29.core.util.ValidationUtil.*;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/admin/restaurants/{restId}/dishes";

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishRestController(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id, @PathVariable int restId) {
        log.info("get dish {} for restaurant {}", id, restId);
        return ResponseEntity.of(dishRepository.get(id, restId));
    }

    @GetMapping
    public List<Dish> getAll(@PathVariable int restId) {
        log.info("getAll for restaurant {}", restId);
        return dishRepository.getAll(restId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restId) {
        log.info("delete {} for restaurant {}", id, restId);
        checkSingleModification(dishRepository.delete(id, restId), "Dish id=" + id + ", Restaurant id=" + restId + " missed");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @PathVariable int restId) {
        log.info("update {} for restaurant {}", dish, restId);
        assureIdConsistent(dish, id);
        checkNotFoundWithId(dishRepository.get(id, restId), "Dish id=" + id + " doesn't belong to restaurant id=" + restId);
        dish.setRestaurant(restaurantRepository.getOne(restId));
        dishRepository.save(dish);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restId) {
        log.info("create {} for restaurant {}", dish, restId);
        checkNew(dish);
        checkNotFoundWithId(restaurantRepository.findById(restId), "Restaurant id=" + restId);
        dish.setRestaurant(restaurantRepository.getOne(restId));
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
