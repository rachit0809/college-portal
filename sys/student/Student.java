package sys.student;

import sys.admin.Admin;
import sys.complaint.Complaint;
import sys.course.Course;
import sys.exceptions.CourseFullException;
import sys.exceptions.DropDeadlinePassedException;
import sys.feedback.FeedbackForm;
import sys.user.User;

import java.time.LocalDateTime;
import java.util.*;

public class Student extends User {
    protected int sem = 1;
    protected int rollNo;
    protected String phoneNumber;
    protected Map<Course, Integer> completedCoursesWithGrades;
    protected HashSet<Course> currCourses;
    protected final int MAXCREDITS = 20;
    protected HashSet<Complaint> complaints;

    public Student(){
        if (init()){
            signup();
            this.completedCoursesWithGrades = new HashMap<>();
            this.currCourses = new HashSet<>();
            this.complaints = new HashSet<>();
            Admin.addStudent(this);
        }
    }

    public boolean init(){return true;}

    public Student(String name, String emailID, String phoneNumber, String password, int rollNo){
        this.name = name;
        this.emailID = emailID;
        this.phoneNumber = phoneNumber;
        this.pass = password;
        this.rollNo = rollNo;
        this.completedCoursesWithGrades = new HashMap<>();
        this.currCourses = new HashSet<>();
        this.complaints = new HashSet<>();

    }

    @Override
    public void signup(){
        System.out.println("Enter name:");
        this.name = input.nextLine();
        boolean t = true;
        while(t) {
            try {
                System.out.println("Enter roll no:");
                this.rollNo = input.nextInt();
                input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        System.out.println("Enter phone number:");
        this.phoneNumber = input.nextLine();
        System.out.println("Enter email ID:");
        this.emailID = input.nextLine();
        System.out.println("Enter password:");
        this.pass = input.nextLine();
        System.out.println("Sign up successful, kindly login to proceed.");
    }

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
                System.out.println("10. Register as a TA");
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
                Admin.convertToTA(this);
                break;
            case 11:
                logout();
                return;
            default:
                System.out.println("Invalid input received. Kindly try again.");
        }
        displayMenu();

    }

    @Override
    public String toString(){
        return ("Name: "+this.getName()+"\nRoll No: "+this.rollNo+"\nEmail ID: "+this.emailID+" | Phone Number: "+this.phoneNumber+"\nSemester: "+this.sem+" | CGPA: "+getCGPA());
    }

    protected HashSet<Course> getAvailableCourses(){
        return Admin.fetchCourses(this.sem);
    }

    protected void viewAvailableCourses(){
        HashSet<Course> availCourses = getAvailableCourses();
        if (availCourses.isEmpty()) {System.out.println("No courses are available at the moment."); return;}
        System.out.println("Here are the available courses:");
        int i=1;
        for (Course course:availCourses){
            System.out.println((i++)+". "+course);
            System.out.println("\n..................................\n");
        }
    }

    protected void registerCourses() throws CourseFullException,DropDeadlinePassedException {
        if (LocalDateTime.now().isAfter(Admin.getDropDeadline())) throw new DropDeadlinePassedException("Drop deadline passed!");
        HashSet<Course> availCourses = getAvailableCourses();
        int currCredits=0;
        for (Course course:currCourses) currCredits+=course.getCredits();
        Course course = null;
        int creds=0;
        while (currCredits+creds<=MAXCREDITS){
            currCredits += creds;
            if (course!=null) { currCourses.add(course); course.setCurrStudents(course.getCurrStudents()+1); System.out.println("Course registered successfully.");}
            if (currCredits == MAXCREDITS) {System.out.println("No more courses can be registered."); return;}
            viewAvailableCourses();
            int cs = 0;
            boolean t = true;
            while(t) {
                try {
                    System.out.println("Enter 0 to exit.");
                    System.out.println("OR");
                    System.out.println("Enter the number of the course you would like to register:");
                    cs = input.nextInt();
                    input.nextLine();
                    t = false;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input received, kindly try again.");
                    input.nextLine();
                }
            }
            switch(cs){
                case 0:
                    return;
                default:
                    int i=0;
                    for (Course availableCourse:availCourses){
                        i++;
                        if (i==cs){
                            boolean prereqMet = true;
                            for (Course preqCourse : availableCourse.getPrerequisites()) {
                                if (!completedCoursesWithGrades.containsKey(preqCourse) || completedCoursesWithGrades.get(preqCourse) < 4) { prereqMet = false; break; }
                            }
                            if (availableCourse.getCurrStudents() >= availableCourse.getStudentCap()) throw new CourseFullException("Course is already full!");
                            else if (prereqMet) {
                                course = availableCourse;
                                creds = course.getCredits();
                                break;
                            }
                        }
                    }
            }
        }
        System.out.println("Credit limit cannot be exceeded.");
    }

