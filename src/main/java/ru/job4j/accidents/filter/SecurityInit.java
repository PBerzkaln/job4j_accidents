package ru.job4j.accidents.filter;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Класс подключает DelegatingFilterProxy.
 * Это главный фильтр, в котором идет обработка запросов.
 * Tomcat автоматически определяет такой класс и подключает эти фильтры.
 */
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}