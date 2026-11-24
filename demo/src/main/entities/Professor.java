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

public class Professor {
    private Long id_person;
    private Float street;
}
