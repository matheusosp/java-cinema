package com.cinema.gerenciamento.dto;

import com.cinema.gerenciamento.entities.Filme;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FilmeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String titulo;
    private String urlImagem;
    private String descricao;
    private Double duracao;

    public FilmeDTO(Filme entity) {
        super();
        id = entity.getId();
        titulo = entity.getTitulo();
        urlImagem = entity.getUrlImagem();
        descricao = entity.getDescricao();
        duracao = entity.getDuracao();
    }
}
