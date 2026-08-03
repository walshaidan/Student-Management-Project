package studentmanagement;

import studentmanagement.model.Course;
import studentmanagement.model.Gender;
import studentmanagement.model.Student;
import studentmanagement.service.StudentService;
import studentmanagement.service.Validator;
import studentmanagement.ui.StudentConsoleView;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.*;

/**
 *  If ever in the mood to update switch statements might be clever
 */

public class StudentManagementApp {
    private final Scanner userInput = new Scanner(System.in);
    private final StudentConsoleView view = new StudentConsoleView();
    private final StudentService studentService = new StudentService();

    private int currentPage = 0;

    public void start() {
        studentService.loadStudents();
        view.begin();

        while (true) {
            view.printMainMenu();
            String s = userInput.nextLine();
            if (s.equalsIgnoreCase("Q")) {
                view.exit();
                break;
            } else if (s.equalsIgnoreCase("X")) {
                view.printLines();
                handleAuditMenu();
            } else if (s.equalsIgnoreCase("Z")) {
                view.printLines();
                handleSearchMenu();
            } else if (s.equalsIgnoreCase("V")) {
                view.printLines();
                handleStudentDatabaseMenu(new ArrayList<>());
            } else if(s.equalsIgnoreCase("S")) {
                view.printLines();
                handleStatisticsMenu();
            } else if(s.equalsIgnoreCase("A")) {
                view.printLines();
                handleAddStudentMenu();
            }
            else {
                view.error();
            }
        }
    }

    private void handleStudentDatabaseMenu(List<Student> myList) {
        boolean sorted = (!myList.isEmpty());
        view.studentDatabaseMenuDetails();
        do {
            printCurrent(currentPage, sorted ? myList : studentService.getStudentList());
            String s1 = userInput.nextLine();
            Integer num = StudentService.tryParseInt(s1);

            if(s1.equalsIgnoreCase("N")) {
                currentPage++;
                view.printLines();
            } else if(s1.equalsIgnoreCase("P")) {
                currentPage--;
                view.printLines();
            } else if(s1.equalsIgnoreCase("B")) {
                currentPage = 0;
                view.printLines();
                break;
            } else if(s1.equalsIgnoreCase("S")) {
                handleStudentDatabaseMenu(sortMenu());
                break;
            } else if(num != null) {
                try {
                    view.printStudentDetails(sorted ? myList.get(num) : studentService.getStudentByIndex(num));
                    while(true)  {
                        String s2 = userInput.nextLine();
                        if(s2.equalsIgnoreCase("B")) {
                            break;
                        } else if(s2.equalsIgnoreCase("D")) {
                            boolean bool = studentService.deleteStudent(
                                    sorted ? myList.get(num).getStudentID() :
                                            studentService.getStudentByIndex(num).getStudentID());
                            if(sorted) System.out.println(myList.remove((int) num));
                            if(bool) {
                                view.studentDeletedDetails();
                            } else {
                                view.error();
                            }
                            break;
                        } else if(s2.equalsIgnoreCase("U")) {
                            updateStudentMenu(
                                    sorted ? myList.get(num).getStudentID() :
                                            studentService.getStudentByIndex(num).getStudentID());
                            break;
                        } else {
                            view.error();
                        }
                    }
                    view.printLines();
                } catch(IndexOutOfBoundsException e) {
                    view.notFoundStudent();
                }
            } else {
                view.error();
            }
        } while (true);
    }

    private void handleSearchMenu() {
        view.searchStudentIDModuleCourse();
        searchByStudentID();
    }

    private void handleAuditMenu() {
        do {
            view.printAuditMenu();
        } while(!userInput.nextLine().equalsIgnoreCase("B"));
    }

