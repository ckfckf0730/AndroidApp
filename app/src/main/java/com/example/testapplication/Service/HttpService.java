package com.example.testapplication.Service;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSource;

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

    public static String HttpGet(String url)
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
    @FunctionalInterface
    public interface MyCallback {
        void invoke(String message);
    }
    private static final String TAG = "StreamData";
    public static void fetchStreamData(String url, MyCallback callback )
    {
        OkHttpClient client = null;
        try
        {
            client = getUnsafeOkHttpClient();
        }
        catch (NoSuchAlgorithmException e)
        {
            return;
        }
        catch (KeyManagementException e)
        {
            return;
        }
        catch (Exception e)
        {
            String str = e.getMessage();
            return;
        }

        // 创建请求
        Request request = new Request.Builder()
                .url(url) // 替换为你的流式数据请求 URL
                .build();

        // 发送请求并获取响应
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                // 请求失败
                int a =0;
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException
            {
                if (response.isSuccessful())
                {
                    // 获取流式数据
                    BufferedSource source = response.body().source();

                    StringBuilder buffer = new StringBuilder();

                    while (!source.exhausted())
                    {
                        // 每次读取一个数据块
                        String dataChunk = source.readUtf8Line(); // 每次读取一行数据
                        if (dataChunk != null && !dataChunk.isEmpty())
                        {
                            buffer.append(dataChunk);
                            // 判断是否有完整的数据包（例如 JSON 对象）
                            if (buffer.toString().endsWith("\n"))
                            {
                                try
                                {
                                    // 解析 JSON 数据
                                    String data = buffer.toString();
                                    // 清空缓冲区
                                    buffer.setLength(0);

                                    callback.invoke(data);



                                }
                                catch (Exception e)
                                {

                                }
                            }
                        }
                    }
                    // 关闭源
                    source.close();
                }
                else
                {

                }
            }
        });
    }
}

