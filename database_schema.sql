-- SCOPE OF IMPROVEMENTS
-- -> somehow differentiate between the bcom sem wise and year wise. 
-- maybe we can achieve this by give different course name like bcom (sem-wise) and bcom(year-wise)

CREATE DATABASE college_results_db;

use college_results_db;

-- College table - CollegeName and address column together are unique to allow same college name together twice
-- Address cannot be empty
CREATE TABLE college (
    college_id INT AUTO_INCREMENT PRIMARY KEY,
    college_name VARCHAR(100) NOT NULL,
    director VARCHAR(100),
    email VARCHAR(100),
    website VARCHAR(100),
    address VARCHAR(255) NOT NULL,
    UNIQUE (college_name, address)
);

-- Courses table - 1 course like bcom can be offered in 2 systems like -> 6 sems or 3 years.
CREATE TABLE course (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    course_length INT NOT NULL,
    UNIQUE (course_name, course_length)
);

-- Semester table -- holds the possible semester values.
CREATE TABLE semester (
    sem_id INT AUTO_INCREMENT PRIMARY KEY,
    sem_no INT NOT NULL UNIQUE  -- Ensure Sem_No is unique across all semesters
);

-- This table manages the relationship between colleges and the courses they offer.
-- It ensures that each college can offer multiple courses, but a specific course can only be associated with a college once.
CREATE TABLE college_course (
    college_course_id INT AUTO_INCREMENT PRIMARY KEY,
    college_id INT NOT NULL,
    course_id INT NOT NULL,
    UNIQUE (college_id, course_id),
    FOREIGN KEY (college_id) REFERENCES college(college_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id)
);

-- Table to manage the many-to-many relationship between courses and semesters.
-- Ensures that each course is associated with specific semesters.
CREATE TABLE course_sem (
    course_sem_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    sem_id INT NOT NULL,
    UNIQUE (course_id, sem_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id),
    FOREIGN KEY (sem_id) REFERENCES semester(sem_id)
);

-- Table to store student details, including personal information, college affiliation, and course enrollment.
-- Unique constraints ensure each student's Roll_No and Enrollment_No are unique within their respective college.
CREATE TABLE student (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    father_name VARCHAR(100) NOT NULL,
    mother_name VARCHAR(100) NOT NULL,
    roll_no VARCHAR(100) NOT NULL,
    enrollment_no VARCHAR(100) NOT NULL,
    college_id INT NOT NULL,
    course_id INT NOT NULL,
    sem_id INT NOT NULL,
    photo VARCHAR(255),
    UNIQUE (college_id, roll_no),
    UNIQUE (college_id, enrollment_no),
    FOREIGN KEY (college_id) REFERENCES college(college_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id),
    FOREIGN KEY (sem_id) REFERENCES semester(sem_id)
);

-- Table to store information about different types of subjects offered within courses.
-- The 'Type' column specifies the category of the subject, including Foundation, Major, Minor, etc.
-- Types: Foundation_Course, Major_1, Major_2, Minor, Open, VOC (Vocational), Internship/Project
CREATE TABLE subject (
    subject_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL UNIQUE
);

-- Table: Optionals
-- Purpose: Stores information about various optional subjects or topics available for courses.
-- Optionals_Name: Name of the optional subject or topic. Ex- maths, phy, etc
-- unique combined key is required so that i can have max 2 entries of each subject, for example - physics with and without practical
CREATE TABLE optional (
    optional_id INT AUTO_INCREMENT PRIMARY KEY,
    optionals_name VARCHAR(100) NOT NULL,
    has_practical BOOLEAN NOT NULL DEFAULT FALSE
    UNIQUE (optionals_name, has_practical)
);

-- Table to manage different optional or specializations available for each subject.
-- Each subject can have multiple optional, and each optional is uniquely identified per subject.
CREATE TABLE subject_optional (
    subject_optional_id INT AUTO_INCREMENT PRIMARY KEY,
    subject_id INT NOT NULL,
    optional_id INT NOT NULL,
    UNIQUE (subject_id, optional_id),
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
    FOREIGN KEY (optional_id) REFERENCES optional(optional_id)
);

