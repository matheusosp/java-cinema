package com.cinema.gerenciamento.repository;

import com.cinema.gerenciamento.entities.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala,Long> {
}
