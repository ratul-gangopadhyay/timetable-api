package com.myschool.timetable.models.dto.request;

import com.myschool.timetable.constants.enums.Subjects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static com.myschool.timetable.constants.Messages.AGE_MANDATORY;
import static com.myschool.timetable.constants.Messages.SALARY_MANDATORY;
import static com.myschool.timetable.constants.Messages.TEACHER_DOJ_MANDATORY;
import static com.myschool.timetable.constants.Messages.TEACHER_NAME_MANDATORY;
import static com.myschool.timetable.constants.Messages.TEACHER_SPECIALIZATIONS_MANDATORY;


@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class TeacherRequestDTO {
    @NotBlank(message = TEACHER_NAME_MANDATORY)
    private String teacherName;
    @NotNull(message = AGE_MANDATORY)
    private Integer age;
    @NotNull(message = SALARY_MANDATORY)
    private Double salary;
    @NotNull(message = TEACHER_DOJ_MANDATORY)
    private Date dateOfJoining;
    @NotNull(message = TEACHER_SPECIALIZATIONS_MANDATORY)
    private List<Subjects> specializations;
}
