package org.shedever.testtaskmirea;

import org.shedever.testtaskmirea.entity.*;
import org.shedever.testtaskmirea.model.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Student> students = JSONParser.parseStudents("data/students.json");

        ExcelFormatter excelFormatter = new ExcelFormatter();
        excelFormatter.createStudentsList(students);
        excelFormatter.createBadObjects(MarkRecord.countDebts(students));

        for (Student student : students) {
            excelFormatter.createGradeBook(student);
        }

        excelFormatter.saveFile("documents/Отчет.xlsx");

        WordFormatter.createStudentsList(students);
        WordFormatter.createBadObjects(MarkRecord.countDebts(students));

        for (Student student : students) {
            WordFormatter.createGradeBook(student);
        }
    }
}
