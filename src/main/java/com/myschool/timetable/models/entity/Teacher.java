package com.myschool.timetable.models.entity;

import com.myschool.timetable.constants.enums.Subjects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "teachers")
public class Teacher {
    @Id
    private String id;
    private String teacherName;
    private Integer age;
    private Double salary;
    private Date dateOfJoining;
    private List<Subjects> specializations;
}
