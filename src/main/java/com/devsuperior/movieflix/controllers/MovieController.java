package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieMinDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<Page<MovieMinDTO>> findPagedByGenreId(
            @RequestParam(name = "genreId", defaultValue = "0") Long genreId,
            @PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<MovieMinDTO> movieMinDTOPage = movieService.findPagedByGenreId(genreId, pageable);

        return ResponseEntity.ok().body(movieMinDTOPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
        MovieDTO movieDTO = movieService.findById(id);
        return ResponseEntity.ok().body(movieDTO);
    }

    @GetMapping(value = "/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> findMovieWithReviews(@PathVariable Long id) {
        List<ReviewDTO> movieReviews = movieService.findMovieWithReviews(id);
        return ResponseEntity.ok(movieReviews);
    }
}
