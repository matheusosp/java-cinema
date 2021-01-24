package com.cinema.gerenciamento.repository;

import com.cinema.gerenciamento.entities.Filme;
import com.cinema.gerenciamento.entities.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilmeRepository extends JpaRepository<Filme,Long> {

    Filme findByTitulo(String titulo);
    List<Filme> findByTituloContaining(String titulo);

}
