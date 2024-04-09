package org.shedever.testtaskmirea;

import org.shedever.testtaskmirea.entity.*;
import org.shedever.testtaskmirea.model.Mark;
import org.shedever.testtaskmirea.model.MarkRecord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(new Student("Иванов Иван Иванович", 3),
                                            new Student("Басс Олег Александрович", 2),
                                            new Student("Лебедев Артемий Андреевич", 2),
                                            new Student("Цукерберг Марк", 3),
                                            new Student("Маск Илон", 4));

        List<Teacher> teachers = Arrays.asList(new Teacher("Волков М.Ю."),
                                            new Teacher("Cиницын А.В."),
                                            new Teacher("Пупкин И.О."),
                                            new Teacher("Романов К.И."),
                                            new Teacher("Путин В.В."));

        List<StudyObject> objects = Arrays.asList(new StudyObject("Иностранный язык", teachers.get(2), 2),
                                                new StudyObject("Программирование на языке Java", teachers.get(3), 1),
                                                new StudyObject("Разработка серверных частей интернет ресурсов", teachers.get(1), 1),
                                                new StudyObject("Математический анализ", teachers.get(1), 2),
                                                new StudyObject("История России", teachers.get(4), 1),
                                                new StudyObject("Безопастность жизнедеятельности", teachers.get(4), 1),
                                                new StudyObject("Системное администрирование", teachers.get(3), 1),
                                                new StudyObject("Криптографические методы защиты информации", teachers.get(2), 1));


        students.get(0).addMark(objects.get(0), 1, Mark.Great);
        students.get(0).addMark(objects.get(1), 1, Mark.Great);
        students.get(0).addMark(objects.get(2), 1, Mark.Great);
        students.get(0).addMark(objects.get(3), 1, Mark.Great);
        students.get(0).addMark(objects.get(4), 1, Mark.Great);
        students.get(0).addMark(objects.get(0), 2, Mark.Good);
        students.get(0).addMark(objects.get(5), 1, Mark.Good);
        students.get(0).addMark(objects.get(6), 1, Mark.Good);
        students.get(0).addMark(objects.get(5), 1, Mark.Good);
        students.get(0).addMark(objects.get(3), 1, Mark.Bad);

        students.get(1).addMark(objects.get(0), 1, Mark.DidntShow);
        students.get(1).addMark(objects.get(1), 1, Mark.DidntShow);
        students.get(1).addMark(objects.get(2), 1, Mark.DidntShow);

        students.get(2).addMark(objects.get(5), 1, Mark.DidntShow);
        students.get(2).addMark(objects.get(3), 1, Mark.DidntShow);
        students.get(2).addMark(objects.get(7), 1, Mark.DidntShow);

        students.get(3).addMark(objects.get(1), 1, Mark.DidntShow);
        students.get(3).addMark(objects.get(2), 1, Mark.DidntShow);
        students.get(3).addMark(objects.get(3), 1, Mark.DidntShow);


        System.out.println(students.get(0).toString());
        System.out.println("----------------------------------");

        countDebts(students);

        for (Student student : students) {
            System.out.println("----------------------------------");
            student.showGradebook();
        }
    }

    public static void countDebts(List<Student> students){
        Map<StudyObject, Integer> debts = new HashMap<>();

        for(Student student : students){
            for (MarkRecord markRecord : student.getGradebook()){
                if (markRecord.getMark() == Mark.DidntShow){
                    if (!debts.containsKey(markRecord.getStudyObject())){
                        debts.put(markRecord.getStudyObject(), 1);
                    }
                    else {
                        debts.put(markRecord.getStudyObject(), debts.get(markRecord.getStudyObject()) + 1);
                    }
                }
            }
        }

        debts.entrySet().stream()
                .sorted(Map.Entry.<StudyObject, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getKey().getName() + " - " + entry.getValue()));
    }
}
