-- Insert three colleges with unique name and address combinations
INSERT INTO College (College_Name, Director, Email, Website, Address) VALUES
('KRG College Gwalior', 'Dr. A. Sharma', 'krg@college.com', 'www.krgcollegegwalior.edu', 'Gwalior, Madhya Pradesh'),
('MLB College Gwalior', 'Dr. B. Gupta', 'mlb@college.com', 'www.mlbcollegegwalior.edu', 'Gwalior, Madhya Pradesh'),
('Amity University Gwalior', 'Dr. C. Mehta', 'amity@university.com', 'www.amitygwalior.edu', 'Gwalior, Madhya Pradesh');

-- Insert courses, ensuring unique combinations of course name and length
INSERT INTO Course (Course_Name, Course_Length) VALUES
('BCom (Sem-wise)', 6),   -- BCom with 6 semesters
('BCom (Year-wise)', 3),  -- BCom with 3 years
('BSc', 3),               -- BSc with 3 semesters
('BA', 3);                -- BA with 3 semesters

-- Insert unique semester numbers for all possible semesters
INSERT INTO Semester (Sem_No) VALUES
(1), (2), (3), (4), (5), (6);

-- Link KRG College Gwalior to BCom (Sem-wise) and BSc courses
INSERT INTO College_Course (College_Id, Course_Id)
SELECT C.Id, Cr.Id FROM College C, Course Cr
WHERE C.College_Name = 'KRG College Gwalior' AND Cr.Course_Name IN ('BCom (Sem-wise)', 'BSc');

-- Link MLB College Gwalior to BSc and BA courses
INSERT INTO College_Course (College_Id, Course_Id)
SELECT C.Id, Cr.Id FROM College C, Course Cr
WHERE C.College_Name = 'MLB College Gwalior' AND Cr.Course_Name IN ('BSc', 'BA');

-- Link Amity University Gwalior to BCom (Sem-wise), BCom (Year-wise), BSc, and BA courses
INSERT INTO College_Course (College_Id, Course_Id)
SELECT C.Id, Cr.Id FROM College C, Course Cr
WHERE C.College_Name = 'Amity University Gwalior' AND Cr.Course_Name IN ('BCom (Sem-wise)', 'BCom (Year-wise)', 'BSc', 'BA');

-- Associate BCom (Sem-wise) with all 6 semesters
INSERT INTO Course_Sem (Course_Id, Sem_Id)
SELECT Cr.Id, S.Id FROM Course Cr, Semester S
WHERE Cr.Course_Name = 'BCom (Sem-wise)' AND S.Sem_No IN (1, 2, 3, 4, 5, 6);

-- Associate BCom (Year-wise) with the first 3 semesters (representing years)
INSERT INTO Course_Sem (Course_Id, Sem_Id)
SELECT Cr.Id, S.Id FROM Course Cr, Semester S
WHERE Cr.Course_Name = 'BCom (Year-wise)' AND S.Sem_No IN (1, 2, 3);

-- Associate BSc with the first 3 semesters
INSERT INTO Course_Sem (Course_Id, Sem_Id)
SELECT Cr.Id, S.Id FROM Course Cr, Semester S
WHERE Cr.Course_Name = 'BSc' AND S.Sem_No IN (1, 2, 3);

-- Associate BA with the first 3 semesters
INSERT INTO Course_Sem (Course_Id, Sem_Id)
SELECT Cr.Id, S.Id FROM Course Cr, Semester S
WHERE Cr.Course_Name = 'BA' AND S.Sem_No IN (1, 2, 3);

