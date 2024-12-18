package com.example.testapplication.Activity.Azure;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.R;
import com.example.testapplication.Service.HttpService;

import java.io.InputStream;
import java.util.HashMap;

public class BigPictureActivity extends AppCompatActivity
{
    private final MutableLiveData<InputStream> imageData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigpicture);

        Intent intent = getIntent();
        var uid =  intent.getStringExtra("image");
        OpenImage(uid);

        imageData.observe(this,newData->
        {
            var bitMap = BitmapFactory.decodeStream(newData);

            ImageView imageView = findViewById(R.id.bigpicture);
            imageView.setImageBitmap(bitMap);
        });
    }



    private void OpenImage(String uid)
    {


        var params =  new HashMap<String,String>();
        params.put("guid",uid);
        HttpService.HttpGetStreamAsync(HttpService.ServerHost + "Azure/DisplayImage",
                params,
                imageData);
    }


}
