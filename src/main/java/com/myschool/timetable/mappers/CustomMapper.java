package com.myschool.timetable.mappers;

import com.myschool.timetable.models.dto.request.MyClassRequestDTO;
import com.myschool.timetable.models.dto.request.TeacherRequestDTO;
import com.myschool.timetable.models.dto.request.TimetableRequestDTO;
import com.myschool.timetable.models.dto.response.MyClassResponseDTO;
import com.myschool.timetable.models.dto.response.TeacherResponseDTO;
import com.myschool.timetable.models.dto.response.TimetableResponseDTO;
import com.myschool.timetable.models.entity.MyClass;
import com.myschool.timetable.models.entity.Teacher;
import com.myschool.timetable.models.entity.Timetable;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomMapper {
    MyClassRequestDTO toRequestDTO(MyClassResponseDTO myClassResponseDTO);

    TeacherRequestDTO toRequestDTO(TeacherResponseDTO teacherResponseDTO);

    MyClassResponseDTO toResponseDTO(MyClass myClass);

    TeacherResponseDTO toResponseDTO(Teacher teacher);

    TimetableResponseDTO toResponseDTO(Timetable timetable);

    List<MyClassResponseDTO> toResponseDTOList(List<MyClass> myClasses);

    List<TeacherResponseDTO> toTeacherResponseDTOList(List<Teacher> teachers);

    List<TimetableResponseDTO> toTimetableResponseDTOList(List<Timetable> timetables);

    MyClass toModel(MyClassRequestDTO myClassRequestDTO);

    Teacher toModel(TeacherRequestDTO teacherRequestDTO);

    Timetable toModel(TimetableRequestDTO timetableRequestDTO);
}
