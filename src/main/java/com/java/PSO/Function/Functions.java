package com.java.PSO.Function;

import com.java.PSO.Function.FunctionImp.FunctionsImpl;

import java.util.ArrayList;

public class Functions implements FunctionsImpl {


    @Override
    public Double FourFunction(ArrayList<Double> parames) {

        //测试函数,寻找最小值，x 假设都在 [5,-5] vmax = [-10,10] w=0.4 c1=c2=2默认初始
        Double res = 0.0;
        int index = 0;
        for (Object parame : parames) {
            res = res + Math.pow((Double) parames.get(index),2);
            index ++;
        }
        return res;

    }
}
