package Object;
import java.io.*;
import java.util.*;

import Utils.Utils;

public class Course {
    private List<Teacher> teachers;
    private List<Student> students;

    public Course() {
        teachers = loadTeachersFromFile("teachers.csv");
        students = loadStudentsFromFile("data.csv");
    }

    private List<Teacher> loadTeachersFromFile(String filename) {
        List<Teacher> teacherList = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            fileReader.readLine();
            String row;
            while ((row = fileReader.readLine()) != null) {
                String[] rowData = row.split(",");
                teacherList.add(new Teacher(rowData[0], rowData[1], rowData[2], rowData[3]));
            }
        } catch (IOException e) {
            System.out.println("Khong the mo file " + filename);
        }
        return teacherList;
    }

    private List<Student> loadStudentsFromFile(String filename) {
        List<Student> studentList = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            fileReader.readLine();
            String row;
            while ((row = fileReader.readLine()) != null) {
                String[] rowData = row.split(",");
                studentList.add(new Student(rowData[0], rowData[1], rowData[2], rowData[3], rowData[4]));
            }
        } catch (IOException e) {
            System.out.println("Khong the mo file " + filename);
        }
        return studentList;
    }

    public void displayClassInformation() {
        Set<String> classIds = new HashSet<>();

        for (Teacher teacher : teachers) {
            classIds.add(teacher.class_id);
        }

        for (String classId : classIds) {
            System.out.println("=======================================");
            System.out.println("Class ID: " + classId);

            System.out.println("Teacher:");
            for (Teacher teacher : teachers) {
                if (teacher.class_id.equals(classId)) {
                    System.out.println("  - Name: " + teacher.name);
                    System.out.println("    Email: " + teacher.email);
                }
            }

            System.out.println("Students:");
            for (Student student : students) {
                if (student.class_id.equals(classId)) {
                    System.out.println("  - Name: " + student.name);
                    System.out.println("    Email: " + student.email);
                    System.out.println("    Student ID: " + student.stu_id);
                    System.out.println("    Mark: " + student.mark);
                }
            }
        }
    }

    public static void classManagerMain() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Course classManager = new Course();
            classManager.displayClassInformation();
            System.out.println("Nhan Enter de quay ve menu chinh.");
            scanner.nextLine();
            Utils.clearScreen();
            break;
        }
    }
}
