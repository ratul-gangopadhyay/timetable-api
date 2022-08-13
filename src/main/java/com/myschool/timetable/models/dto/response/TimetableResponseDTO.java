package com.myschool.timetable.models.dto.response;

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

@Builder
@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TimetableResponseDTO {
    private Standard standard;
    private Section section;
    private MyPeriod period;
    private WeekDay weekDay;
    private String teacherId;
    private String teacherName;
    private Subjects subject;
}
