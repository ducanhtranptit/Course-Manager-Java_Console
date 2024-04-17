package Object;

import java.util.Scanner;

import Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Teacher {
    private String name;
    private String email;
    private String teacher_id;
    private String class_id; // Mã lớp

    public Teacher() {
    }

    public Teacher(String name, String email, String teacher_id, String class_id) {
        this.name = name;
        this.email = email;
        this.teacher_id = teacher_id;
        this.class_id = class_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    static Scanner scanner = new Scanner(System.in);

    public static Teacher readTeacher() {
        Teacher teacher = new Teacher();
        System.out.print("Nhap ten: ");
        teacher.name = scanner.nextLine();
        System.out.print("Nhap email: ");
        teacher.email = scanner.nextLine();
        System.out.print("Nhap ma giao vien: ");
        teacher.teacher_id = scanner.nextLine();
        System.out.print("Nhap ma lop: "); // Thêm nhập mã lớp
        teacher.class_id = scanner.nextLine();
        return teacher;
    }

    public static List<Teacher> readManyTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        System.out.print("Nhap so luong giao vien trong danh sach: ");
        int total = scanner.nextInt();
        scanner.nextLine(); // consume newline
        for (int i = 0; i < total; i++) {
            System.out.println("\nGiao vien " + (i + 1));
            Teacher teacher = readTeacher();
            teachers.add(teacher);
        }
        return teachers;
    }

    public static void printTeacher(Teacher teacher) {
        System.out.println("---");
        System.out.println("Ten giao vien : " + teacher.name);
        System.out.println("Email giao vien : " + teacher.email);
        System.out.println("Ma giao vien : " + teacher.teacher_id);
        System.out.println("Ma lop : " + teacher.class_id); // In ra mã lớp
        System.out.println("---");
    }

    public static void writeTeacher(Teacher teacher, FileWriter file) throws IOException {
        file.write(teacher.name + "," + teacher.email + "," + teacher.teacher_id + "," + teacher.class_id + "\n");
    }

    public static void writeManyTeachers(List<Teacher> teachers) {
        try {
            FileWriter file = new FileWriter("teachers.csv");
            file.write("Ho va ten,Email,Ma giao vien,Ma lop\n");
            for (Teacher teacher : teachers) {
                writeTeacher(teacher, file);
            }
            file.close();
        } catch (IOException e) {
            System.out.println("Khong the mo file teachers.csv.");
        }
    }

    public static Teacher findTeacher(String id) {
        try {
            BufferedReader file = new BufferedReader(new FileReader("teachers.csv"));
            String row;
            while ((row = file.readLine()) != null) {
                if (row.contains(id)) {
                    String[] rowList = row.split(",");
                    return new Teacher(rowList[0], rowList[1], rowList[2], rowList[3]);
                }
            }
            file.close();
        } catch (IOException e) {
            System.out.println("Khong the mo file teachers.csv.");
        }
        return null;
    }

    public static void printAllDataTeachers() {
        try {
            BufferedReader file = new BufferedReader(new FileReader("teachers.csv"));
            file.readLine(); // skip header
            String row;
            while ((row = file.readLine()) != null) {
                String[] rowList = row.split(",");
                printTeacher(new Teacher(rowList[0], rowList[1], rowList[2], rowList[3]));
            }
            file.close();
        } catch (IOException e) {
            System.out.println("Khong the mo file teachers.csv.");
        }
    }

    public static void addTeacher() {
        System.out.println("Them giao vien");
        Teacher newTeacher = readTeacher();
        try {
            FileWriter file = new FileWriter("teachers.csv", true);
            writeTeacher(newTeacher, file);
            file.close();
            System.out.println("\nXong!\n");
        } catch (IOException e) {
            System.out.println("Khong the mo file teachers.csv.");
        }
    }

    public static void deleteTeacher(String id) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("teachers.csv"));
            List<String> fileContent = new ArrayList<>();
            String row;
            boolean found = false;
            while ((row = fileReader.readLine()) != null) {
                if (row.contains(id)) {
                    found = true;
                    continue; // Skip this line (teacher to be deleted)
                }
                fileContent.add(row);
            }
            fileReader.close();

            if (!found) {
                System.out.println("Không tìm thấy giáo viên với mã số " + id);
                return;
            }

            FileWriter fileWriter = new FileWriter("teachers.csv");
            for (String line : fileContent) {
                fileWriter.write(line + "\n");
            }
            fileWriter.close();
            System.out.println("\nXong!\n");
        } catch (IOException e) {
            System.out.println("Không thể mở file teachers.csv.");
        }
    }

    public static void editTeacher(String id) {
        Teacher existingTeacher = findTeacher(id);
        if (existingTeacher != null) {
            System.out.println("Thông tin giáo viên hiện tại:");
            printTeacher(existingTeacher);
            System.out.println("Nhập thông tin mới cho giáo viên (để trống nếu muốn giữ nguyên):");
            Teacher editedTeacher = readTeacher();

            if (!editedTeacher.name.isEmpty()) {
                existingTeacher.name = editedTeacher.name;
            }
            if (!editedTeacher.email.isEmpty()) {
                existingTeacher.email = editedTeacher.email;
            }

            try {
                BufferedReader fileReader = new BufferedReader(new FileReader("teachers.csv"));
                List<String> fileContent = new ArrayList<>();
                String row;
                boolean found = false;
                while ((row = fileReader.readLine()) != null) {
                    if (row.contains(id)) {
                        found = true;
                        row = existingTeacher.name + "," + existingTeacher.email + "," + existingTeacher.teacher_id;
                    }
                    fileContent.add(row);
                }
                fileReader.close();

                if (!found) {
                    System.out.println("Không tìm thấy giáo viên với mã số " + id);
                    return;
                }

                FileWriter fileWriter = new FileWriter("teachers.csv");
                for (String line : fileContent) {
                    fileWriter.write(line + "\n");
                }
                fileWriter.close();
                System.out.println("\nXong!\n");
            } catch (IOException e) {
                System.out.println("Không thể mở file teachers.csv.");
            }
        } else {
            System.out.println("Không tìm thấy giáo viên với mã số " + id);
        }
    }

    public static void showTeacherMenu() {
        System.out.println("*******************************************");
        System.out.println("* Ban hay chon 1 lua chon:                *");
        System.out.println("* 1. Tao mot danh sach giao vien moi      *");
        System.out.println("* 2. Hien thi toan bo danh sach giao vien *");
        System.out.println("* 3. Them giao vien                       *");
        System.out.println("* 4. Xoa giao vien                        *");
        System.out.println("* 5. Sua thong tin giao vien              *");
        System.out.println("* 6. Quay ve menu chinh                   *");
        System.out.println("*******************************************");
    }

    public static void teacherMain() {
        while (true) {
            Utils.clearScreen();
            showTeacherMenu();
            System.out.print("Ban chon (1-6): ");
            String customerChoice = scanner.nextLine();

            switch (customerChoice) {
                case "1":
                    List<Teacher> teachers = readManyTeachers();
                    writeManyTeachers(teachers);
                    System.out.println("Nhan enter de tiep tuc!");
                    scanner.nextLine();
                    Utils.clearScreen();
                    break;
                case "2":
                    printAllDataTeachers();
                    System.out.println("Nhan enter de tiep tuc!");
                    scanner.nextLine();
                    Utils.clearScreen();
                    break;
                case "3":
                    addTeacher();
                    System.out.println("Nhan enter de tiep tuc!");
                    scanner.nextLine();
                    Utils.clearScreen();
                    break;
                case "4":
                    System.out.print("Ban hay nhap ma giao vien muon xoa: ");
                    String line = scanner.nextLine();
                    deleteTeacher(line);
                    System.out.println("Nhan enter de tiep tuc!");
                    scanner.nextLine();
                    Utils.clearScreen();
                    break;
                case "5":
                    System.out.print("Ban hay nhap ma giao vien ban muon sua thong tin: ");
                    String editId = scanner.nextLine();
                    editTeacher(editId);
                    System.out.println("Nhan enter de tiep tuc!");
                    scanner.nextLine();
                    Utils.clearScreen();
                    break;
                case "6":
                    break;
                default:
                    System.out.println("Ban da nhap sai! Nhan enter de nhap lai!");
                    scanner.nextLine();
                    Utils.clearScreen();
            }
            if (customerChoice.equals("6")) {
                Utils.clearScreen();
                break;
            }
        }
    }
}
