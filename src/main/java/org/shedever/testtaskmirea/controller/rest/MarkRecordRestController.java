package org.shedever.testtaskmirea.controller.rest;

import org.shedever.testtaskmirea.dto.MarkRecordDto;
import org.shedever.testtaskmirea.entity.MarkRecord;
import org.shedever.testtaskmirea.entity.Student;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.model.Mark;
import org.shedever.testtaskmirea.service.MarkRecordService;
import org.shedever.testtaskmirea.service.StudentService;
import org.shedever.testtaskmirea.service.StudyObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MarkRecordRestController {
    @Autowired
    private MarkRecordService markRecordService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudyObjectService studyObjectService;

    @PostMapping("/api/addmark")
    public ResponseEntity<String> markRecord(@RequestBody MarkRecordDto markRecordDto) {
        MarkRecord markRecord = new MarkRecord();

        Student student = studentService.getStudent(markRecordDto.getStudentId());
        StudyObject studyObject = studyObjectService.getStudyObject(markRecordDto.getStudyObjectId());
        MarkRecord bufMarkRecord = markRecordService.findMarkByStudyObject(studyObject);
        int term = markRecordDto.getTerm();

        if (bufMarkRecord != null && bufMarkRecord.getTerm() == term )
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        Mark mark = markRecordDto.getMark();

        markRecord.setStudyObject(studyObject);
        markRecord.setTerm(term);
        markRecord.setMark(mark);
        markRecord.setTeacher(studyObject.getTeacher());
        student.addMark(studyObject, term, mark);

        studentService.saveStudent(student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/api/deletemark/{id}")
    public ResponseEntity<String> deleteMarkRecord(@PathVariable Long id) {
        markRecordService.deleteMarkByMarkId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
