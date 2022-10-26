package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;

import java.util.ArrayList;
import java.util.List;

public class MovieReviewDTO extends MovieDTO{

    private List<ReviewDTO> reviews = new ArrayList<>();

    public MovieReviewDTO(){
    }

    public MovieReviewDTO(Movie movie){
        super(movie, movie.getGenre());
    }

    public MovieReviewDTO(Review review) {
        super(review.getMovie(), review.getMovie().getGenre());
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        reviews.add(new ReviewDTO(review, review.getUser()));
    }
}
