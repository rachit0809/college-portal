package sys.admin;

import sys.complaint.Complaint;
import sys.complaint.Status;
import sys.course.Course;
import sys.database.CourseDB;
import sys.database.ProfessorDB;
import sys.database.StudentDB;
import sys.professor.Professor;
import sys.student.Student;
import sys.student.TA;
import sys.user.User;

import java.time.LocalDateTime;
import java.util.*;

public class Admin extends User {

    private static HashSet<Course> courses;
    private static HashSet<Student> students;
    private static HashSet<Professor> professors;
    private static LocalDateTime dropDeadline = LocalDateTime.MAX;

    static {
        Admin.courses = new CourseDB().getData();
        Admin.students = new StudentDB().getData();
        Admin.professors = new ProfessorDB().getData();
    }

    //com.admin constructor and login logout section
    public Admin(){
        signup();
    }

    @Override
    public void signup(){
        this.name = "Admin";
        this.emailID = "admin@iiitd.ac.in";
        this.pass = "youKnowWhoIAm";
    }

    @Override
    public void displayMenu(){
        if(isLoggedOut()) return;
        int in = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Please select from the following options:");
                System.out.println("1. Manage Courses");
                System.out.println("2. Manage Students");
                System.out.println("3. Manage Professors");
                System.out.println("4. Manage Complaints");
                System.out.println("5. Logout");
                in = User.input.nextInt();
                User.input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        switch (in){
            case 1:
                displayCourseCatalog();
                break;
            case 2:
                displayStudentCatalog();
                break;
            case 3:
                displayProfessorCatalog();
                break;
            case 4:
                displayComplaintCatalog();
                break;
            case 5:
                logout();
                return;
            default:
                System.out.println("Invalid input received. Kindly try again.");
        }
        displayMenu();
    }

    // course management section
    //..................................................................................................................
    private void displayCourseCatalog(){
        int in = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Please select from the following options:");
                System.out.println("1. View All Course Records");
                System.out.println("2. View Course Records");
                System.out.println("3. Add Course");
                System.out.println("4. Delete Course");
                System.out.println("5. Update Course");
                System.out.println("6. Update Course Drop Deadline");
                System.out.println("7. Go Back");
                in = User.input.nextInt();
                User.input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        switch (in){
            case 1:
                viewCourse();
                break;
            case 2:
                System.out.println("Enter the course title or course code:");
                viewCourse(fetchCourse(User.input.nextLine()));
                break;
            case 3:
                addCourse(new Course());
                break;
            case 4:
                System.out.println("Enter the course title or course code:");
                delCourse(fetchCourse(User.input.nextLine()));
                break;
            case 5:
                System.out.println("Enter the course title or course code:");
                updateCourse(fetchCourse(User.input.nextLine()));
                break;
            case 6:
                updateDropDeadline();
                break;
            case 7:
                displayMenu();
                return;
            default:
                System.out.println("Invalid input received. Kindly try again.");
        }
        displayCourseCatalog();
    }

    private void viewCourse(){
        if (courses.isEmpty()){
            System.out.println("No courses are present in the system.");
            return;
        }
        int i=1;
        for (Course course:courses){
            System.out.println((i++)+". "+course);
            System.out.println("\n..................................\n");
        }
    }

    //method overloading
    //..................................................................................................................
    private void viewCourse(Course course){
        if (course!=null) System.out.println(course);
        else System.out.println("Course does not exist.");
    }

    private void addCourse(Course course){
        if (courses.contains(course)) { System.out.println("Course already exists."); return;}
        courses.add(course);
        System.out.println("Course added successfully.");
    }

    private void delCourse(Course course){
        if (courses.removeIf(c -> Objects.equals(c,course)))  System.out.println("Course deleted successfully.");
        else System.out.println("Course does not exist.");
    }

