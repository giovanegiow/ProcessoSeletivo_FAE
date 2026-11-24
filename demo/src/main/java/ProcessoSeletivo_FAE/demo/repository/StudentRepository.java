package ProcessoSeletivo_FAE.demo.repository;

import ProcessoSeletivo_FAE.demo.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
}