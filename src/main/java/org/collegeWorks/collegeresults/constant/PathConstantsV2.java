package org.collegeWorks.collegeresults.constant;

public class PathConstantsV2 {

  public static final String V2 = "/v2";
  public static final String COLLEGES = "/colleges";
  public static final String TEACHERS = "/teachers";
  public static final String COURSES_DETAILS = "/course-details";
  public static final String STUDENTS = "/students";
  public static final String SUBJECT_DETAILS = "/subject-details";
  public static final String ENROLLMENT_AND_MARKS = "/enrollment-and-marks";
  public static final String COLLEGES_PATH_V2 = V2 + COLLEGES;
  public static final String TEACHERS_PATH_V2 = V2 + TEACHERS;
  public static final String COURSES_DETAILS_PATH_V2 = V2 + COURSES_DETAILS;
  public static final String STUDENTS_PATH_V2 = V2 + STUDENTS;
  public static final String SUBJECT_DETAILS_PATH_V2 = V2 + SUBJECT_DETAILS;
  public static final String ENROLLMENT_AND_MARKS_V2 = V2 + ENROLLMENT_AND_MARKS;
  public static final String ADD = "/add";
  public static final String ADD_MARKS = "/addMarks";
  public static final String GET = "/get";
  public static final String GET_BY_ID = "/get/id/{id}";
  public static final String GET_BY_NAME = "/get/name/{name}";
  public static final String GET_BY_COURSE_NAME = "/get/name/{courseName}";
  public static final String GET_BY_COURSE_DETAILS_ID = "/getByCourseDetailsId/{courseDetailsId}";
  public static final String GET_BY_COURSE_DETAILS_ID_AND_ROLL_NO = "/getByCourseDetailsIdAndRollNo";
  public static final String GET_BY_COURSE_DETAILS_ID_AND_ENROLLMENT_NO = "/getByCourseDetailsIdAndEnrollmentNo";
  public static final String GET_ALL = "/getAll";
  public static final String GET_BY_PHONE = "/getByPhone/{phoneNo}";
  public static final String GET_BY_EMAIL = "/getByEmail/{email}";
  public static final String GET_SUBJECTS_BY_COURSE = "/course/{courseDetailsId}";
  public static final String GET_SUBJECTS_BY_COURSE_AND_TYPE = "/course/{courseDetailsId}/type/{subjectType}";
  public static final String GET_SUBJECTS_BY_COURSE_TYPE_AND_OPTIONS = "/course/{courseDetailsId}/type/{subjectType}/options/{optionsName}";
  public static final String GET_SUBJECTS_BY_TEACHER_ID = "/teacher/{teacherId}";
  public static final String GET_BY_STUDENT_ID = "/getByStudentId/{studentId}";
  public static final String GET_BY_SUBJECT_DETAILS_ID = "/getBySubjectDetailsId/{subjectDetailsId}";
  public static final String GET_BY_STUDENT_AND_SUBJECT_ID = "/getByStudentAndSubjectId/{studentId}/{subjectDetailsId}";
}
