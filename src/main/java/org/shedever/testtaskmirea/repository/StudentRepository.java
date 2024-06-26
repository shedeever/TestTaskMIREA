package org.shedever.testtaskmirea.repository;

import org.shedever.testtaskmirea.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByFirstNameAndLastNameAndPatronymic(String firstName, String lastName, String patronymic);
}
