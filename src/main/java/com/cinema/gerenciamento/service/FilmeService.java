package com.cinema.gerenciamento.service;

import com.cinema.gerenciamento.dto.FilmeDTO;
import com.cinema.gerenciamento.dto.SessaoDTO;
import com.cinema.gerenciamento.entities.Filme;
import com.cinema.gerenciamento.exception.FilmeJaRegistradoException;
import com.cinema.gerenciamento.exception.IdNaoEncontradoException;
import com.cinema.gerenciamento.exception.ImpossivelDeletarException;
import com.cinema.gerenciamento.repository.FilmeRepository;
import com.cinema.gerenciamento.repository.SessaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    @Autowired
    FilmeRepository repository;

    @Autowired
    SessaoRepository sessaoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<FilmeDTO> findAll(){
        List<Filme> list = repository.findAll();
        return list.stream().map(FilmeDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FilmeDTO> listPagination(int page, int limit){
        List<Filme> list = repository.findAll();
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
        return list.stream().limit(limit).map(FilmeDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FilmeDTO findOne(Long id) throws IdNaoEncontradoException{
        verifyIdExists(id);
        Filme filme = repository.getOne(id);
        return new FilmeDTO(filme);
    }

    @Transactional
    public FilmeDTO create(FilmeDTO dto) throws FilmeJaRegistradoException {

        if(repository.findByTitulo(dto.getTitulo()) != null){
            throw new FilmeJaRegistradoException(String.format("Filme com o titulo %s ja foi registrado.", dto.getTitulo()));
        }
        Filme filme = new Filme(null,dto.getTitulo(),dto.getUrlImagem(),dto.getDescricao(), dto.getDuracao());

        filme = repository.save(filme);
        return new FilmeDTO(filme);
    }

    @Transactional
    public FilmeDTO update(Long id, FilmeDTO filmeDTO) throws IdNaoEncontradoException{
        verifyIdExists(id);
        filmeDTO.setId(id);

        Filme updatedFilme = modelMapper.map(filmeDTO, Filme.class);
        Filme savedFilme = repository.save(updatedFilme);

        return new FilmeDTO(savedFilme);
    }

    @Transactional
    public void delete(Long id) throws ImpossivelDeletarException, IdNaoEncontradoException{
        verifyIdExists(id);
        if(sessaoRepository.findByFilmeId(id).size() > 0){
            throw new ImpossivelDeletarException("Não é possivel deletar um filme com uma sessao vinculada");
        }else {
            repository.deleteById(id);
        }
    }
    public void verifyIdExists(Long id) throws IdNaoEncontradoException{
        repository.findById(id).orElseThrow(() -> new IdNaoEncontradoException("Id não encontrado"));
    }

    public List<FilmeDTO> findByNameContaining(String query) {
        List<Filme> list= repository.findByTituloContaining(query);
        return list.stream().map(FilmeDTO::new).collect(Collectors.toList());
    }

    public FilmeDTO findByName(String query) throws IdNaoEncontradoException{
        Filme filme= repository.findByTitulo(query);
        if(filme.getId() == null){
            throw new IdNaoEncontradoException("Filme não encontrado");
        }

        return new FilmeDTO(filme);
    }

    public List<FilmeDTO> listPaginationFind(int page, int limit, String query) {
        List<Filme> list = repository.findByTituloContaining(query);
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
        return list.stream().limit(limit).map(FilmeDTO::new).collect(Collectors.toList());
    }
}
