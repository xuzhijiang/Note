package org.java.core.base;

class ValueOrReferenceTest {

    public static void main(String[] args) {
        //let’s assume that “red” is pointing to 50 and “blue” is pointing
        //to 100 and these are the memory location of both Balloon objects.
        //50和100是2个对象在heap内存的位置

        Balloon red = new Balloon("Red"); //memory reference 50
        Balloon blue = new Balloon("Blue"); //memory reference 100

        swap(red, blue);
        System.out.println("red color="+red.getColor());
        System.out.println("blue color="+blue.getColor());

        foo(blue);
        System.out.println("blue color="+blue.getColor());
    }

    private static void foo(Balloon balloon) { //baloon=100
        balloon.setColor("Red"); //baloon=100
        balloon = new Balloon("Green"); //baloon=200
        balloon.setColor("Blue"); //baloon = 200
    }

    public static void swap(Object o1, Object o2){
        Object temp = o1;
        o1=o2;
        o2=temp;
    }

    private static class Balloon {

        private String color;

        public Balloon(){}

        public Balloon(String c){
            this.color=c;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

}
