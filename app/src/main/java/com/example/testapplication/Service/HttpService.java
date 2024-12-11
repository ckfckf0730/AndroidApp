package com.example.testapplication.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.cert.X509Certificate;


public class HttpService
{

    public static String test()
    {
        OkHttpClient client = null;
        try
        {
            client = getUnsafeOkHttpClient();
        }
        catch (NoSuchAlgorithmException e)
        {
            return null;
        }
        catch (KeyManagementException e)
        {
            return null;
        }
        catch (Exception e)
        {
            String str = e.getMessage();
            return null;
        }


        // 替换成你的服务器地址
        String url = "https://10.0.2.2:7241/";

        // 构建请求
        Request request = new Request.Builder()
                .url(url)
                .build();

        // 发送请求并接收响应
        OkHttpClient finalClient = client;
        try
        {
            // 执行网络请求
            Response response = finalClient.newCall(request).execute();
            if (response.isSuccessful())
            {
                // 处理成功的响应
                String responseBody = response.body().string();

                return responseBody;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static OkHttpClient getUnsafeOkHttpClient() throws NoSuchAlgorithmException, KeyManagementException
    {
        // 创建一个不验证证书的 TrustManager
        TrustManager[] trustAllCertificates = new TrustManager[]
                {
                        new X509TrustManager()
                        {
                            public X509Certificate[] getAcceptedIssuers()
                            {
                                return new X509Certificate[0];  // 返回空数组，避免返回 null
                            }

                            public void checkClientTrusted(X509Certificate[] certs, String authType)
                            {
                            }

                            public void checkServerTrusted(X509Certificate[] certs, String authType)
                            {
                            }
                        }
                };

        // 初始化 SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

        // 获取 SSLSocketFactory
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        // 创建 OkHttpClient 并配置 SSL
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCertificates[0]) // 使用自定义的 TrustManager
                .hostnameVerifier((hostname, session) -> true)  // 忽略主机名验证
                .build();
    }
}

