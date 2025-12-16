package com.example.proiectul.Cinema.repository;

import com.example.proiectul.Cinema.model.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, String id);

    @Query("""
        SELECT m FROM Movie m
        WHERE (:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%')))
          AND (:minDuration IS NULL OR m.durationMinutes >= :minDuration)
          AND (:maxDuration IS NULL OR m.durationMinutes <= :maxDuration)
          AND (:releaseFrom IS NULL OR m.release_date >= :releaseFrom)
          AND (:releaseTo IS NULL OR m.release_date <= :releaseTo)
    """)
    List<Movie> filterMovies(
            @Param("title") String title,
            @Param("minDuration") Integer minDuration,
            @Param("maxDuration") Integer maxDuration,
            @Param("releaseFrom") LocalDate releaseFrom,
            @Param("releaseTo") LocalDate releaseTo,
            Sort sort
    );
}
