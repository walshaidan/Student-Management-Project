package studentmanagement.service;

import studentmanagement.model.Student;
import studentmanagement.repository.JsonStudentRepository;
import java.util.*;
import java.util.stream.Stream;

public class StudentService {
    private List<Student> currentList;
    private final Set<String> emailList = new HashSet<>();
    private final Set<String> phoneList = new HashSet<>();

    public void loadStudents() {
        currentList = JsonStudentRepository.load();
        emailList.clear();
        phoneList.clear();

        for (Student student : currentList) {
            emailList.add(student.getEmail());
            phoneList.add(student.getPhoneNumber());
        }

        if (currentList.isEmpty()) {
            currentList = generateStudents();
            JsonStudentRepository.save(currentList);
        }
    }

    public void saveStudents() {
        JsonStudentRepository.save(currentList);
    }

    private List<Student> generateStudents() {
        var myList = new ArrayList<>(Stream.generate(StudentGenerator::getRandomStudent)
                .filter(student -> emailList.add(student.getEmail()))
                .filter(student -> phoneList.add(student.getPhoneNumber()))
                .limit(1000)
                .toList());
        Collections.shuffle(myList);
        return myList;
    }

    public List<Student> getStudentList() {
        return this.currentList;
    }

    public Student getStudentByIndex(int num) {
        return currentList.get(num);
    }

    public void addStudentToList(Student student) {
        currentList.add(student);
    }

    public static String getEmail(String firstName, String lastName, int year) {
        StringBuilder sb = new StringBuilder(firstName);
        sb.append(".");
        sb.append(lastName);
        sb.append(".");
        sb.append(year);
        sb.append("@mumail.ie");
        return sb.toString();
    }

    public static String getEmailIncrement(String email, int num) {
        StringBuilder copy = new StringBuilder(email);
        int index = email.indexOf("@");
        copy.insert(index,"_"+num);
        return copy.toString();
    }

    public void deleteEmailFromList(String email) {
        boolean bool = emailList.remove(email);
        if(!bool) throw new IllegalArgumentException("Email Not In Database");
    }

    public void addEmailToList(String email) {
        boolean bool = emailList.add(email);
        if(!bool) throw new IllegalArgumentException("Email Already Exists");
    }

    public boolean containsEmail(String email) {
        for(String e : emailList) {
            if(e.matches(email)) return true;
        }
        return false;
    }

    public void deletePhoneFromList(String phoneNum) {
        boolean bool = phoneList.remove(phoneNum);
        if(!bool) throw new IllegalArgumentException("Phone Number Not In Database");
    }

    public void addPhoneToList(String phoneNum) {
        boolean bool = phoneList.add(phoneNum);
        if(!bool) throw new IllegalArgumentException("Phone Number Already Exists");
    }

    public boolean containsPhoneNumber(String phoneNumber) {
        for(String p : phoneList) {
            if(p.matches(phoneNumber)) return true;
        }
        return false;
    }

    public boolean deleteStudent(int studentID) {
        Iterator<Student> it = currentList.iterator();

        while(it.hasNext()) {
            Student current = it.next();
            if(current.getStudentID() == studentID) {
                deleteEmailFromList(current.getEmail());
                deletePhoneFromList(current.getPhoneNumber());
                it.remove();
                JsonStudentRepository.save(currentList);
                return true;
            }
        }
        return false;
    }

    public Student getStudentByStudentID(int studentID) {
        for (Student student : currentList) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }
        return null;
    }

    public static Integer tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return null;
        }
    }
}