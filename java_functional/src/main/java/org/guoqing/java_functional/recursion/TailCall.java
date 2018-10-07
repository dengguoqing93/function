package org.guoqing.java_functional.recursion;

import java.util.function.Supplier;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-30
 */
public abstract class TailCall<T> {

    public abstract TailCall<T> resume();

    public abstract T eval();

    public abstract boolean isSuspend();

    public static class Return<T> extends TailCall<T> {
        private final T t;

        public Return(T t) {
            this.t = t;
        }

        @Override
        public TailCall<T> resume() {
            return null;
        }

        @Override
        public T eval() {
            return null;
        }

        @Override
        public boolean isSuspend() {
            return false;
        }
    }

    public static class Suspend<T> extends TailCall<T> {
        private final Supplier<TailCall<T>> resume;

        public Suspend(Supplier<TailCall<T>> resume) {
            this.resume = resume;
        }

        @Override
        public TailCall<T> resume() {
            return resume.get();
        }

        @Override
        public T eval() {
            throw new IllegalStateException("Suspend has no value");
        }

        @Override
        public boolean isSuspend() {
            return true;
        }
    }

    public static TailCall<Integer> add(int x,int y){
        return y == 0 ? new TailCall.Return<>(x) :
                new TailCall.Suspend<>(() -> add(x + 1, y - 1));
    }
}
