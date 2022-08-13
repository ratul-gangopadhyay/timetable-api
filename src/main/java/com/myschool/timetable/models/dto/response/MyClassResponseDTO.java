package com.myschool.timetable.models.dto.response;

import com.myschool.timetable.constants.enums.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class MyClassResponseDTO {
    private String id;
    private String standard;
    private Integer classStrength;
    private Section section;
}
