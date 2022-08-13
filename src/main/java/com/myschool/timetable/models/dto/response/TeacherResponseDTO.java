package com.myschool.timetable.models.dto.response;

import com.myschool.timetable.constants.enums.Subjects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;


@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class TeacherResponseDTO {
    private String id;
    private String teacherName;
    private Integer age;
    private Double salary;
    private Date dateOfJoining;
    private List<Subjects> specializations;
}
