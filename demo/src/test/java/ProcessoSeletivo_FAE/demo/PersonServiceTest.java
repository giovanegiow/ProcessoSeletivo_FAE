package ProcessoSeletivo_FAE.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ProcessoSeletivo_FAE.demo.entities.Status;
import ProcessoSeletivo_FAE.demo.entities.Person;
import ProcessoSeletivo_FAE.demo.repository.PersonRepository;
import ProcessoSeletivo_FAE.demo.services.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    private Person pessoa(Long id, String nome){
        Person p = new Person();
        p.setId(id);
        p.setName(nome);
        p.setEmailAddress("pessoa@email.com");
        p.setPhoneNumber("(41) 99999-1212");
        p.setStatus(Status.ACTIVE);
        return p;
    }

    @Test
    void create_deveSalvarERetornarAPessoa() {
        Person novo = pessoa(null, "Ana");
        when(repository.save(novo)).thenReturn(pessoa(1L, "Ana"));

        Person salvo = service.create(novo);

        assertThat(salvo.getId()).isEqualTo(1L);
        verify(repository).save(novo);
    }

    @Test
    void selectById_quandoExiste_deveRetornarAPessoa() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoa(1L, "Ana")));

        Person encontrado = service.selectById(1L);

        assertThat(encontrado.getName()).isEqualTo("Ana");
    }

    @Test
    void selectById_quandoNaoExiste_lancaExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.selectById(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void update_deveAtualizarONome() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoa(1L, "Ana")));
        when(repository.save(any(Person.class))).thenAnswer(inv -> inv.getArgument(0));

        Person atualizado = service.update(pessoa(1L, "Ana Souza"));

        assertThat(atualizado.getName()).isEqualTo("Ana Souza");
    }
}
