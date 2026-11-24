package br.edu.fae.processoseletivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fae.processoseletivo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
