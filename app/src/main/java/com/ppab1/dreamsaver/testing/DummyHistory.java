package com.ppab1.dreamsaver.testing;

import com.ppab1.dreamsaver.model.History;
import com.ppab1.dreamsaver.model.Target;

import java.util.ArrayList;

public class DummyHistory {
    private static final int[] id_history = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31
    };

    private static final int[] id_target = {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 2, 2, 2, 3, 2, 2, 2, 3
    };

    private static final String[] date = {
            "2020-09-17",
            "2020-09-18",
            "2020-09-19",
            "2020-09-20",
            "2020-09-25",
            "2020-09-26",
            "2020-09-27",
            "2020-09-28",
            "2020-09-29",
            "2020-09-30",
            "2020-10-01",
            "2020-10-02",
            "2020-10-07",
            "2020-10-10",
            "2020-10-11",
            "2020-10-12",
            "2020-10-13",
            "2020-10-14",
            "2020-10-15",
            "2020-10-21",
            "2020-10-22",
            "2020-10-22",
            "2020-10-23",
            "2020-10-24",
            "2020-10-24",
            "2020-10-25",
            "2020-10-26",
            "2020-10-26",
            "2020-10-27",
            "2020-10-29",
            "2020-10-31"
    };

    private static final String[] time = {
            "12:10",
            "13:21",
            "13:01",
            "13:05",
            "13:05",
            "14:00",
            "07:21",
            "07:25",
            "06:59",
            "07:03",
            "07:43",
            "10:10",
            "19:20",
            "19:03",
            "20:00",
            "21:07",
            "20:58",
            "19:55",
            "19:55",
            "20:01",
            "12:01",
            "12:02",
            "12:02",
            "12:01",
            "11:59",
            "16:39",
            "19:22",
            "19:01",
            "20:44",
            "19:59",
            "06:33"
    };

    private static final long[] nominal = {
            20000,
            20000,
            20000,
            20000,
            100000,
            20000,
            50000,
            30000,
            20000,
            10000,
            20000,
            10000,
            100000,
            100000,
            -200000,
            200000,
            20000,
            20000,
            20000,
            20000,
            50000,
            30000,
            10000,
            10000,
            15000,
            30000,
            50000,
            1250000,
            5000,
            -30000,
            -50000
    };

    private static final String[] desc = {
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "Besok diganti",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "Bayar hutang",
            "Ada keperluan penting"
    };

    public static ArrayList<History> getDummyHistory(){
        ArrayList<History> historyList = new ArrayList<>();
        for (int i = 0; i < id_history.length; i++) historyList.add(new History(
                id_history[i],
                id_target[i],
                date[i],
                time[i],
                nominal[i],
                desc[i]));
        return historyList;
    }
}
