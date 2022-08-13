package com.myschool.timetable.models.entity;

import com.myschool.timetable.constants.enums.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "classes")
public class MyClass {
    @Id
    private String id;
    private String standard;
    private Integer classStrength;
    private Section section;
}
