package org.shedever.testtaskmirea.controller.rest;

import org.shedever.testtaskmirea.dto.StudentDto;
import org.shedever.testtaskmirea.entity.MarkRecord;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.model.Debts;
import org.shedever.testtaskmirea.model.ExcelFormatter;
import org.shedever.testtaskmirea.model.Mark;
import org.shedever.testtaskmirea.model.WordFormatter;
import org.shedever.testtaskmirea.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class StudentRestContoroller {
    @Autowired
    private StudentService studentService;

    @GetMapping("/api/getstudent/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping("/api/deletestudent/{id}")
    public ResponseEntity<String> deleteStudentByID(@PathVariable(name = "id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/addstudent")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/getstudents")
    public ResponseEntity<List<Student>> getStudents() {
        final List<Student> students = studentService.getAllStudents();

        if (students != null && !students.isEmpty())
            return new ResponseEntity<>(students, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/getgradebook/{id}")
    public ResponseEntity<List<MarkRecord>> getGradebook(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        List<MarkRecord> gradebook = student.getGradebook();

        if (gradebook != null && !gradebook.isEmpty())
            return new ResponseEntity<>(gradebook, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/getcountmarks")
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

    @GetMapping("/api/getcountdebts")
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

    @GetMapping("/api/createexcel")
    public ResponseEntity<String> createExcel() {
        List<Student> students = studentService.getAllStudents();
        ExcelFormatter excelFormatter = new ExcelFormatter();

        excelFormatter.createStudentsList(students);
        excelFormatter.createBadObjects(Debts.countDebts(students));

        for (Student student : students) {
            excelFormatter.createGradeBook(student);
        }

        excelFormatter.saveFile("documents/Отчет.xlsx");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/createword")
    public ResponseEntity<String> createWord() {
        List<Student> students = studentService.getAllStudents();
        WordFormatter.createStudentsList(students);
        WordFormatter.createBadObjects(Debts.countDebts(students));

        for (Student student : students) {
            WordFormatter.createGradeBook(student);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
