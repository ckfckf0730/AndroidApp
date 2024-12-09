package com.example.testapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapplication.Http.HttpService;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // 设置布局文件

        // 获取 Button 和 TextView 的引用
        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);

        // 设置按钮的点击事件监听器
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        var str = HttpService.test();
                    }
                }).start();


            }
        });
    }

}
