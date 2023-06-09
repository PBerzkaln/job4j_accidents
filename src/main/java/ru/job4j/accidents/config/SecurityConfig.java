package ru.job4j.accidents.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.job4j.accidents.util.PassEncoderHandler;

import javax.sql.DataSource;

/**
 * Создадим отдельный класс, в котором сделаем настройки для авторизации.
 * В этом уроке пользователи будут храниться в памяти.
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource ds;

    /**
     * Добавим запросы авторизации и аутентификации.
     * Это нужно сделать, так как мы изменили схему.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(ds)
                .usersByUsernameQuery("select username, password, enabled "
                        + "from users "
                        + "where username = ?")
                .authoritiesByUsernameQuery(
                        " select u.username, a.authority "
                                + "from authorities as a, users as u "
                                + "where u.username = ? and u.authority_id = a.id");
    }

    /**
     * Метод configure(http) содержит описание доступов
     * и конфигурирование страницы входа в приложение.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                /**
                 * - ссылки, которые доступны всем.
                 */
                .antMatchers("/login", "/reg")
                .permitAll()
                /**
                 * - ссылки доступны только пользователем с ролями ADMIN, USER.
                 */
                .antMatchers("/**")
                .hasAnyRole("ADMIN", "USER")
                .and()
                /**
                 * Настройка формы авторизации.
                 */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf()
                .disable();
    }
}