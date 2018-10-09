package org.guoqing.functional.function.charpter4;

import org.guoqing.functional.recursion.TailCall;

import java.util.List;
import java.util.function.Function;

import static org.guoqing.functional.function.CollectionUtilities.*;

/**
 * 栈安全的列表方法的递归
 *
 * @author dengguoqing
 * @date 2018-10-09
 */
public class CollectionsWithStackSafe {
    private CollectionsWithStackSafe() {
    }

    public static <T, U> U foldLeft(List<T> ts, U identity,
                                    Function<U, Function<T, U>> f) {
        return foldLeft_(ts,identity,f).eval();
    }

    private static <T, U> TailCall<U> foldLeft_(List<T> ts, U identity,
                                                Function<U, Function<T, U>> f) {
        return ts.isEmpty() ? TailCall.ret(identity) : TailCall.sus(
                () -> foldLeft_(tail(ts), f.apply(identity).apply(head(ts)), f));
    }

    public static List<Integer> range(Integer start,Integer end){
        return range_(list(),start,end).eval();
    }

    private static TailCall<List<Integer>> range_(List<Integer> list, Integer start,
                                                  Integer end) {
        return end <= start ? TailCall.ret(list) :
                TailCall.sus(() -> range_(append(list, start), start + 1, end));
    }
}
