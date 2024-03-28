package Object;
import java.util.Scanner;

import Utils.Utils;

public class Start {

    public static Scanner scanner = new Scanner(System.in);

    static void showStartedMenu() {
        System.out.println("*********************************************");
        System.out.println("*  CHUONG TRINH QUAN LY THONG TIN KHOA HOC  *");
        System.out.println("*    Sinh vien thuc hien: Vuong Xuan Loi    *");
        System.out.println("*                                           *");
        System.out.println("*                                           *");
        System.out.println("*                                           *");
        System.out.println("*                                           *");
        System.out.println("*  1. Bat dau                    2. Thoat   *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");
    }

    public static void showChoiceMenu() {
        System.out.println("*******************************************");
        System.out.println("* Ban hay chon 1 lua chon:                *");
        System.out.println("* 1. Quan ly thong tin hoc sinh           *");
        System.out.println("* 2. Quan ly thong tin giao vien          *");
        System.out.println("* 3. Hien thi thong tin khoa hoc          *");
        System.out.println("* 4. Thoat ra man hinh chinh              *");
        System.out.println("*******************************************");
    }

    public static void mainStart() {
        while (true) {
            Utils.clearScreen();
            showStartedMenu();
            System.out.print("Ban chon (1-2): ");
            String startMenuChoice = scanner.nextLine();

            switch (startMenuChoice) {
                case "2":
                    Utils.clearScreen();
                    return;
                case "1":
                    while (true) {
                        Utils.clearScreen();
                        showChoiceMenu();
                        System.out.print("Ban chon (1-4): ");
                        String choiceMenuChoice = scanner.nextLine();
                        switch (choiceMenuChoice) {
                            case "1":
                                Student.studentMain();
                                break;
                            case "2":
                                Teacher.teacherMain();
                                break;
                            case "3":
                                Course.classManagerMain();
                                break;
                            case "4":
                                break;
                            default:
                                System.out.println("Ban da nhap sai! Nhan enter de nhap lai!");
                                scanner.nextLine();
                                Utils.clearScreen();
                        }
                        if (choiceMenuChoice.equals("4")) {
                            Utils.clearScreen();
                            break;
                        }
                    }
                    break;

                default:
                    System.out.println("Ban da nhap sai! Nhan enter de nhap lai!");
                    scanner.nextLine();
                    Utils.clearScreen();
            }
        }
    }

}
