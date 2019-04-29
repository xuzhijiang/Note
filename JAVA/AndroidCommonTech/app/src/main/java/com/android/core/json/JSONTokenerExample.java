package com.android.core.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

// 这个类主要是用来解析json的
public class JSONTokenerExample {

    public static void main(String[] args) throws JSONException {
        String jsonStr = "{\"students\":[" +
                "{\"name\":\"jadyli\",\"gender\":\"male\",\"age\":18}," +
                "{\"name\":\"Juliet\",\"gender\":\"female\",\"age\":20}]}";
        JSONTokener jsonTokener = new JSONTokener(jsonStr);

        // nextValue()将json读成了JSONObject
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();

        JSONArray students = jsonObject.getJSONArray("students");

        for (int i = 0; i < students.length(); i++) {
            JSONObject student = students.getJSONObject(i);
            System.out.println("姓名：" + student.getString("name"));
            System.out.println("性别：" + student.getString("gender"));
            System.out.println("年龄：" + student.getInt("age") + "\n");
        }
    }
}
