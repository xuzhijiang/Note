package org.java.core.advanced.DesignPatterns.behavioral.command.demo02;

public class Client {
    public static void main(String[] args) {

        Command openCommand,closeCommand,changeCommand;

        openCommand = new OpenTvCommand();
        closeCommand = new CloseTvCommand();
        changeCommand = new ChangeTvCommand();

        Controller controller = new Controller(openCommand, closeCommand, changeCommand);

        // 遥控器的不同的操作对应电视的不同响应
        controller.open();
        controller.change();
        controller.close();
    }
}