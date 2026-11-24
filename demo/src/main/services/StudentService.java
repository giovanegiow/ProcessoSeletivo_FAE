import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class StudentService {
    @Autowired 
    private StudentRepository repository;

    public Student create(Student obj){
        return repository.save(obj);
    }

    public void delete(Long id_person){
        repository.deleteById(id_person);
    }

    public Student selectById(Long id_person){
        Optional<Student> obj = repository.findById(id_person);
        return obj.get();
    }

    public List<Student> selectAll(){
        return repository.findAll();
    }

    public Student update(Student obj){
        Optional<Student> newObj = repository.findById(obj.getId());
        updateStudent(newObj, obj);
        return repository.save(newObj.get());
    }

    private void updateStudent(Optional<Student> newObj, Student obj){
        newObj.get().setName(obj.getName());
    }
}
