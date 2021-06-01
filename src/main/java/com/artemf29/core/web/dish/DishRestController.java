package com.artemf29.core.web.dish;

import com.artemf29.core.model.Dish;
import com.artemf29.core.repository.DishRepository;
import com.artemf29.core.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

import static com.artemf29.core.util.UrlUtil.DISH_URL;
import static com.artemf29.core.util.ValidationUtil.assureIdConsistent;
import static com.artemf29.core.util.ValidationUtil.checkNew;
import static com.artemf29.core.util.ValidationUtil.checkNotFoundWithId;
import static com.artemf29.core.util.ValidationUtil.checkSingleModification;

@RestController
@RequestMapping(value = DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;

    public DishRestController(DishRepository dishRepository, MenuRepository menuRepository) {
        this.dishRepository = dishRepository;
        this.menuRepository = menuRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int restId, @PathVariable int menuId, @PathVariable int id) {
        log.info("get dish {} for menu {} for restaurant {}", id, menuId, restId);
        return ResponseEntity.of(dishRepository.get(id, menuId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restId, @PathVariable int menuId, @PathVariable int id) {
        log.info("delete {} for menu {} for restaurant {}", id, menuId, restId);
        checkSingleModification(dishRepository.delete(id, menuId), "Dish id=" + id + ", Menu id=" + menuId + " missed");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restId, @PathVariable int menuId, @PathVariable int id) {
        log.info("update {} by id {} for restaurant {}", dish, id, restId);
        assureIdConsistent(dish, id);
        checkNotFoundWithId(menuRepository.getById(menuId, restId), "Menu id = " + menuId + "for Restaurant id=" + restId);
        dish.setMenu(menuRepository.getByDate(LocalDate.now(), restId).get());
        checkNotFoundWithId(dishRepository.get(id, menuId), "Dish id=" + id + " doesn't belong to restaurant id=" + restId);
        dishRepository.save(dish);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restId, @PathVariable int menuId) {
        log.info("create {} for restaurant {}", dish, restId);
        checkNew(dish);
        checkNotFoundWithId(menuRepository.getById(menuId, restId), "Menu id = " + menuId + "for Restaurant id=" + restId);
        dish.setMenu(menuRepository.getByDate(LocalDate.now(), restId).get());
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DISH_URL + "/{id}")
                .buildAndExpand(restId, menuId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
