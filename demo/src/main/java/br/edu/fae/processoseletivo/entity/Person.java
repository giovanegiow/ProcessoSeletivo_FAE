package br.edu.fae.processoseletivo.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Superclasse de Student e Professor (herança do diagrama).
 * JOINED: uma tabela "person" com os campos comuns + uma tabela por subclasse.
 *
 * O campo "type" no JSON ("STUDENT" ou "PROFESSOR") diz ao Jackson qual subclasse
 * instanciar e é devolvido nas respostas, inclusive na listagem geral de /persons.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Student.class, name = "STUDENT"),
    @JsonSubTypes.Type(value = Professor.class, name = "PROFESSOR")
})
@Getter
@Setter
@NoArgsConstructor
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Email
    private String emailAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    /** Multiplicidade 1..* do diagrama: pelo menos um endereço. */
    @NotEmpty
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<@Valid Address> addresses = new ArrayList<>();

    /**
     * Substitui os endereços mantendo a mesma coleção (exigência do orphanRemoval)
     * e ligando cada endereço de volta a esta pessoa.
     */
    public void setAddresses(List<Address> newAddresses) {
        // Cópia antes do clear(): newAddresses pode ser a própria this.addresses.
        List<Address> copy = newAddresses == null ? List.of() : new ArrayList<>(newAddresses);
        this.addresses.clear();
        copy.forEach(this::addAddress);
    }

    public void addAddress(Address address) {
        address.setPerson(this);
        this.addresses.add(address);
    }

    /** Copia os campos comuns de outra pessoa (usado no update). */
    public void updateCommonFields(Person source) {
        this.name = source.getName();
        this.phoneNumber = source.getPhoneNumber();
        this.emailAddress = source.getEmailAddress();
        this.status = source.getStatus();
        setAddresses(source.getAddresses());
    }
}
