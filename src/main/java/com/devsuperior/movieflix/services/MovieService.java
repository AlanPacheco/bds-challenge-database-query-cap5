package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieMinDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
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
import java.util.stream.Collectors;

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
    public Page<MovieMinDTO> findPagedByGenreId(Long genreId, Pageable pageable) {

        if (genreId == 0)
            genreId = null;

        Page<Movie> moviePage = movieRepository.searchMoviesByGenre(genreId, pageable);
        Page<MovieMinDTO> movieMinDTOPage = moviePage.map(movie -> new MovieMinDTO(movie));

        return movieMinDTOPage;
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findMovieWithReviews(Long movieId) {
        try {
            List<Review> reviews = reviewRepository.searchReviewByMovieId(movieId);
            List<ReviewDTO> reviewDTOS = reviews.stream().map(review -> new ReviewDTO(review, review.getUser()))
                    .collect(Collectors.toList());
            return reviewDTOS;
        } catch (EntityNotFoundException error) {
            throw new ResourceNotFoundException("MovieId not found: " + movieId);
        }
    }
}
