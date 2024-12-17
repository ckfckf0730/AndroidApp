package com.example.testapplication.Service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

public class UserService
{
    private static final MutableLiveData<String> userName = new MutableLiveData<>();
    private static boolean isLogin;
    public static void SetObserve(AppCompatActivity activity,
                                  EventService.MyCallback callback)
    {
        userName.observe(activity, newData->
        {
            callback.invoke(newData);
        });
    }

    public static void RemoveObserve(AppCompatActivity activity)
    {
        userName.removeObservers(activity);
    }

    public static String GetUserName()
    {
        return userName.getValue();
    }


    public static void SetUserName(String name)
    {
        userName.postValue(name);
    }

    public static void SetIsLogin(boolean value)
    {
        isLogin = value;
    }

    public static boolean GetIsLogin()
    {
        return isLogin;
    }
}
