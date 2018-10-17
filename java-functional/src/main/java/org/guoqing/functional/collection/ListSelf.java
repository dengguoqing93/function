package org.guoqing.functional.collection;

import org.guoqing.functional.recursion.TailCall;

import java.util.function.Function;

/**
 * 单链表
 *
 * @author dengguoqing
 * @date 2018-10-10
 */
public abstract class ListSelf<T> {
    @SuppressWarnings("rawtypes")
    public static final ListSelf NIL = new Nil();

    private ListSelf() {
    }

    @SuppressWarnings("unchecked")
    public static <T> ListSelf<T> list() {
        return NIL;
    }

    @SafeVarargs
    public static <T> ListSelf<T> list(T... ts) {
        ListSelf<T> n = list();
        for (int i = ts.length - 1; i >= 0; i--) {
            n = new Cons<>(ts[i], n);
        }
        return n;
    }

    public static <T> ListSelf<T> setHead(ListSelf<T> listSelf, T t) {
        return listSelf.setHead(t);
    }

    /**
     * 获取列表头
     *
     * @return T
     */
    public abstract T head();

    /**
     * 获取链表尾部
     *
     * @return listSelf
     */
    public abstract ListSelf<T> tail();

    /**
     * 链表是否为空
     *
     * @return boolean
     */
    public abstract boolean isEmpty();

    /**
     * 在列表的开头添加一个元素
     *
     * @param t 新添加的元素
     * @return listSelf
     */
    public abstract ListSelf<T> cons(T t);

    /**
     * 用新元素替换原列表的第一个元素
     *
     * @param t 用来替换的元素
     * @return listSelf
     */
    public abstract ListSelf<T> setHead(T t);

    /**
     * 从列表中删除前n个元素
     *
     * @param n 删除元素个数
     * @return listSelf
     */
    public abstract ListSelf<T> drop(int n);

    /**
     * 当条件为真时，删除列表的第一个元素
     *
     * @param f 条件
     * @return listSelf
     */
    public abstract ListSelf<T> dropWhile(Function<T, Boolean> f);

    /**
     * 删除列表的最后一个元素
     *
     * @return ListSelf
     */
    public abstract ListSelf<T> init();

    /**
     * 列表反转
     *
     * @return ListSelf
     */
    public abstract ListSelf<T> reverse();

    /**
     * 尾递归调用
     *
     * @param identify
     * @param f
     * @param <U>
     * @return
     */
    public abstract <U> U foldLeft(U identify, Function<U, Function<T, U>> f);

    private static class Nil<T> extends ListSelf<T> {
        private Nil() {
        }

        @Override
        public T head() {
            throw new IllegalStateException("head called en empty list");
        }

        @Override
        public ListSelf<T> tail() {
            throw new IllegalStateException("tail called en empty list");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public ListSelf<T> cons(T t) {
            return new Cons<>(t, this);
        }

        @Override
        public ListSelf<T> setHead(T t) {
            throw new IllegalStateException("setHead called on empty list");
        }

        @Override
        public ListSelf<T> drop(int n) {
            return this;
        }

        @Override
        public ListSelf<T> dropWhile(Function<T, Boolean> f) {
            return this;
        }

        @Override
        public ListSelf<T> init() {
            throw new IllegalStateException("init called on empty list");
        }

        @Override
        public <U> U foldLeft(U identify, Function<U, Function<T, U>> f) {
            return null;
        }

        @Override
        public ListSelf<T> reverse() {
            return this;
        }

        @Override
        public String toString() {
            return "[NIL]";
        }
    }

    private static class Cons<T> extends ListSelf<T> {
        private final T head;
        private final ListSelf<T> tail;

        private Cons(T head, ListSelf<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public ListSelf<T> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ListSelf<T> cons(T t) {
            return new Cons<>(t, this);
        }

        @Override
        public ListSelf<T> setHead(T t) {
            return new Cons<>(t, tail());
        }

        @Override
        public ListSelf<T> drop(int n) {
            return n <= 0 ? this : drop_(this, n).eval();
        }

        private TailCall<ListSelf<T>> drop_(ListSelf<T> listSelf, int n) {
            return n <= 0 || listSelf.isEmpty() ? TailCall.ret(listSelf) :
                    TailCall.sus(() -> drop_(listSelf.tail(), n - 1));
        }

        @Override
        public ListSelf<T> dropWhile(Function<T, Boolean> f) {
            return dropWhile_(this, f).eval();
        }

        private TailCall<ListSelf<T>> dropWhile_(ListSelf<T> list,
                                                 Function<T, Boolean> f) {
            return !list.isEmpty() && f.apply(list.head()) ?
                    TailCall.sus(() -> dropWhile_(list.tail(), f)) :
                    TailCall.ret(this);
        }

        @Override
        public ListSelf<T> init() {
            return reverse().tail().reverse();
        }

        @Override
        public ListSelf<T> reverse() {
            return reverse_(list(), this).eval();
        }

        private TailCall<ListSelf<T>> reverse_(ListSelf<T> acc,
                                               ListSelf<T> listSelf) {
            return listSelf.isEmpty() ? TailCall.ret(acc) : TailCall.sus(
                    () -> reverse_(new Cons<>(listSelf.head(), acc),
                            listSelf.tail()));
        }

        @Override
        public <U> U foldLeft(U identify, Function<U, Function<T, U>> f) {
            return foldLeft_(identify, this, f);
        }

        private <U> U foldLeft_(U acc, ListSelf<T> listSelf,
                                Function<U, Function<T, U>> f) {
            return listSelf.isEmpty() ? acc :
                    foldLeft_(f.apply(acc).apply(listSelf.head()), listSelf.tail(),
                            f);
        }


        @Override
        public String toString() {
            return String
                    .format("[%sNIL]", toString(new StringBuilder(), this).eval());
        }

        private TailCall<StringBuilder> toString(StringBuilder acc,
                                                 ListSelf<T> listSelf) {
            return listSelf.isEmpty() ? TailCall.ret(acc) : TailCall.sus(
                    () -> toString(acc.append(listSelf.head()).append(", "),
                            listSelf.tail()));
        }
    }
}
