package com.example.testapplication.Service;

public class EventService
{
    @FunctionalInterface
    public interface MyCallback
    {
        void invoke(String message);
    }
}
