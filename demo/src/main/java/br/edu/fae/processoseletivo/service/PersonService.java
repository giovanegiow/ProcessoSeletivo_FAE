package br.edu.fae.processoseletivo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fae.processoseletivo.entity.Person;
import br.edu.fae.processoseletivo.repository.PersonRepository;

/** Operações sobre qualquer pessoa (aluno ou professor). Criação e edição ficam nos serviços das subclasses. */
@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Person> selectAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Person selectById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa " + id + " não encontrada"));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa " + id + " não encontrada");
        }
        repository.deleteById(id);
    }
}
