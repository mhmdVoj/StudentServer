package com.project.farjad.player.studentserver.RetrofitRxjava;

import com.google.gson.JsonObject;
import com.project.farjad.player.studentserver.Model.StudentModel;
import com.project.farjad.player.studentserver.RetrofitTools.RetrofitService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.project.farjad.player.studentserver.VolleyTools.ApiService.URL_BASE;

public class ApiRetrofitServiceRxJava {

    RetrofitServiceRxJava retrofitService;

    public ApiRetrofitServiceRxJava() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitService=retrofit.create(RetrofitServiceRxJava.class);
    }


    public Single<List<StudentModel>> GetStudentRetrofit(){
        return retrofitService.getStudents();
    }


    public Single<StudentModel> SaveStudentRetrofit(String FirstName, String LastName, String Course, int Score){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("first_name",FirstName);
        jsonObject.addProperty("last_name",LastName);
        jsonObject.addProperty("course",Course);
        jsonObject.addProperty("score",Score);
        return retrofitService.saveStudent(jsonObject);

    }


}
