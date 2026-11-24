package ProcessoSeletivo_FAE.demo.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ProcessoSeletivo_FAE.demo.entities.Professor;
import ProcessoSeletivo_FAE.demo.services.ProfessorService;

@RestController
@RequestMapping(value = "/professor")
public class ProfessorController {
    @Autowired
    private ProfessorService service;

    @PostMapping
    public ResponseEntity<Professor> create(@RequestBody Professor obj){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(obj));
    }

    @DeleteMapping(value = "/{id_person}")
    public ResponseEntity<Void> delete(@PathVariable Long id_person){
        service.delete(id_person);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id_person}")
    public ResponseEntity<Professor> selectById(@PathVariable  Long id_person){
        return ResponseEntity.ok().body(service.selectById(id_person));
    }

    @GetMapping 
    public ResponseEntity<List<Professor>> selectAll(){
        return ResponseEntity.ok().body(service.selectAll());
    }

    @PutMapping(value = "/{id_person}")
    public ResponseEntity<Professor> update(@PathVariable Long id_person, @RequestBody Professor obj){
        obj.setId(id_person);
        return ResponseEntity.ok().body(service.update(obj));
    }
}