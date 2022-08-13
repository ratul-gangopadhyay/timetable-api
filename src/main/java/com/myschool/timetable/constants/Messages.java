package com.myschool.timetable.constants;

import java.util.Locale;

public class Messages {

    public static final String STANDARD_NOT_BLANK = "Class standard can't be blank or null";
    public static final String CLASS_STRENGTH_RANGE_ERROR = "Class strength must be between 50 and 70";
    public static final String CLASS_ALREADY_EXISTS = "Class %s%s already exists";
    public static final String INVALID_SECTION = "Sections has to provided to add a class. Please add the sections";
    public static final String INVALID_STANDARD = "Standard entered is invalid." +
            "Standard should be between I to XII";
    public static final String INVALID_CLASS = "Class %s%s is currently not present";
    public static final String DELETE_CLASS_SUCCESS = "Successfully deleted class %s";

    public static final String TEACHER_NAME_MANDATORY = "Please provide Teacher Name";
    public static final String TEACHER_DOJ_MANDATORY = "Please provide Teacher's Date Of Joining";
    public static final String TEACHER_SPECIALIZATIONS_MANDATORY = "Please mention specializations subjects for the teacher";
    public static final String SALARY_MANDATORY = "Please provide Teacher's Salary";
    public static final String AGE_MANDATORY = "Please provide Teacher's Age";
    public static final String DELETE_TEACHER_SUCCESS = "Successfully deleted Teacher with id: %s";
    public static final Integer MAX_TEACHERS_LIMIT = 100;
    public static final String MAX_TEACHERS_LIMIT_REACHED = "No vacancy for new Teachers";
    public static final String TEACHER_NOT_FOUND = "No Teacher found for given id: %s";

    public static final String PERIOD_NOT_NULL = "Please enter the period in which you want to add the teacher";
    public static final String WEEKDAY_NOT_NULL = "Please enter the weekday in which you want to add the teacher";
    public static final String TEACHER_ID_NOT_BLANK = "Please provide the Teacher's Id";
    public static final String SUBJECT_NOT_NULL = "Please enter the subject name for the slot";
    public static final String SLOT_ALREADY_OCCUPIED = "%sDAY %s PERIOD is already occupied for Class %s%s";
    public static final String SLOT_NOT_FOUND = "No slot found for %sDAY %s PERIOD for Class %s%s";
    public static final String TEACHER_OCCUPIED = "Teacher with id: %s is busy in some other class on %sDAY %s PERIOD";
    public static final String SUBJECT_MISMATCH = "Teacher with id: %s dosen't teach %s";
    public static final String TIMETABLE_SLOT_DELETE_SUCCESS = "Successfully Deleted Slot Details For Class: %s%s %sDAY %s PERIOD";

    public static final String CSV_ROUTINE_FILENAME = "\"Routine_%s%s.csv\"";
    public static final String CSV_WRITING_ERROR = "Error while generating csv format";
    public static final String ROUTINE_SLOT_FORMAT = "%s"+System.lineSeparator()+"%s";


    private Messages() {

    }
}
