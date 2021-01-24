package com.cinema.gerenciamento.controller;

import com.cinema.gerenciamento.dto.FilmeDTO;
import com.cinema.gerenciamento.exception.FilmeJaRegistradoException;
import com.cinema.gerenciamento.exception.IdNaoEncontradoException;
import com.cinema.gerenciamento.exception.ImpossivelDeletarException;
import com.cinema.gerenciamento.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://127.0.0.1:4200")
@RequestMapping(value = "/filmes")
public class FilmeController {

    @Autowired
    FilmeService service;

    @GetMapping("")
    public ResponseEntity<List<FilmeDTO>> listAll(){
        List<FilmeDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeDTO> listOne(@PathVariable Long id) throws IdNaoEncontradoException{
        FilmeDTO filmeDTO = service.findOne(id);
        return ResponseEntity.ok().body(filmeDTO);
    }

    @GetMapping("/page={page}/limit={limit}")
    public ResponseEntity<List<FilmeDTO>> listPagination(@PathVariable int page,@PathVariable int limit){
        List<FilmeDTO> list = service.listPagination(page,limit);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/page={page}/limit={limit}/q={query}")
    public ResponseEntity<List<FilmeDTO>> listPaginationName(@PathVariable int page,@PathVariable int limit,@PathVariable String query){
        List<FilmeDTO> list = service.listPaginationFind(page,limit, query);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/q={query}")
    public ResponseEntity<List<FilmeDTO>> findByNameContaining(@PathVariable String query){
        List<FilmeDTO> list = service.findByNameContaining(query);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/search={query}")
    public ResponseEntity<FilmeDTO> findByName(@PathVariable String query) throws IdNaoEncontradoException{
        FilmeDTO filme = service.findByName(query);
        return ResponseEntity.ok().body(filme);
    }

    @PostMapping
    public ResponseEntity<FilmeDTO> insert(@RequestBody FilmeDTO dto) throws FilmeJaRegistradoException {
        dto = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmeDTO> update(@PathVariable Long id, @RequestBody FilmeDTO filmeDTO) throws IdNaoEncontradoException{
        FilmeDTO dto = service.update(id, filmeDTO);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws ImpossivelDeletarException, IdNaoEncontradoException {
        service.delete(id);
    }
}