-- Table: College_Offers_Subject
-- Purpose: Maps the relationship between colleges, courses, semesters, and subject optional.
-- It tracks the maximum credits that can be obtained for subjects and practicals within each combination.
-- Unique constraint ensures no duplicate entries for the same combination of College, Course, Semester, and Subject Option.
CREATE TABLE college_offers_subject (
    college_offers_subject_id INT AUTO_INCREMENT PRIMARY KEY,
    college_id INT NOT NULL,
    course_id INT NOT NULL,
    sem_id INT NOT NULL,
    subject_optional_id INT NOT NULL,
    max_credits_subject INT NOT NULL,
    max_credits_practical INT,
    UNIQUE (college_id, course_id, sem_id, subject_optional_id),
    FOREIGN KEY (college_id) REFERENCES college(college_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id),
    FOREIGN KEY (sem_id) REFERENCES semester(sem_id),
    FOREIGN KEY (subject_optional_id) REFERENCES subject_optional(subject_optional_id)
);

-- Enrollment Table
-- Records the enrollment of students in subjects offered by their college.
-- Each entry associates a student with a specific subject offered by the college.
-- This table helps in managing and tracking student enrollments within different courses and subjects.
CREATE TABLE enrollment (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    college_offers_subject_id INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (college_offers_subject_id) REFERENCES college_offers_subject(college_offers_subject_id)
);

-- Table to store marks obtained by students in their enrolled subjects.
-- Columns:
-- Marks_Id: Auto-incrementing primary key for unique identification of each marks record.
-- Enrollment_Id: Foreign key linking to the Enrollment table, representing the student's enrollment in a subject.
-- Main_Marks: The main examination marks for the subject.
-- CCE_Marks: Continuous and Comprehensive Evaluation marks for the subject.
-- Practical_Marks: Marks for practicals (if applicable).
CREATE TABLE marks (
    marks_id INT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id INT NOT NULL,
    main_marks INT NOT NULL,
    cce_marks INT NOT NULL,
    practical_marks INT,
    FOREIGN KEY (enrollment_id) REFERENCES enrollment(enrollment_id)
);

-- Trigger: trg_before_enrollment_insert
-- Purpose: This trigger ensures that an enrollment entry is only inserted if the `College_Id`, `Course_Id`, and `Sem_Id` 
-- in the `Enrollment` table match those in the `Student` and `College_Offers_Subject` tables. It checks the consistency 
-- between student and subject offering details before allowing the insertion.
-- Details:
-- - Declares variables to hold `College_Id`, `Course_Id`, and `Sem_Id` values for both the student and the subject offering.
-- - Fetches these values from the `Student` and `College_Offers_Subject` tables.
-- - Compares these values and raises an error if there is a mismatch, preventing the insertion.
-- SQLSTATE '45000' indicates a generic error and is used to signal a constraint violation.
DELIMITER $$
CREATE TRIGGER trg_before_enrollment_insert
BEFORE INSERT ON enrollment
FOR EACH ROW
BEGIN
    DECLARE student_college_id INT;
    DECLARE student_course_id INT;
    DECLARE student_sem_id INT;
    DECLARE offers_college_id INT;
    DECLARE offers_course_id INT;
    DECLARE offers_sem_id INT;

    -- Fetch student details
    SELECT college_id, course_id, sem_id INTO student_college_id, student_course_id, student_sem_id
    FROM student
    WHERE id = NEW.student_id;

    -- Fetch College_Offers_Subject details
    SELECT college_id, course_id, sem_id INTO offers_college_id, offers_course_id, offers_sem_id
    FROM college_offers_subject
    WHERE id = NEW.college_offers_subject_id;

    -- Check for matching College_Id, Course_Id, Sem_Id
    IF NOT (student_college_id = offers_college_id AND
            student_course_id = offers_course_id AND
            student_sem_id = offers_sem_id) THEN
        SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Mismatch between Student and College_Offers_Subject details.';
    END IF;
END$$

DELIMITER ;