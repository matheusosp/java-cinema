package com.cinema.gerenciamento.dto;


import com.cinema.gerenciamento.entities.Sala;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SalaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;
    private int qtdAssentos;
    private boolean status;

    public SalaDTO(Sala entity) {
        super();
        id = entity.getId();
        nome = entity.getNome();
        qtdAssentos = entity.getQtdAssentos();
    }
}
