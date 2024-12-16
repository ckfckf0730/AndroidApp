package com.example.testapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.R;
import com.example.testapplication.Service.HttpService;
import com.example.testapplication.Service.UserService;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class LoginActivity extends AppCompatActivity
{
    private final MutableLiveData<String> data = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // set layout

        Button button = findViewById(R.id.button);

        data.observe(this, newData ->
        {

            JSONObject jsonResponse = null;
            try
            {
                jsonResponse = new JSONObject(newData);
                String message = jsonResponse.getString("message");
                JSONObject userObject = jsonResponse.getJSONObject("user");
                String email = userObject.getString("email");
                UserService.SetUserName(email);

                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
            catch (JSONException e)
            {
                int b = 0;
            }
            catch (Exception e)
            {
                int b = 0;
            }


        });


        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextInputLayout emailInput = findViewById(R.id.emailInput);
                TextInputLayout passInput = findViewById(R.id.passwordInput);

                Map<String, String> params = new HashMap<String, String>();
                params.put("Email", emailInput.getEditText().getText().toString());
                params.put("Password", passInput.getEditText().getText().toString());
                params.put("RememberMe", "true");
                HttpService.HttpCookieAsync(HttpService.ServerHost + "Account/LoginAndroid",
                        params,
                        data);
            }
        });
    }

}
