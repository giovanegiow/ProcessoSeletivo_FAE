package ProcessoSeletivo_FAE.demo.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter

@Entity

public class Student extends Person {
    private Long studentNumber;
    private String photo;
    @Override
    public String getType() {
        return "STUDENT";
    }
}
