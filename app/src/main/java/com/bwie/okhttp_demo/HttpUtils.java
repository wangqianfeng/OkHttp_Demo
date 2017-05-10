package com.bwie.okhttp_demo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 用途：
 * author：王倩凤Administrator
 * date:2017/5/10.
 */

public class HttpUtils {


    final OkHttpClient okHttpClient = new OkHttpClient();

    private final Handler mHandler;
    private String mString;
    private String mString1;

    public HttpUtils(Handler mHandler) {
        this.mHandler = mHandler;
    }

    //get同步
    public void byGetExecute() {
        final Request request = new Request.Builder()
                .url("http://result.eolinker.com/gfGTLlHc049c6b450500b16971f52bd8e83f6b2fed305ab?uri=news")
                .build();
        new Thread(){
            @Override
            public void run() {
                Response response = null;
                try {

                    response = okHttpClient.newCall(request).execute();
                    //同步请求
                    if (!response.isSuccessful()){
                        throw new IOException("Unexpected code " + response);
                    }
                    Headers headers = response.headers();
                    for (int i = 0; i <headers.size() ; i++) {
                        Log.d("xxx=",headers.name(i)+"===="+headers.value(i));
                    }

                    mString = response.body().string();

                    response.body().close();

                    Message message = new Message();
                    message.what=0;
                    message.obj=mString;
                    mHandler.sendMessage(message);



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //get异步
    public void byGetEnqueue(){

        final Request request = new Request.Builder()
                .url("http://result.eolinker.com/gfGTLlHc049c6b450500b16971f52bd8e83f6b2fed305ab?uri=news")
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {

                    private String mString1;

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        mString1 = response.body().string();


                        response.body().close();

                        Message message = new Message();
                        message.what=1;
                        message.obj=mString1;
                        mHandler.sendMessage(message);

                    }
                });
            }
        }).start();

    }

    public void byPostExecute(){
        new Thread(){

            private String mString2;

            @Override
            public void run() {
                super.run();

                RequestBody formBody = new FormBody.Builder()
                        .add("page", "1")
                        .add("code", "news")
                        .add("pageSize", "20")
                        .add("parentid", "0")
                        .add("type", "1")
                        .build();

                Request request = new Request.Builder()
                        .url("http://admin.wap.china.com/user/NavigateTypeAction.do?processID=getNavigateNews")
                        .post(formBody)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();

                    if (!response.isSuccessful()){
                        throw new IOException("Unexpected code"+response);
                    }

                    mString2 = response.body().string();
                    Log.d("xxx==",mString2);

                    response.body().close();

                    Message message = new Message();
                    message.what=2;
                    message.obj=mString2;
                    mHandler.sendMessage(message);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void byPostEnqueue(){
        new Thread(){
            @Override
            public void run() {
                super.run();

                RequestBody formBody = new FormBody.Builder()
                        .add("page", "1")
                        .add("code", "news")
                        .add("pageSize", "20")
                        .add("parentid", "0")
                        .add("type", "1")

                        .build();

                Request request = new Request.Builder()
                        .url("http://admin.wap.china.com/user/NavigateTypeAction.do?processID=getNavigateNews")
                        .post(formBody)
                        .build();

                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {

                    private String mString3;

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        mString3 = response.body().string();

                        Log.d("xxx==",mString3);

                        response.body().close();

                        Message message = new Message();
                        message.what=3;
                        message.obj=mString3;
                        mHandler.sendMessage(message);
                    }
                });
            }
        }.start();

    }
}
