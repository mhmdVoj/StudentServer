package com.project.farjad.player.studentserver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.project.farjad.player.studentserver.Adapter.StudentListAdapter;
import com.project.farjad.player.studentserver.Model.StudentModel;
import com.project.farjad.player.studentserver.RetrofitRxjava.ApiRetrofitServiceRxJava;
import com.project.farjad.player.studentserver.RetrofitTools.ApiRetrofitService;
import com.project.farjad.player.studentserver.VolleyTools.ApiService;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, StudentListAdapter.OnStudentClickListener {
    private Toolbar toolbar;
    private RecyclerView rcl_main;
    private ExtendedFloatingActionButton btn_add;
    private StudentListAdapter adapter;
    private ApiService apiService;
    ApiRetrofitService apiRetrofitService;
    ApiRetrofitServiceRxJava retrofitServiceRxJava;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitViews();

        setSupportActionBar(toolbar);
        btn_add.setOnClickListener(this);
        InitRecyclerView();

        apiRetrofitService=new ApiRetrofitService();
        retrofitServiceRxJava=new ApiRetrofitServiceRxJava();


        //Retrofit + RxJava
        retrofitServiceRxJava.GetStudentRetrofit()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<StudentModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<StudentModel> studentModels) {
                        adapter=new StudentListAdapter(studentModels,MainActivity.this,MainActivity.this);
                        rcl_main.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_LONG).show();
                    }
                });


        //Retrofit Library
        /*apiRetrofitService.GetStudentRetrofit(new ApiRetrofitService.OnGetStudentRetrofit() {
            @Override
            public void OnSuccess(List<StudentModel> students) {
                adapter=new StudentListAdapter(students,MainActivity.this);
                rcl_main.setAdapter(adapter);
            }

            @Override
            public void OnError(Throwable t) {
                Toast.makeText(MainActivity.this, ""+t, Toast.LENGTH_LONG).show();

            }
        });*/


        //Volley Library
        /* apiService=new ApiService(this,"TAG");
        apiService.getStudents(new ApiService.OnStudentListResult() {
            @Override
            public void OnSuccess(List<StudentModel> students) {
                adapter=new StudentListAdapter(students,MainActivity.this);
                rcl_main.setAdapter(adapter);
            }

            @Override
            public void OnError(VolleyError error) {

            }
        });*/


    }

    private void InitRecyclerView() {
        rcl_main.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL,false));
    }

    private void InitViews() {
        toolbar=findViewById(R.id.toolbar_main);
        rcl_main=findViewById(R.id.rcl_main);
        btn_add=findViewById(R.id.btn_add);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:{
                startActivityForResult(new Intent(MainActivity.this,AddNewActivity.class),1001);
            }break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ((requestCode==1001 && resultCode==RESULT_OK)){
            StudentModel student=data.getParcelableExtra("student");
            adapter.AddStudent(student);
            rcl_main.smoothScrollToPosition(0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiService.CancelRequestApi();
    }

    @Override
    public void OnClickStudent(StudentModel studentModel) {
        startActivity(new Intent(MainActivity.this,UploadStudentPictureActivity.class));
    }
}