    protected void getSchedule(){
        if (currCourses.isEmpty()) {System.out.println("No courses registered yet."); return;}
        int i=1;
        for(Course course:currCourses){
            System.out.println((i++)+". "+course);
        }
    }

    protected void getAcadProgress(){
        System.out.println("CGPA: "+getCGPA());
        System.out.println("Total Credits Completed: "+getCredits());
        for (int i=1;i<this.sem;i++){
            System.out.println("SGPA in Semester "+i+" is: "+getSGPA(i));
        }

    }

    protected void dropCourse(String courseTitleOrCode) throws DropDeadlinePassedException {
        if (LocalDateTime.now().isAfter(Admin.getDropDeadline())) throw new DropDeadlinePassedException("Drop deadline passed!");
        for (Course course:currCourses){
            if (Objects.equals(course.getTitle(), courseTitleOrCode) || Objects.equals(course.getCode(), courseTitleOrCode)) {currCourses.remove(course); System.out.println("Course dropped successfully."); return;}
        }
        System.out.println("No such course found.");
    }

    protected void addComplaint(){
        Complaint complaint = new Complaint();
        complaints.add(complaint);
        System.out.println("Complaint added successfully.");
    }

    protected void viewComplaints(){
        if (complaints.isEmpty()) {System.out.println("No complaints to show."); return;}
        int i=1;
        for (Complaint complaint:complaints){
            System.out.println((i++)+". "+complaint);
            System.out.println("\n..................................\n");
        }
    }

    protected void addFeedback(String courseTitleOrCode){
        for (Map.Entry<Course, Integer> entry : completedCoursesWithGrades.entrySet()){
            if (Objects.equals(entry.getKey().getTitle(), courseTitleOrCode) || Objects.equals(entry.getKey().getCode(), courseTitleOrCode)) {
                entry.getKey().addFeedback(new FeedbackForm());
                System.out.println("Feedback added successfully.");
                return;
            }
        }
        System.out.println("No such course found.");
    }

    public void convertToCompletedCourse(String courseTitleOrCode,int grade){
        for (Course course:currCourses){
            if (Objects.equals(course.getTitle(), courseTitleOrCode) || Objects.equals(course.getCode(), courseTitleOrCode)){
                completedCoursesWithGrades.put(course,grade);
                currCourses.remove(course);
                System.out.println("Grade assigned successfully.");
                return;
            }
        }
        System.out.println("No such course found.");
    }

    //getters and setters
    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Map<Course, Integer> getCompletedCoursesWithGrades() {
        return completedCoursesWithGrades;
    }

    public void setCompletedCoursesWithGrades(Map<Course, Integer> completedCoursesWithGrades) {
        this.completedCoursesWithGrades = completedCoursesWithGrades;
    }

    public HashSet<Course> getCurrCourses() {
        return currCourses;
    }

    public void setCurrCourses(HashSet<Course> currCourses) {
        this.currCourses = currCourses;
    }

    public int getCredits(){
        int totalcreds=0;
        for (Map.Entry<Course, Integer> entry : completedCoursesWithGrades.entrySet()){
            totalcreds += entry.getKey().getCredits();
        }
        return totalcreds;
    }

    public int getCGPA() {
        int cgpa=0;
        int totalcreds=0;
        for (Map.Entry<Course, Integer> entry : completedCoursesWithGrades.entrySet()){
            totalcreds += entry.getKey().getCredits();
            cgpa += entry.getValue()*entry.getKey().getCredits();
        }
        if (totalcreds==0) return 0;
        cgpa /= totalcreds;
        return cgpa;
    }

    public int getSGPA(int sem) {
        int sgpa=0;
        int creds=0;
        for (Map.Entry<Course, Integer> entry : completedCoursesWithGrades.entrySet()){
            if (entry.getKey().getSem() == sem) {
                creds += entry.getKey().getCredits();
                sgpa += entry.getValue() * entry.getKey().getCredits();
            }
        }
        if (creds==0) return 0;
        sgpa /= creds;
        return sgpa;
    }

    public HashSet<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(HashSet<Complaint> complaints) {
        this.complaints = complaints;
    }
}
