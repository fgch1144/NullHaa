package com.example.nullhaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    public static final int s2 = 222;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("HandlerLeak")
            @Override
            public void onClick(View v) {

                handler = new Handler() {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        getSyn();
                        if (msg.what == 1) {
                            textView.setText(String.valueOf(msg.obj));
                        }
                    }
                };
            }
        });
    }

    public void getSyn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://v.juhe.cn/toutiao/index?type=top&key=8486800a8d01262039a811071dbfa192")
                        .get()
                        .build();
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println(call);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message message = new Message();
                        System.out.println(call);
                        String s1 = response.body().string();
                        message.what = 1;
                        message.obj = s1;
                        handler = new Handler();
                        handler.sendMessage(message);
                    }
                });
            }

        }).start();
    }

}

