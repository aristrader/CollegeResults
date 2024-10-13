CREATE DATABASE college_results_db_v2;

USE college_results_db_v2;

-- Stores information about colleges with a unique combination of name and address.
CREATE TABLE college (
    college_id INT AUTO_INCREMENT PRIMARY KEY,
    college_name VARCHAR(100) NOT NULL,
    director VARCHAR(100),
    email VARCHAR(100),
    website VARCHAR(100),
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (college_name, address)  -- Unique constraint on college_name and address
);

-- Contains information about teachers, ensuring the email and phone number are unique across records.
CREATE TABLE teacher (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    college_id INT NOT NULL,
    teacher_name VARCHAR(100) NOT NULL,
    experience INT,
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(10) UNIQUE,
    specialization VARCHAR(100),
    photo VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (college_id) REFERENCES college(college_id)
);

CREATE TABLE course_details (
    course_details_id INT AUTO_INCREMENT PRIMARY KEY,
    college_id INT NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    sem INT NOT NULL,
    class_teacher_id INT,  -- Foreign key from teacher table, can be NULL
    -- class_representative_id INT,  -- Foreign key from student table, can be NULL
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (college_id, course_name, sem),  -- Unique combo of college_id, course_name, and sem
    FOREIGN KEY (college_id) REFERENCES college(college_id),  -- References college table
    FOREIGN KEY (class_teacher_id) REFERENCES teacher(teacher_id)  -- References teacher table
    -- FOREIGN KEY (class_representative_id) REFERENCES student(student_id)  -- References student table
);
-- class_representative_id can be added later if required skipping for now cause the tables won't be created directly otherwise
-- one way to resolve this would be to move the mapping of course_details and student to another table called CR table
-- create tables first then alter the tables to add the required foreign keys.

-- Holds student details, ensuring that roll number and enrollment number are unique within the same course.
CREATE TABLE student (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    father_name VARCHAR(100),
    mother_name VARCHAR(100),
    roll_no VARCHAR(100) NOT NULL,
    enrollment_no VARCHAR(100) NOT NULL,
    course_details_id INT NOT NULL,
    photo VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- added new
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- added new
    UNIQUE (roll_no, course_details_id),  -- Unique combo of roll_no and course_details_id
    UNIQUE (enrollment_no, course_details_id),  -- Unique combo of enrollment_no and course_details_id
    FOREIGN KEY (course_details_id) REFERENCES course_details(course_details_id)
);

-- Tracks subjects offered under specific courses, with practicals indicated by the presence of max_credits_practical.
CREATE TABLE subject_details (
    subject_details_id INT AUTO_INCREMENT PRIMARY KEY,
    course_details_id INT NOT NULL,
    subject_type VARCHAR(50) NOT NULL,
    options_name VARCHAR(100) NOT NULL,
    max_credits_subject INT,
    max_credits_practical INT,
    subject_teacher_id INT,  -- Adjusted data type to match teacher_id
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- added new
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- added new
    UNIQUE (course_details_id, subject_type, options_name),  -- Unique combo of course_details_id, subject_type, and options_name
    FOREIGN KEY (course_details_id) REFERENCES course_details(course_details_id),
    FOREIGN KEY (subject_teacher_id) REFERENCES teacher(teacher_id)  -- Foreign key to teacher table
);

-- Stores the marks obtained by students for each subject, with a unique constraint on student and subject details.
CREATE TABLE enrollment_and_marks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,  -- Adjusted data type to match student_id in the student table
    subject_details_id INT NOT NULL,  -- Adjusted data type to match subject_details_id in the subject_details table
    main_marks VARCHAR(10),
    cce VARCHAR(10),
    practical_marks VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- added new
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- added new
    UNIQUE (student_id, subject_details_id),  -- Unique combo of student_id and subject_details_id
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (subject_details_id) REFERENCES subject_details(subject_details_id)
);
