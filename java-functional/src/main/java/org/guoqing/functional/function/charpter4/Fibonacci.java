package org.guoqing.functional.function.charpter4;

import org.guoqing.functional.recursion.TailCall;

import java.math.BigInteger;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-10-09
 */
public class Fibonacci {
    private static TailCall<BigInteger> fib_(BigInteger acc1, BigInteger acc2,
                                             BigInteger x) {
        if (x.equals(BigInteger.ZERO)) {
            return TailCall.ret(BigInteger.ZERO);
        } else if (x.equals(BigInteger.ONE)) {
            return TailCall.ret(acc1.add(acc2));
        } else {
            return TailCall.sus(() -> fib_(acc2, acc1.add(acc2),
                    x.subtract(BigInteger.ONE)));
        }
    }

    public static BigInteger fib(int x) {
        return fib_(BigInteger.ONE, BigInteger.ZERO, BigInteger.valueOf(x)).eval();
    }
}
