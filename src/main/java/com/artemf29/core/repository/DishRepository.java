package com.artemf29.core.repository;

import com.artemf29.core.model.Dish;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restId")
    int delete(@Param("id") int id, @Param("restId") int restId);

    @Query("SELECT d FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restId")
    Optional<Dish> get(@Param("id") int id, @Param("restId") int restId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restId")
    List<Dish> getAll(@Param("restId") int restId);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.id=:id AND d.restaurant.id=:restId")
    Optional<Dish> getWithRestaurant(@Param("id") int id, @Param("restId") int restId);
}
