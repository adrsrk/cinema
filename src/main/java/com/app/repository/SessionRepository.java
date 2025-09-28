package com.app.repository;

import com.app.entity.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("""
        SELECT s FROM Session s 
        WHERE (:movieId IS NULL OR s.movie.id = :movieId)
        AND (:hallId IS NULL OR s.hall.id = :hallId)
        AND (:dateStart IS NULL OR s.startTime >= :dateStart)
        AND (:dateEnd IS NULL OR s.startTime < :dateEnd)
    """)
    Page<Session> findFiltered(
            @Param("movieId") Long movieId,
            @Param("hallId") Long hallId,
            @Param("dateStart") Instant dateStart,
            @Param("dateEnd") Instant dateEnd,
            Pageable pageable);
}
