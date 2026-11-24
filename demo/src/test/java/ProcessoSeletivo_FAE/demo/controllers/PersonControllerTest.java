package ProcessoSeletivo_FAE.demo.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import ProcessoSeletivo_FAE.demo.entities.Professor;
import ProcessoSeletivo_FAE.demo.entities.Student;
import ProcessoSeletivo_FAE.demo.services.PersonService;

// Testa as rotas de /person: buscar por id e buscar por nome.
@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService service;

    @Test
    void getPorId_deveRetornar200ComAPessoaEOTipo() throws Exception {
        Professor carla = new Professor();
        carla.setId(2L);
        carla.setName("Carla Mendes");
        when(service.selectById(2L)).thenReturn(carla);

        mockMvc.perform(get("/person/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Carla Mendes"))
                .andExpect(jsonPath("$.type").value("PROFESSOR"));
    }

    @Test
    void getPorId_quandoNaoExiste_deveRetornar404() throws Exception {
        when(service.selectById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));

        mockMvc.perform(get("/person/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void search_deveRetornar200ComAsPessoasEncontradas() throws Exception {
        Student ana = new Student();
        ana.setId(1L);
        ana.setName("Ana Souza");
        when(service.selectByName("ana")).thenReturn(List.of(ana));

        mockMvc.perform(get("/person/search").param("name", "ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ana Souza"))
                .andExpect(jsonPath("$[0].type").value("STUDENT"));
    }

    @Test
    void search_semONome_deveRetornar400() throws Exception {
        mockMvc.perform(get("/person/search"))
                .andExpect(status().isBadRequest());
    }
}
