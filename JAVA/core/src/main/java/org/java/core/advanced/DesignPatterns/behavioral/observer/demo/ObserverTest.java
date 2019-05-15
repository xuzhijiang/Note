package org.java.core.advanced.DesignPatterns.behavioral.observer.demo;

public class ObserverTest {

    public static void main(String[] args) {
        MetricsObservable metricsObserable = new MetricsObservable();
        metricsObserable.addObserver(new AdminA());
        metricsObserable.addObserver(new AdminB());
        metricsObserable.updateCounter("request-count", 100l);
    }
}
