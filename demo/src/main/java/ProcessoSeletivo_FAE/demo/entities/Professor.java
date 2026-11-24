package ProcessoSeletivo_FAE.demo.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull(message = "O salário é obrigatório")
    @Positive(message = "O salário deve ser maior que zero")
    private BigDecimal salary;

    @Override
    public String getType() {
        return "PROFESSOR";
    }
}
