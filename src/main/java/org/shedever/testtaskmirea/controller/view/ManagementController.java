package org.shedever.testtaskmirea.controller.view;

import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.entity.Teacher;
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
    private StudyObjectService studyObjectService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/management")
    public String management(Model model) {
        model.addAttribute("studyObjects", studyObjectService.getAllStudyObjects());
        model.addAttribute("teachers", teacherService.getAllTeachers());
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
        Teacher teacher = new Teacher(firstName, patronymic, lastName);
        teacherService.saveTeacher(teacher);
        return "redirect:/management";
    }
}
