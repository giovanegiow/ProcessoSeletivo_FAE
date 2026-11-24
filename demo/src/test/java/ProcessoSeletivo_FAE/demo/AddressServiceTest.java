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

import ProcessoSeletivo_FAE.demo.entities.Address;
import ProcessoSeletivo_FAE.demo.repository.AddressRepository;
import ProcessoSeletivo_FAE.demo.services.AddressService;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private AddressRepository repository;

    @InjectMocks
    private AddressService service;

    private Address endereco(Long id, String cidade){
        Address a = new Address();
        a.setId(id);
        a.setCity(cidade);
        a.setCountry("Brasil");
        a.setState("PR");
        a.setStreet("Rua teste");
        a.setZipCode("00000-000");
        return a;
    }

    @Test
    void create_deveSalvarERetornarOEndereco() {
        Address novo = endereco(null, "Curitiba");
        when(repository.save(novo)).thenReturn(endereco(1L, "Curitiba"));

        Address salvo = service.create(novo);

        assertThat(salvo.getId()).isEqualTo(1L);
        verify(repository).save(novo);
    }

    @Test
    void selectById_quandoExiste_deveRetornarOEndereco() {
        when(repository.findById(1L)).thenReturn(Optional.of(endereco(1L, "Curitiba")));

        Address encontrado = service.selectById(1L);

        assertThat(encontrado.getCity()).isEqualTo("Curitiba");
    }

    @Test
    void selectById_quandoNaoExiste_lancaExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.selectById(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void update_deveAtualizarOEndereco() {
        when(repository.findById(1L)).thenReturn(Optional.of(endereco(1L, "Curitiba")));
        when(repository.save(any(Address.class))).thenAnswer(inv -> inv.getArgument(0));

        Address atualizado = service.update(endereco(1L, "Curitiba"));

        assertThat(atualizado.getCity()).isEqualTo("Curitiba");
    }
}
