import java.util.Random;
import java.util.Scanner;

class Student {
    private int rollNumber;
    private String name;
    private int[] attendance;
    private String year;

    public Student(int rollNumber, String name, String year) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.year = year;
        attendance = new int[30]; // Assuming 1 month with 30 days
        initializeAttendance();
    }

    private void initializeAttendance() {
        Random rand = new Random();
        for (int i = 0; i < attendance.length; i++) {
            attendance[i] = rand.nextInt(2); // 0 for absent, 1 for present
        }
    }

    public double calculateAttendancePercentage() {
        int totalDays = attendance.length;
        int presentDays = 0;
        for (int day : attendance) {
            if (day == 1) {
                presentDays++;
            }
        }
        return (double) presentDays / totalDays * 100;
    }

    public String getStudentInfo() {
        return "Year: " + year + ", Roll Number: " + rollNumber + ", Name: " + name;
    }

    public void markAttendance(int day, int status) {
        if (day >= 1 && day <= attendance.length && (status == 0 || status == 1)) {
            attendance[day - 1] = status;
            System.out.println("Attendance marked for " + name + " on day " + day);
        } else {
            System.out.println("Invalid day or status. Please provide valid inputs.");
        }
    }

    public void displayAttendanceRecord() {
        System.out.println("Attendance Record for " + name + ":");
        for (int i = 0; i < attendance.length; i++) {
            System.out.println("Day " + (i + 1) + ": " + (attendance[i] == 1 ? "Present" : "Absent"));
        }
    }

    public boolean matchesRollNumber(int rollNumber) {
        return this.rollNumber == rollNumber;
    }
}

class FirstYearStudent extends Student {
    public FirstYearStudent(int rollNumber, String name) {
        super(rollNumber, name, "1st Year");
    }
}

class SecondYearStudent extends Student {
    public SecondYearStudent(int rollNumber, String name) {
        super(rollNumber, name, "2nd Year");
    }
}

class ThirdYearStudent extends Student {
    public ThirdYearStudent(int rollNumber, String name) {
        super(rollNumber, name, "3rd Year");
    }
}

class FourthYearStudent extends Student {
    public FourthYearStudent(int rollNumber, String name) {
        super(rollNumber, name, "4th Year");
    }
}

public class AttendanceManagementSystem {
    public static void main(String[] args) {
        Student[] students = new Student[100];

        String[] firstYearNames = generateRandomNames(25);
        String[] secondYearNames = generateRandomNames(25);
        String[] thirdYearNames = generateRandomNames(25);
        String[] fourthYearNames = generateRandomNames(25);

        Random rand = new Random();

        // Create students with names and calculate attendance
        for (int i = 0; i < 25; i++) {
            students[i] = new FirstYearStudent(i + 1, firstYearNames[i]);
            students[i + 25] = new SecondYearStudent(i + 1, secondYearNames[i]);
            students[i + 50] = new ThirdYearStudent(i + 1, thirdYearNames[i]);
            students[i + 75] = new FourthYearStudent(i + 1, fourthYearNames[i]);
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Display Student Info and Attendance Percentage");
            System.out.println("2. Mark Attendance for a Student");
            System.out.println("3. Display Detailed Attendance Record");
            System.out.println("4. Search for Student by Roll Number");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
                continue;
            }
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    for (Student student : students) {
                        System.out.println(student.getStudentInfo());
                        System.out.println("Attendance Percentage: " + student.calculateAttendancePercentage() + "%");
                        System.out.println();
                    }
                    break;
                case 2:
                    int rollNumber;
                    int day;
                    int status;

                    try {
                        System.out.print("Enter Roll Number: ");
                        rollNumber = scanner.nextInt();
                        System.out.print("Enter Day (1-30): ");
                        day = scanner.nextInt();
                        System.out.print("Enter Attendance Status (0 for Absent, 1 for Present): ");
                        status = scanner.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Invalid input. Please enter valid numbers.");
                        scanner.nextLine(); // Consume the invalid input
                        continue;
                    }

                    boolean found = false;
                    for (Student student : students) {
                        if (student.matchesRollNumber(rollNumber)) {
                            student.markAttendance(day, status);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Student with the given Roll Number not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Roll Number: ");
                    rollNumber = scanner.nextInt();
                    boolean studentFound = false;

                    for (Student student : students) {
                        if (student.matchesRollNumber(rollNumber)) {
                            student.displayAttendanceRecord();
                            studentFound = true;
                            break;
                        }
                    }
                    if (!studentFound) {
                        System.out.println("Student with the given Roll Number not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter Roll Number to Search: ");
                    rollNumber = scanner.nextInt();
                    boolean foundStudent = false;

                    for (Student student : students) {
                        if (student.matchesRollNumber(rollNumber)) {
                            System.out.println("Student Found:");
                            System.out.println(student.getStudentInfo());
                            System.out.println("Attendance Percentage: " + student.calculateAttendancePercentage() + "%");
                            foundStudent = true;
                            break;
                        }
                    }
                    if (!foundStudent) {
                        System.out.println("Student with the given Roll Number not found.");
                    }
                    break;
                case 5:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    private static String[] generateRandomNames(int count) {
        String[] names = {
            "Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Hannah", "Isaac", "Julia",
            "Kevin", "Linda", "Mark", "Nina", "Oliver", "Patricia", "Quincy", "Rachel", "Samuel", "Tina",
            "Ulysses", "Victoria", "William", "Xena", "Yara", "Zane"
        };

        String[] randomNames = new String[count];
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            randomNames[i] = names[rand.nextInt(names.length)];
        }
        return randomNames;
    }
}