    private void handleStatisticsMenu() {
        var male = studentService.getStudentList().stream()
                .filter((i) -> i.getGender().toString().equals("MALE"))
                .count();

        var female = studentService.getStudentList().stream()
                .filter((i) -> i.getGender().toString().equals("FEMALE"))
                .count();

        var other = studentService.getStudentList().stream()
                .filter((i) -> i.getGender().toString().equals("OTHER"))
                .count();

        System.out.println(String.format("Gender)%n" +
                "--> %s: %s%n" +
                "--> %s: %s%n" +
                "--> %s: %s%n","MALE",male,"FEMALE",female,"OTHER",other));

        var below30 = studentService.getStudentList().stream()
                .filter((i) -> Period.between(i.getDateOfBirth(),LocalDate.now()).getYears() < 30)
                .count();

        var above30 = studentService.getStudentList().stream()
                .filter((i) -> Period.between(i.getDateOfBirth(),LocalDate.now()).getYears() > 30)
                .count();

        System.out.println(String.format("Age)%n" +
                "--> %s: %s%n" +
                "--> %s: %s%n","Greater Than 30",above30,"Less Than 30",below30));

        //By Bsc/Arts etc.
        //By Amount Per Department -> (Feasible?)
    }

    private List<Student> sortMenu() {
        view.sortDetails();
        String s = userInput.nextLine();
        List<Student> myList;
        switch(s.toUpperCase()) {
            case "S1" -> {
                  myList = new ArrayList<>(studentService.getStudentList().stream()
                          .sorted(Comparator.comparing(Student::getStudentID))
                          .toList());
            }
            case "S2" -> {
                 myList = new ArrayList<>(studentService.getStudentList().stream()
                         .sorted(Comparator.comparing(Student::getStudentID).reversed())
                         .toList());
            }
            case "E" -> {
                 myList = new ArrayList<>(studentService.getStudentList().stream()
                         .sorted(Comparator.comparing(Student::getEnrollmentDate).reversed())
                         .toList());
            }
            case "A" -> {
                 myList = new ArrayList<>(studentService.getStudentList().stream()
                         .sorted(Comparator.comparing(Student::getDateOfBirth))
                         .toList());
            }
            case "C" -> {
                 myList = new ArrayList<>(studentService.getStudentList().stream()
                         .sorted(Comparator.comparing(s1 -> s1.getCourse().name()))
                         .toList());
            }
            default -> {
                myList = Collections.emptyList();
            }
        };
        return myList;
    }

    private void searchByStudentID() {
        while(true) {
            String s = userInput.nextLine();
            if(s.equalsIgnoreCase("B")) {
                break;
            }

            Integer num = StudentService.tryParseInt(s);
            if(num == null) {
                view.studentNotFound();
                break;
            }
            Student student = studentService.getStudentByStudentID(num);
            if(student == null) {
                view.studentNotFound();
                break;
            }
            view.printStudentDetails(student);

            String s1 = userInput.nextLine();
            if(s1.equalsIgnoreCase("B")) {
                break;
            } else if(s1.equalsIgnoreCase("D")) {
                boolean bool = studentService.deleteStudent(Integer.parseInt(s));
                if(bool) {
                    view.studentDeletedDetails();
                } else {
                    view.error();
                }
                break;
            } else if(s1.equalsIgnoreCase("U")) {
                updateStudentMenu(Integer.parseInt(s));
                break;
            } else {
                view.error();
            }
        }
    }

