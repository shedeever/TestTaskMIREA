package org.shedever.testtaskmirea.service;

import org.shedever.testtaskmirea.entity.MarkRecord;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student getStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.orElse(null);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public boolean deleteStudent(Long id) {
        studentRepository.deleteById(id);
        return true;
    }
}
