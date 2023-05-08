package ru.job4j.accidents.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Создает контекст.
 */
@ActiveProfiles("test")
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    /**
     * Создает объект-заглушку.
     * Мы можем отправлять в него запросы.
     */
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccidentService accidents;

    @Test
    /**
     * Подставляет авторизованного пользователя в запрос.
     */
    @WithMockUser
    public void shouldReturnCreateAccidentPage() throws Exception {
        /**
         *
         Делаем запрос. Проверяем статус и вид.
         */
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/createAccident"));
    }

    @Test
    @WithMockUser
    void shouldReturnUpdateAccidentPage() throws Exception {
        var accident = new Accident(1, "", "", "", null, Collections.emptySet());
        when(accidents.findById(accident.getId())).thenReturn(Optional.of(accident));
        this.mockMvc.perform(get("/formUpdateAccident?id=" + accident.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/editAccident"));
    }

    @Test
    @WithMockUser
    void whenPostSaveAccidentThenShouldReturnIndexPage() throws Exception {
        var rIds = Set.of(1, 2, 3);
        var accidentType = new AccidentType(1, null);
        var accident = new Accident(0, "textName", "accidentText", "accidentAddress",
                accidentType, Collections.emptySet());
        this.mockMvc.perform(post("/saveAccident")
                        .param("name", "textName")
                        .param("text", "accidentText")
                        .param("address", "accidentAddress")
                        .param("type.id", "1")
                        .param("rIds", "1", "2", "3"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        ArgumentCaptor<Accident> accidentCapture = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<Set<Integer>> rIdsCapture = ArgumentCaptor.forClass(Set.class);
        ArgumentCaptor<Integer> accidentTypeIdCapture = ArgumentCaptor.forClass(Integer.class);

        verify(accidents).create(accidentCapture.capture(), accidentTypeIdCapture.capture(),
                rIdsCapture.capture());

        assertThat(accidentCapture.getValue()).usingRecursiveComparison()
                .isEqualTo(accident);
        assertThat(rIdsCapture.getValue()).usingRecursiveComparison()
                .isEqualTo(rIds);
        assertThat(accidentTypeIdCapture.getValue()).usingRecursiveComparison()
                .isEqualTo(accidentType.getId());
    }

    @Test
    @WithMockUser
    void whenPostEditAccidentThenShouldReturnRedirectIndexPageAndArgumentCaptureEquals() throws Exception {
        var rIds = Set.of(1, 2, 3);
        var accidentType = new AccidentType(1, null);
        var accident = new Accident(2, "textName", "accidentText", "accidentAddress",
                accidentType, Collections.emptySet());
        this.mockMvc.perform(post("/updateAccident")
                        .param("id", "2")
                        .param("name", "textName")
                        .param("text", "accidentText")
                        .param("address", "accidentAddress")
                        .param("type.id", "1")
                        .param("rIds", "1", "2", "3"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        ArgumentCaptor<Accident> accidentCapture = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<Set<Integer>> rIdsCapture = ArgumentCaptor.forClass(Set.class);

        verify(accidents).update(accidentCapture.capture(), rIdsCapture.capture());

        assertThat(accidentCapture.getValue()).usingRecursiveComparison()
                .isEqualTo(accident);
        assertThat(rIdsCapture.getValue()).usingRecursiveComparison()
                .isEqualTo(rIds);
    }

    @Test
    @WithMockUser
    void whenPostDeleteAccidentTrueThenShouldReturnIndexPage() throws Exception {
        when(accidents.delete(anyInt())).thenReturn(true);
        this.mockMvc.perform(post("/delete/" + anyInt()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
    }

    @Test
    @WithMockUser
    void whenPostDeleteAccidentFalseThenShouldReturnErrorsPage() throws Exception {
        var id = -1;
        var message = "Инцидент с указанным идентификатором не найден";
        when(accidents.delete(id)).thenReturn(false);
        this.mockMvc.perform(post("/delete/" + id))
                .andDo(print())
                .andExpect(model().attribute("message", message))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"));
    }
}
