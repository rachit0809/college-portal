package sys.database;

import sys.course.Course;
import sys.course.Time;


import java.util.HashSet;

public class CourseDB implements Database<Course> {
    private HashSet<Course> data;

    public CourseDB(){
        data = new HashSet<>();
        data.add(new Course("IP","CSE101",1,4,new Time("Monday","9:30AM",3)));
        data.add(new Course("LA","MTH101",1,4,new Time("Tuesday","11:00AM",3)));
        data.add(new Course("DC","ECE101",1,4,new Time("Wednesday","11:00AM",3)));
        data.add(new Course("IHCI","DES101",1,4,new Time("Thursday","4:30PM",3)));
        data.add(new Course("COM","SSH101",1,4,new Time("Friday","1:30PM",3)));
        data.add(new Course("PSS","SSH102",1,2,new Time("Friday","9:30AM",1.5)));
        data.add(new Course("SA","SSH103",1,2,new Time("Friday","3:00PM",1.5)));
    }

    public HashSet<Course> getData(){
        return this.data;
    }
}
