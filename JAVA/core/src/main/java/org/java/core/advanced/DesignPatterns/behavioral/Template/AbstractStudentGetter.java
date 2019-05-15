package org.java.core.advanced.DesignPatterns.behavioral.Template;

import java.util.List;

/**
 * 模板模式
 *
 * 跟策略模式类似，模板模式会先定义好实现的逻辑步骤，
 * 但是具体的实现方式由子类完成，跟策略模式的区别就是模板模式是有逻辑步骤的。
 * 比如要给院系里的学生排序，并取出排名第一的学生。这里就有2个步骤，分别是排序和取出第一名学生。
 */
public abstract class AbstractStudentGetter {

    public final Student getStudent(List<Student> students) {
        sort(students);// 第一步进行排序
        if (!students.isEmpty()) {
            return students.get(0); // 第二步
        }
        return null;
    }

    abstract public void sort(List<Student> students);
}
