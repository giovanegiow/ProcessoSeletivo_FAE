package ProcessoSeletivo_FAE.demo.services;

import java.util.List;
import ProcessoSeletivo_FAE.demo.entities.Person;
import ProcessoSeletivo_FAE.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service

public class PersonService {
    @Autowired 
    private PersonRepository repository;

    public Person create(Person obj){
        obj.replaceAddresses(obj.getAddresses());
        return repository.save(obj);
    }

    public void delete(Long id_person){
        if (!repository.existsById(id_person)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada");
        }
        repository.deleteById(id_person);
    }

    public Person selectById(Long id_person){
        return repository.findById(id_person)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
    }

    public List<Person> selectByName(String name){
        return repository.findByNameContainingIgnoreCase(name);
    }

    public List<Person> selectAll(){
        return repository.findAll();
    }

    public Person update(Person obj){
        Person entity = selectById(obj.getId());
        updatePerson(entity, obj);
        return repository.save(entity);
    }

    private void updatePerson(Person entity, Person obj){
        entity.setName(obj.getName());
        entity.setPhoneNumber(obj.getPhoneNumber());
        entity.setEmailAddress(obj.getEmailAddress());
        entity.setStatus(obj.getStatus());
        entity.replaceAddresses(obj.getAddresses());
    }
}