package com.java.PSO.Function;

import com.java.PSO.Function.FunctionImp.FunctionsImpl;

import java.util.ArrayList;

public class FunctionMake {
    static FunctionsImpl functions=new Functions();
    static FunctionsImpl yourselfunction;

    public static void SetFunction(FunctionsImpl yourselfunction){
        yourselfunction = yourselfunction;
    }

    public static Double FourFunction(ArrayList<Double> List){
        //用户只需要重新实现FourFunction的接口即可
        Double rest = null;
        if (yourselfunction !=null){
             rest = yourselfunction.FourFunction(List);
        }
        else {
             rest = functions.FourFunction(List);
        }
        return rest;
    }


}
