package com.example.demo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "students")
public class StudentAccountListWrapper {

    private List<StudentAccount> students;

    @XmlElement(name = "student")
    public List<StudentAccount> getStudents() {
        return students;
    }

    public void setStudents(List<StudentAccount> students) {
        this.students = students;
    }
}
