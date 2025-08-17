package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {

        User user = authService.authenticated();

        try {
            Review entity = new Review();
            entity.setMovie(movieRepository.getReferenceById(dto.getMovieId()));
            entity.setUser(user);
            entity.setText(dto.getText());
            entity = reviewRepository.save(entity);
            return new ReviewDTO(entity);

        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Filme não encontrado" + dto.getMovieId());
        }
    }
}
