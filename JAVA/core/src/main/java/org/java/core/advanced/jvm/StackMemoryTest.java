package org.java.core.advanced.jvm;

/**
 *  input: // -Xss128K
 */
public class StackMemoryTest {

    private static int count = 0;

    public static void recursion(){
        count++;
        recursion();
    }

    public static void main(String[] args) {
        try {
            recursion();
        } catch (Throwable e){
            System.out.println("counts = " + count);
            e.printStackTrace();
        }
    }
}
