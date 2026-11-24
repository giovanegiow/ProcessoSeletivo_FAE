package ProcessoSeletivo_FAE.demo.repository;

import ProcessoSeletivo_FAE.demo.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByNameContainingIgnoreCase(String name);
}
