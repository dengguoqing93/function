package org.guoqing.java_functional.function;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-25
 */
public class Tuple<T,U> {
    public final T _1;
    public final U _2;

    public Tuple(T _1, U _2) {
        this._1 = _1;
        this._2 = _2;
    }
}
