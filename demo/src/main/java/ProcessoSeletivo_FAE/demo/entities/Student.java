package ProcessoSeletivo_FAE.demo.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter
@EqualsAndHashCode(of = "id_person")

@Entity

public class Student {
    private Long id_person;
    private Long studentNumber;
    private String photo;
}
