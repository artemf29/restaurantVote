package com.artemf29.core.repository;

import com.artemf29.core.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;


@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    Optional<Vote> getById(@Param("id") int id, @Param("userId") int userId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.voteDate=:date AND v.user.id=:userId")
    Optional<Vote> getByDate(@Param("date") LocalDate date, @Param("userId") int userId);
}
