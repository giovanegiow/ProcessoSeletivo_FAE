package br.edu.fae.processoseletivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fae.processoseletivo.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
