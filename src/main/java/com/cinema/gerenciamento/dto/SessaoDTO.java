package com.cinema.gerenciamento.dto;


import com.cinema.gerenciamento.entities.Sessao;
import com.cinema.gerenciamento.entities.TipoAudio;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.ToString;

@NoArgsConstructor
@ToString
public class SessaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String data;
    private String hora;
    private Double valorIngresso;
    private String tipoAnimacao;
    private TipoAudio tipoAudio;
    private FilmeDTO filme;
    private SalaDTO sala;



    public SessaoDTO(Long id, String data, String hora, Double valorIngresso, String tipoAnimacao, TipoAudio tipoAudio) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.valorIngresso = valorIngresso;
        this.tipoAnimacao = tipoAnimacao;
        this.tipoAudio = tipoAudio;
    }

    public SessaoDTO(Sessao entity){
        id = entity.getId();
        data = entity.getData();
        hora = entity.getHora();
        valorIngresso = entity.getValorIngresso();
        tipoAnimacao = entity.getTipoAnimacao();
        tipoAudio = entity.getTipoAudio();
        filme = new FilmeDTO(entity.getFilme());
        sala = new SalaDTO(entity.getSala());
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
        String pattern = "yyyy-MM-dd";
        Date date = new Date(data);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        data = simpleDateFormat.format(date);
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        String pattern2 = "HH:mm:ss";
        Date hour = new Date(hora);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
        hora = simpleDateFormat2.format(hour);
        this.hora = hora;
    }


    public LocalDateTime getHorarioFinal(Double duracaoFIlme) {
        String dataCompleta = getData() +"T"+ getHora();
        int duracaoFilme = duracaoFIlme.intValue();
        LocalDateTime dataFinal = LocalDateTime.parse(dataCompleta).plusMinutes(duracaoFilme);
        return dataFinal;
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

    public FilmeDTO getFilme() {
        return filme;
    }

    public SalaDTO getSala() {
        return sala;
    }

    public void setFilme(FilmeDTO filme) {
        this.filme = filme;
    }

    public void setSala(SalaDTO sala) {
        this.sala = sala;
    }
}
