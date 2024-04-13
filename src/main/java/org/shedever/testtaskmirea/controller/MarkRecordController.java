package org.shedever.testtaskmirea.controller;

import org.shedever.testtaskmirea.dto.MarkRecordDto;
import org.shedever.testtaskmirea.entity.MarkRecord;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.model.Mark;
import org.shedever.testtaskmirea.service.MarkRecordService;
import org.shedever.testtaskmirea.service.StudentService;
import org.shedever.testtaskmirea.service.StudyObjectService;
import org.shedever.testtaskmirea.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MarkRecordController {
    @Autowired
    private MarkRecordService markRecordService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudyObjectService studyObjectService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/addmark")
    public ResponseEntity<String> markRecord(@RequestBody MarkRecordDto markRecordDto) {
        MarkRecord markRecord = new MarkRecord();

        Student student = studentService.getStudent(markRecordDto.getStudentId());
        StudyObject studyObject = studyObjectService.getStudyObject(markRecordDto.getStudyObjectId());
        int term = markRecordDto.getTerm();
        Mark mark = markRecordDto.getMark();

        markRecord.setStudyObject(studyObject);
        markRecord.setTerm(term);
        markRecord.setMark(mark);
        markRecord.setTeacher(studyObject.getTeacher());
        student.addMark(studyObject, term, mark);

        studentService.saveStudent(student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/deletemark/{id}")
    public ResponseEntity<String> deleteMarkRecord(@PathVariable Long id) {
        markRecordService.deleteMarkById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
