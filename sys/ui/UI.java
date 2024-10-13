package sys.ui;

import sys.admin.Admin;
import sys.exceptions.InvalidLoginException;
import sys.professor.Professor;
import sys.student.Student;
import sys.user.User;

import java.util.*;

public class UI {
    private User user;
    private final Scanner input = new Scanner(System.in);

    public void displayScreen(){
        int choice = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Welcome");
                System.out.println("Enter as:");
                System.out.println("1. Administrator");
                System.out.println("2. Student");
                System.out.println("3. Professor");
                System.out.println("4. Exit Session");
                choice = input.nextInt();
                input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        switch(choice) {
            case 1:
                displayAdminScreen();
                break;
            case 2:
                displayStudentScreen();
                break;
            case 3:
                displayProfessorScreen();
                break;
            case 4:
                System.out.println("Thank you for using the Software.");
                System.out.println("Copyrights: Rachit Bhandari");
                System.exit(0);
                return;
            default:
                System.out.println("Wrong option selected.");
        }
        displayScreen();
    }

    public void displayAdminScreen(){
        int choice = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Choose to enter as an administrator: ");
                System.out.println("1. Login");
                System.out.println("2. Go Back");
                choice = input.nextInt();
                input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        switch(choice){
            case 1:
                user = new Admin();
                try {
                    user.login();
                } catch (InvalidLoginException exception) {
                    System.out.println(exception.getMessage());
                }
                if (user.isLoggedOut()) break;
                user.displayMenu();
                break;
            case 2:
                displayScreen();
                return;
            default:
                System.out.println("Wrong option selected.");
        }
        displayAdminScreen();
    }

    public void displayStudentScreen(){
        int choice = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Choose to enter as a student: ");
                System.out.println("1. Signup");
                System.out.println("2. Login");
                System.out.println("3. Go Back");
                choice = input.nextInt();
                input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        switch(choice){
            case 1:
                user = new Student();
                break;
            case 2:
                System.out.println("Enter Email ID:");
                user = Admin.fetchStudent(input.nextLine());
                if (user!=null){
                    try {
                        user.login();
                    } catch (InvalidLoginException exception) {
                        System.out.println(exception.getMessage());
                    }
                    if (user.isLoggedOut()) break;
                    user.displayMenu();
                }
                else System.out.println("No such student found.");
                break;
            case 3:
                displayScreen();
                return;
            default:
                System.out.println("Wrong option selected.");
        }
        displayStudentScreen();
    }

    public void displayProfessorScreen(){
        int choice = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Choose to enter as a professor: ");
                System.out.println("1. Signup");
                System.out.println("2. Login");
                System.out.println("3. Go Back");
                choice = input.nextInt();
                input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        switch(choice){
            case 1:
                user = new Professor();
                break;
            case 2:
                System.out.println("Enter Email ID:");
                user = Admin.fetchProfessor(input.nextLine());
                if (user!=null){
                    try {
                        user.login();
                    } catch (InvalidLoginException exception) {
                        System.out.println(exception.getMessage());
                    }
                    if (user.isLoggedOut()) break;
                    user.displayMenu();
                }
                else System.out.println("No such professor found.");
                break;
            case 3:
                displayScreen();
                return;
            default:
                System.out.println("Wrong option selected.");
        }
        displayProfessorScreen();
    }
}
