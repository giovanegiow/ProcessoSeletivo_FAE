package br.edu.fae.processoseletivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fae.processoseletivo.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
