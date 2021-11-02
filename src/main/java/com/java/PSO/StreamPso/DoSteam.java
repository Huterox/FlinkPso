package com.java.PSO.StreamPso;

import com.java.PSO.ConfigPso.ConfigPso;
import com.java.PSO.Function.FunctionImp.FunctionsImpl;
import com.java.PSO.Function.FunctionMake;
import com.java.PSO.Function.Functions;
import com.java.PSO.StreamPso.Core.Core;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class DoSteam {

    static Bird bird1;

    public static void RunCore() throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Bird> BirdInitStream = env.addSource(new InitBirds());
        KeyedStream<Bird, Integer> Birdtimeing = BirdInitStream.keyBy(Bird::getInterTimes);

        //进行初始化，获取全局最优，获取全局最优需要调用两次MinMapsG这个算子
        //由于是基于流处理，不使用窗口所以必须使用状态流进行全局最优筛选，第一次调用只是选择出全局最优
        //第二次调用是为了给所有的个体赋值，和个体的最优处理
        SingleOutputStreamOperator<Bird> map = Birdtimeing.map(new MinMapsG());
        KeyedStream<Bird, Tuple> id = map.keyBy("id");
        SingleOutputStreamOperator<Bird> map1 = id.map(new MinMapsG());
        SingleOutputStreamOperator<Bird> RealStream = map1.map(new MinMapsPinitial());
//        RealStream.print("init");

        //完成初始化后的数据流，到这里开始进行循环
        IterativeStream<Bird> iterateStream = RealStream.iterate();
        SingleOutputStreamOperator<Bird> IterationBody = iterateStream.keyBy(Bird::getInterTimes) //分组
                .map(new MinMapsG()) //首次寻早最优解
                .keyBy("id") //再次分组两个原因
                .map(new MinMapsG()) // 再次统计最优解，为全局的位置最优解
                .map(new MinMapsP())// 循环处理当中的个体最优解决
                .map(new CalculationPso());//这一步是进行粒子群的运算，也是比较重要的一环

        //需要进入循环的条件
        SingleOutputStreamOperator<Bird> IterationFlag = IterationBody.filter(new FilterFunction<Bird>() {
            @Override
            public boolean filter(Bird bird) throws Exception {
                return bird.getInterTimes() < ConfigPso.IterationsNumber;
            }
        });

        iterateStream.closeWith(IterationFlag);

        SingleOutputStreamOperator<Bird> Outstream = IterationBody.filter(new FilterFunction<Bird>() {
            @Override
            public boolean filter(Bird bird) throws Exception {
                return bird.getInterTimes() >= ConfigPso.IterationsNumber;
            }
        });
//        Outstream.print("1-->");
        //到这一步的话我们的程序已经进行了最后一次的运行，但是此时的是没有进行排序的，所以需要进行最后一次排序
        //这里由于只输出一个，所以这里打算直接开个技术窗口，然后输出最值！
        SingleOutputStreamOperator<Bird> MinBrid = Outstream.countWindowAll(ConfigPso.PopulationNumber).min("Functionresult");
        MinBrid.print("The best bird");

        env.execute();

    }


    static class CalculationPso implements MapFunction<Bird,Bird>{

        @Override
        public Bird map(Bird bird) throws Exception {
            /**
             * @Huterox
             * @Time:2021-11-1
             * 目标，通过Core的Update实现对大鸟（粒子）的位置和速度更新
             * 之后通过更新后的位置计算出目标函数的值，进行设置，前面的算子再进行一个新的轮回
             * 更新粒子迭代次数
             */

            Core.UpDataBird(bird);
            bird.setFunctionresult(FunctionMake.FourFunction(bird.getXpostion()));


            bird.setInterTimes(bird.getInterTimes()+1);
            return bird;
        }
    }


    static class MinMapsP implements MapFunction<Bird,Bird>{
        @Override
        public Bird map(Bird bird) throws Exception{
            //此时状态由Bird自己进行管理，Lfunctionresult记录的就是t-1次的个体最优的值，我们这边是找最小的的函数值
         if(bird.getFunctionresult()<bird.getLFunctionresult()){
             bird.setPbest(bird.getXpostion());
             //更新最优值
             bird.setLFunctionresult(bird.getFunctionresult());
         }
            return bird;
        }
    }


    static class MinMapsPinitial implements MapFunction<Bird,Bird>{

        // 计算个体最优的都是无序的数据流，系统不好记录同时为了性能，所以个体状态由个体自己记录
        @Override
        public Bird map(Bird bird) throws Exception {

            //本次进行初始化
            //为了减少条件判读，所以直接把个体最优的算子进行拆分
            bird.setPbest(bird.getXpostion());
            bird.setLFunctionresult(bird.getFunctionresult());

            return  bird;
        }


    }


    static class MinMapsG implements MapFunction<Bird,Bird>{
        //这个是通用的不存在初始化例外使用的情况

        @Override
        public Bird map(Bird bird) throws Exception {
            //状态流，状态由系统记录
            if(bird1!=null){
                if( bird.getFunctionresult()> bird1.getFunctionresult())
                    bird.setGbest(bird1.getXpostion());
                else {
                    bird.setGbest(bird.getXpostion());
                    bird1=bird;
                }
            }
            else{
                bird1 = bird;
                bird.setGbest(bird.getXpostion());
            }
            return bird;
        }


    }


    static class InitBirds implements SourceFunction<Bird>{


        @Override
        public void run(SourceContext<Bird> ctx) throws Exception {

            for(int i=1;i<=ConfigPso.PopulationNumber; i++) {
                Bird bird = BirdFactory.MakeBird(i);

                Double functionresult = FunctionMake.FourFunction(bird.getXpostion());
                bird.setFunctionresult(functionresult);

                bird.setInterTimes(0);//表示正在初始化
                ctx.collect(bird);
            }

        }

        @Override
        public void cancel() {

        }
    }
}

