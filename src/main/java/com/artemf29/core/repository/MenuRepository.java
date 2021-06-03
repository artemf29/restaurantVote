package com.artemf29.core.repository;

import com.artemf29.core.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restId")
    int delete(@Param("id") int id, @Param("restId") int restId);

    @Query("SELECT m FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restId")
    Optional<Menu> getById(@Param("id") int id, @Param("restId") int restId);

    @EntityGraph(attributePaths = {"dishes","restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.date=:date AND m.restaurant.id=:restId")
    Optional<Menu> getByDate(@Param("date") LocalDate date, @Param("restId") int restId);

    @EntityGraph(attributePaths = {"dishes","restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.date=:date AND m.id=:id AND m.restaurant.id=:restId")
    Optional<Menu> getRestaurantWithDish(@Param("id") int id, @Param("restId") int restId, @Param("date") LocalDate date);

    @EntityGraph(attributePaths = {"dishes","restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.date=:date")
    List<Menu> getAllRestaurantWithDish(@Param("date") LocalDate date);
}
