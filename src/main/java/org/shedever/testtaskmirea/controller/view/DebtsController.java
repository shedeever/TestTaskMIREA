package org.shedever.testtaskmirea.controller.view;

import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.model.Debts;
import org.shedever.testtaskmirea.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class DebtsController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/debts")
    public String debts(Model model) {
        List<Student> students = studentService.getAllStudents();
        List<Map.Entry<StudyObject, Integer>> debtsList = Debts.sortDebtsList(Debts.countDebts(students));
        model.addAttribute("debtsList", debtsList);
        return "debts";
    }
}