    private void updateStudentMenu(int studentID) {
        view.printStudentUpdateDetails();
        String s3 = userInput.nextLine();

        if(s3.equalsIgnoreCase("C")) {
            view.printUpdateCourse();
            try {
                studentService.getStudentByStudentID(studentID).trySetCourse(userInput.nextLine());
                view.success();
            } catch (IllegalArgumentException e) {
                view.printLines();
                System.out.println(e.getMessage());
                view.printLines();
            }
        } else if(s3.equalsIgnoreCase("P")) {
            view.printUpdatePhoneNumber();
            try {
                String phoneNum = userInput.nextLine();
                if(studentService.containsPhoneNumber(phoneNum)) {
                    view.phoneNumberExists();
                } else {
                    String currentPhoneNum = studentService.getStudentByStudentID(studentID).getPhoneNumber();
                    studentService.getStudentByStudentID(studentID).trySetPhoneNumber(phoneNum);
                    studentService.deletePhoneFromList(currentPhoneNum);
                    studentService.addPhoneToList(phoneNum);
                    view.success1();
                }
            } catch (IllegalArgumentException e) {
                view.printLines();
               System.out.println(e.getMessage());
                view.printLines();
            }
        } else if(s3.equalsIgnoreCase("A")) {
            view.printUpdateEircode();
            try {
                studentService.getStudentByStudentID(studentID).setEircode(userInput.nextLine());
                view.success();
            } catch (IllegalArgumentException e) {
                view.printLines();
                System.out.println(e.getMessage());
                view.printLines();
            }
        } else if(s3.equalsIgnoreCase("E")) {
            view.printUpdateEmail();
            try {
                String email = userInput.nextLine();
                if(studentService.containsEmail(email)) {
                    view.emailExists();
                } else {
                    String currentEmail = studentService.getStudentByStudentID(studentID).getEmail();
                    studentService.getStudentByStudentID(studentID).trySetEmail(email);
                    studentService.deleteEmailFromList(currentEmail);
                    studentService.addEmailToList(email);
                    view.success1();
                }
            } catch (IllegalArgumentException e) {
                view.printLines();
                System.out.println(e.getMessage());
                view.printLines();
            }
        } else if(!s3.equalsIgnoreCase("B")) {
            view.error();
        }
        studentService.saveStudents();
    }

    public void handleAddStudentMenu() {
        view.enterFirstName();
        String firstName = userInput.nextLine();

        view.enterLastName();
        String lastName = userInput.nextLine();

        view.enterDateOfBirth();
        LocalDate date;
        while(true) {
            date = Validator.isValidDate(userInput.nextLine());
            if(date != null) break;
            view.invalidDate();
        }

        view.enterGender();
        String genderInput;
        Gender gender;
        while(true) {
            genderInput = userInput.nextLine();
            if(genderInput.equalsIgnoreCase("Male")) {
                gender = Gender.MALE;
                break;
            } else if(genderInput.equalsIgnoreCase("Female")) {
                gender = Gender.FEMALE;
                break;
            } else if(genderInput.equalsIgnoreCase("Other")) {
                gender = Gender.OTHER;
            } else {
                view.invalidGender();
            }
        }

        view.enterEircode();
        String eircode;
        while(true) {
            eircode = userInput.nextLine();
            if(Validator.isValidEircode(eircode)) break;
            view.invalidEircode();
        }

        view.enterPhoneNumber();
        String phoneNumber;
        while(true) {
            phoneNumber = userInput.nextLine();
            if(!Validator.isValidPhoneNumber(phoneNumber)) {
                view.invalidPhoneNumber();
            } else if(studentService.containsPhoneNumber(phoneNumber)) {
                view.containsPhoneNum();
            } else {
                break;
            }
        }

        view.enterCourse();
        Course course;
        while(true) {
            course  = Course.containsCourse(userInput.nextLine());
            if(course == null)  {
                view.invalidCourse();
            } else {
                break;
            }
        }
        view.printLines();

        String email = StudentService.getEmail(firstName,lastName, Year.now().getValue());
        int i = 0;
        while(studentService.containsEmail(email)) {
            email = StudentService.getEmailIncrement(email,i);
            i++;
        }
        studentService.addEmailToList(email);
        studentService.addPhoneToList(phoneNumber);

        Student newStudent = new Student(firstName, lastName, date,LocalDate.now(),gender,phoneNumber,
                email,eircode,course);
        System.out.println(newStudent);
        studentService.addStudentToList(newStudent);
        view.success1();

    }

    public void printCurrent(int current, List<Student> list) {
        for(int i = 1; i <= 10; i++) {
            view.printStudentSummary(i + (current * 10),
                    list.get(i + (current * 10)));
        }
        view.printPageNumber(current + 1);
    }
}