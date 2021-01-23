package com.cinema.gerenciamento.entities;

import com.cinema.gerenciamento.ValidationGroups;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name = "tb_sessao")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Sessao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String data;
    private String hora;
    private Double valorIngresso;
    private String tipoAnimacao;

    @Enumerated(EnumType.STRING)
    private TipoAudio tipoAudio;

    @ManyToOne
    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.FilmeId.class)
    private Filme filme;

    @ManyToOne
    private Sala sala;

    public Sessao(Long id, String data, String hora, Double valorIngresso, String tipoAnimacao, TipoAudio tipoAudio) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.valorIngresso = valorIngresso;
        this.tipoAnimacao = tipoAnimacao;
        this.tipoAudio = tipoAudio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Double getValorIngresso() {
        return valorIngresso;
    }

    public void setValorIngresso(Double valorIngresso) {
        this.valorIngresso = valorIngresso;
    }

    public String getTipoAnimacao() {
        return tipoAnimacao;
    }

    public void setTipoAnimacao(String tipoAnimacao) {
        this.tipoAnimacao = tipoAnimacao;
    }

    public TipoAudio getTipoAudio() {
        return tipoAudio;
    }

    public void setTipoAudio(TipoAudio tipoAudio) {
        this.tipoAudio = tipoAudio;
    }

    public Filme getFilme() {
        return filme;
    }

    public Sala getSala() {
        return sala;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
