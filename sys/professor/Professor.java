package sys.professor;

import sys.admin.Admin;
import sys.student.Student;
import sys.course.Course;
import sys.user.User;
import java.util.HashSet;
import java.util.InputMismatchException;

public class Professor extends User {
    private HashSet<Course> courses;

    public Professor(){
        signup();
        courses = new HashSet<>();
        Admin.addProfessor(this);
    }

    public Professor(String name,String emailID,String password){
        this.name = name;
        this.emailID = emailID;
        this.pass = password;
        courses = new HashSet<>();
    }

    @Override
    public void signup() {
        System.out.println("Enter name:");
        this.name = input.nextLine();
        System.out.println("Enter email ID:");
        this.emailID = input.nextLine();
        System.out.println("Enter password:");
        this.pass = input.nextLine();
        System.out.println("Sign up successful, kindly login to proceed.");
    }

    @Override
    public void displayMenu(){
        if (isLoggedOut()) return;
        refresh();
        int in = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Please select from the following options:");
                System.out.println("1. View Your Details");
                System.out.println("2. View Your Courses");
                System.out.println("3. Update Course Details");
                System.out.println("4. View Enrolled Students");
                System.out.println("5. View Feedbacks");
                System.out.println("6. Logout");
                in = input.nextInt();
                input.nextLine();
                t =false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        switch (in){
            case 1:
                System.out.println(this);
                break;
            case 2:
                viewCourses();
                break;
            case 3:
                viewCourses();
                if (courses.isEmpty()) break;
                System.out.println("Enter the course title or course code:");
                Course course = Admin.fetchCourse(input.nextLine());
                if (courses.contains(course)) Admin.updateCourse(course);
                else System.out.println("This course is not assigned to you.");
                break;
            case 4:
                viewEnrolledStudents();
                break;
            case 5:
                viewFeedbacks();
                break;
            case 6:
                logout();
                return;
            default:
                System.out.println("Invalid input received. Kindly try again.");
                break;
        }
        displayMenu();
    }

    @Override
    public String toString(){
        return ("Name: "+this.name+"\nEmail ID: "+this.emailID);
    }

    private void refresh() {
        this.courses = Admin.fetchCourses(this);
    }

    public HashSet<Course> getCourses(){
        return this.courses;
    }

    public void addCourse(Course course){ this.courses.add(course); }

    private void viewCourses(){
        if (courses.isEmpty()) {System.out.println("No courses assigned."); return;}
        int i=1;
        for (Course course:courses){
            System.out.println((i++)+". "+course);
            System.out.println("\n..................................\n");
        }
    }

    private void viewEnrolledStudents(){
        int j=1;
        boolean flag=false;
        for (Course course:courses){
            System.out.println((j++)+". Course Title: "+course.getTitle()+" | Course Code: "+course.getCode());
            HashSet<Student> enrolledStudents = Admin.fetchStudents(course);
            int i=1;
            for (Student student:enrolledStudents){
                flag = true;
                System.out.println((i++)+". "+student);
                System.out.println("\n...............\n");
            }
            System.out.println("\n..................................\n");
        }
        if (!flag) System.out.println("No enrolled students assigned.");
    }

    private void viewFeedbacks(){
        for (Course course:courses){
            course.viewFeedbacks();
        }
    }
}
