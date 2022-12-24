package com.myschool.timetable.models.dto.response;

import com.myschool.timetable.constants.enums.MyPeriod;
import com.myschool.timetable.constants.enums.Section;
import com.myschool.timetable.constants.enums.Standard;
import com.myschool.timetable.constants.enums.WeekDay;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@Builder
@ToString
public class RoutineResponseDTO {
    private Standard standard;
    private Section section;
    private Map<WeekDay, Map<MyPeriod, TimetableResponseDTO>> routineMap;
}
