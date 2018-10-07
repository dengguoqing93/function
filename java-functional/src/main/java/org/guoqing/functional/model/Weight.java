package org.guoqing.functional.model;

import java.util.function.Function;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-29
 */
public class Weight {

    public final static Weight ZERO = new Weight(0.0);
    public static Function<Weight, Function<OrderLine, Weight>> sum =
            x -> y -> x.add(y.getWeight());

    public final double value;


    private Weight(double value) {
        this.value = value;
    }

    public static Weight weight(double value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0");
        } else {
            return new Weight(value);
        }
    }

    public Weight add(Weight that) {
        return new Weight(this.value + that.value);
    }

    public Weight mult(int amount) {
        return new Weight(amount * this.value);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
