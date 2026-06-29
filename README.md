# Student Grade Management App

This is an Android application designed for managing student grades. It allows users to input student details, enter grades, and calculate the average grade, with specific UI/UX requirements for an academic assignment.

## Features
* **Student Data Input:** Capture student name, surname, and the number of grades to be entered.
* **Data Validation:** Real-time checking to ensure fields are not empty and grades are within the [5; 15] range.
* **Dynamic UI:** The "Oceny" (Grades) button appears only when input data is valid.
* **Grade Calculation:** Allows entering grades and calculating the average score.
* **Conditional Feedback:** Displays different buttons based on the calculated average (e.g., "Super" for >= 3 or a retake application for < 3).
* **Configuration Handling:** Retains state during screen rotations.

##  Technology Stack
* **Language:** Java
* **UI Framework:** Android XML Layouts
* **Layout:** ConstraintLayout 
* **Binding:** View Binding 
* **Architecture:** Multi-Activity with ActivityResultLauncher

##  Key Components
* **MainActivity:** Handles input validation and displays results.
* **GradesActivity:** Manages the input list of grades.
* **strings.xml:** Centralized storage for all display texts and predefined subjects.
