package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public List<GenreDTO> findAll() {
        List<Genre> genreList = genreRepository.findAll();
        List<GenreDTO> dtoList = genreList.stream().map(genre -> new GenreDTO(genre))
                .collect(Collectors.toList());
        return dtoList;
    }

}
