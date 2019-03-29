package org.java.core.advanced.DesignPatterns.behavioral.command.demo02;

/**
 * 遥控器的打开电视按钮(具体命令类)
 */
public class OpenTvCommand implements Command{

    private Telvision tv;

    public OpenTvCommand() {
        tv = new Telvision();
    }

    @Override
    public void execute() {
        tv.open();
    }

}