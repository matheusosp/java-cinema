package com.cinema.gerenciamento.controller;

import com.cinema.gerenciamento.dto.SessaoDTO;
import com.cinema.gerenciamento.exception.FilmeNaoExisteException;
import com.cinema.gerenciamento.exception.IdNaoEncontradoException;
import com.cinema.gerenciamento.exception.SalaOcupadaException;
import com.cinema.gerenciamento.exception.SessionDeleteException;
import com.cinema.gerenciamento.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/sessoes")
public class SessaoController {

    @Autowired
    SessaoService service;

    @GetMapping()
    public ResponseEntity<List<SessaoDTO>> listAll(){
        List<SessaoDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoDTO> listOne(@PathVariable Long id)  throws IdNaoEncontradoException {
        SessaoDTO sessaoDTO = service.findOne(id);
        return ResponseEntity.ok().body(sessaoDTO);
    }

    @GetMapping("/page={page}/limit={limit}")
    public ResponseEntity<List<SessaoDTO>> listPagination(@PathVariable int page,@PathVariable int limit){
        List<SessaoDTO> list = service.listPagination(page,limit);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<SessaoDTO> insert(@RequestBody SessaoDTO dto) throws FilmeNaoExisteException, SalaOcupadaException {
        dto = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessaoDTO> update(@PathVariable Long id, @RequestBody SessaoDTO sessaoDTO)  throws IdNaoEncontradoException{
        SessaoDTO dto = service.update(id, sessaoDTO);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id)  throws IdNaoEncontradoException, SessionDeleteException {
        service.delete(id);
    }
}
