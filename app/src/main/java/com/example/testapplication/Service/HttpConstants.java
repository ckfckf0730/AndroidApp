package com.example.testapplication.Service;

public class HttpConstants
{
    public static String ServerHost = "https://10.0.2.2:7241/";

    //HttpPost
    public static String HttpPost_Login()
    {
        return ServerHost + "Account/LoginAndroid";
    }

    public static String HttpPost_UploadFile()
    {
        return ServerHost + "Azure/UploadFile";
    }

    public static String HttpPost_DeleteFile()
    {
        return ServerHost + "Azure/DeleteFile";
    }

    public static final String HttpPostParam_DeleteFile_1 = "guid";

    //HttpGet
    public static String HttpGet_DisplayImage()
    {
        return ServerHost + "Azure/DisplayImage";
    }

    public static String HttpGet_DownloadImage()
    {
        return ServerHost + "Azure/DownloadAndSave";
    }

    public static String HttpGet_GetUserName()
    {
        return ServerHost + "Account/GetUserName";
    }



    //HttpPost Params Type
    public static final String ParamType_String = "String";
    public static final String ParamType_File = "File";

}
