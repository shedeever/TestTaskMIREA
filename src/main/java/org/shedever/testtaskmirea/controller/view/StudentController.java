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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String students(@RequestParam("lastName") String lastName,
                           @RequestParam("firstName") String firstName,
                           @RequestParam("patronymic") String patronymic, Model model) {
        Student student = studentService.getStudentByFullName(firstName, lastName, patronymic);
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
                             @RequestParam(name = "studentId") Long studentId,
                             RedirectAttributes redirectAttributes, Model model) {
        markRecordService.deleteMarkByMarkId(id);

        Student student = studentService.getStudent(studentId);

        redirectAttributes.addAttribute("lastName", student.getLastName());
        redirectAttributes.addAttribute("firstName", student.getFirstName());
        redirectAttributes.addAttribute("patronymic", student.getPatronymic());

        return "redirect:/student";
    }

    @PostMapping("/student/addmark")
    public String addmark(@RequestParam(name = "studyObjectName") String studyObjectName,
                          @RequestParam(name = "mark") int markInt,
                          @RequestParam(name = "studentId") Long studentId,
                          @RequestParam(name = "term") int term, RedirectAttributes redirectAttributes, Model model) {
        MarkRecord markRecord = new MarkRecord();

        StudyObject studyObject = studyObjectService.getStudyObjectByName(studyObjectName);
        MarkRecord bufMarkRecord = markRecordService.findMarkByStudyObject(studyObject);

        Student student = studentService.getStudent(studentId);

        redirectAttributes.addAttribute("lastName", student.getLastName());
        redirectAttributes.addAttribute("firstName", student.getFirstName());
        redirectAttributes.addAttribute("patronymic", student.getPatronymic());

        if (bufMarkRecord != null && bufMarkRecord.getTerm() == term)
            return "redirect:/student";

        Mark mark = Mark.getMark(markInt);

        markRecord.setStudyObject(studyObject);
        markRecord.setTerm(term);
        markRecord.setMark(mark);
        markRecord.setTeacher(studyObject.getTeacher());
        student.addMark(studyObject, term, mark);

        studentService.saveStudent(student);

        return "redirect:/student";
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
