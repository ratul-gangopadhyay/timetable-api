package com.myschool.timetable.models.dto.request;

import com.myschool.timetable.constants.enums.MyPeriod;
import com.myschool.timetable.constants.enums.Section;
import com.myschool.timetable.constants.enums.Standard;
import com.myschool.timetable.constants.enums.Subjects;
import com.myschool.timetable.constants.enums.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.myschool.timetable.constants.Messages.INVALID_SECTION;
import static com.myschool.timetable.constants.Messages.PERIOD_NOT_NULL;
import static com.myschool.timetable.constants.Messages.STANDARD_NOT_BLANK;
import static com.myschool.timetable.constants.Messages.SUBJECT_NOT_NULL;
import static com.myschool.timetable.constants.Messages.TEACHER_ID_NOT_BLANK;
import static com.myschool.timetable.constants.Messages.WEEKDAY_NOT_NULL;

@Builder
@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TimetableRequestDTO {
    @NotNull(message = STANDARD_NOT_BLANK)
    private Standard standard;
    @NotNull(message = INVALID_SECTION)
    private Section section;
    @NotNull(message = PERIOD_NOT_NULL)
    private MyPeriod period;
    @NotNull(message = WEEKDAY_NOT_NULL)
    private WeekDay weekDay;
    @NotBlank(message = TEACHER_ID_NOT_BLANK)
    private String teacherId;
    @NotNull(message = SUBJECT_NOT_NULL)
    private Subjects subject;
}
