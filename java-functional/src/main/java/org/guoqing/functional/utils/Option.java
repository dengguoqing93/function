package org.guoqing.functional.utils;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-10-16
 */
public abstract class Option<T> {

    private static Option none = new None();

    public static <T> Option<T> some(T t) {
        return new Some<>(t);
    }

    public static <T> Option<T> none() {
        return none;
    }

    public <B> Option<B> flatMap(Function<T, Option<B>> f) {
        return map(f).getOrElse(Option::none);
    }

    public Option<T> orElse(Supplier<Option<T>> defaultValue) {
        return map(x -> this).getOrElse(defaultValue);
    }

    public Option<T> filter(Function<T, Boolean> f) {
        return flatMap(x -> f.apply(x) ? this : null);
    }

    /**
     * 从option中检索值
     *
     * @return
     */
    protected abstract T getOrThrow();

    /**
     * 获取默认值
     *
     * @param defaultValue
     * @return
     */
    public abstract T getOrElse(Supplier<T> defaultValue);

    /**
     * 类型转化
     *
     * @param f   转换函数
     * @param <B> 转换后的类型
     * @return option
     */
    public abstract <B> Option<B> map(Function<T, B> f);

    private static class None<T> extends Option<T> {
        private None() {
        }

        @Override
        protected T getOrThrow() {
            throw new IllegalStateException("get called on none");
        }

        @Override
        public T getOrElse(Supplier<T> defaultValue) {
            return defaultValue.get();
        }

        @Override
        public <B> Option<B> map(Function<T, B> f) {
            return none();
        }

        @Override
        public String toString() {
            return "None";
        }
    }

    private static class Some<T> extends Option<T> {
        private final T value;

        private Some(T value) {
            this.value = value;
        }

        @Override
        protected T getOrThrow() {
            return this.value;
        }

        @Override
        public T getOrElse(Supplier defaultValue) {
            return this.value;
        }

        @Override
        public <B> Option<B> map(Function<T, B> f) {
            return new Some<>(f.apply(this.value));
        }

        @Override
        public String toString() {
            return String.format("Some(%s)", this.value);
        }
    }
}
