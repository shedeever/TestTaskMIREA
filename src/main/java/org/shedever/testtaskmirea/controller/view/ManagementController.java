package org.shedever.testtaskmirea.controller.view;

import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.entity.Teacher;
import org.shedever.testtaskmirea.service.StudentService;
import org.shedever.testtaskmirea.service.StudyObjectService;
import org.shedever.testtaskmirea.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManagementController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudyObjectService studyObjectService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/management")
    public String management(Model model) {
        model.addAttribute("studyObjects", studyObjectService.getAllStudyObjects());
        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("students", studentService.getAllStudents());
        return "management";
    }

    @PostMapping("/management/addstudyobject")
    public String addStudyObject(@RequestParam("studyObjectName") String studyObjectName,
                                 @RequestParam("countTerm") int terms,
                                 @RequestParam("teacherId") Long teacherId, Model model) {
        Teacher teacher = teacherService.getTeacher(teacherId);
        StudyObject studyObject = new StudyObject(studyObjectName, teacher, terms);

        studyObjectService.saveStudyObject(studyObject);
        return "redirect:/management";
    }

    @PostMapping("/management/addteacher")
    public String addTeacher(@RequestParam(name = "firstName") String firstName,
                             @RequestParam(name = "patronymic") String patronymic,
                             @RequestParam(name = "lastName") String lastName, Model model){
        Teacher teacher = new Teacher(firstName, lastName, patronymic);
        teacherService.saveTeacher(teacher);
        return "redirect:/management";
    }

    @PostMapping("/management/addstudent")
    public String addStudent(@RequestParam(name = "firstName") String firstName,
                             @RequestParam(name = "patronymic") String patronymic,
                             @RequestParam(name = "lastName") String lastName,
                             @RequestParam(name = "course") int course, Model model){
        Student student = new Student(firstName, lastName, patronymic, course);
        studentService.saveStudent(student);
        return "redirect:/management";
    }

    @PostMapping("/management/deletestudyobject")
    public String deleteStudyObject(@RequestParam(name = "studyObjectId") Long id, Model model){
        studyObjectService.deleteStudyObject(id);
        return "redirect:/management";
    }

    @PostMapping("/management/deleteteacher")
    public String deleteTeacher(@RequestParam(name = "teacherId") Long id, Model model){
        teacherService.deleteTeacher(id);
        return "redirect:/management";
    }

    @PostMapping("/management/deletestudent")
    public String deleteStudent(@RequestParam(name = "studentId") Long id, Model model){
        studentService.deleteStudent(id);
        return "redirect:/management";
    }
}
