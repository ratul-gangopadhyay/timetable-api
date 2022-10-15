package com.myschool.timetable.api.controller;

import com.myschool.timetable.models.dto.request.TeacherRequestDTO;
import com.myschool.timetable.models.dto.response.TeacherResponseDTO;
import com.myschool.timetable.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.myschool.timetable.constants.Messages.DELETE_TEACHER_SUCCESS;

@RestController
@RequestMapping("/teachers")
@Validated
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherResponseDTO> insertTeacher(@Valid @RequestBody TeacherRequestDTO teacherRequestDTO) {
        return new ResponseEntity<>(teacherService.insertTeacher(teacherRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponseDTO>> fetchAllTeachers() {
        return new ResponseEntity<>(teacherService.fetchAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> fetchTeacherById(@PathVariable String id) {
        return new ResponseEntity<>(teacherService.fetchById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacherById(
            @PathVariable String id,
            @RequestBody TeacherRequestDTO teacherRequestDTO) {
        return new ResponseEntity<>(teacherService.updateById(id, teacherRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacherById(@PathVariable String id) {
        String deletedTeacher = teacherService.deleteById(id);
        String successMessage = String.format(DELETE_TEACHER_SUCCESS, deletedTeacher);
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
