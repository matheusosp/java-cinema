package com.cinema.gerenciamento.repository;

import com.cinema.gerenciamento.entities.Filme;
import com.cinema.gerenciamento.entities.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao,Long> {

    Optional<List<Sessao>> findByFilmeId(Long id);
    List<Sessao> findBySalaId(Long id);

}
