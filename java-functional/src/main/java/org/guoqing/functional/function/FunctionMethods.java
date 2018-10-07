package org.guoqing.functional.function;

import java.util.List;

/**
 * java函数式编程
 *
 * @author dengguoqing
 * @date 2018-09-23
 */
public class FunctionMethods {
    public int percent1= 5;
    public int percent2 =9;
    public final int percent3 = 13;
    public int add(int a,int b){
        return a+b;
    }

    public void setPercent2(int value){
        percent2 = value;
    }

    public int mult(int a,int b){
        a=5;
        b=2;
        return a*b;
    }
    public int div(int a,int b){
        return a/b;
    }

    public int applyTax1(int a){
        return a/100*(100+percent1);
    }

    public int applyTax2(int a){
        return a / 100 * (100 + percent2);
    }

    public int applyTax3(int a){
        return a / 100 * (100 + percent3);
    }

    public List<Integer> append(int i,List<Integer> list){
        list.add(i);
        return list;
    }
}
