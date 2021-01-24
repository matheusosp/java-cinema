package com.cinema.gerenciamento.repository;

import com.cinema.gerenciamento.entities.Filme;
import com.cinema.gerenciamento.entities.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaRepository extends JpaRepository<Sala,Long> {

    Sala findByNome(String nome);

}
