package org.java.core.advanced.DesignPatterns.behavioral.Template;

import java.util.ArrayList;
import java.util.List;

public class TemplatePatternTest {

    public static void main(String[] args) {
        AbstractStudentGetter ageGetter = new AgeStudentGetter();
        AbstractStudentGetter nameGetter = new NameStudentGetter();

        List<Student> studentList = new ArrayList<Student>();
        studentList.add(new Student("jim", 22));
        studentList.add(new Student("format", 25));

        System.out.println(ageGetter.getStudent(studentList));
        System.out.println(nameGetter.getStudent(studentList));
    }

}
