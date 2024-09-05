-- SCOPE OF IMPROVEMENTS
-- -> somehow differentiate between the bcom sem wise and year wise. 
-- maybe we can achieve this by give different course name like bcom (sem-wise) and bcom(year-wise)

CREATE DATABASE CollegeResultsDB;

use CollegeResultsDB;

-- College table - CollegeName and address column together are unique to allow same college name together twice
-- Address cannot be empty
CREATE TABLE College (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    College_Name VARCHAR(100) NOT NULL,
    Director VARCHAR(100),
    Email VARCHAR(100),
    Website VARCHAR(100),
    Address VARCHAR(255) NOT NULL,
    UNIQUE (College_Name, Address)
);

-- Courses table - 1 course like bcom can be offered in 2 systems like -> 6 sems or 3 years.
CREATE TABLE Course (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Course_Name VARCHAR(100) NOT NULL,
    Course_Length INT NOT NULL,
    UNIQUE (Course_Name, Course_Length)
);

-- Semester table -- holds the possible semester values.
CREATE TABLE Semester (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Sem_No INT NOT NULL UNIQUE  -- Ensure Sem_No is unique across all semesters
);

-- This table manages the relationship between colleges and the courses they offer.
-- It ensures that each college can offer multiple courses, but a specific course can only be associated with a college once.
CREATE TABLE College_Course (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    College_Id INT NOT NULL,
    Course_Id INT NOT NULL,
    UNIQUE (College_Id, Course_Id),
    FOREIGN KEY (College_Id) REFERENCES College(Id),
    FOREIGN KEY (Course_Id) REFERENCES Course(Id)
);

-- Table to manage the many-to-many relationship between courses and semesters.
-- Ensures that each course is associated with specific semesters.
CREATE TABLE Course_Sem (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Course_Id INT NOT NULL,
    Sem_Id INT NOT NULL,
    UNIQUE (Course_Id, Sem_Id),
    FOREIGN KEY (Course_Id) REFERENCES Course(Id),
    FOREIGN KEY (Sem_Id) REFERENCES Semester(Id)
);

-- Table to store student details, including personal information, college affiliation, and course enrollment.
-- Unique constraints ensure each student's Roll_No and Enrollment_No are unique within their respective college.
CREATE TABLE Student (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Student_Name VARCHAR(100) NOT NULL,
    Father_Name VARCHAR(100) NOT NULL,
    Mother_Name VARCHAR(100) NOT NULL,
    Roll_No VARCHAR(100) NOT NULL,
    Enrollment_No VARCHAR(100) NOT NULL,
    College_Id INT NOT NULL,
    Course_Id INT NOT NULL,
    Sem_Id INT NOT NULL,
    Photo VARCHAR(255),
    UNIQUE (College_Id, Roll_No),
    UNIQUE (College_Id, Enrollment_No),
    FOREIGN KEY (College_Id) REFERENCES College(Id),
    FOREIGN KEY (Course_Id) REFERENCES Course(Id),
    FOREIGN KEY (Sem_Id) REFERENCES Semester(Id)
);

-- Table to store information about different types of subjects offered within courses.
-- The 'Type' column specifies the category of the subject, including Foundation, Major, Minor, etc.
-- Types: Foundation_Course, Major_1, Major_2, Minor, Open, VOC (Vocational), Internship/Project
CREATE TABLE Subjects (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Type ENUM('Foundation_Course', 'Major_1', 'Major_2', 'Minor', 'Open', 'VOC', 'Internship/Project') NOT NULL
);

-- Table: Optionals
-- Purpose: Stores information about various optional subjects or topics available for courses.
-- Optionals_Name: Name of the optional subject or topic. Ex- maths, phy, etc
CREATE TABLE Optionals (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Optionals_Name VARCHAR(100) NOT NULL,
    HasPractical BOOLEAN NOT NULL DEFAULT FALSE
);

