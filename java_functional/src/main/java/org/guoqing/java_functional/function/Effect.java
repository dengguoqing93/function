package org.guoqing.java_functional.function;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-25
 */
public interface Effect<T> {
    void apply(T t);
}
