package br.edu.fae.processoseletivo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fae.processoseletivo.entity.Professor;
import br.edu.fae.processoseletivo.service.ProfessorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Professor> create(@Valid @RequestBody Professor obj) {
        Professor created = service.create(obj);
        return ResponseEntity.created(URI.create("/professors/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Professor>> selectAll() {
        return ResponseEntity.ok(service.selectAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> selectById(@PathVariable Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> update(@PathVariable Long id, @Valid @RequestBody Professor obj) {
        return ResponseEntity.ok(service.update(id, obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
