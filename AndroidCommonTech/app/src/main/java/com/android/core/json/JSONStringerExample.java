package com.android.core.json;

import org.json.JSONException;
import org.json.JSONStringer;

// 主要用JSONStringer来创建json。
public class JSONStringerExample {
    public static void main(String[] args) throws JSONException {
        JSONStringer jsonStringer = new JSONStringer();

        // 开始编码新对象。 每次调用此方法都必须与对endObject的调用配对。
        jsonStringer.object();

        jsonStringer.key("students");

        jsonStringer.array();
        String[] names = {"jadyli", "Juliet"};
        String[] genders = {"male", "female"};
        int[] ages = {18, 20};
        for (int i = 0; i < names.length; i++) {
            jsonStringer.object();
            jsonStringer.key("name");
            jsonStringer.value(names[i]);
            jsonStringer.key("gender");
            jsonStringer.value(genders[i]);
            jsonStringer.key("age");
            jsonStringer.value(ages[i]);
            jsonStringer.endObject();
        }
        jsonStringer.endArray();

        // 与object()配对
        jsonStringer.endObject();

        System.out.println(jsonStringer.toString());
        // {“students”:[{“name”:”jadyli”,”gender”:”male”,”age”:18},{“name”:”Juliet”,”gender”:”female”,”age”:20}]}
    }
}
