-- Sample Data for Testing Defence Portal APIs
-- Run this script after starting the application to populate test data

-- Insert Mock Test
INSERT INTO mock_tests (title, exam_type, description, total_marks, duration_minutes, negative_marking, status, created_at, updated_at)
VALUES ('BSF Constable Mock Test 2024', 'BSF', 'Complete mock test for BSF Constable exam', 100, 60, true, 'PUBLISHED', NOW(), NOW());

-- Insert Section
INSERT INTO sections (name, section_marks, total_questions, mock_test_id)
VALUES ('General Knowledge', 40, 10, 1);

-- Insert Questions
INSERT INTO questions (question_text, question_type, marks, negative_marks, difficulty_level, section_id)
VALUES
('What is the capital of India?', 'MCQ', 4, 1, 'EASY', 1),
('Who is the current Prime Minister of India?', 'MCQ', 4, 1, 'EASY', 1),
('What is the largest planet in our solar system?', 'MCQ', 4, 1, 'MEDIUM', 1);

-- Insert Options for Question 1
INSERT INTO options (option_text, is_correct, question_id)
VALUES
('New Delhi', true, 1),
('Mumbai', false, 1),
('Kolkata', false, 1),
('Chennai', false, 1);

-- Insert Options for Question 2
INSERT INTO options (option_text, is_correct, question_id)
VALUES
('Narendra Modi', true, 2),
('Rahul Gandhi', false, 2),
('Amit Shah', false, 2),
('Yogi Adityanath', false, 2);

-- Insert Options for Question 3
INSERT INTO options (option_text, is_correct, question_id)
VALUES
('Jupiter', true, 3),
('Saturn', false, 3),
('Mars', false, 3),
('Earth', false, 3);

-- Insert Test Attempt
INSERT INTO test_attempts (user_id, start_time, end_time, status, total_score, mock_test_id)
VALUES (123, NOW(), NULL, 'IN_PROGRESS', 0, 1);

-- Insert User Answers
INSERT INTO user_answers (selected_option_id, is_correct, marks_obtained, attempt_id, question_id)
VALUES
(1, true, 4, 1, 1),  -- Correct answer for Q1
(5, true, 4, 1, 2),  -- Correct answer for Q2
(9, true, 4, 1, 3);  -- Correct answer for Q3

-- Insert Result
INSERT INTO results (total_questions, correct_count, wrong_count, unattempted_count, final_score, percentage, attempt_id)
VALUES (3, 3, 0, 0, 12, 100.0, 1);