package com.ppab1.dreamsaver.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class DateHelper implements Parcelable {
    private int year;
    private int month;
    private int date;

    public DateHelper(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;
    }

    protected DateHelper(Parcel in) {
        year = in.readInt();
        month = in.readInt();
        date = in.readInt();
    }

    public static final Creator<DateHelper> CREATOR = new Creator<DateHelper>() {
        @Override
        public DateHelper createFromParcel(Parcel in) {
            return new DateHelper(in);
        }

        @Override
        public DateHelper[] newArray(int size) {
            return new DateHelper[size];
        }
    };

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(year);
        parcel.writeInt(month);
        parcel.writeInt(date);
    }
}
