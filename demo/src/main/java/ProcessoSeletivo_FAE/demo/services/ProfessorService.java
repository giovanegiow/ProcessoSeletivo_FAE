package ProcessoSeletivo_FAE.demo.services;

import java.util.List;
import ProcessoSeletivo_FAE.demo.entities.Professor;
import ProcessoSeletivo_FAE.demo.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service

public class ProfessorService {
    @Autowired 
    private ProfessorRepository repository;

    public Professor create(Professor obj){
        obj.replaceAddresses(obj.getAddresses());
        return repository.save(obj);
    }

    public void delete(Long id_person){
        if (!repository.existsById(id_person)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado");
        }
        repository.deleteById(id_person);
    }

    public Professor selectById(Long id_person){
        return repository.findById(id_person)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
    }

    public List<Professor> selectAll(){
        return repository.findAll();
    }

    public Professor update(Professor obj){
        Professor entity = selectById(obj.getId());
        updateProfessor(entity, obj);
        return repository.save(entity);
    }

    private void updateProfessor(Professor entity, Professor obj){
        entity.setName(obj.getName());
        entity.setPhoneNumber(obj.getPhoneNumber());
        entity.setEmailAddress(obj.getEmailAddress());
        entity.setStatus(obj.getStatus());
        entity.replaceAddresses(obj.getAddresses());
        entity.setSalary(obj.getSalary());
    }
}
