package com.sundayCinema.sundayCinema.movie.repository.movieInfoRepo;

import com.sundayCinema.sundayCinema.movie.entity.movieInfo.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    @Query("SELECT MAX(e.actorId) FROM Actor e")
    Long findMaxActorId();
}
