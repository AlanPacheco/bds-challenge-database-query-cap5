package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public Page<ReviewDTO> findAllPaged(Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAll(pageable);
        Page<ReviewDTO> reviewDTOPage = reviewPage.map(review -> new ReviewDTO(review, review.getUser()));
        return reviewDTOPage;
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO reviewDTO) {
        Movie movie = movieRepository.getOne(reviewDTO.getMovieId());
        User user = authService.authenticated();

        Review review = new Review();
        review.setMovie(movie);
        review.setUser(user);
        review.setText(reviewDTO.getText());

        review = reviewRepository.save(review);

        return new ReviewDTO(review, review.getUser());
    }
}
