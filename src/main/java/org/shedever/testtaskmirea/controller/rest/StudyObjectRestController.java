package org.shedever.testtaskmirea.controller.rest;

import org.shedever.testtaskmirea.dto.StudyObjectDto;
import org.shedever.testtaskmirea.entity.StudyObject;
import org.shedever.testtaskmirea.service.StudyObjectService;
import org.shedever.testtaskmirea.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudyObjectRestController {
    @Autowired
    private StudyObjectService studyObjectService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/api/addstudyobject")
    public ResponseEntity<String> addStudyObject(@RequestBody StudyObjectDto studyObjectDto) {
        StudyObject studyObject = new StudyObject();
        studyObject.setName(studyObjectDto.getName());
        studyObject.setCountOfTerms(studyObjectDto.getCountOfTerms());
        studyObject.setTeacher(teacherService.getTeacher(studyObjectDto.getTeacherId()));
        studyObjectService.saveStudyObject(studyObject);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/api/deletestudyobject/{id}")
    public ResponseEntity<String> deleteStudyObject(@PathVariable Long id) {
        studyObjectService.deleteStudyObject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/getstudyobjects")
    public ResponseEntity<List<StudyObject>> getStudyObjects() {
        List<StudyObject> studyObjects = studyObjectService.getAllStudyObjects();
        if (studyObjects.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(studyObjectService.getAllStudyObjects(), HttpStatus.OK);
    }
}
