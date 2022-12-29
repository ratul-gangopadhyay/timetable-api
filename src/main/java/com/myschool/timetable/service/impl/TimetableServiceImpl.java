package com.myschool.timetable.service.impl;

import com.myschool.timetable.constants.Messages;
import com.myschool.timetable.constants.enums.MyPeriod;
import com.myschool.timetable.constants.enums.Section;
import com.myschool.timetable.constants.enums.Standard;
import com.myschool.timetable.constants.enums.Subjects;
import com.myschool.timetable.constants.enums.WeekDay;
import com.myschool.timetable.exception.TimeTableException;
import com.myschool.timetable.mappers.CustomMapper;
import com.myschool.timetable.models.dto.request.TimetableRequestDTO;
import com.myschool.timetable.models.dto.response.RoutineResponseDTO;
import com.myschool.timetable.models.dto.response.TimetableResponseDTO;
import com.myschool.timetable.models.entity.Teacher;
import com.myschool.timetable.models.entity.Timetable;
import com.myschool.timetable.repository.ClassRepository;
import com.myschool.timetable.repository.TeacherRepository;
import com.myschool.timetable.repository.TimetableRepository;
import com.myschool.timetable.service.TimetableService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.myschool.timetable.constants.Messages.CSV_WRITING_ERROR;
import static com.myschool.timetable.constants.Messages.INVALID_CLASS;
import static com.myschool.timetable.constants.Messages.ROUTINE_SLOT_FORMAT;
import static com.myschool.timetable.constants.Messages.SLOT_ALREADY_OCCUPIED;
import static com.myschool.timetable.constants.Messages.SLOT_NOT_FOUND;
import static com.myschool.timetable.constants.Messages.SUBJECT_MISMATCH;
import static com.myschool.timetable.constants.Messages.TEACHER_NOT_FOUND;
import static com.myschool.timetable.constants.Messages.TEACHER_OCCUPIED;

@Service
@Transactional
public class TimetableServiceImpl implements TimetableService {
    @Autowired
    private CustomMapper mapper;
    @Autowired
    private TimetableRepository timetableRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public TimetableResponseDTO addSlotDetails(TimetableRequestDTO timetableRequestDTO) {
        validateClass(timetableRequestDTO.getStandard(), timetableRequestDTO.getSection());
        validateSlot(
                timetableRequestDTO.getStandard(),
                timetableRequestDTO.getSection(),
                timetableRequestDTO.getWeekDay(),
                timetableRequestDTO.getPeriod());
        validateTeachersAvailability(
                timetableRequestDTO.getWeekDay(),
                timetableRequestDTO.getPeriod(),
                timetableRequestDTO.getTeacherId());
        validateSubject(timetableRequestDTO.getSubject(), timetableRequestDTO.getTeacherId());
        Timetable newTimetable = mapper.toModel(timetableRequestDTO);
        newTimetable.setTeacherName(getTeacherNameFromDB(timetableRequestDTO.getTeacherId()));
        return mapper.toResponseDTO(timetableRepository.insert(newTimetable));
    }


    @Override
    public TimetableResponseDTO updateSlotDetails(
            Standard standard,
            Section section,
            WeekDay weekDay,
            MyPeriod period,
            TimetableRequestDTO timetableRequestDTO) {

        validateClass(standard, section);
        Timetable existingSlot = validateSlotAvailabilityAndReturnIfPresent(standard, section, weekDay, period);
        if (!existingSlot.getTeacherId().equals(timetableRequestDTO.getTeacherId()))
            validateTeachersAvailability(weekDay, period, timetableRequestDTO.getTeacherId());
        validateSubject(timetableRequestDTO.getSubject(), timetableRequestDTO.getTeacherId());
        Timetable modifiedSlot = Timetable.builder()
                .id(existingSlot.getId())
                .teacherId(timetableRequestDTO.getTeacherId())
                .teacherName(getTeacherNameFromDB(timetableRequestDTO.getTeacherId()))
                .period(period)
                .section(section)
                .standard(standard)
                .weekDay(weekDay)
                .subject(timetableRequestDTO.getSubject()).build();
        return mapper.toResponseDTO(timetableRepository.save(modifiedSlot));
    }

