package com.ppab1.dreamsaver.testing;

import com.ppab1.dreamsaver.model.Target;

import java.util.ArrayList;

public class DummyTarget {
    private static final int[] id = {
            1, 2, 3
    };

    private static final String[] name = {
            "HDD Eksternal", "Laptop", "SSD"
    };

    private static final long[] savingsTarget = {
            650000, 7000000, 400000
    };

    private static final long[] dailyTarget = {
            20000, 10000, 5000
    };

    private static final String[] dateTarget = {
            "2020-10-31", "2022-07-31", "2021-01-31"
    };

    private static final long[] totalSavings = {
            650000, 1340000, 0
            //650000, 0, 0
    };

    private static final int[] position = {
            1, 1, 2
    };

    public static ArrayList<Target> getDummyTarget(){
        ArrayList<Target> targetList = new ArrayList<>();
        for (int i = 0; i < id.length; i++) targetList.add(new Target(
                id[i],
                name[i],
                savingsTarget[i],
                dailyTarget[i],
                dateTarget[i],
                totalSavings[i],
                position[i]));
        return targetList;
    }
}
