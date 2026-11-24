package br.edu.fae.processoseletivo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fae.processoseletivo.entity.Person;
import br.edu.fae.processoseletivo.service.PersonService;

/**
 * Listagem geral (alunos + professores). Cada item traz "type": "STUDENT" | "PROFESSOR".
 * Para criar/editar use /students ou /professors.
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Person>> selectAll() {
        return ResponseEntity.ok(service.selectAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> selectById(@PathVariable Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
