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
import ProcessoSeletivo_FAE.demo.entities.Student;
import ProcessoSeletivo_FAE.demo.repository.StudentRepository;
import ProcessoSeletivo_FAE.demo.services.StudentService;

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
                .isInstanceOf(NoSuchElementException.class);
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
        service.delete(1L);

        verify(repository).deleteById(1L);
    }
}
