package com.project.farjad.player.studentserver.VolleyTools;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.project.farjad.player.studentserver.Model.StudentModel;
import com.project.farjad.player.studentserver.TestRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.GET;

public class ApiService {


    public final static String URL_BASE="http://expertdevelopers.ir/api/v1/";
    private RequestQueue requestQueue;
    private String RequestTag;
    private Gson gson;


    public ApiService(Context context,String RequestTag) {
        this.gson=new Gson();
        this.RequestTag=RequestTag;
        if(requestQueue==null){
            requestQueue=Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    void SaveStudent(String FirstName, String LastName, String Course, int Score, final OnSaveStudentResult onSaveStudentResult){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("first_name",FirstName);
            jsonObject.put("last_name",LastName);
            jsonObject.put("course",Course);
            jsonObject.put("score",Score);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonRequest<StudentModel> request=new GsonRequest<>(Request.Method.POST, StudentModel.class,
                "http://expertdevelopers.ir/api/v1/experts/student",
                    jsonObject,
                new Response.Listener<StudentModel>() {
                    @Override
                    public void onResponse(StudentModel response) {
                        onSaveStudentResult.OnSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        /*JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("first_name",FirstName);
            jsonObject.put("last_name",LastName);
            jsonObject.put("course",Course);
            jsonObject.put("score",Score);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, "http://expertdevelopers.ir/api/v1/experts/student", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                StudentModel student=gson.fromJson(response.toString(),StudentModel.class);
                onSaveStudentResult.OnSuccess(student);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onSaveStudentResult.OnError(error);
            }
        });
        request.setTag(RequestTag);*/
        requestQueue.add(request);
    }


    void getStudents(final OnStudentListResult onStudentListResult){

        GsonRequest<List<StudentModel>> gsonRequest=new GsonRequest<>(Request.Method.GET, new TypeToken<List<StudentModel>>() {
        }.getType(),
                "http://expertdevelopers.ir/api/v1/experts/student",
                new Response.Listener<List<StudentModel>>() {
                    @Override
                    public void onResponse(List<StudentModel> response) {
                        onStudentListResult.OnSuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        onStudentListResult.OnError(error);
            }
        });
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000,2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        gsonRequest.setTag(RequestTag);
        requestQueue.add(gsonRequest);
        /*StringRequest stringRequest=new StringRequest(1Request.Method.GET, "http://expertdevelopers.ir/api/v1/experts/student", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("TAG", "onResponse: "+response);

                ArrayList<StudentModel> students=gson.fromJson(response,new TypeToken<List<StudentModel>>(){}.getType());
                onStudentListResult.OnSuccess(students);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onStudentListResult.OnError(error);

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(RequestTag);
        requestQueue.add(stringRequest);*/
    }

    public interface OnStudentListResult{
        void OnSuccess(List<StudentModel> students);
        void OnError(VolleyError error);
    }

    public interface OnSaveStudentResult{
        void OnSuccess(StudentModel student);
        void OnError(VolleyError error);
    }


    public void CancelRequestApi(){
        requestQueue.cancelAll(RequestTag);
    }
}
