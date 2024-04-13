package org.shedever.testtaskmirea.controller;

import org.shedever.testtaskmirea.dto.StudyObjectDto;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.service.StudyObjectService;
import org.shedever.testtaskmirea.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudyObjectController {
    @Autowired
    private StudyObjectService studyObjectService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/addstudyobject")
    public ResponseEntity<String> addStudyObject(@RequestBody StudyObjectDto studyObjectDto) {
        StudyObject studyObject = new StudyObject();
        studyObject.setName(studyObjectDto.getName());
        studyObject.setCountOfTerms(studyObjectDto.getCountOfTerms());
        studyObject.setTeacher(teacherService.getTeacher(studyObjectDto.getTeacherId()));
        studyObjectService.saveStudyObject(studyObject);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/deletestudyobject/{id}")
    public ResponseEntity<String> deleteStudyObject(@PathVariable Long id) {
        studyObjectService.deleteStudyObject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
