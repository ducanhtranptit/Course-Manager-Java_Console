import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Student {
    String name;
    String email;
    String stu_id;
    String mark;
    String class_id;

    public Student() {
    }

    public Student(String name, String email, String stu_id, String mark, String class_id) {
        this.name = name;
        this.email = email;
        this.stu_id = stu_id;
        this.mark = mark;
        this.class_id = class_id;
    }

    public static Scanner scanner = new Scanner(System.in);

    public static Student readStudent() {
        Student sv = new Student();
        System.out.print("Nhap ten: ");
        sv.name = scanner.nextLine();
        System.out.print("Nhap email: ");
        sv.email = scanner.nextLine();
        System.out.print("Nhap ma sinh vien: ");
        sv.stu_id = scanner.nextLine();
        System.out.print("Nhap diem: ");
        sv.mark = scanner.nextLine();
        System.out.print("Nhap ma lop: "); // Thêm nhập mã lớp
        sv.class_id = scanner.nextLine();
        return sv;
    }

    public static List<Student> readManyStudents() {
        List<Student> students = new ArrayList<>();
        System.out.print("Nhap so luong sinh vien co trong danh sach: ");
        int total = scanner.nextInt();
        scanner.nextLine(); // consume newline
        for (int i = 0; i < total; i++) {
            System.out.println("\nSinh vien " + (i + 1));
            Student stu = readStudent();
            students.add(stu);
        }
        return students;
    }

    public static void printStudent(Student sv) {
        System.out.println("---");
        System.out.println("Ten sinh vien : " + sv.name);
        System.out.println("Email sinh vien : " + sv.email);
        System.out.println("Ma sinh vien : " + sv.stu_id);
        System.out.println("Diem sinh vien : " + sv.mark);
        System.out.println("Ma lop : " + sv.class_id); // In ra mã lớp
        System.out.println("---");
    }

    public static void writeStudent(Student sv, FileWriter file) throws IOException {
        file.write(sv.name + "," + sv.email + "," + sv.stu_id + "," + sv.mark + "," + sv.class_id + "\n");
    }

    public static void writeManyStudents(List<Student> students) {
        try {
            FileWriter file = new FileWriter("data.csv");
            file.write("Ho va ten,Email,Ma sinh vien,Diem,Ma lop\n");
            for (Student stu : students) {
                writeStudent(stu, file);
            }
            file.close();
        } catch (IOException e) {
            System.out.println("Khong the mo file data.csv.");
        }
    }

    public static Student findStudent(String id) {
        try {
            BufferedReader file = new BufferedReader(new FileReader("data.csv"));
            String row;
            while ((row = file.readLine()) != null) {
                if (row.contains(id)) {
                    String[] rowList = row.split(",");
                    return new Student(rowList[0], rowList[1], rowList[2], rowList[3], rowList[4]);
                }
            }
            file.close();
        } catch (IOException e) {
            System.out.println("Khong the mo file data.csv.");
        }
        return null;
    }

    public static void printAllDataStudents() {
        try {
            BufferedReader file = new BufferedReader(new FileReader("data.csv"));
            file.readLine(); // skip header
            String row;
            while ((row = file.readLine()) != null) {
                String[] rowList = row.split(",");
                printStudent(new Student(rowList[0], rowList[1], rowList[2], rowList[3], rowList[4]));
            }
            file.close();
        } catch (IOException e) {
            System.out.println("Khong the mo file data.csv.");
        }
    }

    public static void addStudent() {
        System.out.println("Them sinh vien");
        Student newStudent = readStudent();
        try {
            FileWriter file = new FileWriter("data.csv", true);
            writeStudent(newStudent, file);
            file.close();
            System.out.println("\nXong!\n");
        } catch (IOException e) {
            System.out.println("Khong the mo file data.csv.");
        }
    }

    public static void deleteStudent(String id) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("data.csv"));
            List<String> fileContent = new ArrayList<>();
            String row;
            while ((row = fileReader.readLine()) != null) {
                if (!row.contains(id)) {
                    fileContent.add(row);
                }
            }
            fileReader.close();

            FileWriter fileWriter = new FileWriter("data.csv");
            for (String line : fileContent) {
                fileWriter.write(line + "\n");
            }
            fileWriter.close();
            System.out.println("\nXong!\n");
        } catch (IOException e) {
            System.out.println("Khong the mo file data.csv.");
        }
    }

    public static void editStudent(String id) {
        Student existingStudent = findStudent(id);
        if (existingStudent != null) {
            System.out.println("Thong tin sinh vien hien tai:");
            printStudent(existingStudent);
            System.out.println("Nhap thong tin moi cho sinh vien (de trong neu muon giu nguyen):");
            Student editedStudent = readStudent();

            if (!editedStudent.name.isEmpty()) {
                existingStudent.name = editedStudent.name;
            }
            if (!editedStudent.email.isEmpty()) {
                existingStudent.email = editedStudent.email;
            }
            if (!editedStudent.mark.isEmpty()) {
                existingStudent.mark = editedStudent.mark;
            }

            try {
                BufferedReader fileReader = new BufferedReader(new FileReader("data.csv"));
                List<String> fileContent = new ArrayList<>();
                String row;
                while ((row = fileReader.readLine()) != null) {
                    if (row.contains(id)) {
                        row = existingStudent.name + "," + existingStudent.email + "," + existingStudent.stu_id + ","
                                + existingStudent.mark;
                    }
                    fileContent.add(row);
                }
                fileReader.close();

                FileWriter fileWriter = new FileWriter("data.csv");
                for (String line : fileContent) {
                    fileWriter.write(line + "\n");
                }
                fileWriter.close();
                System.out.println("\nXong!\n");
            } catch (IOException e) {
                System.out.println("Khong the mo file data.csv.");
            }
        } else {
            System.out.println("Khong tim thay sinh vien voi ma so " + id);
        }
    }

    public static void showStudentMenu() {
        System.out.println("*******************************************");
        System.out.println("* Ban hay chon 1 lua chon:                *");
        System.out.println("* 1. Tao mot danh sach sinh vien moi      *");
        System.out.println("* 2. Hien thi toan bo danh sach sinh vien *");
        System.out.println("* 3. Tim kiem sinh vien theo MSV          *");
        System.out.println("* 4. Them sinh vien                       *");
        System.out.println("* 5. Xoa sinh vien theo MSV               *");
        System.out.println("* 6. Sua thong tin sinh vien theo MSV     *");
        System.out.println("* 7. Quay ve menu chinh                   *");
        System.out.println("*******************************************");
    }

    public static void studentMain() {
        while (true) {
            Utils.clearScreen();
            Student.showStudentMenu();
            System.out.print("Ban chon (1-7): ");
            String customerChoice = scanner.nextLine();

            switch (customerChoice) {
                case "1":
                    List<Student> students = Student.readManyStudents();
                    Student.writeManyStudents(students);
                    System.out.println("Nhan enter de tiep tuc!");
                    scanner.nextLine();
                    Utils.clearScreen();
                    break;
                case "2":
                    Student.printAllDataStudents();
                    System.out.println("Nhan enter de tiep tuc!");
                    scanner.nextLine();
                    Utils.clearScreen();
                    break;
                case "3":
                    System.out.print("Ban hay nhap ma sinh vien muon tim kiem: ");
                    String id = scanner.nextLine();
                    Student foundStudent = Student.findStudent(id);
                    if (foundStudent != null) {
                        Student.printStudent(foundStudent);
                    } else {
                        System.out.println("Khong tim thay sinh vien voi ma so " + id);
                    }
                    System.out.println("Nhan enter de tiep tuc!");
                    scanner.nextLine();
                    Utils.clearScreen();
                    break;
                case "4":
                    Student.addStudent();
                    System.out.println("Nhan enter de tiep tuc!");
                    scanner.nextLine();
                    Utils.clearScreen();
                    break;
                case "5":
                    System.out.print("Ban hay nhap ma sinh vien ban muon xoa: ");
                    String line = scanner.nextLine();
                    Student.deleteStudent(line);
                    System.out.println("Nhan enter de tiep tuc!");
                    scanner.nextLine();
                    Utils.clearScreen();
                    break;
                case "6":
                    System.out.print("Ban hay nhap ma sinh vien ban muon sua thong tin: ");
                    String editId = scanner.nextLine();
                    Student.editStudent(editId);
                    System.out.println("Nhan enter de tiep tuc!");
                    scanner.nextLine();
                    Utils.clearScreen();
                    break;
                case "7":
                    break;
                default:
                    System.out.println("Ban da nhap sai! Nhan enter de nhap lai!");
                    scanner.nextLine();
                    Utils.clearScreen();
            }

            if (customerChoice.equals("7")) {
                Utils.clearScreen();
                break;
            }
        }
    }

}
