package org.shedever.testtaskmirea.model;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordFormatter {

    public static void createStudentsList(List<Student> students) {
        XWPFDocument document = new XWPFDocument();

        XWPFTable table = document.createTable();
        XWPFTableRow headerRow = table.getRow(0);

        headerRow.getCell(0).setText("ФИО");
        headerRow.addNewTableCell().setText("Количество оценок отлично");
        headerRow.addNewTableCell().setText("Количество оценок хорошо");
        headerRow.addNewTableCell().setText("Количество оценок удовлетворительно");
        headerRow.addNewTableCell().setText("Количество оценок неудовлетворительно");
        headerRow.addNewTableCell().setText("Количество неявок");
        headerRow.addNewTableCell().setText("Средний балл");

        for (Student student : students) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(student.getName());
            row.getCell(1).setText(String.valueOf(student.getCountByMark(Mark.Great)));
            row.getCell(2).setText(String.valueOf(student.getCountByMark(Mark.Well)));
            row.getCell(3).setText(String.valueOf(student.getCountByMark(Mark.Good)));
            row.getCell(4).setText(String.valueOf(student.getCountByMark(Mark.Bad)));
            row.getCell(5).setText(String.valueOf(student.getCountByMark(Mark.DidntShow)));
            row.getCell(6).setText(String.valueOf(student.averangeMark()));
        }

        saveFile("documents/Список студентов.docx", document);
    }

    public static void createBadObjects(Map<StudyObject, Integer> debts) {
        List<Map.Entry<StudyObject, Integer>> debtsList = MarkRecord.sortDebtsList(debts);

        XWPFDocument document = new XWPFDocument();

        XWPFTable table = document.createTable();
        XWPFTableRow headerRow = table.getRow(0);

        headerRow.getCell(0).setText("Предмет");
        headerRow.addNewTableCell().setText("Количество задолженностей");


        for (Map.Entry<StudyObject, Integer> debtObject : debtsList) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(debtObject.getKey().getName());
            row.getCell(1).setText(String.valueOf(debtObject.getValue()));
        }

        saveFile("documents/Задолженности.docx", document);
    }

    public static void createGradeBook(Student student) {
        XWPFDocument document = new XWPFDocument();

        XWPFTable table = document.createTable();
        XWPFTableRow headerRow = table.getRow(0);

        headerRow.getCell(0).setText("Предмет");
        headerRow.addNewTableCell().setText("Семестр");
        headerRow.addNewTableCell().setText("Преподаватель");
        headerRow.addNewTableCell().setText("Оценка");

        for (MarkRecord mark : student.getGradebook()) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(mark.getStudyObject().getName());
            row.getCell(1).setText(String.valueOf(mark.getTerm()));
            row.getCell(2).setText(mark.getTeacher().getName());
            row.getCell(3).setText(mark.getMark().getStrvalue());
        }

        saveFile("documents/Зачетка " + student.getName() + ".docx", document);
    }

    private static void saveFile(String filename, XWPFDocument document) {
        try(FileOutputStream fos = new FileOutputStream(filename)){
            document.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
