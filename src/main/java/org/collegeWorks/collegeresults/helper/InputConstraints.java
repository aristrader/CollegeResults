package org.collegeWorks.collegeresults.helper;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class InputConstraints {

  public static final Map<String, String> ALLOWED_SUBJECT_TYPES_MAP = new HashMap<>();
  private static final Map<String, AllowedCourses> allowedCoursesMap = new HashMap<>();
  private static final Map<Integer, AllowedSemesters> allowedSemestersMap = new HashMap<>();

  static {
    for (AllowedCourses course : AllowedCourses.values()) {
      allowedCoursesMap.put(course.name().toUpperCase(), course);
    }

    for (AllowedSemesters sem : AllowedSemesters.values()) {
      allowedSemestersMap.put(sem.getSemester(), sem);
    }
  }

  static {
    ALLOWED_SUBJECT_TYPES_MAP.put("FOUNDATION_COURSE", "FOUNDATION_COURSE");
    ALLOWED_SUBJECT_TYPES_MAP.put("MAJOR_1", "MAJOR_1");
    ALLOWED_SUBJECT_TYPES_MAP.put("MAJOR_2", "MAJOR_2");
    ALLOWED_SUBJECT_TYPES_MAP.put("MINOR", "MINOR");
    ALLOWED_SUBJECT_TYPES_MAP.put("OPEN", "OPEN");
    ALLOWED_SUBJECT_TYPES_MAP.put("VOC", "VOC");
    ALLOWED_SUBJECT_TYPES_MAP.put("PROJECT/INTERNSHIP", "PROJECT/INTERNSHIP");
  }

  public static boolean isValidCourse(String courseName) {
    return allowedCoursesMap.containsKey(courseName.toUpperCase());
  }

  public static boolean isValidSemester(int semester) {
    return allowedSemestersMap.containsKey(semester);
  }

  public static boolean isValidSubjectType(String subjectType) {
    return ALLOWED_SUBJECT_TYPES_MAP.containsKey(subjectType.toUpperCase());
  }

  public enum AllowedCourses {
    BCOM, BSC, BA;
  }

  @Getter
  public enum AllowedSemesters {
    SEM_1(1), SEM_2(2), SEM_3(3), SEM_4(4), SEM_5(5), SEM_6(6), SEM_7(7), SEM_8(8);

    private final int semester;

    AllowedSemesters(int semester) {
      this.semester = semester;
    }

  }
}
