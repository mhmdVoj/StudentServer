package com.project.farjad.player.studentserver.VolleyTools;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class GsonRequest<T> extends Request<T> {

    private Gson gson=new Gson();
    private Type type;
    private Response.Listener<T> listener;
    private JSONObject request_body;


    public GsonRequest(int method, Type type, String url,@Nullable JSONObject request_body, Response.Listener<T> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener=listener;
        this.type=type;
        this.request_body=request_body;
    }

    GsonRequest(int method,Type type, String url, Response.Listener<T> listener, @Nullable Response.ErrorListener errorListener){
        this(method, type,url,null,listener,errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        String StringOfResponse=new String(networkResponse.data);
        T response=gson.fromJson(StringOfResponse,type);
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);

    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (request_body==null){
            return super.getBody();
        }else {
            return request_body.toString().getBytes();
        }
    }


    @Override
    public String getBodyContentType() {
        return "application/json";
    }
}


