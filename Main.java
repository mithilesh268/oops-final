import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Student {
    private String studentId;
    private String studentName;
    private int daysPresent;
    private int daysAbsent;

    public Student(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.daysPresent = 0;
        this.daysAbsent = 0;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }
    public int getDaysPresent() {
        return daysPresent;
    }

    public int getDaysAbsent() {
        return daysAbsent;
    }

    public void markPresent() {
        daysPresent++;
    }

    public void markAbsent() {
        daysAbsent++;
    }
}

class Course {
    private String courseId;
    private String courseName;
    private List<Student> enrolledStudents;

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
        enrolledStudents = new ArrayList<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void enrollStudent(Student student) {
        enrolledStudents.add(student);
    }
}

class AttendanceRecord {
    private String date;
    private Map<String, Boolean> studentAttendance;

    public AttendanceRecord(String date) {
        this.date = date;
        studentAttendance = new HashMap<>();
    }

    public String getDate() {
        return date;
    }

    public Map<String, Boolean> getStudentAttendance() {
        return studentAttendance;
    }

    public void markAttendance(String studentId, boolean isPresent) {
        studentAttendance.put(studentId, isPresent);
    }
}

class AttendanceManagementSystem {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nAttendance Management System Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student in Course");
            System.out.println("4. Take Attendance");
            System.out.println("5. Calculate Attendance Percentage");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter Student Name: ");
                    String studentName = scanner.nextLine();
                    students.add(new Student(studentId, studentName));
                    System.out.println("Student added successfully.");
                    break;

                case 2:
                    System.out.print("Enter Course ID: ");
                    String courseId = scanner.nextLine();
                    System.out.print("Enter Course Name: ");
                    String courseName = scanner.nextLine();
                    courses.add(new Course(courseId, courseName));
                    System.out.println("Course added successfully.");
                    break;

                case 3:
                    if (students.isEmpty() || courses.isEmpty()) {
                        System.out.println("Add students and courses before enrolling.");
                        break;
                    }

                    System.out.print("Enter Student ID: ");
                    studentId = scanner.nextLine();
                    System.out.print("Enter Course ID: ");
                    courseId = scanner.nextLine();

                    Student studentToEnroll = findStudentById(studentId, students);
                    Course courseToEnroll = findCourseById(courseId, courses);

                    if (studentToEnroll != null && courseToEnroll != null) {
                        courseToEnroll.enrollStudent(studentToEnroll);
                        System.out.println("Student enrolled in the course.");
                    } else {
                        System.out.println("Student or course not found.");
                    }
                    break;

                case 4:
                    if (courses.isEmpty()) {
                        System.out.println("Add courses before taking attendance.");
                        break;
                    }

                    System.out.print("Enter Course ID: ");
                    courseId = scanner.nextLine();

                    Course courseForAttendance = findCourseById(courseId, courses);

                    if (courseForAttendance != null) {
                        System.out.print("Enter Attendance Date: ");
                        String date = scanner.nextLine();
                        AttendanceRecord attendanceRecord = new AttendanceRecord(date);

                        for (Student student : courseForAttendance.getEnrolledStudents()) {
                            System.out.print("Is " + student.getStudentName() + " present? (true/false): ");
                            boolean isPresent = scanner.nextBoolean();
                            attendanceRecord.markAttendance(student.getStudentId(), isPresent);
                            if (isPresent) {
                                student.markPresent();
                            } else {
                                student.markAbsent();
                            }
                        }

                        attendanceRecords.add(attendanceRecord);
                        System.out.println("Attendance recorded successfully.");
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter Student ID: ");
                    studentId = scanner.nextLine();
                    System.out.print("Enter Course ID: ");
                    courseId = scanner.nextLine();
                    double attendancePercentage = calculateAttendancePercentage(studentId, courseId, courses, attendanceRecords);
                    if (attendancePercentage >= 0) {
                        System.out.println("Attendance Percentage: " + attendancePercentage + "%");
                    } else if (attendancePercentage == -1) {
                        System.out.println("Student or course not found.");
                    } else if (attendancePercentage == -2) {
                        System.out.println("No attendance records for this course.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting the Attendance Management System.");
                    scanner.close();
                    System.exit(0);
                case 7:
                    // Calculate the average attendance
                    double averageAttendance = calculateAverageAttendance(students);
                    System.out.println("Average Attendance: " + averageAttendance + "%");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Student findStudentById(String studentId, List<Student> students) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    private static Course findCourseById(String courseId, List<Course> courses) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }



   private static double calculateAttendancePercentage(String studentId, String courseId, List<Course> courses, List<AttendanceRecord> attendanceRecords) {
       Course course = findCourseById(courseId, courses);
       if (course == null) {
           return -1; // Student or course not found
       }

       Student student = findStudentById(studentId, course.getEnrolledStudents());
       if (student == null) {
           return -1; // Student or course not found
       }

       int totalClasses = 0;
       int attendedClasses = 0;

       for (AttendanceRecord record : attendanceRecords) {
           Boolean isPresent = record.getStudentAttendance().get(studentId);
           if (isPresent != null) {
               totalClasses++;
               if (isPresent) {
                   attendedClasses++;
               }
           }
       }

       if (totalClasses == 0) {
           return -2; // No attendance records for this student
       }

       return ((double) attendedClasses / totalClasses) * 100;
   }


    private static double calculateAverageAttendance(List<Student> students) {
        if (students.isEmpty()) {
            return 0.0;
        }

        int totalDays = 0;
        int totalPresent = 0;

        for (Student student : students) {
            totalDays += student.getDaysPresent() + student.getDaysAbsent();
            totalPresent += student.getDaysPresent();
        }

        if (totalDays > 0) {
            return ((double) totalPresent / totalDays) * 100;
        } else {
            return 0.0; // No attendance records available
        }
    }
}
