package ProcessoSeletivo_FAE.demo.entities;

import java.math.BigDecimal;

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

public class Professor extends Person {
    private BigDecimal salary;

    @Override
    public String getType() {
        return "PROFESSOR";
    }
}
