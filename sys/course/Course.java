package sys.course;

import sys.admin.Admin;
import sys.feedback.FeedbackForm;
import sys.professor.Professor;
import java.util.*;

public class Course {
    private String title;
    private String code;
    private Professor prof;
    private int sem;
    private int credits;
    private HashSet<Course> prerequisites;
    private String syllabus = "Refer to Tech Tree";
    private String location = "C101 LHC";
    private Time time;
    private HashSet<FeedbackForm> feedbacks;
    private int currStudents = 0;
    private int currTAs = 0;
    private int studentCap = 2;
    private int TACap = 2;
    private final Scanner input = new Scanner(System.in);

    public Course(){
        System.out.println("Enter course title:");
        this.title = input.nextLine();
        System.out.println("Enter course code:");
        this.code = input.nextLine();
        boolean t = true;
        while(t) {
            try {
                System.out.println("Enter the semester in which you would like the course to be offered:");
                this.sem = input.nextInt();
                input.nextLine();
                t = false;
                if (this.sem < 1 || this.sem > 8) this.sem = 1;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        t = true;
        while(t) {
            try {
                System.out.println("How many credits would you like to assign to this course?");
                System.out.println("1. 2 credits");
                System.out.println("2. 4 credits");
                int noOfCredits = input.nextInt();
                input.nextLine();
                switch (noOfCredits){
                    case 1:
                        this.credits = 2;
                        break;
                    case 2:
                        this.credits = 4;
                        break;
                    default:
                        throw new InputMismatchException();
                }
                t =false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        prerequisites = new HashSet<>();
        t = true;
        while(t) {
            try {
                System.out.println("Enter the number of prerequisites:");
                int noOfPrereq = input.nextInt();
                input.nextLine();
                t =false;
                while((noOfPrereq--)>0){
                    System.out.println("Enter the course title or the course code:");
                    String courseTitleOrCode = input.nextLine();
                    prerequisites.add(Admin.fetchCourse(courseTitleOrCode));
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        System.out.println("Enter syllabus:");
        this.syllabus = input.nextLine();
        System.out.println("Enter location:");
        this.location = input.nextLine();
        this.time = new Time();
        t = true;
        while(t) {
            try {
                System.out.println("Enter Student Capacity:");
                this.studentCap = input.nextInt();
                input.nextLine();
                t =false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        t = true;
        while(t) {
            try {
                System.out.println("Enter TA Capacity:");
                this.TACap = input.nextInt();
                input.nextLine();
                t =false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        this.feedbacks = new HashSet<>();
    }

    public Course(String title, String code, int sem, int creds, Time time){
        this.title = title;
        this.code = code;
        this.sem = sem;
        this.credits = creds;
        this.time = time;
        this.prerequisites = new HashSet<>();
        this.feedbacks = new HashSet<>();
    }

    @Override
    public String toString(){
        return ("Course Title: "+this.getTitle()+" | Course Code: "+this.getCode()+"\nCourse Credits: "+this.credits+"\nSemester: "+this.sem+ (this.prof == null ? "" : "\nProfessor: "+this.prof)+"\nPrerequisites: "+(prerequisites.isEmpty() ? "None":prerequisites)+"\nSyllabus: "+this.syllabus+"\nLocation: "+this.location+"\nTimings: "+this.time);
    }

    public void addFeedback(FeedbackForm feedback){
        this.feedbacks.add(feedback);
    }

    public void viewFeedbacks(){
        System.out.println(this);
        if (this.feedbacks.isEmpty()){ System.out.println("No feedbacks available at the moment."); return;}
        for (FeedbackForm feedback : feedbacks){
            System.out.println("\n"+feedback);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Professor getProf() {
        return prof;
    }

    public void setProf(Professor prof) {
        this.prof = prof;
    }

    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public HashSet<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(HashSet<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getTACap() {
        return TACap;
    }

    public void setTACap(int TACap) {
        this.TACap = TACap;
    }

    public HashSet<FeedbackForm> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(HashSet<FeedbackForm> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public int getCurrStudents() {
        return currStudents;
    }

    public void setCurrStudents(int currStudents) {
        this.currStudents = currStudents;
    }

    public int getCurrTAs() {
        return currTAs;
    }

    public void setCurrTAs(int currTAs) {
        this.currTAs = currTAs;
    }

    public int getStudentCap() {
        return studentCap;
    }

    public void setStudentCap(int studentCap) {
        this.studentCap = studentCap;
    }


}
