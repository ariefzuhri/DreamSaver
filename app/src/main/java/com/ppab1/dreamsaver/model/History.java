package com.ppab1.dreamsaver.model;

import android.os.Parcel;
import android.os.Parcelable;

public class History implements Parcelable {
    private int id_history;
    private int id_target;
    private String date;
    private String time;
    private long nominal;
    private String desc;

    public History() {}

    public History(int id_history, int id_target, String date, String time, long nominal, String desc) {
        this.id_history = id_history;
        this.id_target = id_target;
        this.date = date;
        this.time = time;
        this.nominal = nominal;
        this.desc = desc;
    }

    protected History(Parcel in) {
        id_history = in.readInt();
        id_target = in.readInt();
        date = in.readString();
        time = in.readString();
        nominal = in.readLong();
        desc = in.readString();
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    public int getId_history() {
        return id_history;
    }

    public void setId_history(int id_history) {
        this.id_history = id_history;
    }

    public int getId_target() {
        return id_target;
    }

    public void setId_target(int id_target) {
        this.id_target = id_target;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getNominal() {
        return nominal;
    }

    public void setNominal(long nominal) {
        this.nominal = nominal;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_history);
        parcel.writeInt(id_target);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeLong(nominal);
        parcel.writeString(desc);
    }
}
