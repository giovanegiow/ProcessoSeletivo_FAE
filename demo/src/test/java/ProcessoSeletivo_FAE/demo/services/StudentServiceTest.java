package ProcessoSeletivo_FAE.demo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ProcessoSeletivo_FAE.demo.entities.Status;
import ProcessoSeletivo_FAE.demo.entities.Student;
import ProcessoSeletivo_FAE.demo.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    private Student aluno(Long id, String nome) {
        Student s = new Student();
        s.setId(id);
        s.setName(nome);
        s.setEmailAddress("aluno@email.com");
        s.setPhoneNumber("(41) 99999-1234");
        s.setStatus(Status.ACTIVE);
        s.setStudentNumber(2024001L);
        return s;
    }

    @Test
    void create_deveSalvarERetornarOAluno() {
        Student novo = aluno(null, "Ana");
        when(repository.save(novo)).thenReturn(aluno(1L, "Ana"));

        Student salvo = service.create(novo);

        assertThat(salvo.getId()).isEqualTo(1L);
        verify(repository).save(novo);
    }

    @Test
    void selectById_quandoExiste_deveRetornarOAluno() {
        when(repository.findById(1L)).thenReturn(Optional.of(aluno(1L, "Ana")));

        Student encontrado = service.selectById(1L);

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
        when(repository.findById(1L)).thenReturn(Optional.of(aluno(1L, "Ana")));
        when(repository.save(any(Student.class))).thenAnswer(inv -> inv.getArgument(0));

        Student atualizado = service.update(aluno(1L, "Ana Souza"));

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

        Student alterado = new Student();
        alterado.setId(99L);

        assertThatThrownBy(() -> service.update(alterado))
                .isInstanceOf(ResponseStatusException.class);
    }
}
