package com.cinema.gerenciamento.service;

import com.cinema.gerenciamento.dto.SalaDTO;
import com.cinema.gerenciamento.dto.SessaoDTO;
import com.cinema.gerenciamento.entities.Filme;
import com.cinema.gerenciamento.entities.Sala;
import com.cinema.gerenciamento.entities.Sessao;
import com.cinema.gerenciamento.exception.*;
import com.cinema.gerenciamento.repository.FilmeRepository;
import com.cinema.gerenciamento.repository.SalaRepository;
import com.cinema.gerenciamento.repository.SessaoRepository;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessaoService {

    @Autowired
    SessaoRepository repository;

    @Autowired
    FilmeRepository filmeRepositoy;

    @Autowired
    SalaRepository salaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<SessaoDTO> findAll(){
        List<Sessao> list = repository.findAll();
        return list.stream().map(x -> new SessaoDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SessaoDTO> listPagination(int page, int limit){
        List<Sessao> list = repository.findAll();

        int paginaLimite = list.size()/limit+1;

        if(page > paginaLimite){
            return Collections.emptyList();
        }
        if(page>1) {
            for (int i = 1; i < page; i++) {
                for(int j = 0;j < limit;j++){
                    list.remove(0);
                }
            }
        }

        return list.stream().limit(limit).map(x -> new SessaoDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SessaoDTO findOne(Long id) throws IdNaoEncontradoException{
        verifyIdExists(id);
        Sessao sessao = repository.getOne(id);
        return new SessaoDTO(sessao);
    }

    @Transactional
    public SessaoDTO create(SessaoDTO dto) throws FilmeNaoExisteException,SalaOcupadaException{
        criarSala();
        Filme filme = getFilmeIfExists(dto);
        Sala sala = salaRepository.getOne(dto.getSala().getId());
        Sessao sessao = modelMapper.map(dto, Sessao.class);
        sessao.setFilme(filme);
        sessao.setSala(sala);
        String data= dto.getData() +"T"+ dto.getHora();
        LocalDateTime dataInicial = LocalDateTime.parse(data);
        List<SessaoDTO> sessoesComSalaIgual = repository.findBySalaId(sala.getId()).stream().map(x -> new SessaoDTO(x)).collect(Collectors.toList());;
        verificaSalaOcupada(dataInicial, sessoesComSalaIgual);
        sessao = repository.save(sessao);
        return new SessaoDTO(sessao);
    }


    public SalaDTO criarSala(){
        Sala sala = new Sala(1L,"Sala 1", 40);
        sala = salaRepository.save(sala);
        return new SalaDTO(sala);
    }

    @Transactional
    public SessaoDTO update(Long id, SessaoDTO sessaoDTO) throws IdNaoEncontradoException{
        verifyIdExists(id);
        sessaoDTO.setId(id);

        Sessao updatedSessao = modelMapper.map(sessaoDTO, Sessao.class);
        Sessao savedSessao = repository.save(updatedSessao);

        return new SessaoDTO(savedSessao);
    }

    @Transactional
    public void delete(Long id) throws IdNaoEncontradoException, SessionDeleteException{
        verifyIdExists(id);
        SessaoDTO sessaoDTO= findOne(id);
        String data = sessaoDTO.getData() +"T"+ sessaoDTO.getHora();
        LocalDateTime dataFinal = LocalDateTime.parse(data);
        if(LocalDateTime.now().isBefore(dataFinal.minusDays(10))){
            repository.deleteById(id);
        }else{
            throw new SessionDeleteException("Uma sessão só pode ser removida se faltar 10 dias ou mais para que ela ocorra.");
        }
    }

    public void verificaSalaOcupada(LocalDateTime testDate, List<SessaoDTO> sessao) throws SalaOcupadaException{
        for (SessaoDTO dto:sessao) {
            String date = dto.getData() +"T"+ dto.getHora();
            LocalDateTime dataItem = LocalDateTime.parse(date);
            LocalDateTime horaFinalItem = dto.getHorarioFinal(dto.getFilme().getDuracao());
            boolean verifica = verificaAsDatas(testDate, dataItem, horaFinalItem);
            if(verifica==true){
                throw new SalaOcupadaException("Esta sala esta ocupada nesta data");
            }
        }
    }
    public boolean verificaAsDatas(LocalDateTime testDate, LocalDateTime startDate, LocalDateTime endDate){
        return !(testDate.isBefore(startDate) || testDate.isAfter(endDate));
    }
    public void verifyIdExists(Long id) throws IdNaoEncontradoException {
        repository.findById(id).orElseThrow(() -> new IdNaoEncontradoException("Id não encontrado"));
    }

    private Filme getFilmeIfExists(SessaoDTO dto) throws FilmeNaoExisteException {
        Filme filme = filmeRepositoy.getOne(dto.getFilme().getId());
        filmeRepositoy.findById(filme.getId()).orElseThrow(() -> new FilmeNaoExisteException("O Filme informado não foi encontrado"));
        return filme;
    }
}
