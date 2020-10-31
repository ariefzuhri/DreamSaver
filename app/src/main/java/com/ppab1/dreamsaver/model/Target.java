package com.ppab1.dreamsaver.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Target implements Parcelable {
    private int id;
    private String name;
    private long savingTarget;
    private long dailyTarget;
    private String dateTarget;
    private long totalSavings;
    private int position;

    public Target() {}

    public Target(int id, String name, long savingTarget, long dailyTarget, String dateTarget, long totalSavings, int position) {
        this.id = id;
        this.name = name;
        this.savingTarget = savingTarget;
        this.dailyTarget = dailyTarget;
        this.dateTarget = dateTarget;
        this.totalSavings = totalSavings;
        this.position = position;
    }

    protected Target(Parcel in) {
        id = in.readInt();
        name = in.readString();
        savingTarget = in.readLong();
        dailyTarget = in.readLong();
        dateTarget = in.readString();
        totalSavings = in.readLong();
        position = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeLong(savingTarget);
        dest.writeLong(dailyTarget);
        dest.writeString(dateTarget);
        dest.writeLong(totalSavings);
        dest.writeInt(position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Target> CREATOR = new Creator<Target>() {
        @Override
        public Target createFromParcel(Parcel in) {
            return new Target(in);
        }

        @Override
        public Target[] newArray(int size) {
            return new Target[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSavingTarget() {
        return savingTarget;
    }

    public void setSavingTarget(long savingTarget) {
        this.savingTarget = savingTarget;
    }

    public long getDailyTarget() {
        return dailyTarget;
    }

    public void setDailyTarget(long dailyTarget) {
        this.dailyTarget = dailyTarget;
    }

    public String getDateTarget() {
        return dateTarget;
    }

    public void setDateTarget(String dateTarget) {
        this.dateTarget = dateTarget;
    }

    public long getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(long totalSavings) {
        this.totalSavings = totalSavings;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
