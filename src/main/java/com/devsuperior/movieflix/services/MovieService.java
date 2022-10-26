package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public MovieDTO findById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        Movie movie = movieOptional.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        return new MovieDTO(movie, movie.getGenre());
    }

    @Transactional(readOnly = true)
    public Page<MovieDTO> findPagedByGenreId(Long genreId, Pageable pageable) {
        if (genreId == 0)
            genreId = null;

        Page<Movie> moviePage = movieRepository.searchMoviesByGenre(genreId, pageable);
        Page<MovieDTO> movieDTOPage = moviePage.map(movie -> new MovieDTO(movie, movie.getGenre()));

        return movieDTOPage;
    }

    @Transactional(readOnly = true)
    public MovieReviewDTO findMovieWithReviews(Long movieId) {
        try {
            MovieReviewDTO movieReviewDTO = new MovieReviewDTO(movieRepository.getOne(movieId));
            List<Review> reviews = reviewRepository.searchReviewByMovieId(movieId);
            if (!reviews.isEmpty()) {
                reviews.forEach(review -> movieReviewDTO.addReview(review));
            }
            return movieReviewDTO;
        } catch (EntityNotFoundException error) {
            throw new ResourceNotFoundException("MovieId not found: " + movieId);
        }
    }
}
