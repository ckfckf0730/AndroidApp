package com.example.testapplication.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.R;
import com.example.testapplication.Service.HttpService;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class LoginActivity extends AppCompatActivity
{
    private final MutableLiveData<Response> data = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // set layout

        Button button = findViewById(R.id.button);

        data.observe(this, newData ->
        {
            List<String> cookies = newData.headers("Set-Cookie");
            if (!cookies.isEmpty())
            {
                for (String cookie : cookies)
                {
                    int a = 0;
                }
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
                HttpService.HttpPostAsync(HttpService.ServerHost + "Account/LoginAndroid",
                        params,
                        data);
            }
        });
    }

}
