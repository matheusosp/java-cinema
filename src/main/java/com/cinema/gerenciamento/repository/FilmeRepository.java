package com.cinema.gerenciamento.repository;

import com.cinema.gerenciamento.entities.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmeRepository extends JpaRepository<Filme,Long> {

    Optional<Filme> findByTitulo(String titulo);

}
