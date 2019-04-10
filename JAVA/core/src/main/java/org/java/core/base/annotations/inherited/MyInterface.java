package org.java.core.base.annotations.inherited;

@MyAnnotation(value = "MyInterface value")
public interface MyInterface {

    @MyAnnotation("do sth value")
    void doSomething();
}
