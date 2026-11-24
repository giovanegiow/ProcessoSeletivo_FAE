package ProcessoSeletivo_FAE.demo.repository;

import ProcessoSeletivo_FAE.demo.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    
}
