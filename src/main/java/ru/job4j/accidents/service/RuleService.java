package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.List;

public interface RuleService {
    List<Rule> findAll();
}