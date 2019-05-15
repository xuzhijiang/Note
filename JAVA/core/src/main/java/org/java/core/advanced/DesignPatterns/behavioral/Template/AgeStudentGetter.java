package org.java.core.advanced.DesignPatterns.behavioral.Template;

import java.util.Comparator;
import java.util.List;

// // 取出年纪最大的学生
public class AgeStudentGetter extends AbstractStudentGetter{
    @Override
    public void sort(List<Student> students) {
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.getAge() - o1.getAge();
            }
        });
    }
}
