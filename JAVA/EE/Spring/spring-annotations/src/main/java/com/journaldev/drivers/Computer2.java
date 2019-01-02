package com.journaldev.drivers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// @PreDestroy和@PostConstruct是bean initMethod和
// destroyMethod的替代方法。 它可以在我们定义bean类时使用
public class Computer2 {

   @PostConstruct
   public void turnOn(){
       System.out.println("Load operating system");
   }

   @PreDestroy
   public void turnOff(){
       System.out.println("Close all programs");
   }
}
