package sys.database;

import sys.student.Student;
import java.util.HashSet;

public class StudentDB implements Database<Student>{
    private HashSet<Student> data;

    public StudentDB(){
        data = new HashSet<>();
        data.add(new Student("Rachit Bhandari","rachit1@iiitd.ac.in","+91-987654321","rb",1));
        data.add(new Student("Aakanksha","aakanksha2@iiitd.ac.in","+91-987654322","a",2));
        data.add(new Student("Eshmeet Bhachu","eshmeet3@iiitd.ac.in","+91-987654323","eb",3));
        data.add(new Student("Palak Yadav","palak4@iiitd.ac.in","+91-987654324","py",4));
        data.add(new Student("Jasleen Kaur","jasleen5@iiitd.ac.in","+91-987654325","jk",5));
    }

    public HashSet<Student> getData(){
        return this.data;
    }
}
