package com.artemf29.core.web;

import com.artemf29.core.model.Restaurant;
import com.artemf29.core.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.artemf29.core.util.UrlUtil.RESTAURANT_URL;
import static com.artemf29.core.util.ValidationUtil.assureIdConsistent;
import static com.artemf29.core.util.ValidationUtil.checkNew;
import static com.artemf29.core.util.ValidationUtil.checkNotFoundWithId;
import static com.artemf29.core.util.ValidationUtil.checkSingleModification;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepository restaurantRepository;

    public RestaurantRestController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping(RESTAURANT_URL + "/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    @GetMapping(RESTAURANT_URL)
    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @CacheEvict(value = "menus", allEntries = true)
    @DeleteMapping(RESTAURANT_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        checkSingleModification(restaurantRepository.delete(id), "Restaurant id=" + id + " not found");
    }

    @CacheEvict(value = "menus", allEntries = true)
    @PutMapping(value = RESTAURANT_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {}", restaurant);
        assureIdConsistent(restaurant, id);
        checkNotFoundWithId(restaurantRepository.findById(id), "Restaurant id=" + id);
        restaurantRepository.save(restaurant);
    }

    @PostMapping(value = RESTAURANT_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANT_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
