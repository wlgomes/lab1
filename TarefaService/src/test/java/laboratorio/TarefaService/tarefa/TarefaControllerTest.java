package laboratorio.TarefaService.tarefa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(TarefaController.class)
@ExtendWith(MockitoExtension.class)
class TarefaControllerTest {

    @MockBean
    TarefaService tarefaService;

    @Mock
    private Tarefa tarefa;
    @Autowired
    private MockMvc mockMvc;
    Tarefa tarefa1 = new Tarefa(3, "https://gutenberg.org/cache/epub/69283/pg69283.txt", "Em processamento", "Desconhecido", Timestamp.valueOf(LocalDateTime.now()));


    @Test
    //tarefacontroller linhas 28 a 30
    void CT1() throws Exception {
        this.mockMvc
                .perform(get("/tarefa/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("identificar.html"));
    }

    @Test
    //identificar
    void CT2() throws Exception {
        when(tarefaService.getTarefaById(anyInt())).thenReturn(tarefa1);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/tarefa/identificar")
                        .param("url", "https://gutenberg.org/cache/epub/69283/pg69283.txt"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("identificar.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("id", "Tarefa Id: 3"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("sucesso"));

    }

    @Test
    void CT3() throws Exception {
        when(tarefaService.getTarefaById(anyInt())).thenReturn(tarefa1);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/tarefa/identificar")
                        .param("url", "urlinvalido"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("identificar.html"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("erro"));
    }
    @Test
    void CT3v1() throws Exception {
        when(tarefaService.getTarefaById(anyInt())).thenReturn(tarefa1);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/tarefa/identificar")
                        .param("url", "urlinvalidourlinvalidourlinvaliurlinvalidourlinvalidourlinvalidourlinvalidourlinvalidourlinvalidourlinvalidourlinvalidodourlinvalidourlinvalidourlinvalidourlinvalidourlinvalido"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("identificar.html"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("erro"));
    }


    @Test
    void CT5() throws Exception {
        this.mockMvc
                .perform(get("/tarefa/estado"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("estado.html"));
    }

    @Test
    void CT6() throws Exception {
        when(tarefaService.getTarefaById(anyInt())).thenReturn(tarefa1);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/tarefa/estadoId")
                        .param("id", tarefa.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("estado.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("url", "URL documento: https://gutenberg.org/cache/epub/69283/pg69283.txt"))
                .andExpect(MockMvcResultMatchers.model().attribute("estado", "Estado: Em processamento"))
                .andExpect(MockMvcResultMatchers.model().attribute("idioma", ""));
    }
    @Test
        //ct6 ainda não está concluido
    void CT6v1() throws Exception {
        when(tarefaService.getTarefaById(anyInt())).thenReturn(new Tarefa());
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/tarefa/estadoId")
                        .param("id", tarefa.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("estado.html"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("erro"));

    }

}