package com.myschool.timetable.service;

import com.myschool.timetable.constants.enums.Section;
import com.myschool.timetable.constants.enums.Standard;
import com.myschool.timetable.models.dto.request.MyClassRequestDTO;
import com.myschool.timetable.models.dto.response.MyClassResponseDTO;

import javax.validation.Valid;
import java.util.List;

public interface ClassService {
    MyClassResponseDTO insertClass(@Valid MyClassRequestDTO classRequestDTO);

    List<MyClassResponseDTO> fetchAll();

    MyClassResponseDTO fetchClassByStandardAndSection(Standard standard, Section section);

    MyClassResponseDTO updateClassByStandardAndSection(MyClassRequestDTO classRequestDTO, Standard standard, Section section);

    String deleteClassByStandardAndSection(Standard standard, Section section);
}
