package org.java.core.advanced.DesignPatterns.behavioral.Template;

import java.util.Comparator;
import java.util.List;

// 按照名字字母排序取出第一个学生
public class NameStudentGetter extends AbstractStudentGetter{
    @Override
    public void sort(List<Student> students) {
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });
    }
}
