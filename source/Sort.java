package source;

import java.util.Comparator;
import java.util.List;

public class Sort {

    public void sortByFirstName(List<Student> students) {
        students.sort(Comparator.comparing(Student::getFirstName));
    }

    public void sortByLastName(List<Student> students) {
        students.sort(Comparator.comparing(Student::getLastName));
    }

    public void sortByEmail(List<Student> students) {
        students.sort(Comparator.comparing(Student::getEmail));
    }

    public void sortByAge(List<Student> students) {
        students.sort(Comparator.comparing(Student::getAge));
    }

    public void sortByGrade(List<Student> students) {
        students.sort(Comparator.comparing(Student::getGrade));
    }

    public void sortBySubject(List<Student> students) {
        students.sort(Comparator.comparing(Student::getSubject));
    }
}
