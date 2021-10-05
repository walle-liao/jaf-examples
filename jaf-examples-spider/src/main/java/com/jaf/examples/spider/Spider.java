package com.jaf.examples.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.java.Log;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: liaozhicheng
 * @date: 2021/10/5
 */
@Log
public class Spider {

    public static void main(String[] args) {

        // 参考有道笔记 Spider
        String url = "";
        String token = "";

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("jtoken", token)
                .addHeader("clientType", "WX_PROGRAM")
                .addHeader("userType", "USER_CUSTOMER")
                .build();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Call call = okHttpClient.newCall(request);
                    Response response = call.execute();
                    String result = response.body().string();
                    parseResponse(result);
                } catch (IOException e) {
                    System.out.println(e.getCause());
                }
            }
        }, new Date(), 2000L);
    }


    private static void parseResponse(String responseJson) {
        try {
            JSONObject jo = JSON.parseObject(responseJson);
            JSONArray ja = jo.getJSONObject("data").getJSONArray("source");
            for(int i = 0; i < ja.size(); i++) {
                JSONObject o = ja.getJSONObject(i);
                if("满号".equals(o.getString("memo"))) {
                    System.out.println(o.getString("begintime") + " - 满号");
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    System.err.println(o.getString("begintime") + " - 有号.......");
                }
            }
        } catch (Exception e ) {
            System.out.println("error : " + responseJson);
        }
    }

}
