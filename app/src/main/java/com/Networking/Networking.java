package com.Networking;

import android.os.Handler;
import android.os.Looper;

import com.Util.ProgressView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Networking {

    private static volatile Networking instance;
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    private Networking()
    {
        if (instance != null)
        {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static Networking getInstance()
    {
        if (instance == null)
        { //if there is no instance available... create new one
            synchronized (Networking.class)
            {
                if (instance == null) instance = new Networking();
            }
        }

        return instance;
    }

    /**
     * METODO GET API
     *
     * @param url
     * @param callback
     */
    public static void get(String url, ICallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        excecute(request, callback);
    }

    /**
     * METODO GET API
     *  @param url
     * @param callback
     */
    public static void get(String url, String token, ICallback callback) {

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .build();

        excecute(request, callback);
    }

    public static void getJSON(String url, String json, Callback callback)
    {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * METODO POST API
     *
     * @param url
     * @param json
     * @param callback
     */
    public static void post(String url, String json, final ICallback callback) {

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        excecute(request, callback);
    }

    /**
     *
     * @param url
     * @param json
     * @param token
     * @param callback
     */
    public static void post(String url, String json, String token, final ICallback callback) {

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", "Bearer " + token)
                .build();

        excecute(request, callback);
    }

    /**
     * MEGOTO PUT API
     * @param url
     * @param json
     * @param callback
     */
    public static void put(String url, String json, ICallback callback) {

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();


        excecute(request, callback);

    }

    /**
     * MEGOTO PUT API
     * @param url
     * @param json
     * @param callback
     */
    public static void put(String url, String json, String token, ICallback callback) {

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .header("Authorization", "Bearer " + token)
                .build();

        excecute(request, callback);

    }

    /**
     * EJECUTA METODO API
     *
     * @param request
     * @param callback
     */
    public static void excecute(Request request, final ICallback callback) {

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                final String error = e.getMessage();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFail(error);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String resp = response.body().string();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(resp);
                        }
                    });

                } else {
                    String remoteResponse = response.body().string();
                    try {
                        JSONObject json = new JSONObject(remoteResponse);
                        final String error = json.getString("error");

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFail(error);
                            }
                        });
                    } catch (final JSONException e) {
                        //callback.onFail("SYSTEM", e.getMessage());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFail(e.getMessage());
                            }
                        });
                    }

                }
            }
        });
    }

    /**
     * INTERFACE CALLBACK
     */
    public interface ICallback {
        void onFail(String error);
        void onSuccess(String response);
    }

}
