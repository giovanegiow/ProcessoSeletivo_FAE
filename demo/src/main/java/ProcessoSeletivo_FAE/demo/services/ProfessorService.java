package ProcessoSeletivo_FAE.demo.services;

import java.util.List;
import java.util.Optional;
import ProcessoSeletivo_FAE.demo.entities.Professor;
import ProcessoSeletivo_FAE.demo.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ProfessorService {
    @Autowired 
    private ProfessorRepository repository;

    public Professor create(Professor obj){
        obj.replaceAddresses(obj.getAddresses());
        return repository.save(obj);
    }

    public void delete(Long id_person){
        repository.deleteById(id_person);
    }

    public Professor selectById(Long id_person){
        Optional<Professor> obj = repository.findById(id_person);
        return obj.get();
    }

    public List<Professor> selectAll(){
        return repository.findAll();
    }

    public Professor update(Professor obj){
        Optional<Professor> newObj = repository.findById(obj.getId());
        updateProfessor(newObj, obj);
        return repository.save(newObj.get());
    }

    private void updateProfessor(Optional<Professor> newObj, Professor obj){
        newObj.get().setName(obj.getName());
        newObj.get().setPhoneNumber(obj.getPhoneNumber());
        newObj.get().setEmailAddress(obj.getEmailAddress());
        newObj.get().setStatus(obj.getStatus());
        newObj.get().replaceAddresses(obj.getAddresses());
        newObj.get().setSalary(obj.getSalary());
    }
}
