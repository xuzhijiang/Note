package com.journaldev.drivers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
