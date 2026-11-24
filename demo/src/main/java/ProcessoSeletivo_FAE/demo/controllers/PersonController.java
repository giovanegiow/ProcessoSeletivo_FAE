package ProcessoSeletivo_FAE.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/person")
public class PersonController {
    @Autowired
    private PersonService service;

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person obj){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(obj));
    }

    @DeleteMapping(value = "/{id_person}")
    public ResponseEntity<Void> delete(@PathVariable Long id_person){
        service.delete(id_person);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id_person}")
    public ResponseEntity<Person> selectById(@PathVariable  Long id_person){
        return ResponseEntity.ok().body(service.selectById(id_person));
    }

    @GetMapping 
    public ResponseEntity<List<Person>> selectAll(){
        return ResponseEntity.ok().body(service.selectAll());
    }

    @PutMapping(value = "/{id_person}")
    public ResponseEntity<Person> update(@PathVariable Long id_person, @RequestBody Person obj){
        obt.setId(id_person);
        return ResponseEntity.ok().body(service.update(obj));
    }
}
