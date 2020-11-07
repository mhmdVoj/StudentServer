package com.project.farjad.player.studentserver.RetrofitTools;

import com.google.gson.JsonObject;
import com.project.farjad.player.studentserver.Model.StudentModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitService {

    @GET ("experts/student")
    Call<List<StudentModel>> getStudents();


    @POST ("experts/student")
    Call<StudentModel> saveStudent(@Body JsonObject body);
}
