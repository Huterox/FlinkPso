[TOC]
# FlinkPso
使用Flink编写的PSO算法模型工具

# 具体使用

## 自定义你的目标函数

我们开放了一个接口用于自定义目标函数，以便针对不同的优化场景。

关于使用你只需要实现一个接口，此时在接口内定义你的函数
下面是一个示例
```java

    static class TotalFunction implements FunctionsImpl{

        @Override
        public Double FourFunction(ArrayList<Double> parames) {
            return null;
        }
    }
    
```    
## 设置目标函数
在这一步你只需要将你的函数导入运算系统即可。
```java
FunctionMake.SetFunction(new TotalFunction());
```
之后你还需要做的是对你的函数的相关参数进行设置，例如你的目标函数的维度，迭代次数等等。
值得一提的是，你必须设置你的变量个数，也就是声明你的目标函数的维度。

## 运行
运行非常简单，你只需要调用 `DoSteam.RunCore();` 即可。之后就是静静地等待结果。
## 具体示例

具体的例子请参考如下：

```java
package com.java.PSO.RunPso;

import com.java.PSO.ConfigPso.ConfigPso;
import com.java.PSO.Function.FunctionImp.FunctionsImpl;
import com.java.PSO.StreamPso.DoSteam;

import java.util.ArrayList;

public class RunPso {
    public static void main(String[] args) throws Exception {
        //调用Flink 方式
        //1.实现FunctionImpl接口，该接口的为目标函数，不实现默认使用案例。
        FunctionMake.SetFunction(new TotalFunction());
        //2.调用配置类进行相关参数设置,不设置默认使用默认配置，但是重点需要设置参数个数
        ConfigPso.ParamesNumber = 3;
        //3.运行核心
        DoSteam.RunCore();


    }

    static class TotalFunction implements FunctionsImpl{

        @Override
        public Double FourFunction(ArrayList<Double> parames) {
            do something you want
            return null;
        }
    }
}
```
之后结果将输出如下类型的参数
> The best bird:2> Bird(id=9, Pbest=[-2.696461290214497E-7, 1.888839707431761E-7, -3.292871075358361E-7], Gbest=[2.8232826557437633E-8, 1.3849311677496851E-8, -2.006572561901652E-8], Functionresult=2.193299565877222E-18, LFunctionresult=2.1681618848927858E-13, Xpostion=[-3.3993504062773074E-7, 2.279120524951932E-7, -4.1071585970869873E-7], Vpersent=[-7.028891160628103E-8, 3.9028081752017106E-8, -8.142875217286262E-8], InterTimes=100)



其中 Functionresult=2.193299565877222E-18 表示最佳结果，Xpostion=[-3.3993504062773074E-7, 2.279120524951932E-7, -4.1071585970869873E-7] 表示最佳位置。


# 特别注意
该模型内自带了一个例子，如果直接运行模型的话会自动调用自己默认的示例。
此外该模型的数据精度较高，所以总体运算较慢。
该模型默认优化目标函数的最小值。

# 说明
2021 11 2 
Author：Huterox
详细源码解释：https://blog.csdn.net/FUTEROX/article/details/121090727

# 更新
V0.52 重新更改了循环过程当中的全局最优算子，减少了不必要的比较，提高了运行效率。
    --StreamPso
        --DoStream
            --MinMapsGinterate(Update)


