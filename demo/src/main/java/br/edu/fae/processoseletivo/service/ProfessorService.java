package br.edu.fae.processoseletivo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fae.processoseletivo.entity.Professor;
import br.edu.fae.processoseletivo.repository.ProfessorRepository;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Professor create(Professor obj) {
        obj.setId(null); // id é gerado pelo banco; ignora o que vier no corpo
        obj.setAddresses(obj.getAddresses()); // garante o vínculo endereço -> pessoa
        return repository.save(obj);
    }

    @Transactional(readOnly = true)
    public List<Professor> selectAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Professor selectById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor " + id + " não encontrado"));
    }

    @Transactional
    public Professor update(Long id, Professor obj) {
        Professor entity = selectById(id);
        entity.updateCommonFields(obj);
        entity.setSalary(obj.getSalary());
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(selectById(id));
    }
}
