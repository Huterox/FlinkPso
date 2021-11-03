package com.java.PSO.StreamPso.Core;
import com.java.PSO.ConfigPso.ConfigPso;
import com.java.PSO.StreamPso.Bird;

import java.util.ArrayList;
import java.util.Random;

public class Core {

    static Random random = new Random();

    public static void UpdateSpeed(Bird bird){
        ArrayList<Double> CurrentSpeed = bird.getVpersent();
        //更新速度,传入大鸟，会自动更新大鸟的速度，同时返回更新后的速度向量
        Double fai1 = ConfigPso.C1 * random.nextDouble(); //c1*r1
        Double fai2 = ConfigPso.C2 * random.nextDouble(); //c2*r2
        int index = 0;
        for (Double aDouble : CurrentSpeed) {

            aDouble = ConfigPso.w * aDouble + fai1*(bird.getPbest().get(index) - bird.getXpostion().get(index))
                    + fai2*(bird.getGbest().get(index) - bird.getXpostion().get(index));
            CurrentSpeed.set(index,aDouble);

            index ++ ;

        }
        //完成对速度的更新
        bird.setVpersent(CurrentSpeed);

    }

    public static void UpdatePosition(Bird bird){
        //更新位置,传入大鸟，会自动更新大鸟的位置，同时返回更新后的位置的向量
        int index = 0;
        ArrayList<Double> CurrentXposition = bird.getXpostion();
        for (Double aDouble : CurrentXposition) {

//            System.out.println(aDouble+"<--->"+bird.getVpersent().get(index));
            aDouble = aDouble+bird.getVpersent().get(index);
            CurrentXposition.set(index,aDouble);
            index++;
        }
        //完成对位置的更新

        bird.setXpostion(CurrentXposition);

    }

    public static void UpDataBird(Bird bird){
        //返回Bird，负责对前面的方法进行调度。只需要调用这一个方法就可以实现位置和速度更新
        //先更新速度然后才能够更新位置
        //由于每一个个体过来都会需要执行一下算子，所以每一次在执行的时候fai1,fai2都是不同的
        //也就是每一个在每一轮当中的fai都是不同的，有可能会提高拟真度。
        UpdateSpeed(bird);
        UpdatePosition(bird);
    }

}
