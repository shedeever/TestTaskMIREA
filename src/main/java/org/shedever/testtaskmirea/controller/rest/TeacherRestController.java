package org.shedever.testtaskmirea.controller.rest;

import org.shedever.testtaskmirea.entity.Teacher;
import org.shedever.testtaskmirea.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherRestController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/api/addteacher")
    public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher) {
        teacherService.saveTeacher(teacher);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/getteachers")
    public ResponseEntity<List<Teacher>> getTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();

        if (teachers != null && !teachers.isEmpty())
            return new ResponseEntity<>(teachers, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/deleteteachers/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
