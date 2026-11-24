package ProcessoSeletivo_FAE.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter
@EqualsAndHashCode(of = "id")

@Entity
@Inheritance(strategy = InheritanceType.JOINED) 
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public String getType() {          
        return "PERSON";
    }
}
