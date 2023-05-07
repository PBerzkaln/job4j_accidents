package ru.job4j.accidents.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;

/**
 * Создает контекст.
 */
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    /**
     * Создает объект-заглушку.
     * Мы можем отправлять в него запросы.
     */
    @Autowired
    private MockMvc mockMvc;

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
    public void shouldReturnEditAccidentPage() throws Exception {
        this.mockMvc.perform(get("/formUpdateAccident/?id=18"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/editAccident"));
    }
}

