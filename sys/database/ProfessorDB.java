package sys.database;

import sys.professor.Professor;
import java.util.HashSet;

public class ProfessorDB implements Database<Professor>{
    private HashSet<Professor> data;

    public ProfessorDB(){
        data = new HashSet<>();
        data.add(new Professor("Dr. Sonal Keshwani","sonal@iiitd.ac.in","sk"));
        data.add(new Professor("Dr. Tammam Tilo","tammam@iiitd.ac.in","tt"));
        data.add(new Professor("Dr. Md. Shad Akhtar","shad@iiitd.ac.in","msa"));
        data.add(new Professor("Dr. Payel C. Mukherjee","payel@iiitd.ac.in","pcm"));
        data.add(new Professor("Dr. Subhajit Ghosechowdhry","subhajit@iiitd.ac.in","sg"));
    }

    public HashSet<Professor> getData(){
        return this.data;
    }
}
