package com.project.farjad.player.studentserver.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StudentModel implements Parcelable {


    private int id;
    private String first_name;
    private String last_name;
    @SerializedName("course")
    private String Course;
    private int score;
    private String ceated_time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCeated_time() {
        return ceated_time;
    }

    public void setCeated_time(String ceated_time) {
        this.ceated_time = ceated_time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeString(this.Course);
        dest.writeInt(this.score);
        dest.writeString(this.ceated_time);
    }

    public StudentModel() {
    }

    protected StudentModel(Parcel in) {
        this.id = in.readInt();
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.Course = in.readString();
        this.score = in.readInt();
        this.ceated_time = in.readString();
    }

    public static final Parcelable.Creator<StudentModel> CREATOR = new Parcelable.Creator<StudentModel>() {
        @Override
        public StudentModel createFromParcel(Parcel source) {
            return new StudentModel(source);
        }

        @Override
        public StudentModel[] newArray(int size) {
            return new StudentModel[size];
        }
    };
}
