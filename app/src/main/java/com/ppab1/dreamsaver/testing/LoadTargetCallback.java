package com.ppab1.dreamsaver.testing;

import com.ppab1.dreamsaver.model.Target;

import java.util.ArrayList;

public interface LoadTargetCallback{
    void preExecute();
    void postExecute(ArrayList<Target> targetList);
}