    @Override
    public TimetableResponseDTO fetchSlotDetails(Standard standard, Section section, WeekDay weekDay, MyPeriod period) {
        return mapper
                .toResponseDTO(timetableRepository
                        .findByStandardSectionWeekdayPeriod(standard, section, weekDay, period)
                        .orElseThrow(() -> new TimeTableException(
                                String.format(SLOT_NOT_FOUND, weekDay, period, standard, section),
                                HttpStatus.NOT_FOUND)));
    }

    @Override
    public List<TimetableResponseDTO> fetchAll() {
        return mapper.toTimetableResponseDTOList(timetableRepository.findAll());
    }

    @Override
    public List<TimetableResponseDTO> fetchRoutine(Standard standard, Section section) {
        validateClass(standard, section);
        return mapper.toTimetableResponseDTOList(timetableRepository.findAllByStandardAndSection(standard, section));
    }

    @Override
    public RoutineResponseDTO fetchRoutineInMap(Standard standard, Section section) {
        Map<WeekDay, Map<MyPeriod, TimetableResponseDTO>> routineMap = new HashMap<>();
        List<TimetableResponseDTO> timetables = fetchRoutine(standard, section);
        for (WeekDay day : WeekDay.values()) {
            Map<MyPeriod, TimetableResponseDTO> map = timetables
                    .stream()
                    .filter(timetable -> timetable.getWeekDay().equals(day))
                    .collect(Collectors.toMap(TimetableResponseDTO::getPeriod, entry -> entry));
            routineMap.put(day, map);
        }
        return RoutineResponseDTO.builder()
                .section(section)
                .standard(standard)
                .routineMap(routineMap).build();
    }

    @Override
    public String deleteSlotDetails(Standard standard, Section section, WeekDay weekDay, MyPeriod period) {
        Timetable timetableToBeDeleted = timetableRepository.
                findByStandardSectionWeekdayPeriod(standard, section, weekDay, period)
                .orElseThrow(() -> new TimeTableException(String.format(SLOT_NOT_FOUND,
                        weekDay,
                        period,
                        standard,
                        section), HttpStatus.NOT_FOUND));
        timetableRepository.delete(timetableToBeDeleted);
        return String.format(Messages.TIMETABLE_SLOT_DELETE_SUCCESS, standard, section, weekDay, period);
    }

