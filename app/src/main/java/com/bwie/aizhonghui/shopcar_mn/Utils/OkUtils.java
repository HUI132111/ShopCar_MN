package com.bwie.aizhonghui.shopcar_mn.Utils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by DANGEROUS_HUI on 2017/10/24.
 */

public class OkUtils {

    public void MyOk(String url, Map<String,String> map, final Callbacks callbacks){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody.Builder builder=new FormBody.Builder();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            builder.add(stringStringEntry.getKey(),stringStringEntry.getValue());
        }
        RequestBody body=builder.build();
        Request request=new Request.Builder()
                .post(body)
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callbacks.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              callbacks.onResponse(call,response);
            }
        });
    }

    public interface Callbacks{
        void onFailure(Call call,IOException e);
        void onResponse(Call call,Response response);
    }
}
