import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/address")
public class AddressController {
    @Autowired
    private AddressService service;

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody Address obj){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(obj));
    }

    @DeleteMapping(value = "/{id_person}")
    public ResponseEntity<Void> delete(@PathVariable Long id_person){
        service.delete(id_person);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id_person}")
    public ResponseEntity<Address> selectById(@PathVariable  Long id_person){
        return ResponseEntity.ok().body(service.selectById(id_person));
    }

    @PutMapping(value = "/{id_person}")
    public ResponseEntity<Address> update(@PathVariable Long id_person, @RequestBody Address obj){
        obt.setId(id_person);
        return ResponseEntity.ok().body(service.update(obj));
    }
}
