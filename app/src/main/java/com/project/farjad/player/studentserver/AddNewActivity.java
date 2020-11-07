package com.project.farjad.player.studentserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.project.farjad.player.studentserver.Model.StudentModel;
import com.project.farjad.player.studentserver.RetrofitRxjava.ApiRetrofitServiceRxJava;
import com.project.farjad.player.studentserver.RetrofitTools.ApiRetrofitService;
import com.project.farjad.player.studentserver.VolleyTools.ApiService;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddNewActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar_new;
    private ExtendedFloatingActionButton btn_done;
    private EditText edt_first_name;
    private EditText edt_last_name;
    private EditText edt_course;
    private EditText edt_score;
    private ApiService apiService;
    private ApiRetrofitService apiRetrofitService;
    private ApiRetrofitServiceRxJava apiRetrofitServiceRxJava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        InitViews();
    }

    private void InitViews() {
        edt_first_name=findViewById(R.id.edt_first_name);
        edt_last_name=findViewById(R.id.edt_last_name);
        edt_course=findViewById(R.id.edt_course);
        edt_score=findViewById(R.id.edt_score);
        toolbar_new=findViewById(R.id.toolbar_new);
        btn_done =findViewById(R.id.btn_done);
        btn_done.setOnClickListener(this);

        apiService=new ApiService(this,"TAG");
        apiRetrofitServiceRxJava=new ApiRetrofitServiceRxJava();


        setSupportActionBar(toolbar_new);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black_24dp);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_done:{
                if (edt_first_name.length()>0 &&
                    edt_last_name.length()>0 &&
                    edt_course.length()>0 &&
                    edt_score.length()>0){

                    apiRetrofitService=new ApiRetrofitService();



                    //Retrofit + RxJava
                    apiRetrofitServiceRxJava.SaveStudentRetrofit(edt_first_name.getText().toString(),
                            edt_last_name.getText().toString(),
                            edt_course.getText().toString(),
                            Integer.parseInt(edt_score.getText().toString()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<StudentModel>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(StudentModel studentModel) {
                                    Intent intent=new Intent();
                                    intent.putExtra("student",studentModel);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(AddNewActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                                }
                            });

                    //Retrofit
                    /*apiRetrofitService.SaveStudentRetrofit(edt_first_name.getText().toString(),
                            edt_last_name.getText().toString(),
                            edt_course.getText().toString(),
                            Integer.parseInt(edt_score.getText().toString()), new ApiRetrofitService.OnSaveStudentRetrofit() {
                                @Override
                                public void OnSuccess(StudentModel studentModel) {
                                    Intent intent=new Intent();
                                    intent.putExtra("student",studentModel);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }

                                @Override
                                public void OnError(Throwable t) {
                                    Toast.makeText(AddNewActivity.this, ""+t, Toast.LENGTH_SHORT).show();
                                }
                            });*/

                    //Volley
                    /*
                    apiService.SaveStudent(edt_first_name.getText().toString(),
                            edt_last_name.getText().toString(),
                            edt_course.getText().toString(),
                            Integer.parseInt(edt_score.getText().toString()), new ApiService.OnSaveStudentResult() {
                                @Override
                                public void OnSuccess(StudentModel student) {
                                    Intent intent=new Intent();
                                    intent.putExtra("student",student);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }

                                @Override
                                public void OnError(VolleyError error) {
                                    Toast.makeText(AddNewActivity.this, ""+error, Toast.LENGTH_SHORT).show();

                                }
                            });*/

                }else {
                    Toast.makeText(this, "Please Complete Fields", Toast.LENGTH_SHORT).show();
                }
            }
                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiService.CancelRequestApi();
    }
}