    private void updateDropDeadline(){
        int year = 0, month = 0, date = 0, hour = 0, minute = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Enter the year of deadline:");
                year = input.nextInt();
                input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        t = true;
        while(t) {
            try {
                System.out.println("Enter the month of deadline:");
                month = input.nextInt();
                input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        t = true;
        while(t) {
            try {
                System.out.println("Enter the date of deadline:");
                date = input.nextInt();
                input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        t = true;
        while(t) {
            try {
                System.out.println("Enter the hour of deadline (24 hour format):");
                hour = input.nextInt();
                input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        t = true;
        while(t) {
            try {
                System.out.println("Enter the minutes of deadline:");
                minute = input.nextInt();
                input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        dropDeadline = LocalDateTime.of(year, month, date, hour, minute);
        System.out.println("Course drop deadline updated successfully.");
    }

    //com.student management section
    //..................................................................................................................
    private void displayStudentCatalog(){
        if (isLoggedOut()) return;
        int in = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Please select from the following options:");
                System.out.println("1. View All Student Records");
                System.out.println("2. View Student Records");
                System.out.println("3. Update Student Records");
                System.out.println("4. Assign Grade");
                System.out.println("5. Update Semester");
                System.out.println("6. Go Back");
                in = User.input.nextInt();
                User.input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        int cs = 0;
        switch (in){
            case 1:
                viewStudent();
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
                                viewStudent(fetchStudent(rollNo));
                                t = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input received, kindly try again.");
                                input.nextLine();
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Enter email ID:");
                        viewStudent(fetchStudent(User.input.nextLine()));
                        break;
                    default:
                        System.out.println("Invalid input received. Kindly try again.");
                }
                break;
            case 3:
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
                                updateStudent(fetchStudent(rollNo));
                                t = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input received, kindly try again.");
                                input.nextLine();
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Enter email ID:");
                        updateStudent(fetchStudent(User.input.nextLine()));
                        break;
                    default:
                        System.out.println("Invalid input received. Kindly try again.");
                }
                break;
            case 4:
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
                String courseTitleOrCode;
                int grade = 0;
                switch (cs){
                    case 1:
                        int rollNo = 0;
                        t = true;
                        while(t) {
                            try {
                                System.out.println("Enter roll number:");
                                rollNo = User.input.nextInt();
                                User.input.nextLine();
                                t = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input received, kindly try again.");
                                input.nextLine();
                            }
                        }
                        System.out.println("Enter course title or course code:");
                        courseTitleOrCode = User.input.nextLine();
                        t = true;
                        while(t) {
                            try {
                                System.out.println("Enter grade:");
                                grade = User.input.nextInt();
                                User.input.nextLine();
                                t = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input received, kindly try again.");
                                input.nextLine();
                            }
                        }
                        assignGrade(fetchStudent(rollNo),courseTitleOrCode,grade);
                        break;
                    case 2:
                        System.out.println("Enter email ID:");
                        String emailID = User.input.nextLine();
                        System.out.println("Enter course title or course code:");
                        courseTitleOrCode = User.input.nextLine();
                        t = true;
                        while(t) {
                            try {
                                System.out.println("Enter grade:");
                                grade = User.input.nextInt();
                                User.input.nextLine();
                                t = false;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input received, kindly try again.");
                                input.nextLine();
                            }
                        }
                        assignGrade(fetchStudent(emailID),courseTitleOrCode,grade);
                        break;
                    default:
                        System.out.println("Invalid input received. Kindly try again.");
                }
                break;
            case 5:
                changeSemester();
                break;
            case 6:
                displayMenu();
                return;
            default:
                System.out.println("Invalid input received. Kindly try again.");
        }
        displayStudentCatalog();
    }

    private void viewStudent(){
        if (students.isEmpty()){
            System.out.println("No students are present in the system.");
            return;
        }
        int i=1;
        for (Student student:students){
            System.out.println((i++)+". "+student);
            System.out.println("\n..................................\n");
        }
    }

    //method overloading
    private void viewStudent(Student student){
        if (student!=null) System.out.println(student);
        else System.out.println("Student not found.");
    }

    private void updateStudent(Student student){
        if (student==null) { System.out.println("Student not found."); return; }
        System.out.println(student);
        int choice = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Select from the attributes you would like to update:");
                System.out.println("1. Name");
                System.out.println("2. Roll Number");
                System.out.println("3. Email ID");
                System.out.println("4. Phone Number");
                System.out.println("5. Semester");
                choice = User.input.nextInt();
                User.input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        switch(choice){
            case 1:
                System.out.println("Enter Name:");
                student.setName(User.input.nextLine());
                System.out.println("Name updated successfully.");
                break;
            case 2:
                t = true;
                while(t) {
                    try {
                        System.out.println("Enter Roll Number:");
                        int updatedRollNo = User.input.nextInt();
                        User.input.nextLine();
                        student.setRollNo(updatedRollNo);
                        System.out.println("Roll number updated successfully.");
                        t = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input received, kindly try again.");
                        input.nextLine();
                    }
                }
                break;
            case 3:
                System.out.println("Enter Email ID:");
                student.setEmailID(User.input.nextLine());
                System.out.println("Email ID updated successfully.");
                break;
            case 4:
                System.out.println("Enter Phone Number:");
                student.setPhoneNumber(User.input.nextLine());
                System.out.println("Phone number updated successfully.");
                break;
            case 5:
                t = true;
                while(t) {
                    try {
                        System.out.println("Enter Semester:");
                        int updatedSemester = User.input.nextInt();
                        User.input.nextLine();
                        student.setSem(updatedSemester);
                        System.out.println("Semester updated successfully.");
                        t = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input received, kindly try again.");
                        input.nextLine();
                    }
                }
            default:
                System.out.println("Invalid input.");
        }
    }

    private void assignGrade(Student student, String courseTitleOrCode, int grade) {
        if (student == null) { System.out.println("Student not found."); return; }
        student.convertToCompletedCourse(courseTitleOrCode, grade);
    }

    private void changeSemester(){
        for (Student student:students){
            boolean changeSem = true;
            if (student.getCurrCourses().isEmpty()){
                for (Map.Entry<Course,Integer> entry:student.getCompletedCoursesWithGrades().entrySet()){
                    if (entry.getKey().getSem()==student.getSem() && entry.getValue()<4) {changeSem = false; break;}
                }
            }
            if (changeSem) student.setSem(student.getSem()+1);
        }
        System.out.println("Semester of passed students changed successfully.");
    }

    //com.professor management section
    //..................................................................................................................
    private void displayProfessorCatalog(){
        if (isLoggedOut()) return;
        int in = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Please select from the following options:");
                System.out.println("1. View All Professor Records");
                System.out.println("2. View Professor Records");
                System.out.println("3. Assign Professor to Course");
                System.out.println("4. Go Back");
                in = User.input.nextInt();
                User.input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        switch (in){
            case 1:
                viewProfessor();
                break;
            case 2:
                System.out.println("Provide the emailID of the professor:");
                viewProfessor(fetchProfessor(User.input.nextLine()));
                break;
            case 3:
                System.out.println("Enter the course title or the course code:");
                String courseNameOrCode = User.input.nextLine();
                System.out.println("Enter the emailID of the professor:");
                String emailID = User.input.nextLine();
                assignProfessor(fetchProfessor(emailID),fetchCourse(courseNameOrCode));
                break;
            case 4:
                displayMenu();
                return;
            default:
                System.out.println("Invalid input received. Kindly try again.");
        }
        displayProfessorCatalog();
    }

    private void viewProfessor(){
        if (professors.isEmpty()){
            System.out.println("No professors are present in the system.");
            return;
        }
        int i=1;
        for (Professor professor:professors){
            System.out.println((i++)+". "+professor);
            System.out.println("\n..................................\n");
        }
    }

    //method overloading
    private void viewProfessor(Professor professor){
        if (professor!=null) System.out.println(professor);
        else System.out.println("Professor not found.");
    }

    private void assignProfessor(Professor professor, Course course){
        if (course==null){ System.out.println("No such course found."); return; }
        if (professor==null){ System.out.println("No such professor found."); return; }
        if (professor.getCourses().contains(course)){ System.out.println("Professor already linked to this course."); return; }
        if(course.getProf()!=null){ System.out.println("Course already has a professor assigned to it."); return; }
        course.setProf(professor);
        professor.addCourse(course);
        System.out.println("Professor assigned to course successfully.");
    }

    //complaints management section
    //..................................................................................................................
    private void displayComplaintCatalog(){
        if (isLoggedOut()) return;
        int in = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Please select from the following options:");
                System.out.println("1. View All Complaints");
                System.out.println("2. View Pending Complaints");
                System.out.println("3. View Resolved Complaints");
                System.out.println("4. View Complaints By Date");
                System.out.println("5. Update Complaint Status");
                System.out.println("6. Go Back");
                in = User.input.nextInt();
                User.input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        switch (in){
            case 1:
                viewComplaints();
                break;
            case 2:
                viewPendingComplaints();
                break;
            case 3:
                viewResolvedComplaints();
                break;
            case 4:
                System.out.println("Enter date: (dd/mm/yyyy)");
                viewComplaintsByDate(User.input.nextLine());
                break;
            case 5:
                resolveComplaint();
                break;
            case 6:
                displayMenu();
                return;
            default:
                System.out.println("Invalid input received. Kindly try again.");
        }
        displayComplaintCatalog();
    }

    private void viewComplaints(){
        int i=1;
        boolean exist =false;
        for (Student student:students){
            for (Complaint complaint:student.getComplaints()){
                exist = true;
                System.out.println((i++)+". "+complaint);
                System.out.println("\n..................................\n");
            }
        }
        if (!exist) System.out.println("No complaints present.");
    }

    private void viewPendingComplaints(){
        int i=1;
        boolean exist = false;
        for (Student student:students){
            for (Complaint complaint:student.getComplaints()){
                if (complaint.getStatus()== Status.PENDING) {
                    exist = true;
                    System.out.println((i++)+". "+complaint);
                    System.out.println("\n..................................\n");
                }
            }
        }
        if (!exist) System.out.println("No complaints present.");
    }

    private void viewResolvedComplaints(){
        int i=1;
        boolean exist = false;
        for (Student student:students){
            for (Complaint complaint:student.getComplaints()){
                if (complaint.getStatus()== Status.RESOLVED) {
                    exist = true;
                    System.out.println((i++)+". "+complaint);
                    System.out.println("\n..................................\n");
                }
            }
        }
        if (!exist) System.out.println("No complaints present.");
    }

    private void viewComplaintsByDate(String date){
        int i=1;
        boolean exist = false;
        for (Student student:students){
            for (Complaint complaint:student.getComplaints()){
                if (Objects.equals(complaint.getDate(), date)) {
                    exist = true;
                    System.out.println((i++)+". "+complaint);
                    System.out.println("\n..................................\n");
                }
            }
        }
        if (!exist) System.out.println("No complaints present.");
    }

    private void resolveComplaint(){
        viewComplaints();
        int no = 0;
        boolean t = true;
        while(t) {
            try {
                System.out.println("Enter the complaint number:");
                no = User.input.nextInt();
                User.input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        int i=1;
        for (Student student:students) {
            for (Complaint complaint : student.getComplaints()) {
                if (i == no) {
                    System.out.println("Complaint found.");
                    System.out.println(complaint);
                    if (complaint.getStatus() == Status.RESOLVED) {
                        System.out.println("Do you want to change the status to PENDING? (y/n)");
                        String c = User.input.nextLine();
                        switch (c) {
                            case "y", "yes", "YES", "Yes":
                                complaint.setStatus(Status.PENDING);
                                complaint.setRemarks("");
                                System.out.println("Status updated successfully.");
                                return;
                            case "n", "no", "NO", "No":
                                System.out.println("No changes were done.");
                                return;
                            default:
                                System.out.println("Invalid input.");
                                return;
                        }
                    } else {
                        System.out.println("Do you want to change the status to RESOLVED? (y/n)");
                        String c = User.input.nextLine();
                        switch (c) {
                            case "y", "yes", "YES", "Yes":
                                complaint.setStatus(Status.RESOLVED);
                                System.out.println("Enter remarks:");
                                String remarks = User.input.nextLine();
                                complaint.setRemarks(remarks);
                                System.out.println("Status updated successfully.");
                                return;
                            case "n", "no", "NO", "No":
                                System.out.println("No changes were done.");
                                return;
                            default:
                                System.out.println("Invalid input.");
                                return;
                        }
                    }
                }
                i++;
            }
        }
    }

    //Static methods
    //..................................................................................................................

    //course
    public static Course fetchCourse(String courseTitleOrCode){
        for (Course course:courses) if (Objects.equals(course.getTitle(), courseTitleOrCode) || Objects.equals(course.getCode(), courseTitleOrCode)) return course;
        return null;
    }

    public static HashSet<Course> fetchCourses(Professor professor){
        HashSet<Course> profCourses = new HashSet<>();
        for(Course course:courses) if (course.getProf() == professor) profCourses.add(course);
        return profCourses;
    }

    public static HashSet<Course> fetchCourses(int sem){
        HashSet<Course> coursesBySem = new HashSet<>();
        for(Course course:courses) if (course.getSem()==sem) coursesBySem.add(course);
        return coursesBySem;
    }

    public static void updateCourse(Course course){
        if (course==null) {System.out.println("Course not found."); return;}
        System.out.println(course);
        int choice = 0;
        boolean t = true;
        while(t){
            try {
                System.out.println("Select from the attributes you would like to update:");
                System.out.println("1. Course Title");
                System.out.println("2. Course Code");
                System.out.println("3. Credits");
                System.out.println("4. Semester");
                System.out.println("5. Professor");
                System.out.println("6. Prerequisites");
                System.out.println("7. Syllabus");
                System.out.println("8. Location");
                System.out.println("9. Time");
                choice = User.input.nextInt();
                User.input.nextLine();
                t = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        switch(choice){
            case 1:
                System.out.println("Enter new course title:");
                course.setTitle(User.input.nextLine());
                System.out.println("Course title updated successfully.");
                break;
            case 2:
                System.out.println("Enter new course code:");
                course.setCode(User.input.nextLine());
                System.out.println("Course code updated successfully.");
                break;
            case 3:
                int noOfCredits = 0;
                t = true;
                while(t){
                    try {
                        System.out.println("How many credits would you like to assign to this course?");
                        System.out.println("1. 2 credits");
                        System.out.println("2. 4 credits");
                        noOfCredits = User.input.nextInt();
                        User.input.nextLine();
                        t = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input received, kindly try again.");
                        input.nextLine();
                    }
                }
                switch (noOfCredits){
                    case 1:
                        course.setCredits(2);
                        System.out.println("Credits set to 2 successfully.");
                        break;
                    case 2:
                        course.setCredits(4);
                        System.out.println("Credits set to 4 successfully.");
                        break;
                    default:
                        System.out.println("Invalid input.");
                }
                break;
            case 4:
                t = true;
                while(t){
                    try {
                        System.out.println("Enter the new semester:");
                        int sem = User.input.nextInt();
                        User.input.nextLine();
                        if (sem>=1 && sem<=8) { course.setSem(sem); System.out.println("Semester updated successfully."); }
                        else System.out.println("Please enter a valid semester.");
                        t = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input received, kindly try again.");
                        input.nextLine();
                    }
                }
                break;
            case 5:
                System.out.println("Enter the EmailID of the new professor:");
                course.setProf(Admin.fetchProfessor(User.input.nextLine()));
                System.out.println("Professor updated successfully.");
                break;
            case 6:
                int noOfPrereq = 0;
                t = true;
                while(t){
                    try {
                        System.out.println("Enter the number of prerequisites:");
                        noOfPrereq = User.input.nextInt();
                        User.input.nextLine();
                        t = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input received, kindly try again.");
                        input.nextLine();
                    }
                }
                HashSet<Course> prerequisites = new HashSet<>();
                while((noOfPrereq--)>0){
                    System.out.println("Enter the course title or course code:");
                    prerequisites.add(Admin.fetchCourse(User.input.nextLine()));
                }
                course.setPrerequisites(prerequisites);
                System.out.println("Prerequisites updated successfully.");
                break;
            case 7:
                System.out.println("Enter new syllabus:");
                course.setSyllabus(User.input.nextLine());
                System.out.println("Syllabus updated successfully.");
                break;
            case 8:
                System.out.println("Enter new location:");
                course.setLocation(User.input.nextLine());
                System.out.println("Location updated successfully.");
                break;
            case 9:
                t = true;
                while(t){
                    try {
                        System.out.println("Enter new time:");
                        String day = User.input.nextLine();
                        String startTime = User.input.nextLine();
                        double duration = User.input.nextDouble();
                        User.input.nextLine();
                        course.getTime().setTime(day,startTime,duration);
                        System.out.println("Time updated successfully.");
                        t = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input received, kindly try again.");
                        input.nextLine();
                    }
                }
            default:
                System.out.println("Invalid input.");
        }
    }

    //student
    public static void addStudent(Student student){
        students.add(student);
    }

    public static Student fetchStudent(int rollNo){
        for (Student student:students) if (Objects.equals(student.getRollNo(), rollNo)) return student;
        return null;
    }

    public static Student fetchStudent(String emailID){
        for (Student student:students) if (Objects.equals(student.getEmailID(), emailID)) return student;
        return null;
    }

    public static HashSet<Student> fetchStudents(Course course) {
        HashSet<Student> studentsByCourse = new HashSet<>();
        for(Student student:students) if (student.getCurrCourses().contains(course)) studentsByCourse.add(student);
        return studentsByCourse;
    }

    //professor
    public static void addProfessor(Professor professor){
        professors.add(professor);
    }

    public static Professor fetchProfessor(String emailID){
        for (Professor prof:professors) if (Objects.equals(prof.getEmailID(), emailID)) return prof;
        return null;
    }

    public static void convertToTA(Student student){
        System.out.println("Enter the course title or course code for TAShip:");
        Course course = fetchCourse(input.nextLine());
        if (course==null) {System.out.println("Course not found."); return;}
        if (course.getCurrTAs() < course.getTACap()){
            Student ta = new TA(student,course);
            students.remove(student);
            students.add(ta);
            student.logout();
            System.out.println("Registered as a TA successfully.");
            System.out.println("Kindly login again to view TA functionalities.");
            course.setCurrTAs(course.getCurrTAs()+1);
        }
        else{
            System.out.println("The TA-Ship capacity has already been reached.");
        }

    }

    public static LocalDateTime getDropDeadline() {
        return dropDeadline;
    }

    public static void setDropDeadline(LocalDateTime dropDeadline) {
        Admin.dropDeadline = dropDeadline;
    }

}
