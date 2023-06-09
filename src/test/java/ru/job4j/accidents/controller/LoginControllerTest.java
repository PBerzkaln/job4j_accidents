package ru.job4j.accidents.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;

/**
 * Создает контекст.
 */
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginControllerTest {

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
    public void shouldReturnLoginPage() throws Exception {
        /**
         *
         Делаем запрос. Проверяем статус и вид.
         */
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"));
    }

    @Test
    void whenGetLoginPageErrorParamNotNullThenShouldReturnLoginPageMessageError() throws Exception {
        var errorMessage = "Username or Password is incorrect !!";
        this.mockMvc.perform(get("/login?error=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorMessage", errorMessage))
                .andExpect(view().name("users/login"));
    }

    @Test
    void whenGetLoginPageLogoutParamNotNullThenShouldReturnLoginPageMessageError() throws Exception {
        var errorMessage = "You have been successfully logged out !!";
        this.mockMvc.perform(get("/login?logout=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorMessage", errorMessage))
                .andExpect(view().name("users/login"));
    }
}