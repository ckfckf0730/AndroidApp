package com.example.testapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.R;
import com.example.testapplication.Service.HttpConstants;
import com.example.testapplication.Service.HttpService;
import com.example.testapplication.Service.UserService;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

                Map<String, Pair<String, Object>> params = new HashMap<String, Pair<String, Object>>();
                params.put("Email", new Pair<String,Object>("String",
                        emailInput.getEditText().getText().toString()));
                params.put("Password", new Pair<String,Object>("String",
                        passInput.getEditText().getText().toString()));
                params.put("RememberMe", new Pair<String,Object>("String", "true"));
                HttpService.HttpPostAsync(HttpConstants.HttpPost_Login(),
                        params,
                        data);
            }
        });
    }

}
