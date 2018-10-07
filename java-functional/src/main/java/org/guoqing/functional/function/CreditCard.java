package org.guoqing.functional.function;

import java.util.function.Function;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-23
 */
public class CreditCard {
    public static void main(String[] args) {
        Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>>
                compose = x -> y -> z -> x.apply(y.apply(z));
        Function<Integer, Integer> triple = x -> x * 3;
        Function<Integer, Integer> square = x -> x * x;
        Function<Integer,Integer> function =compose.apply(square).apply(triple);
        System.out.println(function.apply(4));
    }
}
