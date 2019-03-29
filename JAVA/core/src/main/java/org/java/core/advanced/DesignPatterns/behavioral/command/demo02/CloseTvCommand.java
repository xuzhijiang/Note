package org.java.core.advanced.DesignPatterns.behavioral.command.demo02;

public class CloseTvCommand implements Command{
    private Telvision tv;

    public CloseTvCommand() {
        tv = new Telvision();
    }

    @Override
    public void execute() {
        tv.close();
    }

}