package org.guoqing.java_functional.function;

import java.util.function.Supplier;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-25
 */
public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result<T>>> {

    private Case(Supplier<Boolean> booleanSupplier,
                 Supplier<Result<T>> resultSupplier) {
        super(booleanSupplier, resultSupplier);

    }

    public static <T> Case<T> matchCase(Supplier<Boolean> condition,
                                        Supplier<Result<T>> value) {

        return new Case<>(condition, value);
    }

    public static <T> DefaultCase<T> matchCase(Supplier<Result<T>> value) {
        return new DefaultCase<>(() -> true, value);
    }

    public static <T> Result<T> match(DefaultCase<T> defaultCase,
                                      Case<T>... matchers) {
        for (Case<T> matcher : matchers) {
            if (matcher._1.get()) {
                return matcher._2.get();
            }
        }
        return defaultCase._2.get();
    }

    private static class DefaultCase<T> extends Case<T> {
        private DefaultCase(Supplier<Boolean> booleanSupplier,
                            Supplier<Result<T>> resultSupplier) {
            super(booleanSupplier, resultSupplier);

        }
    }
}
