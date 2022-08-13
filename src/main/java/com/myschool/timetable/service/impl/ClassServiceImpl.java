package com.myschool.timetable.service.impl;

import com.myschool.timetable.constants.enums.Section;
import com.myschool.timetable.constants.enums.Standard;
import com.myschool.timetable.exception.TimeTableException;
import com.myschool.timetable.mappers.CustomMapper;
import com.myschool.timetable.models.dto.request.MyClassRequestDTO;
import com.myschool.timetable.models.dto.response.MyClassResponseDTO;
import com.myschool.timetable.models.entity.MyClass;
import com.myschool.timetable.repository.ClassRepository;
import com.myschool.timetable.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

import static com.myschool.timetable.constants.Messages.CLASS_ALREADY_EXISTS;
import static com.myschool.timetable.constants.Messages.CLASS_STRENGTH_RANGE_ERROR;
import static com.myschool.timetable.constants.Messages.INVALID_CLASS;

@Service
@Transactional
public class ClassServiceImpl implements ClassService {

    @Autowired
    private CustomMapper customMapper;
    @Autowired
    private ClassRepository classRepository;

    @Override
    public MyClassResponseDTO insertClass(@Valid MyClassRequestDTO classRequestDTO) {
        validateClassRequestDTO(classRequestDTO);
        return customMapper.toResponseDTO(classRepository.insert(customMapper.toModel(classRequestDTO)));
    }

    @Override
    public List<MyClassResponseDTO> fetchAll() {
        return customMapper.toResponseDTOList(classRepository.findAll());
    }

    @Override
    public MyClassResponseDTO fetchClassByStandardAndSection(Standard standard, Section section) {
        return customMapper.toResponseDTO(getMyClass(standard, section));
    }


    @Override
    public MyClassResponseDTO updateClassByStandardAndSection(MyClassRequestDTO classRequestDTO, Standard standard, Section section) {
        MyClass existingClass = getMyClass(standard, section);
        if (classRequestDTO.getClassStrength() != null) {
            validateEditableFields(classRequestDTO);
            existingClass.setClassStrength(classRequestDTO.getClassStrength());
        }
        return customMapper.toResponseDTO(classRepository.save(existingClass));
    }

    @Override
    public String deleteClassByStandardAndSection(Standard standard, Section section) {
        MyClass existingClass = getMyClass(standard, section);
        classRepository.delete(existingClass);
        return existingClass.getStandard().concat(existingClass.getSection().toString());
    }

    private void validateEditableFields(MyClassRequestDTO classRequestDTO) {
        Integer classStrength = classRequestDTO.getClassStrength();
        if (classStrength > 70 || classStrength < 50)
            throw new TimeTableException(CLASS_STRENGTH_RANGE_ERROR, HttpStatus.BAD_REQUEST);
    }

    private MyClass getMyClass(Standard standard, Section section) {
        return classRepository
                .findByStandardAndSection(standard, section)
                .orElseThrow(
                        () -> new TimeTableException(String.format(INVALID_CLASS, standard, section),
                                HttpStatus.NOT_FOUND));
    }

    /**
     * Validates whether the class request dto is valid or not. <br/>
     * Checks if there is any class already present with the supplied standard and section combination
     *
     * @param classRequestDTO The class request dto to be validated
     */
    private void validateClassRequestDTO(MyClassRequestDTO classRequestDTO) {
        if (classRepository.findByStandardAndSection(classRequestDTO.getStandard(),
                classRequestDTO.getSection()).isPresent())
            throw new TimeTableException(String.format(CLASS_ALREADY_EXISTS,
                    classRequestDTO.getStandard(),
                    classRequestDTO.getSection()),
                    HttpStatus.BAD_REQUEST);

    }
}
