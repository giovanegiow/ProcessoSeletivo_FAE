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

import br.edu.fae.processoseletivo.entity.Student;
import br.edu.fae.processoseletivo.service.StudentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student obj) {
        Student created = service.create(obj);
        return ResponseEntity.created(URI.create("/students/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Student>> selectAll() {
        return ResponseEntity.ok(service.selectAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> selectById(@PathVariable Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @Valid @RequestBody Student obj) {
        return ResponseEntity.ok(service.update(id, obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
