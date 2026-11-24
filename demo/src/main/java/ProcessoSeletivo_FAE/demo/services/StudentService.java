package ProcessoSeletivo_FAE.demo.services;

import java.util.List;
import ProcessoSeletivo_FAE.demo.entities.Student;
import ProcessoSeletivo_FAE.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service

public class StudentService {
    @Autowired 
    private StudentRepository repository;

    public Student create(Student obj){
        obj.replaceAddresses(obj.getAddresses());
        return repository.save(obj);
    }

    public void delete(Long id_person){
        if (!repository.existsById(id_person)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        repository.deleteById(id_person);
    }

    public Student selectById(Long id_person){
        return repository.findById(id_person)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
    }

    public List<Student> selectAll(){
        return repository.findAll();
    }

    public Student update(Student obj){
        Student entity = selectById(obj.getId());
        updateStudent(entity, obj);
        return repository.save(entity);
    }

    private void updateStudent(Student entity, Student obj){
        entity.setName(obj.getName());
        entity.setPhoneNumber(obj.getPhoneNumber());
        entity.setEmailAddress(obj.getEmailAddress());
        entity.setStatus(obj.getStatus());
        entity.replaceAddresses(obj.getAddresses());
        entity.setStudentNumber(obj.getStudentNumber());
        entity.setPhoto(obj.getPhoto());
    }
}
