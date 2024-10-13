package sys.complaint;

import java.util.*;

public class Complaint {
    private String complaint;
    private Status status;
    private String remarks = "";
    private String date;
    private final Scanner input = new Scanner(System.in);

    public Complaint(){
        addComplaint();
    }

    public void addComplaint() {
        System.out.println("Enter your complaint:");
        this.complaint = input.nextLine();
        System.out.println("Enter date: (dd/mm/yyyy)");
        this.date = input.nextLine();
        this.status = Status.PENDING;
    }

    @Override
    public String toString(){
        return ("Complaint: "+this.complaint+"\nDate: "+this.date+" | Status: "+this.status+"\nRemarks: "+this.remarks);
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
