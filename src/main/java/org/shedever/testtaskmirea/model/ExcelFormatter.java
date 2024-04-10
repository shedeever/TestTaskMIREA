package org.shedever.testtaskmirea.model;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelFormatter {
    private final Workbook workbook;

    public ExcelFormatter() {
        this.workbook = new XSSFWorkbook();
    }

    public void createStudentsList(List<Student> students) {
        Sheet sheet = workbook.createSheet("Список студентов");
        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("ФИО");
        headerRow.createCell(1).setCellValue("Количество оценок отлично");
        headerRow.createCell(2).setCellValue("Количество оценок хорошо");
        headerRow.createCell(3).setCellValue("Количество оценок удовлетворительно");
        headerRow.createCell(4).setCellValue("Количество оценок неудовлетворительно");
        headerRow.createCell(5).setCellValue("Количество неявок");
        headerRow.createCell(6).setCellValue("Средний балл");

        int iterRow = 1;
        for (Student student : students) {
            Row row = sheet.createRow(iterRow++);
            row.createCell(0).setCellValue(student.getName());
            row.createCell(1).setCellValue(student.getCountByMark(Mark.Great));
            row.createCell(2).setCellValue(student.getCountByMark(Mark.Well));
            row.createCell(3).setCellValue(student.getCountByMark(Mark.Good));
            row.createCell(4).setCellValue(student.getCountByMark(Mark.Bad));
            row.createCell(5).setCellValue(student.getCountByMark(Mark.DidntShow));
            row.createCell(6).setCellValue(student.averangeMark());
        }

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void createBadObjects(Map<StudyObject, Integer> debts) {
        List<Map.Entry<StudyObject, Integer>> debtsList = MarkRecord.sortDebtsList(debts);

        Sheet sheet = workbook.createSheet("Задолженности");
        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("Предмет");
        headerRow.createCell(1).setCellValue("Количество задолженностей");

        int iterRow = 1;
        for (Map.Entry<StudyObject, Integer> debtObject : debtsList) {
            Row row = sheet.createRow(iterRow++);
            row.createCell(0).setCellValue(debtObject.getKey().getName());
            row.createCell(1).setCellValue(debtObject.getValue());
        }

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void createGradeBook(Student student) {
        Sheet sheet = workbook.createSheet("Зачетная книжка " + student.getName());
        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("Предмет");
        headerRow.createCell(1).setCellValue("Семестр");
        headerRow.createCell(2).setCellValue("Преподаватель");
        headerRow.createCell(3).setCellValue("Оценка");

        int iterRow = 1;
        for (MarkRecord mark : student.getGradebook()) {
            Row row = sheet.createRow(iterRow++);
            row.createCell(0).setCellValue(mark.getStudyObject().getName());
            row.createCell(1).setCellValue(mark.getTerm());
            row.createCell(2).setCellValue(mark.getTeacher().getName());
            row.createCell(3).setCellValue(mark.getMark().getStrvalue());
        }

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void saveFile(String filename) {
        try(FileOutputStream fos = new FileOutputStream(filename)){
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
