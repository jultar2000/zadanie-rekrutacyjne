package com.example.rekrutacja.domain;

import com.example.rekrutacja.domain.entity.Movie;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID>, JpaSpecificationExecutor<Movie> {

  Optional<Movie> findByTitle(String title);

  boolean existsByTitle(String title);

}