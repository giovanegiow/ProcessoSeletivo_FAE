package ProcessoSeletivo_FAE.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
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
public class Person implements Serializable {
    @id_person
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_person;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
