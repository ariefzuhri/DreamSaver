package com.ppab1.dreamsaver.callback;

import com.ppab1.dreamsaver.model.History;
import com.ppab1.dreamsaver.model.Target;

import java.util.ArrayList;

public interface LoadHistoryCallback {
    void preExecute();
    void postExecute(ArrayList<History> historyList);
}