package com.android.core.Enum;

import android.text.TextUtils;

public enum Type {

    /**
     * 枚举列表
     */
    Logcat(0, "logcat"),
    Tcpdump(1, "tcpdump");

    private String name;

    private int value;

    /**
     * 枚举的构造器是不能被外界调用的，所以private是冗余的
     */
    private Type(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    /**
     * 根据名字获得对应的枚举类型
     *
     * @param name
     * @return 如果存在名字对应的枚举类型，就返回该类型，否则返回null
     */
    public static Type contains(String name) {
        if (!TextUtils.isEmpty(name)) {
            Type[] values = Type.values();
            for (Type type : values) {
                if (name.equals(type.value))
                    return type;
            }
        }
        return null;
    }
}
