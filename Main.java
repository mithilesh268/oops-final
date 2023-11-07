import java.util.Arrays;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
class Student {
    private int rollNumber;
    private String name;
    private AttendanceDay[] attendance;
    private String year;

    public Student(int rollNumber, String name, String year) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.year = year;
        attendance = new AttendanceDay[30]; // Assuming 1 month with 30 days
        initializeAttendance();
    }

    private void initializeAttendance() {
        Random rand = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        for (int i = 0; i < attendance.length; i++) {
            Date day = new Date(currentDate.getTime() - i * 24 * 60 * 60 * 1000); // Subtract i days
            String formattedDate = dateFormat.format(day);
            attendance[i] = new AttendanceDay(formattedDate, rand.nextInt(2)); // 0 for absent, 1 for present
        }
    }

    public double calculateAttendancePercentage() {
        int totalDays = attendance.length;
        int presentDays = 0;
        for (AttendanceDay day : attendance) {
            if (day.getStatus() == 1) {
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
            attendance[day - 1].setStatus(status);
            System.out.println("Attendance marked for " + name + " on Day " + day);
        } else {
            System.out.println("Invalid day or status. Please provide valid inputs.");
        }
    }

    public void displayAttendanceRecord() {
        System.out.println("Attendance Record for " + name + " (Year: " + year + "):");
        for (int i = 0; i < attendance.length; i++) {
            System.out.println("Day " + (i + 1) + ": " + attendance[i].getStatusText() + " on " + attendance[i].getDate());
        }
    }

    public boolean matchesRollNumber(int rollNumber) {
        return this.rollNumber == rollNumber;
    }
}

class AttendanceDay {
    private String date;
    private int status; // 0 for absent, 1 for present

    public AttendanceDay(String date, int status) {
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return (status == 1) ? "Present" : "Absent";
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

class AttendanceManagementSystem {
    public static void main(String[] args) {
        Student[] students = new Student[100];

        // Create arrays for names and roll numbers for each year
        String[] firstYearNames = {
                "Amit", "Bhumi", "Cheery", "Daksh", "Ekant",
                "Farhan", "Geet", "Himank", "Isha", "Jimmy",
                "Kriti", "Leo", "Moon", "Noor", "Oreo",
                "Petter", "Qureshi", "Riya", "Sanket", "Tina",
                "Usha", "Vicky", "William", "Xander", "Yasika"
        };

        int[] firstYearRollNumbers = generateRollNumbers(firstYearNames);

        String[] secondYearNames = {
                "Ankit", "Bhaumik", "Cheetna", "Diya", "Eivy",
                "Faruk", "Gita", "Hanshika", "Isabel", "John",
                "Kareena", "Lisha", "Meet", "Nathan", "Oliver",
                "Prince", "Queen", "Ruchi", "Sam", "Taylor",
                "Urvashi", "Vishal", "William", "Xena", "Yamini"
        };

        int[] secondYearRollNumbers = generateRollNumbers(secondYearNames);

        String[] thirdYearNames = {
                "Amit", "Bhumi", "Cheery", "Daksh", "Ekant",
                "Farhan", "Geet", "Himank", "Isha", "Jimmy",
                "Kriti", "Leo", "Moon", "Noor", "Oreo",
                "Petter", "Qureshi", "Riya", "Sanket", "Tina",
                "Usha", "Vicky", "William", "Xander", "Yasika"
        };

        int[] thirdYearRollNumbers = generateRollNumbers(thirdYearNames);

        String[] fourthYearNames = {
                "Ankit", "Bhaumik", "Cheetna", "Diya", "Eivy",
                "Faruk", "Gita", "Hanshika", "Isabel", "John",
                "Kareena", "Lisha", "Meet", "Nathan", "Oliver",
                "Prince", "Queen", "Ruchi", "Sam", "Taylor",
                "Urvashi", "Vishal", "William", "Xena", "Yamini"
        };

        int[] fourthYearRollNumbers = generateRollNumbers(fourthYearNames);

        // Create students with names and calculate attendance
        for (int i = 0; i < 25; i++) {
            students[i] = new FirstYearStudent(firstYearRollNumbers[i], firstYearNames[i]);
            students[i + 25] = new SecondYearStudent(secondYearRollNumbers[i], secondYearNames[i]);
            students[i + 50] = new ThirdYearStudent(thirdYearRollNumbers[i], thirdYearNames[i]);
            students[i + 75] = new FourthYearStudent(fourthYearRollNumbers[i], fourthYearNames[i]);
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

    private static int[] generateRollNumbers(String[] names) {
        int[] rollNumbers = new int[names.length];
        for (int i = 0; i < names.length; i++) {
            rollNumbers[i] = i + 1;
        }
        return rollNumbers;
    }

    private static void shuffleArray(int[] arr) {
        Random rand = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }
}
