package com.cinema.gerenciamento.entities;

import java.io.Serializable;

import com.cinema.gerenciamento.ValidationGroups;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_filme")
@ToString
public class Filme implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = ValidationGroups.FilmeId.class)
    @EqualsAndHashCode.Include
    private Long id;

    private String titulo;
    private String urlImagem;
    private String descricao;
    private Double duracao;


}
