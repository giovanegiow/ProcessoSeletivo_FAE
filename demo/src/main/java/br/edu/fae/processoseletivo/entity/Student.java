package br.edu.fae.processoseletivo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student extends Person {

    /** String, não número: matrícula é identificador e pode ter zeros à esquerda. */
    @NotBlank
    @Column(nullable = false, unique = true)
    private String studentNumber;

    /** URL ou imagem em base64 (data URL); @Lob porque base64 passa de 255 caracteres. */
    @Lob
    private String photo;
}
