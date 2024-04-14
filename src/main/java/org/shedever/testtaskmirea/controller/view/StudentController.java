package org.shedever.testtaskmirea.controller.view;

import org.shedever.testtaskmirea.dto.StudentDto;
import org.shedever.testtaskmirea.entity.MarkRecord;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.model.Debts;
import org.shedever.testtaskmirea.model.ExcelFormatter;
import org.shedever.testtaskmirea.model.Mark;
import org.shedever.testtaskmirea.model.WordFormatter;
import org.shedever.testtaskmirea.service.MarkRecordService;
import org.shedever.testtaskmirea.service.StudentService;
import org.shedever.testtaskmirea.service.StudyObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private MarkRecordService markRecordService;
    @Autowired
    private StudyObjectService studyObjectService;

    @GetMapping("/student")
    public String students(@RequestParam("index") Long id, Model model) {
        Student student = studentService.getStudent(id);
        StudentDto studentDto = new StudentDto(student.getFullName(),
                student.getCountByMark(Mark.Great),
                student.getCountByMark(Mark.Well),
                student.getCountByMark(Mark.Good),
                student.getCountByMark(Mark.Bad),
                student.getCountByMark(Mark.DidntShow),
                student.averangeMark());

        model.addAttribute("countmarks", studentDto);
        model.addAttribute("student", student);
        return "student";
    }

    @PostMapping("/student/deletemark")
    public String deletemark(@RequestParam(name = "index") Long id,
                          @RequestParam(name = "studentId") Long studentId, Model model) {
        markRecordService.deleteMarkByMarkId(id);

        return "redirect:/student?index=" + studentId;
    }

    @PostMapping("/student/addmark")
    public String addmark(@RequestParam(name = "studyObjectName") String studyObjectName,
                          @RequestParam(name = "mark") int markInt,
                          @RequestParam(name = "studentId") Long studentId,
                          @RequestParam(name = "term") int term, Model model) {
        MarkRecord markRecord = new MarkRecord();

        StudyObject studyObject = studyObjectService.getStudyObjectByName(studyObjectName);
        MarkRecord bufMarkRecord = markRecordService.findMarkByStudyObject(studyObject);
        if (bufMarkRecord != null && bufMarkRecord.getTerm() == term)
            return "redirect:/student?index=" + studentId;

        Mark mark = Mark.getMark(markInt);
        Student student = studentService.getStudent(studentId);

        markRecord.setStudyObject(studyObject);
        markRecord.setTerm(term);
        markRecord.setMark(mark);
        markRecord.setTeacher(studyObject.getTeacher());
        student.addMark(studyObject, term, mark);

        studentService.saveStudent(student);
        return "redirect:/student?index=" + studentId;
    }

    @GetMapping("/createexcel")
    public String createExcel(Model model) {
        List<Student> students = studentService.getAllStudents();
        ExcelFormatter excelFormatter = new ExcelFormatter();

        excelFormatter.createStudentsList(students);
        excelFormatter.createBadObjects(Debts.countDebts(students));

        for (Student student : students) {
            excelFormatter.createGradeBook(student);
        }

        excelFormatter.saveFile("documents/Отчет.xlsx");

        return "redirect:/";
    }

    @GetMapping("/createword")
    public String createWord(Model model) {
        List<Student> students = studentService.getAllStudents();
        WordFormatter.createStudentsList(students);
        WordFormatter.createBadObjects(Debts.countDebts(students));

        for (Student student : students) {
            WordFormatter.createGradeBook(student);
        }

        return "redirect:/";
    }
}
