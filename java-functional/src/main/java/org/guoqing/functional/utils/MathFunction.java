package org.guoqing.functional.utils;

import org.guoqing.functional.collection.ListSelf;

import java.util.function.Function;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-10-16
 */
public class MathFunction {
    public static <A extends Comparable<A>> Function<ListSelf<A>, Option<A>> max() {
        return xs -> xs.isEmpty() ? Option.none() : Option.some(
                xs.foldLeft(xs.head(), x -> y -> x.compareTo(y) > 0 ? x : y));
    }
}