-- Table to manage different options or specializations available for each subject.
-- Each subject can have multiple options, and each option is uniquely identified per subject.
CREATE TABLE Subject_Options (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Subject_Id INT NOT NULL,
    Option_Id INT NOT NULL,
    UNIQUE (Subject_Id, Option_Id),
    FOREIGN KEY (Subject_Id) REFERENCES Subjects(Id),
    FOREIGN KEY (Option_Id) REFERENCES Optionals(Id)
);

-- Table: College_Offers_Subject
-- Purpose: Maps the relationship between colleges, courses, semesters, and subject options. 
-- It tracks the maximum credits that can be obtained for subjects and practicals within each combination.
-- Unique constraint ensures no duplicate entries for the same combination of College, Course, Semester, and Subject Option.
CREATE TABLE College_Offers_Subject (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    College_Id INT NOT NULL,
    Course_Id INT NOT NULL,
    Sem_Id INT NOT NULL,
    Subject_Option_Id INT NOT NULL,
    MaxCreditsSubject INT NOT NULL,
    MaxCreditsPractical INT,
    UNIQUE (College_Id, Course_Id, Sem_Id, Subject_Option_Id),
    FOREIGN KEY (College_Id) REFERENCES College(Id),
    FOREIGN KEY (Course_Id) REFERENCES Course(Id),
    FOREIGN KEY (Sem_Id) REFERENCES Semester(Id),
    FOREIGN KEY (Subject_Option_Id) REFERENCES Subject_Options(Id)
);

-- Enrollment Table
-- Records the enrollment of students in subjects offered by their college.
-- Each entry associates a student with a specific subject offered by the college.
-- This table helps in managing and tracking student enrollments within different courses and subjects.
CREATE TABLE Enrollment (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Student_Id INT NOT NULL,
    College_Offers_Subject_Id INT NOT NULL,
    FOREIGN KEY (Student_Id) REFERENCES Student(Id),
    FOREIGN KEY (College_Offers_Subject_Id) REFERENCES College_Offers_Subject(Id)
);

-- Table to store marks obtained by students in their enrolled subjects.
-- Columns:
-- Marks_Id: Auto-incrementing primary key for unique identification of each marks record.
-- Enrollment_Id: Foreign key linking to the Enrollment table, representing the student's enrollment in a subject.
-- Main_Marks: The main examination marks for the subject.
-- CCE_Marks: Continuous and Comprehensive Evaluation marks for the subject.
-- Practical_Marks: Marks for practicals (if applicable).
CREATE TABLE Marks (
    Marks_Id INT AUTO_INCREMENT PRIMARY KEY,
    Enrollment_Id INT NOT NULL,
    Main_Marks INT NOT NULL,
    CCE_Marks INT NOT NULL,
    Practical_Marks INT,
    FOREIGN KEY (Enrollment_Id) REFERENCES Enrollment(Id)
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
BEFORE INSERT ON Enrollment
FOR EACH ROW
BEGIN
    DECLARE student_college_id INT;
    DECLARE student_course_id INT;
    DECLARE student_sem_id INT;
    DECLARE offers_college_id INT;
    DECLARE offers_course_id INT;
    DECLARE offers_sem_id INT;

    -- Fetch student details
    SELECT College_Id, Course_Id, Sem_Id INTO student_college_id, student_course_id, student_sem_id
    FROM Student
    WHERE Id = NEW.Student_Id;

    -- Fetch College_Offers_Subject details
    SELECT College_Id, Course_Id, Sem_Id INTO offers_college_id, offers_course_id, offers_sem_id
    FROM College_Offers_Subject
    WHERE Id = NEW.College_Offers_Subject_Id;

    -- Check for matching College_Id, Course_Id, Sem_Id
    IF NOT (student_college_id = offers_college_id AND
            student_course_id = offers_course_id AND
            student_sem_id = offers_sem_id) THEN
        SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Mismatch between Student and College_Offers_Subject details.';
    END IF;
END$$

DELIMITER ;