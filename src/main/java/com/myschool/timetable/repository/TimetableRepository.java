package com.myschool.timetable.repository;

import com.myschool.timetable.constants.enums.MyPeriod;
import com.myschool.timetable.constants.enums.Section;
import com.myschool.timetable.constants.enums.Standard;
import com.myschool.timetable.constants.enums.WeekDay;
import com.myschool.timetable.models.entity.Timetable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimetableRepository extends MongoRepository<Timetable, String> {
    @Query("{standard: ?0, section: ?1, weekDay: ?2, period: ?3}")
    Optional<Timetable> findByStandardSectionWeekdayPeriod(Standard standard, Section section, WeekDay weekDay, MyPeriod period);

    @Query("{weekDay: ?0, period: ?1, teacherId: ?2}")
    Optional<Timetable> findByWeekdayAndPeriodAndTeacherId(WeekDay weekDay, MyPeriod period, String teacherId);

    List<Timetable> findAllByStandardAndSection(Standard standard, Section section);
}
