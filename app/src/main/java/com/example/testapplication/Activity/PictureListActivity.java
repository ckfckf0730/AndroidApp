package com.example.testapplication.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.Model.HttpViewModel;
import com.example.testapplication.R;
import com.example.testapplication.Service.EventService;
import com.example.testapplication.Service.HttpService;

public class PictureListActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturelist);


        EventService.MyCallback myCallback = (message) -> {

            // 处理数据 (例如根据 dataType 判断处理方式)
            if (message.contains("\"dataType\":\"img\""))
            {
                // 处理图片数据
                int a = 0;
            }
            else if (message.contains("\"dataType\":\"bing\""))
            {
                // 处理其他类型的数据
                int a = 0;
            }
        };

        HttpService.fetchStreamData(
                "https://10.0.2.2:7241/Azure/StreamThumbnailData"
                ,myCallback);
    }



}