    @Override
    public void writeClassRoutineToCsv(PrintWriter writer, Standard standard, Section section) {
        List<Timetable> timetables = timetableRepository.findAllByStandardAndSection(standard, section);
        Map<MyPeriod, String> mondayMap = timetables.stream().filter(slot -> slot.getWeekDay().equals(WeekDay.MON))
                .collect(Collectors.toMap(Timetable::getPeriod,
                        timetable -> String.format(ROUTINE_SLOT_FORMAT, timetable.getSubject(), timetable.getTeacherName())));
        Map<MyPeriod, String> tuesdayMap = timetables.stream().filter(slot -> slot.getWeekDay().equals(WeekDay.TUE))
                .collect(Collectors.toMap(Timetable::getPeriod,
                        timetable -> String.format(ROUTINE_SLOT_FORMAT, timetable.getSubject(), timetable.getTeacherName())));
        Map<MyPeriod, String> wednesdayMap = timetables.stream().filter(slot -> slot.getWeekDay().equals(WeekDay.WED))
                .collect(Collectors.toMap(Timetable::getPeriod,
                        timetable -> String.format(ROUTINE_SLOT_FORMAT, timetable.getSubject(), timetable.getTeacherName())));
        Map<MyPeriod, String> thursdayMap = timetables.stream().filter(slot -> slot.getWeekDay().equals(WeekDay.THU))
                .collect(Collectors.toMap(Timetable::getPeriod,
                        timetable -> String.format(ROUTINE_SLOT_FORMAT, timetable.getSubject(), timetable.getTeacherName())));
        Map<MyPeriod, String> fridayMap = timetables.stream().filter(slot -> slot.getWeekDay().equals(WeekDay.FRI))
                .collect(Collectors.toMap(Timetable::getPeriod,
                        timetable -> String.format(ROUTINE_SLOT_FORMAT, timetable.getSubject(), timetable.getTeacherName())));


        String[] headers = new String[]{"DAY/PERIOD", "FIRST", "SECOND", "THIRD", "FOURTH", "FIFTH"};
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.Builder.create().setHeader(headers).build())) {
            csvPrinter.printRecord(
                    WeekDay.MON,
                    mondayMap.get(MyPeriod.FIRST),
                    mondayMap.get(MyPeriod.SECOND),
                    mondayMap.get(MyPeriod.THIRD),
                    mondayMap.get(MyPeriod.FOURTH),
                    mondayMap.get(MyPeriod.FIFTH));
            csvPrinter.printRecord(
                    WeekDay.TUE,
                    tuesdayMap.get(MyPeriod.FIRST),
                    tuesdayMap.get(MyPeriod.SECOND),
                    tuesdayMap.get(MyPeriod.THIRD),
                    tuesdayMap.get(MyPeriod.FOURTH),
                    tuesdayMap.get(MyPeriod.FIFTH));
            csvPrinter.printRecord(
                    WeekDay.WED,
                    wednesdayMap.get(MyPeriod.FIRST),
                    wednesdayMap.get(MyPeriod.SECOND),
                    wednesdayMap.get(MyPeriod.THIRD),
                    wednesdayMap.get(MyPeriod.FOURTH),
                    wednesdayMap.get(MyPeriod.FIFTH));
            csvPrinter.printRecord(
                    WeekDay.THU,
                    thursdayMap.get(MyPeriod.FIRST),
                    thursdayMap.get(MyPeriod.SECOND),
                    thursdayMap.get(MyPeriod.THIRD),
                    thursdayMap.get(MyPeriod.FOURTH),
                    thursdayMap.get(MyPeriod.FIFTH));
            csvPrinter.printRecord(
                    WeekDay.FRI,
                    fridayMap.get(MyPeriod.FIRST),
                    fridayMap.get(MyPeriod.SECOND),
                    fridayMap.get(MyPeriod.THIRD),
                    fridayMap.get(MyPeriod.FOURTH),
                    fridayMap.get(MyPeriod.FIFTH));


        } catch (IOException e) {
            throw new TimeTableException(CSV_WRITING_ERROR, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


    private Timetable validateSlotAvailabilityAndReturnIfPresent(Standard standard, Section section, WeekDay weekDay, MyPeriod period) {
        return timetableRepository.
                findByStandardSectionWeekdayPeriod(standard, section, weekDay, period)
                .orElseThrow(() -> new TimeTableException(String.format(SLOT_NOT_FOUND,
                        weekDay,
                        period,
                        standard,
                        section), HttpStatus.NOT_FOUND));
    }

    private void validateSlot(Standard standard, Section section, WeekDay weekDay, MyPeriod period) {

        if (timetableRepository.findByStandardSectionWeekdayPeriod(standard, section, weekDay, period).isPresent())
            throw new TimeTableException(String.format(SLOT_ALREADY_OCCUPIED, weekDay, period, standard, section),
                    HttpStatus.BAD_REQUEST);
    }

    private void validateClass(Standard standard, Section section) {
        if (classRepository.findByStandardAndSection(standard, section).isEmpty())
            throw new TimeTableException(String.format(INVALID_CLASS, standard, section), HttpStatus.NOT_FOUND);
    }

    private void validateTeachersAvailability(WeekDay weekDay, MyPeriod period, String teacherId) {
        getTeacherFromDB(teacherId);
        timetableRepository.findByWeekdayAndPeriodAndTeacherId(weekDay, period, teacherId).ifPresent(timetable -> {
            throw new TimeTableException(String.format(TEACHER_OCCUPIED, timetable.getTeacherName(), weekDay, period), HttpStatus.BAD_REQUEST);
        });
    }

    private Teacher getTeacherFromDB(String teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TimeTableException(String.format(TEACHER_NOT_FOUND, teacherId),
                        HttpStatus.NOT_FOUND));
    }

    private void validateSubject(Subjects subject, String teacherId) {
        Teacher teacher = getTeacherFromDB(teacherId);
        if (!teacher.getSpecializations().contains(subject))
            throw new TimeTableException(String.format(SUBJECT_MISMATCH, teacherId, subject),
                    HttpStatus.BAD_REQUEST);
    }

    private String getTeacherNameFromDB(String id) {
        return getTeacherFromDB(id).getTeacherName();
    }
}
