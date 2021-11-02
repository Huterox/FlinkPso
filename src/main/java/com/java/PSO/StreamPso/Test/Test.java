package com.java.PSO.StreamPso.Test;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class Test {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Integer> IntegerOut = env.addSource(new SourceFunction<Integer>() {
            @Override
            public void run(SourceContext<Integer> ctx) throws Exception {
                for (int i = 1; i <= 10; i++) {
                    ctx.collect(i);
                }
            }
            @Override
            public void cancel() {

            }
        });

        IterativeStream<Integer> iterate = IntegerOut.iterate();
        //现在小爷要把这个通过循环全部变成5
        SingleOutputStreamOperator<Integer> map = iterate.map(new MapFunction<Integer, Integer>() {
            @Override
            public Integer map(Integer integer) throws Exception {
                if (integer > 5)
                    integer -= 1;
                else if (integer < 5)
                    integer += 1;

                return integer;
            }
        }).map(new MapFunction<Integer, Integer>() {
            @Override
            public Integer map(Integer integer) throws Exception {
                System.out.println("hehe");
                return integer;
            }
        });

        SingleOutputStreamOperator<Integer> facebody = map.filter(new FilterFunction<Integer>() {
            @Override
            public boolean filter(Integer integer) throws Exception {
                //不是5的回去循环
                return integer != 5;
            }
        });

        DataStream<Integer> integerDataStream = iterate.closeWith(facebody);

        SingleOutputStreamOperator<Integer> outdata = map.filter(new FilterFunction<Integer>() {
            @Override
            public boolean filter(Integer integer) throws Exception {
                return integer == 5;
            }
        });
        integerDataStream.print("Interge:");
        outdata.print("Out");

        env.execute();
    }
}
