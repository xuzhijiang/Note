package org.java.core.base.copy;

public class Test {

    public static void main(String[] args) {
        StringObject sb = new StringObject();
        sb.setName("123");
        System.out.println(sb);
        change(sb);
        System.out.println(sb.getName());
    }

    public static void change(StringObject sb) {
    	System.out.println(sb);
        sb = new StringObject();
        System.out.println(sb);
        sb.setName("456");
    }

}
class StringObject {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}