package org.guoqing.functional.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 集合工具类
 *
 * @author dengguoqing
 * @date 2018-09-25
 */
public class CollectionUtilities<T, U> {
    private static Effect<Double> printWith2Decimals = x -> {
        System.out.printf("%.2f", x);
        System.out.println();
    };
    private static Function<Executable, Function<Executable, Executable>> compose =
            x -> y -> () -> {
                x.exec();
                y.exec();
            };
    private static Executable ez = () -> {
    };
    private static Function<Double, Double> addTax = x -> x * 1.09;
    private static Function<Double, Double> addShipping = x -> x + 3.50;
    private static List<Double> price =
            CollectionUtilities.list(10.10, 23.45, 32.07, 9.23);
    private static List<Double> priceIncludingTax = map(price, addTax);
    private static List<Double> pricesIncludingShipping =
            map(priceIncludingTax, addShipping);
    private static Executable program = foldLeft(pricesIncludingShipping, ez,
            e -> d -> compose.apply(e).apply(() -> printWith2Decimals.apply(d)));

    /**
     * 根据函数，将类型为T的列表按照f映射为类型为U的列表
     *
     * @param list T类型的列表
     * @param f    映射函数
     * @param <T>  原始类型
     * @param <U>  结果类型
     * @return list
     */
    public static <T, U> List<U> map(List<T> list, Function<T, U> f) {
        List<U> result = new ArrayList<>();
        for (T t : list) {
            result.add(f.apply(t));
        }
        return result;
    }

    /**
     * 创建空列表
     *
     * @param <T> T
     * @return list
     */
    public static <T> List<T> list() {
        return Collections.emptyList();
    }

    /**
     * 创建包含一个元素的列表
     *
     * @param t   包含的元素
     * @param <T> T
     * @return list
     */
    public static <T> List<T> list(T t) {
        return Collections.singletonList(t);
    }

    /**
     * 创建一个元素列表的副本
     *
     * @param list list
     * @param <T>  类型
     * @return list
     */
    public static <T> List<T> list(List<T> list) {
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

    /**
     * 创建一个由变长数组组成的列表
     *
     * @param ts  变长数组
     * @param <T> 类型
     * @return list
     */
    @SafeVarargs
    public static <T> List<T> list(T... ts) {
        return Collections
                .unmodifiableList(Arrays.asList(Arrays.copyOf(ts, ts.length)));
    }

    /**
     * 拷贝列表并返回
     *
     * @param ts  列表
     * @param <T> T
     * @return list
     */
    public static <T> List<T> copy(List<T> ts) {
        return new ArrayList<>(ts);
    }

    /**
     * 返回一个列表的第一个元素
     *
     * @param ts  列表
     * @param <T> 列表元素类型
     * @return T
     */
    public static <T> T head(List<T> ts) {
        if (ts.size() == 0) {
            throw new IllegalStateException("head of empty list");
        }
        return ts.get(0);
    }

    /**
     * 返回一个列表的尾部，除第一个元素外的所有元素
     *
     * @param ts  ts
     * @param <T> T
     * @return list
     */
    public static <T> List<T> tail(List<T> ts) {
        if (ts.size() == 0) {
            throw new IllegalStateException("tail of empty list");
        }
        List<T> result = copy(ts);
        result.remove(0);
        return Collections.unmodifiableList(result);
    }

    /**
     * 向一个列表添加一个元素
     *
     * @param ts  源列表
     * @param t   待添加的元素
     * @param <T> 元素类型
     * @return 添加元素后的列表
     */
    public static <T> List<T> append(List<T> ts, T t) {
        List<T> result = copy(ts);
        result.add(t);
        return Collections.unmodifiableList(result);
    }

    /**
     * 折叠函数，将T类型的列表生成结果U
     *
     * @param ts       列表
     * @param u        结果类型的初始值
     * @param function 转化函数
     * @param <T>      源类型
     * @param <U>      结果类型
     * @return U
     */
    public static <T, U> U foldLeft(List<T> ts, U u,
                                    Function<U, Function<T, U>> function) {
        U result = u;
        for (T t : ts) {
            result = function.apply(result).apply(t);
        }
        return result;
    }

    /**
     * 右折叠方式进行折叠列表
     *
     * @param ts       源列表
     * @param u        折叠结果初始化
     * @param function 折叠函数
     * @param <T>      T
     * @param <U>      U
     * @return U
     */
    public static <T, U> U foldRight(List<T> ts, U u,
                                     Function<T, Function<U, U>> function) {
        //非递归实现
        U result = u;
        for (int i = ts.size() - 1; i >= 0; i--) {
            result = function.apply(ts.get(i)).apply(result);
        }
        //递归实现
        return ts.isEmpty() ? u :
                function.apply(head(ts)).apply(foldRight(tail(ts), u, function));
    }

    public static <T> List<T> prepend(T t, List<T> list) {
        return foldLeft(list, list(t), a -> b -> append(a, b));
    }

    /**
     * 使用左折叠的方式实现列表的反转
     *
     * @param list 反转列表
     * @param <T> 类型
     * @return list
     */
    public static <T> List<T> reserve(List<T> list) {
        return foldLeft(list, list(),
                x -> y -> foldLeft(x, list(y), a -> b -> append(a, b)));
    }

    /**
     * 映射的左折叠实现
     * @param list 源列表
     * @param f 映射函数
     * @param <T> 源类型
     * @param <U> 目标类型
     * @return list
     */
    public static <T, U> List<U> mapViaFoldLeft(List<T> list, Function<T, U> f) {
        return foldLeft(list, list(), x -> y -> append(x, f.apply(y)));
    }

    /**
     * 映射的右折叠实现
     * @param list 源列表
     * @param f 映射函数
     * @param <T> 源类型
     * @param <U> 目标类型
     * @return list
     */
    public static <T, U> List<U> mapViaFoldRight(List<T> list, Function<T, U> f) {
        return foldRight(list, list(), x -> y -> prepend(f.apply(x), y));
    }

    /**
     * 反递归生成一个函数
     * @param seed 种子
     * @param f 循环函数
     * @param p 循环条件
     * @param <T>  类型
     * @return list
     */
    public static <T> List<T> unfold(T seed,Function<T,T> f,Function<T,Boolean> p){
        List<T> list = new ArrayList<>();
        T temp = seed;
        while (p.apply(temp)){
            list = append(list,temp);
            temp = f.apply(temp);
        }
        return list;
    }

    /**
     * 生成start和end之间的整数列表
     * @param start 开始整数
     * @param end 结束整数
     * @return list
     */
    public static List<Integer> range(int start,int end){
        return unfold(start, x -> x + 1, x -> x < end);
    }

    public static void main(String[] args) {
        program.exec();
    }

}
