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
    @Deprecated
    public static  Double w = 0.4; //为了优化算法我们决定废弃固定的w，采用线性变化w的值

    public static  Double X_down = -2.0;
    public static  Double X_up = 2.0;

    public static  Double V_min = -4.0;
    public static  Double V_max = 4.0;

    public static  Integer PopulationNumber = 20; //种群个数
    public static  Integer IterationsNumber = 100;//迭代次数不能为0

    public static  Integer ParamesNumber = 2;

    public static Double LineGoW(Integer interator){
        Double linegow = 0.0;
        /*
        * wd=wstart-(wstart - wend)x (d/K)
            d是当前迭代的次数,K是迭代总次数
            wstart一般取0.9，wend一般取0.4
        */
        linegow = 0.9-((0.5)*(interator.doubleValue()/ConfigPso.IterationsNumber));
        return linegow;
    }


}
