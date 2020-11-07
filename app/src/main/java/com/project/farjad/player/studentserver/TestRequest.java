package com.project.farjad.player.studentserver;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.net.HttpCookie;

public class TestRequest<T> extends Request<T> {
    private Gson gson =new Gson();
    private Type type ;
    private Response.Listener<T> listener;
    private JsonObject jsonObjectBody;

    public TestRequest(int method, String url, Type type, JsonObject jsonObjectBody, Response.Listener<T> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.type = type;
        this.listener = listener;
        this.jsonObjectBody = jsonObjectBody;
    }

    TestRequest(int method, String url, Type type, Response.Listener<T> listener, @Nullable Response.ErrorListener errorListener) {
        this (method, url,type,null,listener, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        String StringOfResponse = new String(networkResponse.data);
        T response =gson.fromJson(StringOfResponse,type);
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }


    @Override
    public byte[] getBody() throws AuthFailureError {
        if (jsonObjectBody==null) {
            return super.getBody();
        }else {
            return jsonObjectBody.toString().getBytes();
        }
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }
}
