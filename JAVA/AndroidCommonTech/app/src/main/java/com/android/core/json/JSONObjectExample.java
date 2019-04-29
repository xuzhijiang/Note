package com.android.core.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * json object: {"name": "jadyli", "gender": "male"}
 * 如果值是String类型且含有双引号或冒号，需要使用”\”转义。
 */
public class JSONObjectExample {

    public static void main(String[] args) throws JSONException  {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "xzj");
        jsonObject.put("gender", "male");
        jsonObject.put("age", 18);
        System.out.println(jsonObject.toString(1));

        String json = "{\"name\": \"jadyli\", \"gender\": \"male\", \"age\": 18}";
        JSONObject obj = new JSONObject(json);
        System.out.println("name：" + obj.getString("name"));
        System.out.println("gender：" + obj.getString("gender"));
        System.out.println("age：" + obj.getString("age"));
    }

}
