package studentmanagement.ui;

import studentmanagement.model.Student;

import java.util.concurrent.TimeUnit;

public class StudentConsoleView {
    public void begin() {
        System.out.println(" ".repeat(17) + "*".repeat(24));
        System.out.println(" ".repeat(16) + "* " + "Welcome Maynooth Admin" + " *");
        System.out.println(" ".repeat(17) + "*".repeat(24));
    }

    public void printMainMenu() {
        printStars();
        System.out.println(" ".repeat(25) + "Main Menu");
        printStars();
        System.out.println("- Press (Q) To Quit");
//        System.out.println("- Press (X) The Audit Log");
        System.out.println("- Press (Z) Search By StudentID");
        System.out.println("- Press (V) To View The Student Database");
        System.out.println("- Press (S) To Get Statistics");
        System.out.println("- Press (A) To Add A Student");
        printLines();
    }

    public void printAuditMenu() {
        printStars();
        System.out.println(" ".repeat(25) + "Audit Menu");
        printStars();
        System.out.println("- Press (B) to go back");
        printLines();
    }

    public void printStudentSummary(int number, Student student) {
        System.out.println(number + ") " + student.generateFullName() + " - " +
                student.getCourse().name());
    }

    public void printStudentDetails(Student student) {
        printLines();
        System.out.println("- Press (B) To Go Back");
        System.out.println("- Press (D) To Delete From Database");
        System.out.println("- Press (U) To Update Student Details");
        printLines();
        System.out.println(student);
        printLines();
    }

    public void studentDatabaseMenuDetails() {
        printStars();
        System.out.println(" ".repeat(23) + "Database Menu");
        printStars();
        System.out.println("- Enter Corresponding Number To Get Further Student Details");
        System.out.println("- Press (S) To Sort");
        System.out.println("- Press (N) To Go To The Next Page");
        System.out.println("- Press (P) To Go To Previous Page");
        System.out.println("- Press (B) To Go Back");
        printLines();
    }

    public void sortDetails() {
        printLines();
        System.out.println("- Press (S1) To Sort By StudentID Ascending");
        System.out.println("- Press (S2) To Sort By StudentID Descending");
        System.out.println("- Press (E) To Sort By EnrollmentDate");
        System.out.println("- Press (A) To Sort By Age");
        System.out.println("- Press (C) To Sort By Course");
        System.out.println("- Press (B) To Go Back");
        printLines();
    }

    public void searchStudentIDModuleCourse() {
        printStars();
        System.out.println(" ".repeat(24) + "Search Menu");
        printStars();
        System.out.println("- Enter StudentID");
        System.out.println("- Press (B) To Go Back");
        printLines();
    }

    public void printStudentUpdateDetails() {
        printLines();
        System.out.println("- Press (C) To Update Course");
        System.out.println("- Press (P) To Phone Number");
        System.out.println("- Press (A) To Update Eircode");
        System.out.println("- Press (E) To Update Email");
        System.out.println("- Press (B) To Go Back");
        printLines();
    }

    public void printPageNumber(int pageNumber) {
        System.out.println("(Page " + pageNumber + ")");
        printLines();
    }

    public void error() {
        printLines();
        System.out.print("Error, try again: ");
        try {
            for(int i = 3; i > 0; i--) {
                System.out.print(i + " ");
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted");
        }
        printLines();
    }

    public void exit() {
        printStars();
        System.out.println("Thank you!");
        printStars();
    }

    public void notFoundStudent() {
        printLines();
        System.out.println("Sorry Student With Those Details Not Found (Press B For Back)");
        printLines();
    }

    public void studentDeletedDetails() {
        printLines();
        System.out.println("Student Deleted");
        printLines();
    }

    public void printUpdatePhoneNumber() {
        printLines();
        System.out.println("Enter The Updated PhoneNumber: ");
        printLines();
    }

    public void printUpdateCourse() {
        printLines();
        System.out.println("Enter The Updated Course: ");
        printLines();
    }

    public void printUpdateEircode() {
        printLines();
        System.out.println("Enter The Updated Eircode: ");
        printLines();
    }

    public void printUpdateEmail() {
        printLines();
        System.out.println("Enter The Updated Email: ");
        printLines();
    }

    public void success() {
        printLines();
        System.out.println("Successfully Updated!");
    }

    public void success1() {
        printLines();
        System.out.println("Successfully Updated!");
        printLines();
    }

    public void studentNotFound() {
        printLines();
        System.out.println("Student Not Found");
        printLines();
    }

    public void phoneNumberExists() {
        printLines();
        System.out.println("Phone Number Already Exists");
        printLines();
    }

    public void emailExists() {
        printLines();
        System.out.println("Email Already Exists");
        printLines();
    }

    public void enterFirstName() {
        System.out.println("Please Enter First Name: ");
    }

    public void enterLastName() {
        printLines();
        System.out.println("Please Enter Last Name: ");
    }

    public void enterDateOfBirth() {
        printLines();
        System.out.println("Please Enter Date Of Birth In For dd/MM/yyyy: ");
    }

    public void enterGender() {
        printLines();
        System.out.println("Please Enter Gender: ");
    }

    public void enterPhoneNumber() {
        printLines();
        System.out.println("Please Enter Phone Number In Form XXX-XXX-XXXX: ");
    }

    public void enterEircode() {
        printLines();
        System.out.println("Please Enter Eircode In Form XXX XXXX: ");
    }

    public void enterCourse() {
        printLines();
        System.out.println("Please Enter Course: ");
    }

    public void containsPhoneNum() {
        printLines();
        System.out.println("Phone Number Already In Database");
    }

    public void invalidGender() {
        printLines();
        System.out.println("Invalid Gender");
    }

    public void invalidEircode() {
        printLines();
        System.out.println("Invalid Eircode");
    }

    public void invalidPhoneNumber() {
        printLines();
        System.out.println("Invalid Phone Number");
    }

    public void invalidCourse() {
        printLines();
        System.out.println("Invalid Course");
    }

    public void invalidDate() {
        printLines();
        System.out.println("Invalid Date");
    }


    private static void printStars() {
        System.out.println("*".repeat(60));
    }

    public void printLines() {
        System.out.println("-".repeat(60));
    }
}