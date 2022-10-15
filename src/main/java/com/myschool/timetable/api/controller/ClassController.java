package com.myschool.timetable.api.controller;

import com.myschool.timetable.constants.enums.Section;
import com.myschool.timetable.constants.enums.Standard;
import com.myschool.timetable.models.dto.request.MyClassRequestDTO;
import com.myschool.timetable.models.dto.response.MyClassResponseDTO;
import com.myschool.timetable.service.ClassService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import static com.myschool.timetable.constants.Messages.DELETE_CLASS_SUCCESS;

@RestController
@RequestMapping("/class")
@Validated
public class ClassController {

    @Autowired
    private ClassService classService;

    @ApiOperation(value = "Insert Class", notes = "This method inserts a new Class")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyClassResponseDTO> insertClass(@Valid @RequestBody MyClassRequestDTO myClassRequestDTO) {
        return new ResponseEntity<>(classService.insertClass(myClassRequestDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Fetch All Classes", notes = "This method fetches all registered classes")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MyClassResponseDTO>> fetchAllClasses() {
        return new ResponseEntity<>(classService.fetchAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch Class By Standard and Section", notes = "This method fetches class by standard and section")
    @GetMapping(value = "/standards/{standard}/section/{section}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyClassResponseDTO> fetchClassByStandardAndSection(@PathVariable Standard standard,
                                                                             @PathVariable Section section) {
        return new ResponseEntity<>(classService.fetchClassByStandardAndSection(standard, section), HttpStatus.OK);
    }

    @ApiOperation(value = "Updates Class By Standard and Section", notes = "This method updates class by standard and section")
    @PutMapping(value = "/standards/{standard}/section/{section}",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyClassResponseDTO> updateClassByStandardAndSection(
            @PathVariable Standard standard,
            @PathVariable Section section,
            @RequestBody MyClassRequestDTO classRequestDTO) {
        return new ResponseEntity<>(classService.updateClassByStandardAndSection(classRequestDTO, standard, section), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes Class By Standard and Section", notes = "This method deletes class by standard and section")
    @DeleteMapping(value = "/standards/{standard}/section/{section}")
    public ResponseEntity<String> deleteClassByStandardAndSection(@PathVariable Standard standard,
                                                                  @PathVariable Section section) {
        String deletedClass = classService.deleteClassByStandardAndSection(standard, section);
        String message = String.format(DELETE_CLASS_SUCCESS, deletedClass);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
