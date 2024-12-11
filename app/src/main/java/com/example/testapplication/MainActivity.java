package com.example.testapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.Model.HttpViewModel;
import com.example.testapplication.Service.HtmlService;

public class MainActivity extends AppCompatActivity
{
    private HttpViewModel httpViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // set layout

        // get Button & TextView
        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);

        // 创建 ViewModelProvider，用于获取 MainViewModel 实例
        httpViewModel = new ViewModelProvider(this)
                .get(HttpViewModel.class);

        // 观察 LiveData 数据变化，并更新 UI
        httpViewModel.getData().observe(this, newData -> {
            // 更新 UI
            HtmlService.GetCssRel(newData);
            HtmlService.DeSerializeHtmlText(newData,this);
        });

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                httpViewModel.fetchDataFromNetwork();
            }
        });
    }

}
