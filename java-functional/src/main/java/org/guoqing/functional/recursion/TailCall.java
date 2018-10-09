package org.guoqing.functional.recursion;

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
            throw new IllegalStateException("Return has no resume");
        }

        @Override
        public T eval() {
            return t;
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
            TailCall<T> tailRec = this;
            while (tailRec.isSuspend()){
                tailRec.resume();
            }
            return tailRec.eval();
        }

        @Override
        public boolean isSuspend() {
            return true;
        }


    }

    public static <T> Return<T> ret(T t){
        return new Return<>(t);
    }

    public static <T> Suspend<T> sus(Supplier<TailCall<T>> s){
        return new Suspend<>(s);
    }

    public static int add(int x,int y){
        return addRec(x,y).eval();
    }

    private static TailCall<Integer> addRec(int x,int y){
        return y == 0 ? ret(x) : sus(() -> addRec(x + 1, y - 1));
    }
}
