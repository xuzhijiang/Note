package com.android.core.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * json array: [ {"name": "jadyli", "gender": "male"}, {"name": "sanqi", "gender":
 * "male"} ]
 */
public class JSONArrayExample {
    public static void main(String[] args) throws JSONException {

        String[] names = { "jadyli", "Juliet" };
        String[] genders = { "male", "female" };
        int[] ages = { 18, 20 };

        JSONArray students = new JSONArray();

        for (int i = 0; i < names.length; i++) {
            JSONObject student = new JSONObject();
            student.put("name", names[i]);
            student.put("gender", genders[i]);
            student.put("age", ages[i]);
            students.put(student);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("students", students);
        System.out.println(jsonObject.toString(1));
        // 输出如下形式:
        // {“students”:[{“name”:”jadyli”,”gender”:”male”,”age”:18},{“name”:”Juliet”,”gender”:”female”,”age”:20}]}

        JSONObject obj = new JSONObject(jsonObject.toString());
        JSONArray students1 = obj.getJSONArray("students");
        for (int i = 0; i < students1.length(); i++) {
            JSONObject student = students1.getJSONObject(i);
            System.out.println("姓名：" + student.getString("name"));
            System.out.println("性别：" + student.getString("gender"));
            System.out.println("年龄：" + student.getInt("age") + "\n");
        }
    }
}
