package ProcessoSeletivo_FAE.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "A matrícula é obrigatória")
    private Long studentNumber;

    // A foto chega do front como texto (data URL em base64: "data:image/png;base64,...").
    // @Lob: coluna de texto grande, porque o padrão (255 caracteres) não comporta uma imagem.
    // Limite de ~1,5 MB de texto, o que corresponde a uma imagem de ~1 MB.
    @Lob
    @Size(max = 1_500_000, message = "A foto deve ter no máximo 1 MB")
    private String photo;

    @Override
    public String getType() {
        return "STUDENT";
    }
}
