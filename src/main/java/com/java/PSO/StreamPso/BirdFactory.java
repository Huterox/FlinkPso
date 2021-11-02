package com.java.PSO.StreamPso;

import com.java.PSO.ConfigPso.ConfigPso;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class BirdFactory {
    static Bird bird = new Bird();
    static Random random = new Random();

    @Test
    public static Bird MakeBird(Integer id) throws CloneNotSupportedException {
        //设置bird的初始位置，id
        Bird clone = (Bird) bird.clone();

        ArrayList<Double> X_init = new ArrayList<Double>();
        ArrayList<Double> V_init =new ArrayList<Double>();
        clone.setId(id);
        for (int i = 0; i< ConfigPso.ParamesNumber; i++){
            X_init.add(i,(random.nextDouble()*(Math.abs(ConfigPso.X_down)+ConfigPso.X_up))-Math.abs(ConfigPso.X_down));
            V_init.add(i,(random.nextDouble()*(Math.abs(ConfigPso.V_min)+ConfigPso.V_max))-Math.abs(ConfigPso.V_min));
        }
        clone.setVpersent(V_init);
        clone.setXpostion(X_init);

        return clone;
    }

}
