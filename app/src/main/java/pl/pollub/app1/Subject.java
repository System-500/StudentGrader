package pl.pollub.app1;
import java.io.Serializable;
public class Subject implements Serializable{
    private final String subjectName;
    private int SubjectGrade;

    public Subject(String subjectName, int subjectGrade) {
        this.subjectName = subjectName;
        SubjectGrade = subjectGrade;
    }

    public Subject(String subjectName) {
        this.SubjectGrade = 2;
        this.subjectName = subjectName;
    }

    public void setSubjectGrade(int subjectGrade) {
        SubjectGrade = subjectGrade;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getSubjectGrade() {
        return SubjectGrade;
    }


}
