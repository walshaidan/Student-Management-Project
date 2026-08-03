package studentmanagement.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

public class Validator {
    public static boolean isValidEmail(String email) {
        if(!email.matches("^[A-Za-z]+\\.[A-Za-z]+(_[0-9]+)?\\.[0-9]{4}@mumail\\.ie")) return false;
        return true;
    }

    public static boolean isValidEircode(String s) {
        if(s.length() != 8 || s.charAt(3) != ' ') return false;
        String sub = s.substring(0,3);
        String[] RoutingKeys = StudentGenerator.getRoutingKeys();

        for(String rout : RoutingKeys) {
            if(rout.equals(sub)) return true;
        }
        return false;
    }

    public static boolean isValidPhoneNumber(String phoneNum) {
        if(phoneNum.charAt(3) != '-' || phoneNum.charAt(7) != '-') return false;
        String[] prefixes = StudentGenerator.getPrefixes();
        String sub = phoneNum.substring(0,3);

        for(String s : prefixes) {
            if(s.equals(sub)) return true;
        }
        return false;
    }

    public static LocalDate isValidDate(String input) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            return LocalDate.parse(input, dateFormat);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}