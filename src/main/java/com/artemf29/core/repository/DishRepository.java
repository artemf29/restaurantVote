package com.artemf29.core.repository;

import com.artemf29.core.model.Dish;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.menu.id=:menuId")
    int delete(@Param("id") int id, @Param("menuId") int menuId);

    @Query("SELECT d FROM Dish d WHERE d.id=:id AND d.menu.id=:menuId")
    Optional<Dish> get(@Param("id") int id, @Param("menuId") int menuId);
}
