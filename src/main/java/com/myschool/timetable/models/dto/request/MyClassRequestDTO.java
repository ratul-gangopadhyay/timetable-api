package com.myschool.timetable.models.dto.request;

import com.myschool.timetable.constants.enums.Section;
import com.myschool.timetable.constants.enums.Standard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

import static com.myschool.timetable.constants.Messages.CLASS_STRENGTH_RANGE_ERROR;
import static com.myschool.timetable.constants.Messages.INVALID_SECTION;
import static com.myschool.timetable.constants.Messages.STANDARD_NOT_BLANK;


@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class MyClassRequestDTO {
    @NotNull(message = STANDARD_NOT_BLANK)
    private Standard standard;
    @Range(max = 70, min = 50, message = CLASS_STRENGTH_RANGE_ERROR)
    private Integer classStrength;
    @NotNull(message = INVALID_SECTION)
    private Section section;
}
