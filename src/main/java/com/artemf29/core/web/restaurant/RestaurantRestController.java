package com.artemf29.core.web.restaurant;

import com.artemf29.core.model.Restaurant;
import com.artemf29.core.repository.RestaurantRepository;
import com.artemf29.core.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
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
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/admin/restaurants";

    private final RestaurantRepository restaurantRepository;

    public RestaurantRestController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping(REST_URL + "/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    @GetMapping(REST_URL + "/{id}/with-dish")
    public ResponseEntity<Restaurant> getWithDish(@PathVariable int id) {
        log.info("getWithDish {}", id);
        return ResponseEntity.of(restaurantRepository.getWithDish(id));
    }

    @Cacheable("restaurants")
    @GetMapping(REST_URL)
    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @DeleteMapping(REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        checkSingleModification(restaurantRepository.delete(id), "Restaurant id=" + id + " not found");
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @PutMapping(value = REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {}", restaurant);
        assureIdConsistent(restaurant, id);
        ValidationUtil.checkNotFoundWithId(restaurantRepository.findById(id), "Restaurant id=" + id);
        restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @PostMapping(value = REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Cacheable("restaurants")
    @GetMapping("/rest/choice")
    public List<Restaurant> getChoice() {
        log.info("getAll restaurants");
        return restaurantRepository.getChoice();
    }
}
