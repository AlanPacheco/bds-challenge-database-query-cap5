package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT obj " +
            "FROM Movie obj " +
            "WHERE (:genreId = NULL OR obj.genre.id = :genreId)")
    Page<Movie> searchMoviesByGenre(Long genreId, Pageable pageable);


}
