package sys.student;

import sys.admin.Admin;
import sys.course.Course;
import sys.exceptions.CourseFullException;
import sys.exceptions.DropDeadlinePassedException;
import sys.user.User;

import java.util.InputMismatchException;
import java.util.Map;

public class TA extends Student{
    private final Course TACourse;

    public TA(Student student, Course course){
        this.name = student.getName();
        this.emailID = student.getEmailID();
        this.pass = student.getPass();
        this.isLogin = student.isLogin();
        this.sem = student.getSem();
        this.rollNo = student.getRollNo();
        this.phoneNumber = student.getPhoneNumber();
        this.completedCoursesWithGrades = student.getCompletedCoursesWithGrades();
        this.currCourses = student.getCurrCourses();
        this.complaints = student.getComplaints();
        this.TACourse = course;
    }

    @Override
    public boolean init(){return false;}

    @Override
    public void displayMenu(){
        if (isLoggedOut()) return;
        int in = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Please select from the following options:");
                System.out.println("1. View Your Details");
                System.out.println("2. View Available Courses");
                System.out.println("3. Register Courses");
                System.out.println("4. View Schedule");
                System.out.println("5. Track Academic Progress");
                System.out.println("6. Drop Courses");
                System.out.println("7. Submit Complaints");
                System.out.println("8. View Complaints");
                System.out.println("9. Give Feedback");
                System.out.println("10. View TA Catalog");
                System.out.println("11. Logout");
                in = input.nextInt();
                input.nextLine();
                t = false;
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
                viewAvailableCourses();
                break;
            case 3:
                try{
                    registerCourses();
                }
                catch (DropDeadlinePassedException | CourseFullException exception){
                    System.out.println(exception.getMessage());
                }
                break;
            case 4:
                getSchedule();
                break;
            case 5:
                getAcadProgress();
                break;
            case 6:
                try{
                    System.out.println("Enter the course title or the course code:");
                    dropCourse(input.nextLine());
                }
                catch (DropDeadlinePassedException exception){
                    System.out.println(exception.getMessage());
                }
                break;
            case 7:
                addComplaint();
                break;
            case 8:
                viewComplaints();
                break;
            case 9:
                System.out.println("Enter the course title or the course code:");
                addFeedback(input.nextLine());
                break;
            case 10:
                displayTACatalog();
                break;
            case 11:
                logout();
                return;
            default:
                System.out.println("Invalid input received. Kindly try again.");
        }
        displayMenu();
    }

    private void displayTACatalog(){
        if (isLoggedOut()) return;
        int in = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Please select from the following options:");
                System.out.println("1. View TA-Ship Course Details");
                System.out.println("2. View Student Grades");
                System.out.println("3. Update Student Grades");
                System.out.println("4. Go Back");
                in = input.nextInt();
                input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        int cs = 0;
        switch (in){
            case 1:
                System.out.println(this.getTACourse());
                break;
            case 2:
                t = true;
                while(t) {
                    try {
                        System.out.println("Please select from the following options:");
                        System.out.println("1. Using Roll Number");
                        System.out.println("2. Using Email ID");
                        cs = User.input.nextInt();
                        User.input.nextLine();
                        t = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input received, kindly try again.");
                        input.nextLine();
                    }
                }
                switch (cs){
                    case 1:
                        t = true;
                        while(t) {
                            try {
                                System.out.println("Enter roll number:");
                                int rollNo = User.input.nextInt();
                                User.input.nextLine();
                                viewStudentGrades(Admin.fetchStudent(rollNo));
                                t = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input received, kindly try again.");
                                input.nextLine();
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Enter email ID:");
                        viewStudentGrades(Admin.fetchStudent(input.nextLine()));
                        break;
                    default:
                        System.out.println("Invalid input received. Kindly try again.");
                }
                break;
            case 3:
                t = true;
                while(t){
                    try {
                        System.out.println("Please select from the following options:");
                        System.out.println("1. Using Roll Number");
                        System.out.println("2. Using Email ID");
                        cs = User.input.nextInt();
                        User.input.nextLine();
                        t = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input received, kindly try again.");
                        input.nextLine();
                    }
                }
                switch (cs){
                    case 1:
                        t = true;
                        while(t){
                            try {
                                System.out.println("Enter roll number:");
                                int rollNo = User.input.nextInt();
                                User.input.nextLine();
                                updateStudentGrades(Admin.fetchStudent(rollNo));
                                t = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input received, kindly try again.");
                                input.nextLine();
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Enter email ID:");
                        updateStudentGrades(Admin.fetchStudent(input.nextLine()));
                        break;
                    default:
                        System.out.println("Invalid input received. Kindly try again.");
                }
                break;
            case 4:
                displayMenu();
                return;
            default:
                System.out.println("Invalid input received. Kindly try again.");
        }
        displayTACatalog();
    }

    private void viewStudentGrades(Student student){
        if (student == null) {System.out.println("Student doesn't exist."); return;}
        for (Map.Entry<Course, Integer> entry : student.getCompletedCoursesWithGrades().entrySet()){
            if (entry.getKey() == TACourse){
                System.out.println("GPA in this course: "+entry.getValue());
                return;
            }
        }
        System.out.println("Student hasn't completed or opted for this course yet.");
    }

    private void updateStudentGrades(Student student){
        if (student == null) {System.out.println("Student doesn't exist."); return;}
        for (Map.Entry<Course, Integer> entry : student.getCompletedCoursesWithGrades().entrySet()){
            if (entry.getKey() == TACourse){
                System.out.println("GPA in this course: "+entry.getValue());
                System.out.println("Enter new grade to be assigned to the student:");
                int grade = input.nextInt();
                input.nextLine();
                entry.setValue(grade);
                System.out.println("GPA in this course: "+entry.getValue());
                return;
            }
        }
        System.out.println("Student hasn't completed or opted for this course yet.");
    }

    public Course getTACourse() {
        return TACourse;
    }

    @Override
    public String toString(){
        return ("Name: "+this.getName()+"\nRoll No: "+this.rollNo+"\nEmail ID: "+this.emailID+" | Phone Number: "+this.phoneNumber+"\nSemester: "+this.sem+" | CGPA: "+getCGPA()+"\nTA of the Course: "+this.getTACourse().getTitle()+" | "+this.getTACourse().getCode());
    }
}
