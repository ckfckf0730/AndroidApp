package com.example.testapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication.Activity.Azure.PictureListActivity;
import com.example.testapplication.Activity.Azure.UploadPictureActivity;
import com.example.testapplication.Model.HttpViewModel;
import com.example.testapplication.R;
import com.example.testapplication.Service.NavigationHelper;

public class MainActivity extends AppCompatActivity
{
    private HttpViewModel httpViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // set layout

        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);


        httpViewModel = new ViewModelProvider(this)
                .get(HttpViewModel.class);

        httpViewModel.getData().observe(this, newData -> {
            Intent intent = new Intent(MainActivity.this,
                    PictureListActivity.class);
            startActivity(intent);
        });

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,
                        UploadPictureActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                httpViewModel.fetchDataFromNetwork(
                        "https://10.0.2.2:7241/Azure/PictureList");
            }
        });

        NavigationHelper.CreateNavigation(this);
    }

}
