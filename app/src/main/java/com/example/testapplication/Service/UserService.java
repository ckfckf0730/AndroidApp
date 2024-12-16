package com.example.testapplication.Service;

public class UserService
{
    private static String userName;

    public static String GetUserName()
    {
        return userName;
    }


    public static void SetUserName(String name)
    {
        userName = name;
    }

}
