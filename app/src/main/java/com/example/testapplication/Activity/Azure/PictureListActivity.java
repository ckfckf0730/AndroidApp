package com.example.testapplication.Activity.Azure;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Pair;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.R;
import com.example.testapplication.Service.EventService;
import com.example.testapplication.Service.HttpConstants;
import com.example.testapplication.Service.HttpService;
import com.example.testapplication.Service.NavigationHelper;

import org.json.JSONObject;

import java.util.HashMap;


public class PictureListActivity extends AppCompatActivity
{
    private final MutableLiveData<String> data = new MutableLiveData<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturelist);
        NavigationHelper.CreateNavigation(this);

        // callback of one picture data getting
        data.observe(this, newData ->
        {
            if (newData.contains("\"dataType\":\"img\""))
            {
                try
                {
                    JSONObject jsonResponse = new JSONObject(newData);
                    String src = jsonResponse.getString("imageSrc");
                    String uid = jsonResponse.getString("resId");
                    ImageView imageView = new ImageView(this);
                    var bitmap =decodeBase64ToBitmap(src);
                    if(bitmap != null)
                    {
                        LinearLayout mainLayout = findViewById(R.id.picturelist);
                        LinearLayout subLayout =new LinearLayout(this);
                        subLayout.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        containerParams.setMargins(0, 0, 0, 50);
                        subLayout.setLayoutParams(containerParams);

                        //image
                        imageView.setImageBitmap(bitmap);
                        //image clicked event
                        imageView.setOnClickListener(v->{
                            OpenImage(uid);
                                });
                        subLayout.addView(imageView);

                        //button
                        Button downloadButton = new Button(this);
                        Button deleteButton = new Button(this);
                        downloadButton.setText("Download");
                        deleteButton.setText("Delete");
                        subLayout.addView(downloadButton);
                        subLayout.addView(deleteButton);

                        //button clicked event
                        deleteButton.setOnClickListener(v->{
                            DeleteImage(uid);
                        });

                        mainLayout.addView(subLayout);

                        //set size
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        float ratio = (float)height / width;

                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        params.width =500;
                        params.height =(int)(500 *ratio);
                        imageView.setLayoutParams(params);
                    }

                }
                catch (Exception e)
                {
                    var a = e.getMessage();
                }

            }
            else if (newData.contains("\"dataType\":\"bing\""))
            {
                // Promote picture
                int a = 0;
            }


        });



        EventService.MyCallback myCallback = (message) ->
        {
            data.postValue(message);

        };

        HttpService.fetchStreamData(
                "https://10.0.2.2:7241/Azure/StreamThumbnailData"
                , myCallback);
    }

    private Bitmap decodeBase64ToBitmap(String src)
    {
        try
        {
            String base64String = src.substring(src.indexOf(",") + 1);
            byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        }
        catch (Exception e)
        {
            var a = e.getMessage();
            return null;
        }
    }



    private void OpenImage(String uid)
    {
        Intent intent = new Intent(PictureListActivity.this,
                BigPictureActivity.class);

        intent.putExtra("image", uid);

        startActivity(intent);
    }

    private void DeleteImage(String uid)
    {
        var params = new HashMap<String, Pair<String,Object>>();
        params.put(HttpConstants.HttpPostParam_DeleteFile_1,
                new Pair<String, Object>(HttpConstants.ParamType_String,uid));

        HttpService.HttpPostAsync(
                HttpConstants.HttpPost_DeleteFile(),
                params,
                null);

        recreate();
    }

}
