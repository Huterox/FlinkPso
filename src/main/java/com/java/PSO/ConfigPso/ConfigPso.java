package com.java.PSO.ConfigPso;


public  class ConfigPso {
    //关于粒子群算法的相关参数设置
    /**
     *X(i+1) = X(i) + V(i+1)
     * V(i+1) = w*V(i) +c1*r1*(Pbest-X(i)) + c2*r2*(Gbest-X(i))
     * r1,r2为随机数【0，1】这边不设置
     */


    public static  Double C1 = 2.0;
    public static  Double C2 = 2.0;
    public static  Double w = 0.4;

    public static  Double X_down = -2.0;
    public static  Double X_up = 2.0;

    public static  Double V_min = -4.0;
    public static  Double V_max = 4.0;

    public static  Integer PopulationNumber = 20; //种群个数
    public static  Integer IterationsNumber = 100;//迭代次数不能为0

    public static  Integer ParamesNumber = 2;
    

}
