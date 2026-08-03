package studentmanagement.model;


import studentmanagement.service.Validator;
import java.time.LocalDate;

public class Student {
    private int studentID;
    private static int lastStudentID = 1;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private LocalDate enrollmentDate;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String eircode;
    private Course course;

    public Student() {
    }

    public Student(String firstName, String lastName, LocalDate dateOfBirth, LocalDate enrollmentDate, Gender gender,
                   String phoneNumber, String email, String eircode, Course course) {
        studentID = lastStudentID++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.enrollmentDate = enrollmentDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.eircode = eircode;
        this.course = course;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String generateFullName() {
        return firstName + " " + lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public Gender getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEircode() {
        return eircode;
    }

    public Course getCourse() {
        return course;
    }

    public void trySetPhoneNumber(String phoneNumber) {
        if(!Validator.isValidPhoneNumber(phoneNumber)) throw new IllegalArgumentException(
                "Invalid Phone Number Entered");
        this.phoneNumber = phoneNumber;
    }

    public void setEircode(String eircode) {
        if(!Validator.isValidEircode(eircode)) throw new IllegalArgumentException("Invalid Eircode Entered");
        this.eircode = eircode;
    }

    public void trySetEmail(String email) {
        if(!Validator.isValidEmail(email)) throw new IllegalArgumentException("Invalid Email Entered");
        this.email = email;
    }

    public void trySetCourse(String s) {
        Course course = Course.containsCourse(s);
        if(course == null) throw new IllegalArgumentException("Invalid Course Entered");
        this.course = course;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, DOB: %s, Enrollment: %s, Gender: %s, Eircode: %s%n" +
                        "Course: %s%n" +
                        "Phone Number: %s%n" +
                        "Email: %s%n" +
                        "StudentID: %s ",firstName + " " + lastName, dateOfBirth, enrollmentDate, gender,
                eircode, course.name(), phoneNumber, email,studentID);
    }
}