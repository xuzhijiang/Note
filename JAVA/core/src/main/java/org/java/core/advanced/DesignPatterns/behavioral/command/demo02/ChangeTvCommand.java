package org.java.core.advanced.DesignPatterns.behavioral.command.demo02;

/**
 * 遥控机换台按钮
 */
public class ChangeTvCommand implements Command{

    private Telvision tv;

    public ChangeTvCommand() {
        tv = new Telvision();
    }

    @Override
    public void execute() {
        tv.changeChannel();
    }

}