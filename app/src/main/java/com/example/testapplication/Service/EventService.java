package com.example.testapplication.Service;

import org.json.JSONException;

public class EventService
{
    @FunctionalInterface
    public interface MyCallback
    {
        void invoke(String message);
    }
}
