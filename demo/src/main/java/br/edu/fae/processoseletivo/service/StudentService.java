package br.edu.fae.processoseletivo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fae.processoseletivo.entity.Student;
import br.edu.fae.processoseletivo.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Student create(Student obj) {
        obj.setId(null); // id é gerado pelo banco; ignora o que vier no corpo
        obj.setAddresses(obj.getAddresses()); // garante o vínculo endereço -> pessoa
        return repository.save(obj);
    }

    @Transactional(readOnly = true)
    public List<Student> selectAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Student selectById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno " + id + " não encontrado"));
    }

    @Transactional
    public Student update(Long id, Student obj) {
        Student entity = selectById(id);
        entity.updateCommonFields(obj);
        entity.setStudentNumber(obj.getStudentNumber());
        entity.setPhoto(obj.getPhoto());
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(selectById(id));
    }
}
