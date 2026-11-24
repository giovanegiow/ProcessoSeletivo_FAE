package ProcessoSeletivo_FAE.demo.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import ProcessoSeletivo_FAE.demo.entities.Person;
import ProcessoSeletivo_FAE.demo.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/person")
public class PersonController {
    @Autowired
    private PersonService service;

    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody Person obj){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(obj));
    }

    @DeleteMapping(value = "/{id_person}")
    public ResponseEntity<Void> delete(@PathVariable Long id_person){
        service.delete(id_person);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<Person>> selectByName(@RequestParam String name){
        return ResponseEntity.ok().body(service.selectByName(name));
    }

    @GetMapping 
    public ResponseEntity<List<Person>> selectAll(){
        return ResponseEntity.ok().body(service.selectAll());
    }

    @PutMapping(value = "/{id_person}")
    public ResponseEntity<Person> update(@PathVariable Long id_person, @Valid @RequestBody Person obj){
        obj.setId(id_person);
        return ResponseEntity.ok().body(service.update(obj));
    }
}
