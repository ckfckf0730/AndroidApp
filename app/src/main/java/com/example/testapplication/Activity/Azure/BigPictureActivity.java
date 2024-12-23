package com.example.testapplication.Activity.Azure;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.DataModel.FileStream;
import com.example.testapplication.R;
import com.example.testapplication.Service.HttpConstants;
import com.example.testapplication.Service.HttpService;

import java.io.InputStream;
import java.util.HashMap;

public class BigPictureActivity extends AppCompatActivity
{
    private final MutableLiveData<FileStream> imageData = new MutableLiveData<>();

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
            var bitMap = BitmapFactory.decodeStream(newData.inputStream);

            ImageView imageView = findViewById(R.id.bigpicture);
            imageView.setImageBitmap(bitMap);
        });
    }



    private void OpenImage(String uid)
    {


        var params =  new HashMap<String,String>();
        params.put("guid",uid);
        HttpService.HttpGetStreamAsync(HttpConstants.HttpGet_DisplayImage() ,
                params,
                imageData);
    }


}
