package ProcessoSeletivo_FAE.demo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ProcessoSeletivo_FAE.demo.entities.Status;
import ProcessoSeletivo_FAE.demo.entities.Person;
import ProcessoSeletivo_FAE.demo.repository.PersonRepository;

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
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void update_deveAtualizarONome() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoa(1L, "Ana")));
        when(repository.save(any(Person.class))).thenAnswer(inv -> inv.getArgument(0));

        Person atualizado = service.update(pessoa(1L, "Ana Souza"));

        assertThat(atualizado.getName()).isEqualTo("Ana Souza");
    }

    @Test
    void delete_deveChamarORepositorio() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void delete_quandoNaoExiste_lancaExcecaoENaoApaga() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(ResponseStatusException.class);

        verify(repository, never()).deleteById(99L);
    }

    @Test
    void update_quandoNaoExiste_lancaExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Person alterado = new Person();
        alterado.setId(99L);

        assertThatThrownBy(() -> service.update(alterado))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void selectByName_deveRetornarAsPessoasEncontradas() {
        when(repository.findByNameContainingIgnoreCase("ana"))
                .thenReturn(List.of(pessoa(1L, "Ana Souza"), pessoa(2L, "Mariana Lima")));

        List<Person> encontradas = service.selectByName("ana");

        assertThat(encontradas).hasSize(2);
        assertThat(encontradas.get(0).getName()).isEqualTo("Ana Souza");
    }

    @Test
    void selectByName_quandoNinguemTemONome_retornaListaVazia() {
        when(repository.findByNameContainingIgnoreCase("xyz")).thenReturn(List.of());

        assertThat(service.selectByName("xyz")).isEmpty();
    }
}
