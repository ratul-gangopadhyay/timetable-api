package com.myschool.timetable.api.controller;

import com.myschool.timetable.constants.enums.MyPeriod;
import com.myschool.timetable.constants.enums.Section;
import com.myschool.timetable.constants.enums.Standard;
import com.myschool.timetable.constants.enums.WeekDay;
import com.myschool.timetable.models.dto.request.TimetableRequestDTO;
import com.myschool.timetable.models.dto.response.TimetableResponseDTO;
import com.myschool.timetable.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.myschool.timetable.constants.Messages.CSV_ROUTINE_FILENAME;

@RestController
@RequestMapping("/slot")
public class TimetableController {
    @Autowired
    private TimetableService timetableService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimetableResponseDTO> addSlotDetails(@Valid @RequestBody TimetableRequestDTO timetableRequestDTO) {
        return new ResponseEntity<>(timetableService.addSlotDetails(timetableRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TimetableResponseDTO>> fetchAllSlots() {
        return new ResponseEntity<>(timetableService.fetchAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/standard/{standard}/section/{section}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TimetableResponseDTO>> fetchClassRoutine(
            @PathVariable Standard standard,
            @PathVariable Section section
    ) {
        return new ResponseEntity<>(timetableService.fetchRoutine(standard, section), HttpStatus.OK);
    }

    @GetMapping(value = "/standard/{standard}/section/{section}/weekday/{weekDay}/period/{period}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimetableResponseDTO> fetchSlotDetails(
            @PathVariable Standard standard,
            @PathVariable Section section,
            @PathVariable WeekDay weekDay,
            @PathVariable MyPeriod period) {
        return new ResponseEntity<>(timetableService.fetchSlotDetails(
                standard,
                section,
                weekDay,
                period), HttpStatus.OK);
    }

    @PutMapping(
            value = "/standard/{standard}/section/{section}/weekday/{weekDay}/period/{period}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimetableResponseDTO> updateSlotDetails(
            @PathVariable Standard standard,
            @PathVariable Section section,
            @PathVariable WeekDay weekDay,
            @PathVariable MyPeriod period,
            @Valid @RequestBody TimetableRequestDTO timetableRequestDTO) {
        return new ResponseEntity<>(timetableService.updateSlotDetails(
                standard,
                section,
                weekDay,
                period,
                timetableRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/standard/{standard}/section/{section}/weekday/{weekDay}/period/{period}")
    public ResponseEntity<String> deleteSlotDetails(
            @PathVariable Standard standard,
            @PathVariable Section section,
            @PathVariable WeekDay weekDay,
            @PathVariable MyPeriod period) {
        return new ResponseEntity<>(timetableService.deleteSlotDetails(
                standard,
                section,
                weekDay,
                period), HttpStatus.OK);
    }
    @GetMapping(value = "generate-csv-routine/standard/{standard}/section/{section}", produces = "text/csv")
    public void generateCsvClassRoutine(
            HttpServletResponse servletResponse,
            @PathVariable Standard standard,
            @PathVariable Section section
    ) throws IOException {
        servletResponse.setContentType("text/csv");
        String fileName = String.format(CSV_ROUTINE_FILENAME, standard, section);
        servletResponse.addHeader("Content-Disposition",String.format("attachment; filename=%s", fileName));
        timetableService.writeClassRoutineToCsv(servletResponse.getWriter(), standard, section);
    }

}
