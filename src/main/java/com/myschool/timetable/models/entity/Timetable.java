package com.myschool.timetable.models.entity;

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
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "timetable")
public class Timetable {
    @Id
    private String id;
    private Standard standard;
    private Section section;
    private MyPeriod period;
    private WeekDay weekDay;
    private String teacherId;
    private String teacherName;
    private Subjects subject;
}
