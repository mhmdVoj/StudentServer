package com.project.farjad.player.studentserver.RetrofitRxjava;

import com.google.gson.JsonObject;
import com.project.farjad.player.studentserver.Model.StudentModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitServiceRxJava {

    @GET ("experts/student")
    Single<List<StudentModel>> getStudents();


    @POST ("experts/student")
    Single<StudentModel> saveStudent(@Body JsonObject body);
}
