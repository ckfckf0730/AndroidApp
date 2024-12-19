package com.example.testapplication.Application;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.app.Application;
import android.os.Environment;
import androidx.lifecycle.MutableLiveData;

import com.example.testapplication.Service.HttpConstants;
import com.example.testapplication.Service.HttpService;
import com.example.testapplication.Service.UserService;

import org.json.JSONObject;

public class BootApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();


        // 比如初始化一些全局资源、配置、SDK等
        // 初始化网络库、数据库、日志库等
        initializeGlobalResources();

        initializeUser();


    }

    private void initializeGlobalResources()
    {
        // 执行全局初始化操作，比如初始化日志、全局配置等
        HttpService.InitCookieFile(this.getCacheDir());

    }

    private final MutableLiveData<String> data = new MutableLiveData<>();

    private void initializeUser()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    var message = HttpService.HttpGet(
                            HttpConstants.HttpGet_GetUserName());
                    JSONObject jsonResponse = new JSONObject(message);
                    String islogin = jsonResponse.getString("islogin");
                    String name = jsonResponse.getString("name");
                    UserService.SetIsLogin(islogin.equals("true"));
                    UserService.SetUserName(name);
                }
                catch (Exception e)
                {
                    var str = e.getMessage();
                }

            }


        }).start();

    }



}
