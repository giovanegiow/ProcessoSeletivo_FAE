package ProcessoSeletivo_FAE.demo.repository;

import ProcessoSeletivo_FAE.demo.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
