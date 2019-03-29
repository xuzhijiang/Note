package org.java.core.advanced.DesignPatterns.behavioral.command.demo02;

public class Controller {

    private Command openTvCommand;
    private Command closeTvCommand;
    private Command changeTvCommand;

    public Controller(Command openTvCommand, Command closeTvCommand,
                      Command changeTvCommand) {
        super();
        this.openTvCommand = openTvCommand;
        this.closeTvCommand = closeTvCommand;
        this.changeTvCommand = changeTvCommand;
    }

    public void open(){
        openTvCommand.execute();
    }

    public void close(){
        closeTvCommand.execute();
    }

    public void change(){
        changeTvCommand.execute();
    }
}