package studentmanagement.service;

import studentmanagement.model.Course;
import studentmanagement.model.Gender;
import studentmanagement.model.Student;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class StudentGenerator {
    private static final Random RAND = new Random();

    private static final String[] FIRST_NAMES = {"James", "John", "Robert", "Michael", "William", "David",
            "Richard", "Joseph", "Thomas", "Charles", "Christopher", "Daniel", "Matthew", "Anthony", "Mark", "Donald",
            "Steven", "Paul", "Andrew", "Joshua", "Kenneth", "Kevin", "Brian", "George", "Edward", "Ronald", "Timothy",
            "Jason", "Jeffrey", "Ryan", "Jacob", "Gary", "Nicholas", "Eric", "Jonathan", "Stephen", "Larry", "Justin",
            "Scott", "Brandon", "Benjamin", "Samuel", "Frank", "Gregory", "Raymond", "Alexander", "Patrick", "Jack",
            "Dennis", "Jerry", "Mary", "Patricia", "Jennifer", "Linda", "Elizabeth", "Barbara", "Susan", "Jessica",
            "Sarah", "Karen", "Nancy", "Lisa", "Betty", "Margaret", "Sandra", "Ashley", "Kimberly", "Emily", "Donna",
            "Michelle", "Carol", "Amanda", "Dorothy", "Melissa", "Deborah", "Stephanie", "Rebecca", "Sharon", "Laura",
            "Cynthia", "Kathleen", "Amy", "Angela", "Shirley", "Anna", "Brenda", "Pamela", "Emma", "Nicole", "Helen",
            "Samantha", "Katherine", "Christine", "Debra", "Rachel", "Carolyn", "Janet", "Maria", "Olivia", "Grace"};

    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia",
            "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
            "Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez",
            "Clark", "Ramirez", "Lewis", "Robinson", "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres",
            "Nguyen", "Hill", "Flores", "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell",
            "Carter", "Roberts", "Gomez", "Phillips", "Evans", "Turner", "Diaz", "Parker", "Cruz", "Edwards", "Collins",
            "Reyes", "Stewart", "Morris", "Morales", "Murphy", "Cook", "Rogers", "Gutierrez", "Ortiz", "Morgan",
            "Cooper", "Peterson", "Bailey", "Reed", "Kelly", "Howard", "Ramos", "Kim", "Cox", "Ward", "Richardson",
            "Watson", "Brooks", "Chavez", "Wood", "James", "Bennett", "Gray", "Mendoza", "Ruiz", "Hughes", "Price",
            "Alvarez", "Castillo", "Sanders", "Patel", "Myers", "Long", "Ross", "Foster", "Jimenez", "Powell",
            "Jenkins"};

    private static final String[] ROUTING_KEYS = {"A41","A42","A45","A63","A67","A75","A81","A82","A83","A84",
            "A85","A86","A91","A92","A94","A96","A98","C15", "D01","D02","D03","D04","D05","D06","D6W","D07","D08","D09"
            ,"D10","D11","D12","D13","D14","D15","D16","D17","D18","D20","D22","D24","E21","E25","E32","E34","E41",
            "E45","E53","E91", "F12","F23","F26","F28","F31","F35","F42","F45","F52","F56","F91","F92","F93","F94",
            "H12","H14","H16","H18","H23","H53","H54","H62","H65","H71","H91","K32","K34","K36","K45","K56","K67","K78",
            "N37","N39","N41","N91", "P12","P14","P17","P24","P25","P31","P32","P36","P43","P47","P51","P56","P61",
            "P67","P72","P75","P81","P85","R14","R21","R32","R35","R42","R45","R51","R56","R93","R95", "T12","T23",
            "T34","T45","T56","V14","V15","V23","V31","V35","V42","V92","V93","V94","V95","W12","W23","W34","W91","X35",
            "X42","X91","Y14","Y21","Y25","Y34","Y35"};

    private static final String[] PREFIXES = {"083","085","086","087","089"};

    private static final String[] NUM_UPPERCASE = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q",
            "R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};

    private static String firstName() {
        return FIRST_NAMES[RAND.nextInt(FIRST_NAMES.length)];
    }

    private static String lastName() {
        return LAST_NAMES[RAND.nextInt(LAST_NAMES.length)];
    }

    private static String routingKey() {
        return ROUTING_KEYS[RAND.nextInt(ROUTING_KEYS.length)];
    }

    public static String phonePrefix() {
        return PREFIXES[RAND.nextInt(PREFIXES.length)];
    }

    private static String numOrUppercase() {
        return NUM_UPPERCASE[RAND.nextInt(NUM_UPPERCASE.length)];
    }

    public StudentGenerator() {
    }

    public static Student getRandomStudent() {
        String firstName = firstName();
        String lastName = lastName();
        LocalDate dateOfBirth = getRandomDate();
        LocalDate enrollmentDate = getRandomDate(RAND.nextInt(2015,2026));
        Gender gender = Gender.values()[RAND.nextInt(3)];
        String phoneNumber = getRandomPhoneNumber();
        String email = StudentService.getEmail(firstName,lastName,enrollmentDate.getYear());
        String eircode = getRandomEircode();

        return new Student(firstName,lastName,dateOfBirth,enrollmentDate,gender,phoneNumber,email,eircode,
                Course.randomCourse());
    }

    private static LocalDate getRandomDate() {
        int year = RAND.nextInt(1990,2009);
        return getRandomDate(year);
    }

    private static LocalDate getRandomDate(int year) {
        int month = RAND.nextInt(1,13);
        int maxDay = java.time.YearMonth.of(year, month).lengthOfMonth();
        int day = RAND.nextInt(1, maxDay + 1);

        return LocalDate.of(year,month,day);
    }

    private static String getRandomPhoneNumber() {
        StringBuilder sb = new StringBuilder(phonePrefix());
        sb.append("-");
        for(int i = 0; i < 3; i++) {
            sb.append(RAND.nextInt(10));
        }
        sb.append("-");
        for(int i = 0; i < 5; i++) {
            sb.append(RAND.nextInt(10));
        }
        return sb.toString();
    }

    private static String getRandomEircode() {
        StringBuilder sb = new StringBuilder(routingKey());
        sb.append(" ");

        for(int i = 0; i < 4; i++) {
            sb.append(numOrUppercase());
        }
        return sb.toString();
    }

    public static String[] getRoutingKeys() {
        return ROUTING_KEYS.clone();
    }

    public static String[] getPrefixes() {
        return PREFIXES.clone();
    }
}