package com.myschool.timetable.service;

import com.myschool.timetable.constants.enums.MyPeriod;
import com.myschool.timetable.constants.enums.Section;
import com.myschool.timetable.constants.enums.Standard;
import com.myschool.timetable.constants.enums.WeekDay;
import com.myschool.timetable.models.dto.request.TimetableRequestDTO;
import com.myschool.timetable.models.dto.response.TimetableResponseDTO;

import java.io.PrintWriter;
import java.util.List;

public interface TimetableService {
    TimetableResponseDTO addSlotDetails(TimetableRequestDTO timetableRequestDTO);

    TimetableResponseDTO updateSlotDetails(
            Standard standard,
            Section section,
            WeekDay weekDay,
            MyPeriod period,
            TimetableRequestDTO timetableRequestDTO);

    TimetableResponseDTO fetchSlotDetails(Standard standard, Section section, WeekDay weekDay, MyPeriod period);

    List<TimetableResponseDTO> fetchAll();

    List<TimetableResponseDTO> fetchRoutine(Standard standard, Section section);

    String deleteSlotDetails(Standard standard, Section section, WeekDay weekDay, MyPeriod period);

    void writeClassRoutineToCsv(PrintWriter writer, Standard standard, Section section);
}
