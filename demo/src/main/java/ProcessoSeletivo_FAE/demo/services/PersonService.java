package ProcessoSeletivo_FAE.demo.services;

import java.util.List;
import java.util.Optional;
import ProcessoSeletivo_FAE.demo.entities.Person;
import ProcessoSeletivo_FAE.demo.entities.Student;
import ProcessoSeletivo_FAE.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class PersonService {
    @Autowired 
    private PersonRepository repository;

    public Student create(Student obj){
        obj.replaceAddresses(obj.getAddresses());
        return repository.save(obj);
    }

    public void delete(Long id_person){
        repository.deleteById(id_person);
    }

    public Person selectById(Long id_person){
        Optional<Person> obj = repository.findById(id_person);
        return obj.get();
    }

    public List<Person> selectAll(){
        return repository.findAll();
    }

    public Person update(Person obj){
        Optional<Person> newObj = repository.findById(obj.getId());
        updatePerson(newObj, obj);
        return repository.save(newObj.get());
    }

    private void updatePerson(Optional<Person> newObj, Person obj){
        newObj.get().setName(obj.getName());
        newObj.get().setPhoneNumber(obj.getPhoneNumber());
        newObj.get().setEmailAddress(obj.getEmailAddress());
        newObj.get().setStatus(obj.getStatus());

        newObj.get().setStatus(obj.getStatus());
        newObj.get().replaceAddresses(obj.getAddresses());
    }
}