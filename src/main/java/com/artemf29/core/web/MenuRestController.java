package com.artemf29.core.web;

import com.artemf29.core.model.Menu;
import com.artemf29.core.repository.MenuRepository;
import com.artemf29.core.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.artemf29.core.util.UrlUtil.MENU_URL;
import static com.artemf29.core.util.ValidationUtil.assureIdConsistent;
import static com.artemf29.core.util.ValidationUtil.checkNew;
import static com.artemf29.core.util.ValidationUtil.checkNotFoundWithId;
import static com.artemf29.core.util.ValidationUtil.checkSingleModification;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuRestController(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping(MENU_URL + "/{id}")
    public ResponseEntity<Menu> get(@PathVariable int restId, @PathVariable int id) {
        log.info("get menu {} for restaurant {}", id, restId);
        return ResponseEntity.of(menuRepository.getById(id, restId));
    }

    @GetMapping(MENU_URL + "/by-date")
    public ResponseEntity<Menu> getByDate(@PathVariable int restId, @RequestParam LocalDate date) {
        log.info("get menu by date {} for restaurant {}", date, restId);
        return ResponseEntity.of(menuRepository.getRestaurantWithDish(restId, date));
    }

    @CacheEvict(value = "menus", allEntries = true)
    @DeleteMapping(MENU_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restId, @PathVariable int id) {
        log.info("delete menu {} for restaurants {}", id, restId);
        checkSingleModification(menuRepository.delete(id, restId), "Menu id=" + id + ", Restaurant id=" + restId + " not found");
    }

    @CacheEvict(value = "menus", allEntries = true)
    @PutMapping(value = MENU_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Menu menu, @PathVariable int restId, @PathVariable int id) {
        log.info("update menu {} by id {} for restaurant {}", menu, id, restId);
        assureIdConsistent(menu, id);
        checkNotFoundWithId(menuRepository.getByDate(id, LocalDate.now(), restId), "Menu id=" + id + ", Restaurant id=" + restId + " not found");
        menu.setRestaurant(restaurantRepository.getOne(restId));
        menuRepository.save(menu);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @PostMapping(value = MENU_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu, @PathVariable int restId) {
        log.info("create menu {} for restaurant {}", menu, restId);
        checkNew(menu);
        checkNotFoundWithId(restaurantRepository.findById(restId), "Restaurant id=" + restId);
        menu.setRestaurant(restaurantRepository.getOne(restId));
        Menu created = menuRepository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MENU_URL + "/{id}")
                .buildAndExpand(restId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Cacheable("menus")
    @GetMapping("/rest/restaurants/{restId}/menu/{id}/with-dishes")
    public ResponseEntity<Menu> getRestaurantWithDish(@PathVariable int restId, @PathVariable int id) {
        log.info("getWithDish {} for restaurants {}", id, restId);
        return ResponseEntity.of(menuRepository.getRestaurantWithDish(restId, LocalDate.now()));
    }

    @Cacheable("menus")
    @GetMapping("/rest/restaurants/menu/with-dishes")
    public List<Menu> getAllRestaurantWithDish() {
        log.info("getAll menus with restaurants with dishes");
        return menuRepository.getAllRestaurantWithDish(LocalDate.now());
    }
}
