package com.java.PSO.StreamPso;

import com.java.PSO.ConfigPso.ConfigPso;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Data
@ToString
@NoArgsConstructor
public class Bird implements Cloneable {
    //大鸟的编号
    private Integer id;
    private ArrayList<Double> Pbest;
    private ArrayList<Double> Gbest;

    private Double Functionresult;

    private Double LFunctionresult;

    private ArrayList<Double> Xpostion;
    private ArrayList<Double> Vpersent;
    private Integer InterTimes;


    public Bird(Integer id, ArrayList<Double> pbest, ArrayList<Double> gbest, Double functionresult, Double LFunctionresult, ArrayList<Double> xpostion, ArrayList<Double> vpersent, Integer interTimes) {
        this.id = id;
        this.Pbest = pbest;
        this.Gbest = gbest;
        this.Functionresult = functionresult;
        this.LFunctionresult = LFunctionresult;
        this.InterTimes = interTimes;
        this.setXpostion(xpostion);
        this.setVpersent(vpersent);
    }

    public void setXpostion(ArrayList<Double> xpostion) {
        //越界处理
        int index = 0;
        for (Double aDouble : xpostion) {
            if(aDouble > ConfigPso.X_up)
                xpostion.set(index,ConfigPso.X_up);
            else if (aDouble < ConfigPso.X_down)
                xpostion.set(index,ConfigPso.X_down);
            index++;
        }

        Xpostion = xpostion;
    }

    public void setVpersent(ArrayList<Double> vpersent) {
        int index = 0;
        for (Double aDouble : vpersent) {
            if(aDouble > ConfigPso.V_max)
                vpersent.set(index,ConfigPso.V_max);
            else if (aDouble < ConfigPso.V_min)
                vpersent.set(index,ConfigPso.V_min);
            index++;
        }
        Vpersent = vpersent;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
