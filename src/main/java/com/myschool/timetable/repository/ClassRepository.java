package com.myschool.timetable.repository;

import com.myschool.timetable.constants.enums.Section;
import com.myschool.timetable.constants.enums.Standard;
import com.myschool.timetable.models.entity.MyClass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassRepository extends MongoRepository<MyClass, String> {
    @Query("{standard: ?0, section: ?1}")
    Optional<MyClass> findByStandardAndSection(Standard standard, Section section);
}
