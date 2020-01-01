package com.android.core.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class School implements Parcelable {

    private int studentsSum;

    private String name;

    private int teachersSum;

    public int getStudentsSum() {
        return studentsSum;
    }

    public void setStudentsSum(int studentsSum) {
        this.studentsSum = studentsSum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeachersSum() {
        return teachersSum;
    }

    public void setTeachersSum(int teachersSum) {
        this.teachersSum = teachersSum;
    }

    public static final Creator<School> CREATOR = new Creator<School>() {

        @Override
        public School createFromParcel(Parcel in) {
            School school = new School();
            school.setName(in.readString());
            school.setStudentsSum(in.readInt());
            school.setTeachersSum(in.readInt());
            return school;
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(studentsSum);
        dest.writeInt(teachersSum);
    }
}
