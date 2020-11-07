package com.project.farjad.player.studentserver.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.farjad.player.studentserver.R;
import com.project.farjad.player.studentserver.Model.StudentModel;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {

    private List<StudentModel> students;
    private Context context;
    private OnStudentClickListener listener;


    public StudentListAdapter(List<StudentModel> students, Context context,OnStudentClickListener listener) {
        this.students = students;
        this.context = context;
        this.listener =listener;
    }

    public void AddStudent(StudentModel studentModel){
        this.students.add(0,studentModel);
        notifyItemChanged(0);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.student_item_layout,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.BindStudentContent(students.get(position));

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_name;
        private TextView txt_course;
        private TextView sub_name;
        private TextView score;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_course=itemView.findViewById(R.id.txt_course);
            txt_name=itemView.findViewById(R.id.txt_name);
            score=itemView.findViewById(R.id.score_txt);
            sub_name=itemView.findViewById(R.id.txt_sub_name);
        }

        @SuppressLint("SetTextI18n")
        public void BindStudentContent (StudentModel studentModel){
            txt_name.setText(studentModel.getFirst_name()+ " "+studentModel.getLast_name());
            txt_course.setText(studentModel.getCourse());
            sub_name.setText(studentModel.getFirst_name().substring(0,1));
            score.setText(String.valueOf(studentModel.getScore()));
            itemView.setOnClickListener(view -> {
                listener.OnClickStudent(studentModel);
            });



        }


    }

    public interface OnStudentClickListener{
        void OnClickStudent(StudentModel studentModel);
    }
}
