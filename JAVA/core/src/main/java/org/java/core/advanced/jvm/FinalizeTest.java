package org.java.core.advanced.jvm;

import java.lang.ref.WeakReference;

public class FinalizeTest {

    public static void main(String[] args) {
        Car car = new Car(9999, "black");
        WeakReference<Car> carWeakReference = new WeakReference<>(car);

        int i = 0;
        while(true) {
            if(carWeakReference.get() != null) {
                i++;
                System.out.println("Object is alive for "+i+" loops - "+carWeakReference);
            } else {
                System.out.println("Object has been collected.");
                break;
            }
        }
    }
}

class Car {
    private double price;
    private String colour;

    public Car(double price, String colour){
        this.price = price;
        this.colour = colour;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("i will be destroyed");
    }
}
