package com.tarteka.movies.controllers;

import com.tarteka.movies.entity.MovieEntity;
import com.tarteka.movies.repositories.MovieRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @CrossOrigin
    @GetMapping
    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<MovieEntity> getMovieById(@PathVariable Long id) {
        Optional<MovieEntity> movie = movieRepository.findById(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<MovieEntity> createMovie(@RequestBody MovieEntity movie) {
        MovieEntity savedMovie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<MovieEntity> updateMovie(@PathVariable Long id, @RequestBody MovieEntity updatedMovie) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        updatedMovie.setId(id);
        MovieEntity savedMovie = movieRepository.save(updatedMovie);
        return ResponseEntity.ok(savedMovie);
    }

    @CrossOrigin
    @GetMapping("vote/{id}/{rating}")
    public ResponseEntity<MovieEntity> voteMovie(@PathVariable Long id, @PathVariable double rating) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        rating = Math.max(0.0, Math.min(rating, 10.0));

        Optional<MovieEntity> optionalMovie = movieRepository.findById(id);
        MovieEntity movie = optionalMovie.get();

        double newRating = ((movie.getVotes() * movie.getRating()) + rating) / (movie.getVotes() + 1);

        movie.setRating(newRating);
        movie.setVotes(movie.getVotes() + 1);

        MovieEntity savedMovie = movieRepository.save(movie);

        return ResponseEntity.ok(savedMovie);
    }
}
