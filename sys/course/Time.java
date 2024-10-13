package sys.course;

import java.util.*;

public class Time {
    private String day;
    private String startTime;
    private double duration;
    private final Scanner input = new Scanner(System.in);

    public Time(){
        System.out.println("Enter the timings of this course:");
        System.out.println("Enter day:");
        String day = input.nextLine();
        System.out.println("Enter start time:");
        String startTime = input.nextLine();
        double duration = 0.0;
        boolean t = true;
        while (t){
            try{
                System.out.println("Enter the duration (in hours):");
                duration = input.nextDouble();
                input.nextLine();
                t = false;
            }
            catch (InputMismatchException e){
                System.out.println("Invalid input received, kindly try again.");
                input.nextLine();
            }
        }
        setTime(day,startTime,duration);
    }

    public Time(String day,String startTime,double duration){
        this.day = day;
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public String toString(){
        return ("Day: "+this.day+" | Time: "+this.startTime+" | Duration: "+this.duration+" hours");
    }

    public void setTime(String day, String startTime, double duration){
        this.day = day;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public double getDuration() {
        return duration;
    }

    public Time getTime(){
        return this;
    }
}
