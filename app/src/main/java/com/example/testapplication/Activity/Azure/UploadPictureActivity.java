package com.example.testapplication.Activity.Azure;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.R;
import com.example.testapplication.Service.HttpConstants;
import com.example.testapplication.Service.HttpService;
import com.example.testapplication.Service.NavigationHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

public class UploadPictureActivity extends AppCompatActivity
{
    private Uri selectedFIle;
    private final MutableLiveData<String> data = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpicture);
        NavigationHelper.CreateNavigation(this);

        TextView text = findViewById(R.id.upload_text);
        TextView textSelected = findViewById(R.id.selected_text);
        ViewGroup layout = (ViewGroup) text.getParent();

        //upload callback
        data.observe(this, newData->{
            JSONObject jsonResponse = null;
            try
            {
                jsonResponse = new JSONObject(newData);
                String isSuccess = jsonResponse.getString("success");
                String message = jsonResponse.getString("message");
                ;
                if(isSuccess.equals("true"))
                {
                    textSelected.setTextColor(Color.parseColor("#00FF00"));
                }
                else
                {
                    textSelected.setTextColor(Color.parseColor("#FF0000"));
                }
                textSelected.setText(message);

            }
            catch (JSONException e)
            {
                textSelected.setText("Upload Faultly");
                textSelected.setTextColor(Color.parseColor("#FF0000"));
            }

        });



        ActivityResultLauncher<String> getContent = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri ->
                {
                    if (uri != null)
                    {
                        textSelected.setText(getFileNameFromUri(uri));
                        textSelected.setTextColor(Color.parseColor("#000000"));
                        selectedFIle = uri;
                    }
                    else
                    {
                        textSelected.setText("");
                        selectedFIle = null;
                    }
                });

        if (com.example.testapplication.Service.UserService.GetIsLogin())
        {
            text.setText("Please select a picture.");

            Button buttonSelect = new Button(this);
            buttonSelect.setText("Select File");
            Button buttonUpload = new Button(this);
            buttonUpload.setText("Upload");

            buttonSelect.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    getContent.launch("*/*");
                }
            });

            buttonUpload.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (selectedFIle != null)
                    {
                        try
                        {
                            InputStream fileInputStream =
                                    getContentResolver().openInputStream(selectedFIle);
                            if (fileInputStream != null)
                            {

                                var params = new HashMap<String, Pair<String, Object>>();
                                params.put("file",new Pair<String,Object>
                                        ("File",fileInputStream));

                                HttpService.HttpPostAsync(
                                        HttpConstants.HttpPost_UploadFile(),
                                        params,
                                        data);
                            }


//
                        }
                        catch (Exception e)
                        {

                        }
                    }
                }
            });

            layout.addView(buttonSelect);
            layout.addView(buttonUpload);

        }
        else
        {
            text.setText("Please login.");
        }

    }


    private String getFileNameFromUri(Uri uri)
    {
        String fileName = null;
        Cursor cursor = null;
        try
        {
            // 使用 ContentResolver 获取文件名
            String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst())
            {
                int columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
                fileName = cursor.getString(columnIndex);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return fileName;
    }


}
