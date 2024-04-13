package org.shedever.testtaskmirea.controller;

import org.shedever.testtaskmirea.dto.StudentDto;
import org.shedever.testtaskmirea.entity.MarkRecord;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.model.Debts;
import org.shedever.testtaskmirea.model.Mark;
import org.shedever.testtaskmirea.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class StudentContoroller {
    @Autowired
    private StudentService studentService;

    @DeleteMapping("/deletestudent/{id}")
    public ResponseEntity<String> deleteStudentByID(@PathVariable(name = "id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addstudent")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudents() {
        final List<Student> students = studentService.getAllStudents();

        if (students != null && !students.isEmpty())
            return new ResponseEntity<>(students, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getgradebook/{id}")
    public ResponseEntity<List<MarkRecord>> getGradebook(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        List<MarkRecord> gradebook = student.getGradebook();

        if (gradebook != null && !gradebook.isEmpty())
            return new ResponseEntity<>(gradebook, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getcountmarks")
    public ResponseEntity<List<StudentDto>> getMarks() {
        List<Student> students = studentService.getAllStudents();
        List<StudentDto> marks = new ArrayList<>();

        students.forEach(student -> {
            marks.add(new StudentDto(student.getFullName(),
                    student.getCountByMark(Mark.Great),
                    student.getCountByMark(Mark.Well),
                    student.getCountByMark(Mark.Good),
                    student.getCountByMark(Mark.Bad),
                    student.getCountByMark(Mark.DidntShow),
                    student.averangeMark()));
        });

        if (!marks.isEmpty())
            return new ResponseEntity<>(marks, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getcountdebts")
    public ResponseEntity<Map<String, Integer>> getCountDebts(){
        List<Student> students = studentService.getAllStudents();

        List<Map.Entry<StudyObject, Integer>> debts = Debts.sortDebtsList(Debts.countDebts(students));
        Map<String, Integer> debtsList = new LinkedHashMap<>();
        debts.forEach(debt -> {
            debtsList.put(debt.getKey().getName(), debt.getValue());
        });
        if (!debts.isEmpty())
            return new ResponseEntity<>(debtsList, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
