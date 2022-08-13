package com.myschool.timetable.service.impl;

import com.myschool.timetable.exception.TimeTableException;
import com.myschool.timetable.mappers.CustomMapper;
import com.myschool.timetable.models.dto.request.TeacherRequestDTO;
import com.myschool.timetable.models.dto.response.TeacherResponseDTO;
import com.myschool.timetable.models.entity.Teacher;
import com.myschool.timetable.repository.TeacherRepository;
import com.myschool.timetable.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.myschool.timetable.constants.Messages.MAX_TEACHERS_LIMIT;
import static com.myschool.timetable.constants.Messages.MAX_TEACHERS_LIMIT_REACHED;
import static com.myschool.timetable.constants.Messages.TEACHER_NOT_FOUND;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CustomMapper mapper;

    @Override
    public TeacherResponseDTO insertTeacher(TeacherRequestDTO teacherRequestDTO) {
        if (teacherRepository.findAll().size() >= MAX_TEACHERS_LIMIT)
            throw new TimeTableException(MAX_TEACHERS_LIMIT_REACHED, HttpStatus.BAD_REQUEST);
        return mapper.toResponseDTO(teacherRepository.insert(mapper.toModel(teacherRequestDTO)));
    }

    @Override
    public List<TeacherResponseDTO> fetchAll() {
        return mapper.toTeacherResponseDTOList(teacherRepository.findAll());
    }

    @Override
    public TeacherResponseDTO fetchById(String id) {
        return mapper.toResponseDTO(getTeacherById(id));
    }

    @Override
    public TeacherResponseDTO updateById(String id, TeacherRequestDTO teacherRequestDTO) {
        Teacher existingTeacher = getTeacherById(id);
        Teacher modifiedTeacher = Teacher.builder()
                .teacherName(teacherRequestDTO.getTeacherName())
                .age(teacherRequestDTO.getAge())
                .dateOfJoining(teacherRequestDTO.getDateOfJoining())
                .salary(teacherRequestDTO.getSalary())
                .specializations(teacherRequestDTO.getSpecializations())
                .id(existingTeacher.getId()).build();
        return mapper.toResponseDTO(teacherRepository.save(modifiedTeacher));
    }

    @Override
    public String deleteById(String id) {
        String teacherDeleted = getTeacherById(id).getId();
        teacherRepository.deleteById(teacherDeleted);
        return teacherDeleted;
    }

    private Teacher getTeacherById(String id) {
        return teacherRepository
                .findById(id)
                .orElseThrow(
                        () -> new TimeTableException(
                                String.format(TEACHER_NOT_FOUND, id), HttpStatus.NOT_FOUND));
    }
}
