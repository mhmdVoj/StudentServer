package com.project.farjad.player.studentserver.RetrofitTools;

import com.google.gson.JsonObject;
import com.project.farjad.player.studentserver.Model.StudentModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.project.farjad.player.studentserver.VolleyTools.ApiService.URL_BASE;

public class ApiRetrofitService {

    RetrofitService retrofitService;

    public ApiRetrofitService() {
        Retrofit retrofit=new Retrofit.Builder()                        //initialize retrofit
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitService=retrofit.create(RetrofitService.class);         //initialize RetrofitService
    }


    public void GetStudentRetrofit(final OnGetStudentRetrofit onGetStudentRetrofit){
        retrofitService.getStudents().enqueue(new Callback<List<StudentModel>>() {
            @Override
            public void onResponse(Call<List<StudentModel>> call, Response<List<StudentModel>> response) {
                onGetStudentRetrofit.OnSuccess(response.body());

            }
            @Override
            public void onFailure(Call<List<StudentModel>> call, Throwable t) {
                onGetStudentRetrofit.OnError(t);
            }
        });
    }
    public interface OnGetStudentRetrofit{
        void OnSuccess(List<StudentModel> students);
        void OnError(Throwable t);
    }



    public void SaveStudentRetrofit(String FirstName, String LastName, String Course, int Score, final OnSaveStudentRetrofit onSaveStudentRetrofit){


        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("first_name",FirstName);
        jsonObject.addProperty("last_name",LastName);
        jsonObject.addProperty("course",Course);
        jsonObject.addProperty("score",Score);


        retrofitService.saveStudent(jsonObject).enqueue(new Callback<StudentModel>() {
            @Override
            public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                onSaveStudentRetrofit.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<StudentModel> call, Throwable t) {
                onSaveStudentRetrofit.OnError(t);
            }
        });

    }
    public interface OnSaveStudentRetrofit{
        void OnSuccess(StudentModel studentModel);
        void OnError(Throwable t);
    }


}
