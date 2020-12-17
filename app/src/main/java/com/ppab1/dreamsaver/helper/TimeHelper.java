package com.ppab1.dreamsaver.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeHelper implements Parcelable {
    private int hour;
    private int minute;

    public TimeHelper(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    protected TimeHelper(Parcel in) {
        hour = in.readInt();
        minute = in.readInt();
    }

    public static final Creator<TimeHelper> CREATOR = new Creator<TimeHelper>() {
        @Override
        public TimeHelper createFromParcel(Parcel in) {
            return new TimeHelper(in);
        }

        @Override
        public TimeHelper[] newArray(int size) {
            return new TimeHelper[size];
        }
    };

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(hour);
        parcel.writeInt(minute);
    }
}
