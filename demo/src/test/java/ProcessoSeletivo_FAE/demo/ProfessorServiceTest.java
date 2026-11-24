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

import java.math.BigDecimal;

import ProcessoSeletivo_FAE.demo.entities.Status;
import ProcessoSeletivo_FAE.demo.entities.Professor;
import ProcessoSeletivo_FAE.demo.repository.ProfessorRepository;
import ProcessoSeletivo_FAE.demo.services.ProfessorService;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceTest {
    
    @Mock
    private ProfessorRepository repository;

    @InjectMocks
    private ProfessorService service;

    private Professor professor(Long id, String nome) {
        Professor p = new Professor();
        p.setId(id);
        p.setName(nome);
        p.setEmailAddress("professor@email.com");
        p.setStatus(Status.ACTIVE);
        p.setSalary(new BigDecimal("9900.00"));
        return p;
    }

    @Test
    void create_deveSalvarERetornarOProfessor() {
        Professor novo = professor(null, "Ana");
        when(repository.save(novo)).thenReturn(professor(1L, "Ana"));

        Professor salvo = service.create(novo);

        assertThat(salvo.getId()).isEqualTo(1L);
        verify(repository).save(novo);
    }

    @Test
    void selectById_quandoExiste_deveRetornarOProfessor() {
        when(repository.findById(1L)).thenReturn(Optional.of(professor(1L, "Ana")));

        Professor encontrado = service.selectById(1L);

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
        when(repository.findById(1L)).thenReturn(Optional.of(professor(1L, "Ana")));
        when(repository.save(any(Professor.class))).thenAnswer(inv -> inv.getArgument(0));

        Professor atualizado = service.update(professor(1L, "Ana Souza"));

        assertThat(atualizado.getName()).isEqualTo("Ana Souza");
    }
}
