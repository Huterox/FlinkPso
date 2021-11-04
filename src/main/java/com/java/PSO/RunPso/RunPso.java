package com.java.PSO.RunPso;

import com.java.PSO.ConfigPso.ConfigPso;
import com.java.PSO.Function.FunctionImp.FunctionsImpl;
import com.java.PSO.Function.FunctionMake;
import com.java.PSO.StreamPso.DoSteam;

import java.util.ArrayList;

public class RunPso {
    public static void main(String[] args) throws Exception {
        //调用Flink 方式
        //1.实现FunctionImpl接口，该接口的为目标函数，不实现默认使用案例。
//        FunctionMake.SetFunction(new TotalFunction());
        //2.调用配置类进行相关参数设置,不设置默认使用默认配置，但是重点需要设置参数个数
        ConfigPso.ParamesNumber = 10;
        ConfigPso.IterationsNumber=100;
        ConfigPso.PopulationNumber = 10;
        //3.运行核心
        DoSteam.RunCore();


    }

    static class TotalFunction implements FunctionsImpl{

        @Override
        public Double FourFunction(ArrayList<Double> parades) {
            //13个测试函数随便调用
//            return CECFactory.getf3(parades);
//            do something what you want
            return null;
        }
    }
}
