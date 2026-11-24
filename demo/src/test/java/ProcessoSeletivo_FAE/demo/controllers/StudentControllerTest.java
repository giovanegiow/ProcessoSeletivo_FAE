package ProcessoSeletivo_FAE.demo.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import ProcessoSeletivo_FAE.demo.entities.Status;
import ProcessoSeletivo_FAE.demo.entities.Student;
import ProcessoSeletivo_FAE.demo.services.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService service;

    @Test
    void post_deveRetornar201ComOAlunoCriado() throws Exception {
        Student salvo = new Student();
        salvo.setId(1L);
        salvo.setName("Ana");
        salvo.setStatus(Status.ACTIVE);
        when(service.create(any(Student.class))).thenReturn(salvo);

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Ana","emailAddress":"ana@email.com",
                                 "phoneNumber":"1","status":"ACTIVE","studentNumber":2024001,
                                 "addresses":[{"street":"Rua A, 10","city":"Curitiba","state":"PR",
                                               "zipCode":"80000-000","country":"Brasil"}]}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("STUDENT"));
    }

    @Test
    void get_deveRetornar200ComOAluno() throws Exception {
        Student s = new Student();
        s.setId(1L);
        s.setName("Ana");
        when(service.selectById(1L)).thenReturn(s);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana"));
    }

    @Test
    void delete_deveRetornar204() throws Exception {
        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }

    @Test
    void get_quandoNaoExiste_deveRetornar404() throws Exception {
        when(service.selectById(99L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        mockMvc.perform(get("/student/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void post_comDadosInvalidos_deveRetornar400ComOsCampos() throws Exception {
        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"","emailAddress":"email-invalido","status":"ACTIVE",
                                 "addresses":[]}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("O nome é obrigatório"))
                .andExpect(jsonPath("$.emailAddress").value("E-mail inválido"))
                .andExpect(jsonPath("$.phoneNumber").value("O telefone é obrigatório"))
                .andExpect(jsonPath("$.studentNumber").value("A matrícula é obrigatória"))
                .andExpect(jsonPath("$.addresses").value("Informe pelo menos um endereço"));

        // dados inválidos nem chegam ao service
        verify(service, never()).create(any(Student.class));
    }

    @Test
    void post_comEnderecoIncompleto_deveRetornar400() throws Exception {
        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Ana","emailAddress":"ana@email.com","phoneNumber":"1",
                                 "status":"ACTIVE","studentNumber":2024001,
                                 "addresses":[{"street":"","city":"Curitiba","state":"PR",
                                               "zipCode":"80000-000","country":"Brasil"}]}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$['addresses[0].street']").value("A rua é obrigatória"));
    }
}
