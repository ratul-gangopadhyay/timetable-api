package com.myschool.timetable.service;

import com.myschool.timetable.models.dto.request.TeacherRequestDTO;
import com.myschool.timetable.models.dto.response.TeacherResponseDTO;

import java.util.List;

public interface TeacherService {
    TeacherResponseDTO insertTeacher(TeacherRequestDTO teacherRequestDTO);

    List<TeacherResponseDTO> fetchAll();

    TeacherResponseDTO fetchById(String id);

    TeacherResponseDTO updateById(String id, TeacherRequestDTO teacherRequestDTO);

    String deleteById(String id);
}
