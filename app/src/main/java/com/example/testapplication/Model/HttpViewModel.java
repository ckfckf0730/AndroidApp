package com.example.testapplication.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapplication.Service.HttpService;



public class    HttpViewModel extends ViewModel
{
    // LiveData 用于在 UI 中观察数据变化
    private final MutableLiveData<String> data = new MutableLiveData<>();

    // 获取 LiveData 数据
    public LiveData<String> getData()
    {
        return data;
    }

    // 通过网络请求获取数据，并更新 LiveData
    public void fetchDataFromNetwork()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                // 假设 HttpService.test() 发起 HTTP 请求并返回文本数据
                String response = HttpService.test();

                // 将请求结果传递给 LiveData，这样 UI 可以自动更新
                data.postValue(response);
            }
        }).start();
    }
}
